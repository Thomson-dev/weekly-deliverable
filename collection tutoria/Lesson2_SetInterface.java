import java.util.*;
import java.util.stream.*;

/**
 * LESSON 2: The Set Interface
 *
 * A Set is a Collection that CANNOT contain duplicate elements.
 * It models the mathematical set abstraction.
 *
 * Three general-purpose Set implementations:
 *
 *  1. HashSet        — backed by a hash table
 *                      FASTEST performance, NO guaranteed order
 *
 *  2. TreeSet        — backed by a red-black tree
 *                      Elements sorted by NATURAL ORDER (or Comparator)
 *                      Slower than HashSet
 *
 *  3. LinkedHashSet  — hash table + linked list
 *                      Maintains INSERTION ORDER
 *                      Slightly slower than HashSet, much faster than TreeSet
 *
 * Rule of thumb:
 *   Need speed?          → HashSet
 *   Need sorted order?   → TreeSet
 *   Need insertion order?→ LinkedHashSet
 */
public class Lesson2_SetInterface {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. WHY Sets? — Eliminating Duplicates
        // ----------------------------------------------------------------
        System.out.println("=== 1. Eliminating Duplicates ===");

        List<String> withDups = Arrays.asList("apple", "banana", "apple", "cherry", "banana", "banana");
        System.out.println("Original list (with duplicates): " + withDups);

        // HashSet — fastest, unordered
        Collection<String> noDupsHash = new HashSet<>(withDups);
        System.out.println("HashSet (no order guarantee):   " + noDupsHash);

        // LinkedHashSet — preserves insertion order
        Collection<String> noDupsLinked = new LinkedHashSet<>(withDups);
        System.out.println("LinkedHashSet (insertion order): " + noDupsLinked);

        // TreeSet — sorted alphabetically
        Collection<String> noDupsTree = new TreeSet<>(withDups);
        System.out.println("TreeSet (sorted):                " + noDupsTree);


        // ----------------------------------------------------------------
        // 2. COLLECTING INTO A SET using Stream API (JDK 8+)
        // ----------------------------------------------------------------
        System.out.println("\n=== 2. Stream API → Set ===");

        // Collect into an unordered Set
        Set<String> streamSet = withDups.stream()
                .collect(Collectors.toSet());
        System.out.println("Collectors.toSet(): " + streamSet);

        // Collect into a specific Set type (TreeSet) — sorted result
        Set<String> sortedSet = withDups.stream()
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("Collectors.toCollection(TreeSet::new): " + sortedSet);


        // ----------------------------------------------------------------
        // 3. BASIC OPERATIONS
        // ----------------------------------------------------------------
        System.out.println("\n=== 3. Basic Operations ===");

        Set<String> s = new HashSet<>();
        System.out.println("add 'red':   " + s.add("red"));    // true  — was added
        System.out.println("add 'green': " + s.add("green"));  // true  — was added
        System.out.println("add 'red':   " + s.add("red"));    // false — already present, Set unchanged

        System.out.println("Set: " + s);
        System.out.println("size(): " + s.size());
        System.out.println("isEmpty(): " + s.isEmpty());
        System.out.println("contains('green'): " + s.contains("green"));

        System.out.println("remove('green'): " + s.remove("green")); // true — was present
        System.out.println("remove('blue'):  " + s.remove("blue"));  // false — was not present
        System.out.println("Set after removes: " + s);


        // ----------------------------------------------------------------
        // 4. FindDups — printing distinct words (two styles)
        // ----------------------------------------------------------------
        System.out.println("\n=== 4. FindDups Example ===");

        String[] words = {"i", "came", "i", "saw", "i", "left"};

        // Style A — Stream API
        Set<String> distinctStream = Arrays.asList(words).stream()
                .collect(Collectors.toSet());
        System.out.println("Distinct (HashSet, Stream):  " + distinctStream.size() + " words: " + distinctStream);

        // Style B — for-each + add
        Set<String> distinctForEach = new HashSet<>();
        for (String w : words) {
            distinctForEach.add(w);
        }
        System.out.println("Distinct (HashSet, for-each): " + distinctForEach.size() + " words: " + distinctForEach);

        // Style C — TreeSet gives alphabetical order
        Set<String> distinctSorted = new TreeSet<>(Arrays.asList(words));
        System.out.println("Distinct (TreeSet, sorted):   " + distinctSorted.size() + " words: " + distinctSorted);


