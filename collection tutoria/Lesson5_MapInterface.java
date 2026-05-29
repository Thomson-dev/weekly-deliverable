import java.util.*;
import java.util.stream.*;

/**
 * LESSON 5: The Map Interface
 *
 * A Map stores KEY → VALUE pairs. No duplicate keys allowed.
 * Each key maps to exactly ONE value.
 *
 * Think of it like a dictionary: the word (key) maps to its definition (value).
 *
 * Key methods:
 *   put(K key, V value)          — add or update a mapping
 *   get(Object key)              — retrieve value by key (null if not found)
 *   remove(Object key)           — delete a mapping
 *   containsKey(Object key)      — does this key exist?
 *   containsValue(Object value)  — does this value exist?
 *   size()                       — number of key-value pairs
 *   isEmpty()                    — true if no pairs
 *   putAll(Map m)                — copy all pairs from another map
 *   clear()                      — remove everything
 *
 * Three general-purpose implementations (mirror the three Set types):
 *   HashMap        — fastest, NO guaranteed order
 *   TreeMap        — keys sorted by natural order (or Comparator)
 *   LinkedHashMap  — keys in INSERTION ORDER
 */
public class Lesson5_MapInterface {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // 1. BASIC OPERATIONS — put, get, remove, containsKey, containsValue
        // ----------------------------------------------------------------
        System.out.println("=== 1. Basic Operations ===");

        Map<String, Integer> scores = new HashMap<>();

        // put(key, value) — adds a new mapping
        scores.put("Alice", 95);
        scores.put("Bob",   78);
        scores.put("Carol", 88);
        scores.put("Dave",  78);   // duplicate VALUE is fine
        System.out.println("Map: " + scores);

        // put with an existing key REPLACES the value, returns the OLD value
        Integer old = scores.put("Bob", 85);
        System.out.println("Replaced Bob's score. Old value was: " + old);
        System.out.println("Map after update: " + scores);

        // get(key) — retrieve value; returns null if key not found
        System.out.println("Alice's score:  " + scores.get("Alice"));
        System.out.println("Unknown key:    " + scores.get("Zara"));  // null

        // containsKey / containsValue
        System.out.println("containsKey('Carol'):   " + scores.containsKey("Carol"));
        System.out.println("containsValue(95):      " + scores.containsValue(95));

        // remove(key) — deletes the pair, returns the removed value (or null)
        Integer removed = scores.remove("Dave");
        System.out.println("Removed Dave, value was: " + removed);
        System.out.println("Map after remove: " + scores);

        // size / isEmpty
        System.out.println("size(): " + scores.size());
        System.out.println("isEmpty(): " + scores.isEmpty());


        // ----------------------------------------------------------------
        // 2. THREE IMPLEMENTATIONS — same data, different ordering
        // ----------------------------------------------------------------
        System.out.println("\n=== 2. HashMap vs TreeMap vs LinkedHashMap ===");

        List<String> words = Arrays.asList("if", "it", "is", "to", "be", "it", "is", "up", "to", "me");

        // Build frequency table with HashMap (no order)
        Map<String, Integer> hashMap = buildFrequency(words, new HashMap<>());
        System.out.println("HashMap   (no order):        " + hashMap);

        // TreeMap — keys sorted alphabetically
        Map<String, Integer> treeMap = buildFrequency(words, new TreeMap<>());
        System.out.println("TreeMap   (alphabetical):    " + treeMap);

        // LinkedHashMap — keys in insertion (first-seen) order
        Map<String, Integer> linkedMap = buildFrequency(words, new LinkedHashMap<>());
        System.out.println("LinkedHashMap (first-seen):  " + linkedMap);


        // ----------------------------------------------------------------
        // 3. FREQUENCY TABLE — classic Map pattern
        //    Counts how many times each word appears.
        //    The tricky part: first time a word appears, freq is null.
        // ----------------------------------------------------------------
        System.out.println("\n=== 3. Frequency Table Pattern ===");

        String[] sentence = {"to", "be", "or", "not", "to", "be", "that", "is", "the", "question"};
        Map<String, Integer> freq = new HashMap<>();

