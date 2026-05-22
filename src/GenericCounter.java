import java.util.*;

// Our own functional interface — a "property tester"
// (Java 8+ has java.util.function.Predicate<T> built-in, but let's define ours)
interface Predicate<T> {
    boolean test(T element);
}

public class GenericCounter {

    // Generic method: count elements in a collection matching a predicate
    // <T> — type parameter scoped to this method
    // Collection<T> — works with List, Set, Queue, etc.
    // Predicate<T> — the property to test
    public static <T> int countIf(Collection<T> collection, Predicate<T> predicate) {
        int count = 0;
        for (T element : collection) {
            if (predicate.test(element)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Property 1: Odd integers
        int oddCount = countIf(numbers, n -> n % 2 != 0);

        System.out.println("Odd numbers: " + oddCount); // 5

        // Property 2: Prime numbers
        int primeCount = countIf(numbers, GenericCounter::isPrime);
        System.out.println("Prime numbers: " + primeCount); // 4 (2,3,5,7)

        // Property 3: Palindromes (works with strings!)
        List<String> words = Arrays.asList("racecar", "hello", "level", "world", "madam");
        int palindromeCount = countIf(words, GenericCounter::isPalindrome);
        System.out.println("Palindromes: " + palindromeCount); // 3
    }

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private static boolean isPalindrome(String s) {
        String reversed = new StringBuilder(s).reverse().toString();
        return s.equals(reversed);
    }
}