        // ----------------------------------------------------------------
        // 5. BULK OPERATIONS — Set Algebra
        //
        //   Think of s1 and s2 as mathematical sets:
        //
        //   s1 = {A, B, C}
        //   s2 = {B, C, D}
        //
        //   Union        (s1 ∪ s2) = {A, B, C, D}  — everything in either
        //   Intersection (s1 ∩ s2) = {B, C}         — only what's in BOTH
        //   Difference   (s1 − s2) = {A}             — in s1 but NOT in s2
        // ----------------------------------------------------------------
        System.out.println("\n=== 5. Bulk Operations (Set Algebra) ===");

        Set<String> s1 = new HashSet<>(Arrays.asList("A", "B", "C"));
        Set<String> s2 = new HashSet<>(Arrays.asList("B", "C", "D"));
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);

        // containsAll → subset check: is s2 a subset of s1?
        System.out.println("s1.containsAll(s2) [s2 ⊆ s1?]: " + s1.containsAll(s2)); // false

        // UNION — copy s1 first so we don't destroy it
        Set<String> union = new HashSet<>(s1);
        union.addAll(s2);
        System.out.println("Union        (s1 ∪ s2): " + union);

        // INTERSECTION — copy s1 first
        Set<String> intersection = new HashSet<>(s1);
        intersection.retainAll(s2);
        System.out.println("Intersection (s1 ∩ s2): " + intersection);

        // DIFFERENCE — copy s1 first
        Set<String> difference = new HashSet<>(s1);
        difference.removeAll(s2);
        System.out.println("Difference   (s1 − s2): " + difference);


        // ----------------------------------------------------------------
        // 6. FindDups2 — separating unique words from duplicate words
        // ----------------------------------------------------------------
        System.out.println("\n=== 6. FindDups2 — Unique vs Duplicate Words ===");

        String[] input = {"i", "came", "i", "saw", "i", "left"};

        Set<String> uniques = new HashSet<>();
        Set<String> dups    = new HashSet<>();

        for (String word : input) {
            // add() returns false when the element was already in the set
            if (!uniques.add(word)) {
                dups.add(word);
            }
        }

        // Remove duplicates from uniques → set difference
        uniques.removeAll(dups);

        System.out.println("Unique words:    " + uniques);   // [left, saw, came]
        System.out.println("Duplicate words: " + dups);      // [i]


        // ----------------------------------------------------------------
        // 7. SYMMETRIC SET DIFFERENCE
        //    Elements in s1 OR s2 but NOT in both  →  (s1 ∪ s2) − (s1 ∩ s2)
        // ----------------------------------------------------------------
        System.out.println("\n=== 7. Symmetric Set Difference ===");

        Set<String> a = new HashSet<>(Arrays.asList("A", "B", "C"));
        Set<String> b = new HashSet<>(Arrays.asList("B", "C", "D"));

        Set<String> symmetricDiff = new HashSet<>(a);   // start with a copy of a
        symmetricDiff.addAll(b);                         // → union
        Set<String> tmp = new HashSet<>(a);
        tmp.retainAll(b);                                // → intersection
        symmetricDiff.removeAll(tmp);                    // union − intersection

        System.out.println("a              = " + a);
        System.out.println("b              = " + b);
        System.out.println("Symmetric diff = " + symmetricDiff); // [A, D]


        // ----------------------------------------------------------------
        // 8. GENERIC UTILITY METHOD — removeDups
        //    Returns a new Set preserving insertion order, with no duplicates
        // ----------------------------------------------------------------
        System.out.println("\n=== 8. Generic removeDups Utility ===");

        List<Integer> numbersWithDups = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3);
        Set<Integer> cleanNumbers = removeDups(numbersWithDups);
        System.out.println("Original:  " + numbersWithDups);
        System.out.println("No dups:   " + cleanNumbers);


        // ----------------------------------------------------------------
        // 9. PROGRAMMING BEST PRACTICE — Code to the Interface, not the class
        // ----------------------------------------------------------------
        System.out.println("\n=== 9. Code to the Interface ===");

        // GOOD — declared as Set<String>, easy to swap implementation
        Set<String> good = new HashSet<>();
        good.add("one");

        // To switch to sorted order, only the constructor changes:
        Set<String> alsoGood = new TreeSet<>();
        alsoGood.add("one");

        // BAD — declared as HashSet<String>, locks you into one implementation
        // HashSet<String> bad = new HashSet<>();  // avoid this style

        System.out.println("Always declare variables as the interface type (Set), not the implementation (HashSet).");
        System.out.println("This lets you swap HashSet <-> TreeSet <-> LinkedHashSet by changing ONE line.");
    }


    // Generic method: removes duplicates from any Collection,
    // returning a LinkedHashSet that preserves insertion order.
    public static <E> Set<E> removeDups(Collection<E> c) {
        return new LinkedHashSet<>(c);
    }
}
