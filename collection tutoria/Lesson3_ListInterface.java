import java.util.*;
import java.util.stream.*;

/**
 * LESSON 3: The List Interface
 *
 * A List is an ORDERED Collection (also called a sequence).
 * Lists MAY contain duplicate elements.
 *
 * Extras over Collection:
 *   1. Positional access  — get(i), set(i,e), add(i,e), remove(i)
 *   2. Search             — indexOf(o), lastIndexOf(o)
 *   3. ListIterator       — traverse forwards AND backwards, add/set during iteration
 *   4. Range-view         — subList(from, to)
 *
 * Two general-purpose implementations:
 *   ArrayList  — backed by a resizable array. Best general performance.
 *   LinkedList — backed by a doubly-linked list. Better when adding/removing at
 *                head or middle frequently; slower random access.
 */
public class Lesson3_ListInterface {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. COLLECTION OPERATIONS INHERITED BY LIST
        //    Remember: add/addAll always append to the END by default.
        // ----------------------------------------------------------------
        System.out.println("=== 1. Collection Operations on a List ===");

        List<String> list1 = new ArrayList<>(Arrays.asList("A", "B", "C"));
        List<String> list2 = new ArrayList<>(Arrays.asList("D", "E", "F"));

        // Destructive: appends list2 into list1
        list1.addAll(list2);
        System.out.println("list1 after addAll(list2): " + list1);

        // Non-destructive: third list = list1 + list2
        List<String> list3 = new ArrayList<>(list1);  // copy list1
        list3.addAll(list2);
        System.out.println("list3 (list1 + list2):     " + list3);

        // Collecting from a stream into a List (JDK 8+)
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Alice");
        List<String> upperNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Stream → List (uppercase): " + upperNames);

        // Two Lists are equal if they contain the same elements IN THE SAME ORDER
        List<String> x = Arrays.asList("A", "B", "C");
        List<String> y = Arrays.asList("A", "B", "C");
        List<String> z = Arrays.asList("C", "B", "A");
        System.out.println("x.equals(y) [same order]:    " + x.equals(y)); // true
        System.out.println("x.equals(z) [diff order]:    " + x.equals(z)); // false


        // ----------------------------------------------------------------
        // 2. POSITIONAL ACCESS — get, set, add(index), remove(index)
        // ----------------------------------------------------------------
        System.out.println("\n=== 2. Positional Access ===");

        List<String> fruits = new ArrayList<>(Arrays.asList("Mango", "Apple", "Cherry", "Banana"));
        //  Index:                                               0        1        2         3

        System.out.println("get(1):          " + fruits.get(1));          // Apple

        String old = fruits.set(1, "Avocado");  // replaces index 1, returns old value
        System.out.println("set(1, Avocado) returned: " + old);           // Apple
        System.out.println("List after set:  " + fruits);

        fruits.add(2, "Grape");  // inserts at index 2, shifts everything right
        System.out.println("After add(2, Grape): " + fruits);

        String removed = fruits.remove(0);  // removes index 0, returns it
        System.out.println("remove(0) returned: " + removed);             // Mango
        System.out.println("After remove(0): " + fruits);


        // ----------------------------------------------------------------
        // 3. SEARCH — indexOf, lastIndexOf
        // ----------------------------------------------------------------
        System.out.println("\n=== 3. Search Operations ===");

        List<String> words = new ArrayList<>(Arrays.asList("cat", "dog", "cat", "bird", "cat"));

        System.out.println("indexOf('cat'):     " + words.indexOf("cat"));      // 0 — first
        System.out.println("lastIndexOf('cat'): " + words.lastIndexOf("cat"));  // 4 — last
        System.out.println("indexOf('fish'):    " + words.indexOf("fish"));     // -1 — not found


        // ----------------------------------------------------------------
        // 4. POLYMORPHIC SWAP ALGORITHM
        //    Works on ANY List regardless of its implementation type.
        // ----------------------------------------------------------------
        System.out.println("\n=== 4. Swap (Polymorphic Algorithm) ===");