        for (String word : sentence) {
            Integer count = freq.get(word);
            //  count == null means first time we've seen this word → set to 1
            //  count != null means we've seen it before → increment
            freq.put(word, (count == null) ? 1 : count + 1);
        }

        System.out.println("Frequency table: " + freq);

        // Modern JDK 8+ alternative using getOrDefault
        Map<String, Integer> freq2 = new HashMap<>();
        for (String word : sentence) {
            freq2.put(word, freq2.getOrDefault(word, 0) + 1);
        }
        System.out.println("Same result with getOrDefault: " + freq2);

        // Even more concise using merge()
        Map<String, Integer> freq3 = new HashMap<>();
        for (String word : sentence) {
            freq3.merge(word, 1, Integer::sum);  // if key absent: put 1; else: add 1
        }
        System.out.println("Same result with merge():      " + freq3);


        // ----------------------------------------------------------------
        // 4. CONVERSION CONSTRUCTOR — copy a Map
        // ----------------------------------------------------------------
        System.out.println("\n=== 4. Conversion Constructor ===");

        Map<String, Integer> original = new HashMap<>();
        original.put("x", 10);
        original.put("y", 20);

        // Create a TreeMap from a HashMap — changes implementation, keeps all data
        Map<String, Integer> sorted = new TreeMap<>(original);
        System.out.println("Original HashMap: " + original);
        System.out.println("Copied to TreeMap (sorted): " + sorted);


        // ----------------------------------------------------------------
        // 5. BULK OPERATIONS — putAll, clear
        // ----------------------------------------------------------------
        System.out.println("\n=== 5. Bulk Operations ===");

        Map<String, String> defaults = new HashMap<>();
        defaults.put("color", "blue");
        defaults.put("font",  "Arial");
        defaults.put("size",  "12");

        Map<String, String> overrides = new HashMap<>();
        overrides.put("color", "red");   // overrides the default
        overrides.put("weight", "bold"); // adds a new key

        // newAttributeMap pattern: start with defaults, then overlay overrides
        Map<String, String> config = newAttributeMap(defaults, overrides);
        System.out.println("defaults:  " + defaults);
        System.out.println("overrides: " + overrides);
        System.out.println("merged:    " + config);
        // color=red (overridden), font=Arial (default kept), size=12 (default kept), weight=bold (new)


        // ----------------------------------------------------------------
        // 6. COLLECTION VIEWS — the three ways to look at a Map
        //
        //   keySet()   → Set<K>            — all keys
        //   values()   → Collection<V>     — all values (can have duplicates)
        //   entrySet() → Set<Map.Entry<K,V>>— all key-value pairs together
        // ----------------------------------------------------------------
        System.out.println("\n=== 6. Collection Views ===");

        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("one", 1);
        m.put("two", 2);
        m.put("three", 3);
        m.put("four", 4);

        // keySet() — iterate over keys
        System.out.print("Keys:   ");
        for (String key : m.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();

        // values() — iterate over values
        System.out.print("Values: ");
        for (int val : m.values()) {
            System.out.print(val + " ");
        }
        System.out.println();

        // entrySet() — iterate over key-value pairs (most common idiom)
        System.out.println("Key-Value pairs:");
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            System.out.println("  " + entry.getKey() + " → " + entry.getValue());
        }


        // ----------------------------------------------------------------
        // 7. REMOVING DURING ITERATION — use Iterator on a view
        //    Just like with List/Set, you must use iterator.remove()
        //    to safely delete entries while iterating a Map.
        // ----------------------------------------------------------------
        System.out.println("\n=== 7. Removing During Iteration ===");

        Map<String, Integer> data = new HashMap<>();
        data.put("Alice", 95);
        data.put("Bob",   45);   // failing
        data.put("Carol", 82);
        data.put("Dave",  38);   // failing
        data.put("Eve",   91);

        System.out.println("Before: " + data);

        // Remove all entries where score < 50
        Iterator<Map.Entry<String, Integer>> entryIt = data.entrySet().iterator();
        while (entryIt.hasNext()) {
            Map.Entry<String, Integer> entry = entryIt.next();
            if (entry.getValue() < 50) {
                entryIt.remove();   // safely removes the map entry
            }
        }

