import java.util.*;

/**
 * PRACTICE: Using the Iterator Interface
 *
 * Iterator gives you a cursor that walks through a collection one step at a time.
 * The only SAFE way to remove elements during traversal is through the iterator.
 *
 * public interface Iterator<E> {
 *     boolean hasNext();   // is there a next element?
 *     E       next();      // return next element and advance cursor
 *     void    remove();    // remove the element that next() just returned
 * }
 */
public class IteratorPractice {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // TASK 1: Remove a specific word from a list
        // ----------------------------------------------------------------
        System.out.println("=== Task 1: Remove a specific word ===");

        List<String> words = new ArrayList<>(
                Arrays.asList("apple", "banana", "cherry", "banana", "date", "banana"));

        System.out.println("Before: " + words);

        String wordToRemove = "banana";

        Iterator<String> it = words.iterator();
        while (it.hasNext()) {
            String current = it.next();           // step forward and grab the element
            if (current.equals(wordToRemove)) {
                it.remove();                      // safe removal — no ConcurrentModificationException
            }
        }

        System.out.println("After removing \"" + wordToRemove + "\": " + words);


        // ----------------------------------------------------------------
        // TASK 2: Remove all words that start with a specific character
        // ----------------------------------------------------------------
        System.out.println("\n=== Task 2: Remove words starting with 'c' ===");

        List<String> names = new ArrayList<>(
                Arrays.asList("Carl", "Alice", "Charlie", "Bob", "Catherine", "Diana"));

        System.out.println("Before: " + names);

        Iterator<String> it2 = names.iterator();
        while (it2.hasNext()) {
            String name = it2.next();
            if (name.toLowerCase().startsWith("c")) {   // check first character
                it2.remove();
            }
        }

        System.out.println("After removing names starting with 'c': " + names);


        // ----------------------------------------------------------------
        // TASK 3: Remove all words shorter than a given length
        // ----------------------------------------------------------------
        System.out.println("\n=== Task 3: Remove words shorter than 4 characters ===");

        List<String> mixed = new ArrayList<>(
                Arrays.asList("hi", "hello", "ok", "world", "no", "java", "go"));

        System.out.println("Before: " + mixed);

        int minLength = 4;
        Iterator<String> it3 = mixed.iterator();
        while (it3.hasNext()) {
            if (it3.next().length() < minLength) {
                it3.remove();
            }
        }

        System.out.println("After removing words shorter than " + minLength + ": " + mixed);


        // ----------------------------------------------------------------
        // TASK 4: Remove numbers that match a condition (even numbers)
        // ----------------------------------------------------------------
        System.out.println("\n=== Task 4: Remove even numbers from a list ===");

        List<Integer> numbers = new ArrayList<>(
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        System.out.println("Before: " + numbers);

        Iterator<Integer> it4 = numbers.iterator();
        while (it4.hasNext()) {
            int num = it4.next();
            if (num % 2 == 0) {       // if remainder when divided by 2 is 0 → even
                it4.remove();
            }
        }

        System.out.println("After removing even numbers: " + numbers);


        // ----------------------------------------------------------------
        // TASK 5: Remove null and empty strings
        // ----------------------------------------------------------------
        System.out.println("\n=== Task 5: Remove null and empty strings ===");

        List<String> dirty = new ArrayList<>(
                Arrays.asList("hello", null, "", "world", null, "  ", "java", ""));

        System.out.println("Before: " + dirty);

        Iterator<String> it5 = dirty.iterator();
        while (it5.hasNext()) {
            String val = it5.next();
            if (val == null || val.trim().isEmpty()) {  // null OR blank string
                it5.remove();
            }
        }

        System.out.println("After removing null/empty: " + dirty);


        // ----------------------------------------------------------------
        // TASK 6: Remove duplicates while preserving order
        //         (keep first occurrence, remove all later ones)
        // ----------------------------------------------------------------
        System.out.println("\n=== Task 6: Remove duplicates (keep first occurrence) ===");

        List<String> withDups = new ArrayList<>(
                Arrays.asList("dog", "cat", "bird", "cat", "dog", "fish", "bird"));

        System.out.println("Before: " + withDups);

        Set<String> seen = new HashSet<>();          // tracks what we've already visited
        Iterator<String> it6 = withDups.iterator();
        while (it6.hasNext()) {
            String item = it6.next();
            if (!seen.add(item)) {                  // add() returns false if already present
                it6.remove();                       // it's a duplicate → remove it
            }
        }

        System.out.println("After removing duplicates: " + withDups);


        // ----------------------------------------------------------------
        // TASK 7: Collect removed items into a separate list
        //         (split one list into two: kept and removed)
        // ----------------------------------------------------------------
        System.out.println("\n=== Task 7: Split list into 'kept' and 'removed' ===");

        List<String> original = new ArrayList<>(
                Arrays.asList("error: disk full", "info: started", "error: timeout",
                        "info: connected", "error: null pointer"));

        List<String> errors   = new ArrayList<>();
        List<String> infos    = new ArrayList<>();

        Iterator<String> it7 = original.iterator();
        while (it7.hasNext()) {
            String line = it7.next();
            if (line.startsWith("error")) {
                errors.add(line);
                it7.remove();           // move error lines out of original
            } else {
                infos.add(line);
            }
        }

        System.out.println("Errors:  " + errors);
        System.out.println("Infos:   " + infos);
        System.out.println("Original after split: " + original); // empty — all moved out


        // ----------------------------------------------------------------
        // WHY YOU CANNOT USE A REGULAR FOR-EACH LOOP TO REMOVE
        // The code below would throw ConcurrentModificationException:
        //
        //   for (String s : words) {
        //       if (s.equals("banana"))
        //           words.remove(s);   // ← ILLEGAL during for-each!
        //   }
        //
        // The for-each hides the iterator internally and the list detects
        // that something outside the iterator modified its structure.
        // Iterator.remove() is the ONLY safe way to remove during traversal.
        // ----------------------------------------------------------------
        System.out.println("\n=== Reminder: why Iterator.remove() matters ===");
        System.out.println("list.remove() inside a for-each throws ConcurrentModificationException.");
        System.out.println("it.remove()   inside a while(it.hasNext()) is always safe.");
    }
}

