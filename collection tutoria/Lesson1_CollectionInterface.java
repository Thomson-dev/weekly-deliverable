import java.util.*;
import java.util.stream.*;

/**
 * LESSON 1: The Collection Interface
 *
 * A Collection represents a group of objects known as its elements.
 * It is the root interface in the Java Collections Framework hierarchy.
 *
 * Key methods in Collection<E>:
 *   - int size()
 *   - boolean isEmpty()
 *   - boolean contains(Object element)
 *   - boolean add(E element)
 *   - boolean remove(Object element)
 *   - Iterator<E> iterator()
 *   - boolean containsAll(Collection<?> c)
 *   - boolean addAll(Collection<? extends E> c)
 *   - boolean removeAll(Collection<?> c)
 *   - boolean retainAll(Collection<?> c)
 *   - void clear()
 *   - Object[] toArray()
 *   - Stream<E> stream()          (JDK 8+)
 *   - Stream<E> parallelStream()  (JDK 8+)
 */
public class Lesson1_CollectionInterface {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. CONVERSION CONSTRUCTOR
        // Converting any Collection to an ArrayList using the copy constructor
        // ----------------------------------------------------------------
        Collection<String> c = new HashSet<>(Arrays.asList("Apple", "Banana", "Cherry"));

        List<String> list = new ArrayList<>(c);  // conversion constructor
        System.out.println("Converted list: " + list);


        // ----------------------------------------------------------------
        // 2. BASIC OPERATIONS
        // ----------------------------------------------------------------
        System.out.println("\n--- Basic Operations ---");
        System.out.println("Size: " + list.size());
        System.out.println("Is empty: " + list.isEmpty());
        System.out.println("Contains 'Banana': " + list.contains("Banana"));

        list.add("Durian");
        System.out.println("After add: " + list);

        list.remove("Banana");
        System.out.println("After remove: " + list);


        // ----------------------------------------------------------------
        // 3. TRAVERSING — Method 1: Aggregate Operations (Stream API, JDK 8+)
        // ----------------------------------------------------------------
        System.out.println("\n--- Aggregate Operations (Stream) ---");

        // Print elements that start with 'C'
        list.stream()
            .filter(e -> e.startsWith("C"))
            .forEach(e -> System.out.println("Starts with C: " + e));

        // Join all elements with a comma
        String joined = list.stream()
            .map(Object::toString)
            .collect(Collectors.joining(", "));
        System.out.println("Joined: " + joined);


        // ----------------------------------------------------------------
        // 4. TRAVERSING — Method 2: for-each Construct
        // ----------------------------------------------------------------
        System.out.println("\n--- for-each Construct ---");
        for (Object o : list) {
            System.out.println(o);
        }


        // ----------------------------------------------------------------
        // 5. TRAVERSING — Method 3: Iterator
        // Use Iterator when you need to safely REMOVE elements during traversal
        // ----------------------------------------------------------------
        System.out.println("\n--- Iterator (removing elements with 'a' in name) ---");
        List<String> mutableList = new ArrayList<>(Arrays.asList("Apple", "Cherry", "Durian", "Avocado"));
        for (Iterator<String> it = mutableList.iterator(); it.hasNext(); ) {
            String element = it.next();
            if (element.contains("a") || element.contains("A")) {
                it.remove();  // safe removal during iteration
            }
        }
        System.out.println("After iterator removal: " + mutableList);


        // ----------------------------------------------------------------
        // 6. BULK OPERATIONS
        // ----------------------------------------------------------------
        System.out.println("\n--- Bulk Operations ---");
        List<String> base   = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        List<String> other  = new ArrayList<>(Arrays.asList("B", "D", "E"));

        // containsAll
        System.out.println("base.containsAll(other): " + base.containsAll(other));

        // addAll
        base.addAll(other);
        System.out.println("After addAll: " + base);

        // removeAll — removes elements that exist in 'other'
        base.removeAll(other);
        System.out.println("After removeAll: " + base);

        // retainAll — keeps only elements that exist in 'other'
        List<String> retainTest = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
        retainTest.retainAll(other);
        System.out.println("After retainAll with other: " + retainTest);

        // Removing all instances of a specific element using singleton
        List<String> withNulls = new ArrayList<>(Arrays.asList("X", null, "Y", null));
        withNulls.removeAll(Collections.singleton(null));
        System.out.println("After removing nulls: " + withNulls);

        // clear
        base.clear();
        System.out.println("After clear, isEmpty: " + base.isEmpty());


        // ----------------------------------------------------------------
        // 7. ARRAY OPERATIONS
        // ----------------------------------------------------------------
        System.out.println("\n--- Array Operations ---");
        Collection<String> fruits = Arrays.asList("Mango", "Grape", "Pear");

        // toArray() — returns Object[]
        Object[] objArray = fruits.toArray();
        System.out.println("Object array length: " + objArray.length);

        // toArray(T[]) — returns typed String[]
        String[] strArray = fruits.toArray(new String[0]);
        System.out.println("String array: " + Arrays.toString(strArray));
    }


    // ----------------------------------------------------------------
    // 8. POLYMORPHIC FILTER using Iterator (works on ANY Collection)
    // ----------------------------------------------------------------
    static void filter(Collection<?> c) {
        for (Iterator<?> it = c.iterator(); it.hasNext(); ) {
            if (it.next() == null) {
                it.remove();
            }
        }
    }
}