        System.out.println("After removing scores < 50: " + data);


        // ----------------------------------------------------------------
        // 8. setValue() DURING ITERATION — modify value without breaking iterator
        // ----------------------------------------------------------------
        System.out.println("\n=== 8. Updating Values During Iteration (setValue) ===");

        Map<String, Integer> prices = new LinkedHashMap<>();
        prices.put("Apple",  100);
        prices.put("Banana", 50);
        prices.put("Cherry", 200);

        System.out.println("Before (cents): " + prices);

        // Apply a 10% discount to every value
        for (Map.Entry<String, Integer> entry : prices.entrySet()) {
            entry.setValue((int)(entry.getValue() * 0.9));  // safe — through the entry
        }

        System.out.println("After 10% discount: " + prices);


        // ----------------------------------------------------------------
        // 9. MAP ALGEBRA — using Collection views with bulk operations
        // ----------------------------------------------------------------
        System.out.println("\n=== 9. Map Algebra ===");

        Map<String, Integer> m1 = new HashMap<>();
        m1.put("a", 1); m1.put("b", 2); m1.put("c", 3);

        Map<String, Integer> m2 = new HashMap<>();
        m2.put("b", 2); m2.put("c", 3);

        // Is m2 a submap of m1? (does m1 contain all of m2's entries?)
        System.out.println("m1.entrySet().containsAll(m2.entrySet()) [m2 ⊆ m1]: "
                + m1.entrySet().containsAll(m2.entrySet())); // true

        // Do m1 and m2 have the same keys?
        System.out.println("m1.keySet().equals(m2.keySet()): "
                + m1.keySet().equals(m2.keySet())); // false — m1 has "a" too

        // Keys common to both maps
        Set<String> commonKeys = new HashSet<>(m1.keySet());
        commonKeys.retainAll(m2.keySet());
        System.out.println("Common keys: " + commonKeys); // [b, c]

        // Remove from m1 all entries that exist in m2
        Map<String, Integer> m1Copy = new HashMap<>(m1);
        m1Copy.entrySet().removeAll(m2.entrySet());
        System.out.println("m1 minus m2's entries: " + m1Copy); // {a=1}

        // Remove from m1 all keys that appear in m2
        Map<String, Integer> m1Copy2 = new HashMap<>(m1);
        m1Copy2.keySet().removeAll(m2.keySet());
        System.out.println("m1 minus m2's keys:    " + m1Copy2); // {a=1}


        // ----------------------------------------------------------------
        // 10. VALIDATE ATTRIBUTE MAP — real-world Map algebra example
        // ----------------------------------------------------------------
        System.out.println("\n=== 10. Validate Attribute Map ===");

        Map<String, String> attributes = new HashMap<>();
        attributes.put("username", "thomson");
        attributes.put("email",    "thomson@example.com");
        attributes.put("age",      "21");
        attributes.put("nickname", "tom");  // not in permitted set

        Set<String> required  = new HashSet<>(Arrays.asList("username", "email"));
        Set<String> permitted = new HashSet<>(Arrays.asList("username", "email", "age", "phone"));

        boolean valid = validate(attributes, required, permitted);
        System.out.println("Attribute map is valid: " + valid);


        // ----------------------------------------------------------------
        // 11. MULTIMAP PATTERN — Map<K, List<V>>
        //     One key maps to MULTIPLE values (stored as a List).
        //     Used when you need to group items by a common property.
        // ----------------------------------------------------------------
        System.out.println("\n=== 11. Multimap (Map<String, List<String>>) ===");

        // Group students by their grade
        Map<String, List<String>> byGrade = new HashMap<>();

        addToMultimap(byGrade, "A", "Alice");
        addToMultimap(byGrade, "B", "Bob");
        addToMultimap(byGrade, "A", "Carol");  // second student with grade A
        addToMultimap(byGrade, "C", "Dave");
        addToMultimap(byGrade, "B", "Eve");

        System.out.println("Students by grade:");
        for (Map.Entry<String, List<String>> entry : byGrade.entrySet()) {
            System.out.println("  Grade " + entry.getKey() + ": " + entry.getValue());
        }