        List<Integer> nums = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50));
        System.out.println("Before swap(1,3): " + nums);
        swap(nums, 1, 3);
        System.out.println("After  swap(1,3): " + nums);  // [10, 40, 30, 20, 50]


        // ----------------------------------------------------------------
        // 5. SHUFFLE — random permutation
        // ----------------------------------------------------------------
        System.out.println("\n=== 5. Shuffle ===");

        List<String> cards = new ArrayList<>(Arrays.asList("Ace", "2", "3", "4", "5", "6", "7"));

        // Library shuffle — uses a default Random source
        Collections.shuffle(cards);
        System.out.println("Shuffled cards: " + cards);

        // Arrays.asList shortcut — wraps an existing array as a List view
        // NOTE: this view is fixed-size (cannot add/remove), but set() works fine
        String[] arr = {"X", "Y", "Z"};
        List<String> fixedView = Arrays.asList(arr);
        Collections.shuffle(fixedView);
        System.out.println("Shuffled array view: " + fixedView);
        System.out.println("Original array also shuffled: " + Arrays.toString(arr)); // same data!


        // ----------------------------------------------------------------
        // 6. LIST ITERATOR — traverse forwards AND backwards
        //    Extra methods over Iterator:
        //      hasPrevious() / previous()   — go backwards
        //      nextIndex() / previousIndex() — current cursor position
        //      set(e)                        — replace last visited element
        //      add(e)                        — insert before cursor
        // ----------------------------------------------------------------
        System.out.println("\n=== 6. ListIterator ===");

        List<String> letters = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));

        // Forward traversal
        System.out.print("Forward:  ");
        ListIterator<String> it = letters.listIterator();
        while (it.hasNext()) {
            System.out.print(it.nextIndex() + ":" + it.next() + " ");
        }
        System.out.println();

        // Backward traversal — start the iterator at the END (list.size())
        System.out.print("Backward: ");
        ListIterator<String> itRev = letters.listIterator(letters.size());
        while (itRev.hasPrevious()) {
            System.out.print(itRev.previousIndex() + ":" + itRev.previous() + " ");
        }
        System.out.println();

        // set() — replace while iterating
        List<String> data = new ArrayList<>(Arrays.asList("hello", "world", "foo"));
        ListIterator<String> setIt = data.listIterator();
        while (setIt.hasNext()) {
            String val = setIt.next();
            setIt.set(val.toUpperCase());   // replace with uppercase
        }
        System.out.println("After set() uppercase: " + data);

        // add() — insert before cursor position
        List<String> addDemo = new ArrayList<>(Arrays.asList("a", "b", "c"));
        ListIterator<String> addIt = addDemo.listIterator();
        addIt.next();         // cursor moves past "a"
        addIt.add("X");       // inserts "X" between "a" and "b"
        System.out.println("After add('X') at position 1: " + addDemo);


        // ----------------------------------------------------------------
        // 7. CUSTOM indexOf USING ListIterator
        //    Demonstrates previousIndex() to find where we just were.
        // ----------------------------------------------------------------
        System.out.println("\n=== 7. Custom indexOf via ListIterator ===");

        List<String> search = Arrays.asList("cat", "dog", null, "bird", null);
        System.out.println("indexOf('bird') = " + indexOf(search, "bird")); // 3
        System.out.println("indexOf(null)   = " + indexOf(search, null));   // 2
        System.out.println("indexOf('fish') = " + indexOf(search, "fish")); // -1


        // ----------------------------------------------------------------
        // 8. RANGE-VIEW — subList(fromIndex, toIndex)
        //    Returns a VIEW (backed by the original list), not a copy.
        //    Changes to the subList are reflected in the original, and vice versa.
        //    Range is half-open: [fromIndex, toIndex)
        // ----------------------------------------------------------------
        System.out.println("\n=== 8. Range-View: subList ===");

        List<String> full = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F"));
        System.out.println("Full list:           " + full);

        // View elements at index 1, 2, 3  (toIndex 4 is excluded)
        List<String> sub = full.subList(1, 4);
        System.out.println("subList(1,4):        " + sub);          // [B, C, D]

        // Modify via subList — changes the backing list too
        sub.set(0, "Z");
        System.out.println("After sub.set(0,'Z'): " + full);        // [A, Z, C, D, E, F]

        // Clear a range — remove elements B..D from the original
        List<String> clearDemo = new ArrayList<>(Arrays.asList("A","B","C","D","E","F"));
        clearDemo.subList(1, 4).clear();
        System.out.println("After subList(1,4).clear(): " + clearDemo); // [A, E, F]

        // Search within a range
        List<Integer> numbers = new ArrayList<>(Arrays.asList(10, 20, 30, 20, 40));
        int foundAt = numbers.subList(2, 5).indexOf(20);
        System.out.println("indexOf(20) within subList(2,5): " + foundAt); // 1 — relative to subList


        // ----------------------------------------------------------------
        // 9. DEAL HAND — real-world subList example
        //    Removes n cards from the END of a deck and returns them as a hand.
        //    Removing from the end is faster for ArrayList.
        // ----------------------------------------------------------------
        System.out.println("\n=== 9. Deal Hand (subList real-world example) ===");

        // Build a 52-card deck
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        String[] ranks = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};

        List<String> deck = new ArrayList<>();
        for (String suit : suits)
            for (String rank : ranks)
                deck.add(rank + " of " + suit);

        System.out.println("Deck size: " + deck.size());  // 52

        Collections.shuffle(deck);

        System.out.println("Hand 1: " + dealHand(deck, 5));
        System.out.println("Hand 2: " + dealHand(deck, 5));
        System.out.println("Hand 3: " + dealHand(deck, 5));
        System.out.println("Remaining deck size: " + deck.size()); // 37


        // ----------------------------------------------------------------
        // 10. LIST ALGORITHMS from Collections class
        // ----------------------------------------------------------------
        System.out.println("\n=== 10. List Algorithms (Collections class) ===");

        List<Integer> algo = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));
        System.out.println("Original:    " + algo);

        Collections.sort(algo);
        System.out.println("sort():      " + algo);          // [1, 1, 2, 3, 4, 5, 6, 9]

        Collections.reverse(algo);
        System.out.println("reverse():   " + algo);          // [9, 6, 5, 4, 3, 2, 1, 1]

        Collections.rotate(algo, 2);
        System.out.println("rotate(2):   " + algo);          // last 2 move to front

        Collections.swap(algo, 0, algo.size() - 1);
        System.out.println("swap(0,end): " + algo);

        Collections.fill(new ArrayList<>(algo), 0);          // fill doesn't affect algo, just demo
        List<Integer> filled = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        Collections.fill(filled, 7);
        System.out.println("fill(7):     " + filled);        // [7, 7, 7, 7, 7]

        Collections.replaceAll(algo, 1, 99);
        System.out.println("replaceAll(1→99): " + algo);

        // binarySearch — list MUST be sorted first
        List<Integer> sorted = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9, 11));
        int idx = Collections.binarySearch(sorted, 7);
        System.out.println("binarySearch(7): index = " + idx); // 3

        // copy — destination must be at least as large as source
        List<Integer> src  = Arrays.asList(100, 200, 300);
        List<Integer> dest = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        Collections.copy(dest, src);
        System.out.println("copy(dest, src): " + dest);      // [100, 200, 300, 0, 0]


        // ----------------------------------------------------------------
        // 11. REPLACE ALL OCCURRENCES using ListIterator
        //     Polymorphic: works on any List
        // ----------------------------------------------------------------
        System.out.println("\n=== 11. replace() using ListIterator ===");

        List<String> sentence = new ArrayList<>(Arrays.asList("the","cat","sat","on","the","cat"));
        replace(sentence, "cat", "dog");
        System.out.println("After replace(cat→dog): " + sentence);

        // Replace one value with MULTIPLE values
        List<String> expansion = new ArrayList<>(Arrays.asList("feline", "creature"));
        replaceWithMany(sentence, "dog", expansion);
        System.out.println("After replaceWithMany(dog→[feline,creature]): " + sentence);
    }


    // ----------------------------------------------------------------
    // Utility: swap two elements in any List
    // ----------------------------------------------------------------
    public static <E> void swap(List<E> a, int i, int j) {
        E tmp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, tmp);
    }

    // ----------------------------------------------------------------
    // Utility: custom indexOf using ListIterator
    //   Uses previousIndex() because after calling next() the cursor
    //   has already moved past the element we just examined.
    // ----------------------------------------------------------------
    public static <E> int indexOf(List<E> list, E e) {
        for (ListIterator<E> it = list.listIterator(); it.hasNext(); ) {
            if (e == null ? it.next() == null : e.equals(it.next()))
                return it.previousIndex();
        }
        return -1;
    }

    // ----------------------------------------------------------------
    // Utility: replace all occurrences of val with newVal
    // ----------------------------------------------------------------
    public static <E> void replace(List<E> list, E val, E newVal) {
        for (ListIterator<E> it = list.listIterator(); it.hasNext(); ) {
            if (val == null ? it.next() == null : val.equals(it.next()))
                it.set(newVal);
        }
    }

    // ----------------------------------------------------------------
    // Utility: replace all occurrences of val with a sequence of values
    // ----------------------------------------------------------------
    public static <E> void replaceWithMany(List<E> list, E val, List<? extends E> newVals) {
        for (ListIterator<E> it = list.listIterator(); it.hasNext(); ) {
            if (val == null ? it.next() == null : val.equals(it.next())) {
                it.remove();
                for (E e : newVals)
                    it.add(e);
            }
        }
    }

    // ----------------------------------------------------------------
    // Utility: deal n cards from the end of a deck (removes them from deck)
    // ----------------------------------------------------------------
    public static <E> List<E> dealHand(List<E> deck, int n) {
        int deckSize = deck.size();
        List<E> handView = deck.subList(deckSize - n, deckSize); // view of last n cards
        List<E> hand = new ArrayList<>(handView);                 // copy to a new list
        handView.clear();                                          // remove from deck
        return hand;
    }
}