        // ----------------------------------------------------------------
        // 12. ANAGRAM GROUPING — advanced multimap example
        //     Alphabetize each word → use as key.
        //     All words with the same letters map to the same key.
        // ----------------------------------------------------------------
        System.out.println("\n=== 12. Anagram Groups ===");

        String[] dictionary = {
            "eat", "tea", "tan", "ate", "nat", "bat",
            "listen", "silent", "enlist", "inlets",
            "race", "care", "acre", "arce"
        };

        Map<String, List<String>> anagramMap = new HashMap<>();

        for (String word : dictionary) {
            String key = alphabetize(word);          // sort letters: "eat" → "aet"
            List<String> group = anagramMap.get(key);
            if (group == null) {
                anagramMap.put(key, group = new ArrayList<>());
            }
            group.add(word);
        }

        System.out.println("Anagram groups:");
        for (List<String> group : anagramMap.values()) {
            if (group.size() >= 2) {                 // only print groups with 2+ words
                System.out.println("  " + group);
            }
        }


        // ----------------------------------------------------------------
        // 13. STREAM API → MAP (JDK 8+)
        // ----------------------------------------------------------------
        System.out.println("\n=== 13. Stream API → Map ===");

        List<String> people = Arrays.asList("Alice", "Bob", "Carol", "Dave");

        // Map each name to its length
        Map<String, Integer> nameLengths = people.stream()
                .collect(Collectors.toMap(
                        name -> name,           // key:   the name itself
                        String::length          // value: its length
                ));
        System.out.println("Name lengths: " + nameLengths);

        // Group names by their first letter
        Map<Character, List<String>> byFirstLetter = people.stream()
                .collect(Collectors.groupingBy(name -> name.charAt(0)));
        System.out.println("By first letter: " + byFirstLetter);

        // Count names by first letter
        Map<Character, Long> countByFirstLetter = people.stream()
                .collect(Collectors.groupingBy(name -> name.charAt(0), Collectors.counting()));
        System.out.println("Count by first letter: " + countByFirstLetter);
    }


    // ----------------------------------------------------------------
    // Utility: build a frequency map using any Map implementation
    // ----------------------------------------------------------------
    static Map<String, Integer> buildFrequency(List<String> words, Map<String, Integer> map) {
        for (String w : words)
            map.put(w, map.getOrDefault(w, 0) + 1);
        return map;
    }

    // ----------------------------------------------------------------
    // Utility: merge defaults with overrides (overrides win on conflict)
    // ----------------------------------------------------------------
    static <K, V> Map<K, V> newAttributeMap(Map<K, V> defaults, Map<K, V> overrides) {
        Map<K, V> result = new HashMap<>(defaults);  // start with all defaults
        result.putAll(overrides);                     // overlay overrides on top
        return result;
    }

    // ----------------------------------------------------------------
    // Utility: validate that an attribute map has required keys
    //          and no keys outside the permitted set
    // ----------------------------------------------------------------
    static <K, V> boolean validate(Map<K, V> attrMap, Set<K> required, Set<K> permitted) {
        boolean valid = true;
        Set<K> keys = attrMap.keySet();

        if (!keys.containsAll(required)) {
            Set<K> missing = new HashSet<>(required);
            missing.removeAll(keys);
            System.out.println("  Missing required attributes: " + missing);
            valid = false;
        }

        if (!permitted.containsAll(keys)) {
            Set<K> illegal = new HashSet<>(keys);
            illegal.removeAll(permitted);
            System.out.println("  Illegal (unpermitted) attributes: " + illegal);
            valid = false;
        }

        return valid;
    }

    // ----------------------------------------------------------------
    // Utility: add a value to a multimap (creates the list if needed)
    // ----------------------------------------------------------------
    static <K, V> void addToMultimap(Map<K, List<V>> map, K key, V value) {
        List<V> list = map.get(key);
        if (list == null) {
            map.put(key, list = new ArrayList<>());
        }
        list.add(value);
    }

    // ----------------------------------------------------------------
    // Utility: sort a word's letters alphabetically ("eat" → "aet")
    // ----------------------------------------------------------------
    static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
