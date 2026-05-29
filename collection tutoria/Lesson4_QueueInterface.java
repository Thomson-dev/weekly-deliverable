import java.util.*;
import java.util.stream.*;

/**
 * LESSON 4: The Queue Interface
 *
 * A Queue holds elements PRIOR to processing — like a real-world queue/line.
 * It extends Collection and adds methods for insert, remove, and examine.
 *
 * public interface Queue<E> extends Collection<E> {
 *     E       element();        // examine head — throws if empty
 *     boolean offer(E e);       // insert      — returns false if full
 *     E       peek();           // examine head — returns null if empty
 *     E       poll();           // remove head  — returns null if empty
 *     E       remove();         // remove head  — throws if empty
 *     boolean add(E e);         // insert       — throws if full (inherited)
 * }
 *
 * Every method has TWO forms:
 * ┌────────────┬──────────────────────┬────────────────────────────┐
 * │ Operation  │ Throws exception     │ Returns special value      │
 * ├────────────┼──────────────────────┼────────────────────────────┤
 * │ Insert     │ add(e)               │ offer(e) → false if fails  │
 * │ Remove     │ remove()             │ poll()   → null if empty   │
 * │ Examine    │ element()            │ peek()   → null if empty   │
 * └────────────┴──────────────────────┴────────────────────────────┘
 *
 * Common Queue implementations:
 *   LinkedList      — FIFO queue, allows null (but avoid it — null = special value)
 *   PriorityQueue   — orders by natural order or Comparator (NOT FIFO)
 *   ArrayDeque      — fast double-ended queue, preferred over LinkedList for stacks/queues
 */
public class Lesson4_QueueInterface {

    public static void main(String[] args) throws InterruptedException {

        // ----------------------------------------------------------------
        // 1. BASIC QUEUE OPERATIONS — two forms of each
        // ----------------------------------------------------------------
        System.out.println("=== 1. Basic Queue Operations ===");

        Queue<String> queue = new LinkedList<>();

        // INSERT
        queue.add("first");      // throws IllegalStateException if bounded queue is full
        queue.offer("second");   // returns false if bounded queue is full (safe form)
        queue.offer("third");
        System.out.println("Queue after 3 inserts: " + queue);

        // EXAMINE — look at the head WITHOUT removing it
        System.out.println("element() [throws if empty]: " + queue.element()); // "first"
        System.out.println("peek()    [null if empty]:   " + queue.peek());    // "first"
        System.out.println("Queue unchanged after peek:  " + queue);

        // REMOVE — take from the head
        System.out.println("remove() [throws if empty]: " + queue.remove()); // "first"
        System.out.println("poll()   [null if empty]:   " + queue.poll());   // "second"
        System.out.println("Queue after 2 removals: " + queue);              // [third]

        // Behaviour on an EMPTY queue
        Queue<String> empty = new LinkedList<>();
        System.out.println("\nOn an empty queue:");
        System.out.println("peek()  → " + empty.peek());   // null  (safe)
        System.out.println("poll()  → " + empty.poll());   // null  (safe)
        try {
            empty.element();                                // throws NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println("element() threw: " + e.getClass().getSimpleName());
        }
        try {
            empty.remove();                                 // throws NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println("remove()  threw: " + e.getClass().getSimpleName());
        }


        // ----------------------------------------------------------------
        // 2. FIFO ORDERING — First In, First Out
        //    Elements exit in the SAME order they entered.
        // ----------------------------------------------------------------
        System.out.println("\n=== 2. FIFO Ordering ===");

        Queue<String> fifo = new LinkedList<>();
        fifo.offer("Task-A");
        fifo.offer("Task-B");
        fifo.offer("Task-C");

        System.out.println("Processing order (FIFO):");
        while (!fifo.isEmpty()) {
            System.out.println("  Processing: " + fifo.poll());
        }
        // Output: Task-A, Task-B, Task-C  — same order as insertion


        // ----------------------------------------------------------------
        // 3. COUNTDOWN TIMER — classic queue example
        //    Preloads the queue with numbers, then removes one per "second".
        //    (Thread.sleep removed here so the output is instant for learning)
        // ----------------------------------------------------------------
        System.out.println("\n=== 3. Countdown Timer ===");

        int time = 5;
        Queue<Integer> countdown = new LinkedList<>();

        // Load the queue descending: 5, 4, 3, 2, 1, 0
        for (int i = time; i >= 0; i--)
            countdown.add(i);

        System.out.print("Countdown: ");
        while (!countdown.isEmpty()) {
            System.out.print(countdown.remove() + " ");
            // Thread.sleep(1000); // uncomment to make it tick once per second
        }
        System.out.println("Go!");


        // ----------------------------------------------------------------
        // 4. PRIORITY QUEUE — NOT FIFO
        //    Orders elements by their NATURAL ORDER (or a custom Comparator).
        //    The element with the SMALLEST value is always at the head.
        // ----------------------------------------------------------------
        System.out.println("\n=== 4. PriorityQueue (natural order — smallest first) ===");

        Queue<Integer> pq = new PriorityQueue<>();
        pq.offer(42);
        pq.offer(7);
        pq.offer(100);
        pq.offer(1);
        pq.offer(23);

        System.out.println("peek() (smallest element): " + pq.peek()); // 1

        System.out.print("Poll order (ascending):    ");
        while (!pq.isEmpty()) {
            System.out.print(pq.poll() + " ");  // 1 7 23 42 100
        }
        System.out.println();


        // ----------------------------------------------------------------
        // 5. PRIORITY QUEUE WITH CUSTOM COMPARATOR — largest first
        // ----------------------------------------------------------------
        System.out.println("\n=== 5. PriorityQueue (custom Comparator — largest first) ===");

        Queue<Integer> maxPQ = new PriorityQueue<>(Comparator.reverseOrder());
        maxPQ.offer(42);
        maxPQ.offer(7);
        maxPQ.offer(100);
        maxPQ.offer(1);
        maxPQ.offer(23);

        System.out.print("Poll order (descending): ");
        while (!maxPQ.isEmpty()) {
            System.out.print(maxPQ.poll() + " ");  // 100 42 23 7 1
        }
        System.out.println();


        // ----------------------------------------------------------------
        // 6. PRIORITY QUEUE WITH STRINGS — alphabetical order
        // ----------------------------------------------------------------
        System.out.println("\n=== 6. PriorityQueue with Strings (alphabetical) ===");

        Queue<String> wordPQ = new PriorityQueue<>();
        wordPQ.offer("Banana");
        wordPQ.offer("Apple");
        wordPQ.offer("Cherry");
        wordPQ.offer("Date");

        System.out.print("Poll order (alphabetical): ");
        while (!wordPQ.isEmpty()) {
            System.out.print(wordPQ.poll() + " ");  // Apple Banana Cherry Date
        }
        System.out.println();


        // ----------------------------------------------------------------
        // 7. HEAP SORT using PriorityQueue
        //    Drain elements from PriorityQueue into a List → sorted result.
        //    PriorityQueue always removes the smallest element first.
        // ----------------------------------------------------------------
        System.out.println("\n=== 7. heapSort using PriorityQueue ===");

        List<Integer> unsorted = Arrays.asList(5, 2, 8, 1, 9, 3, 7);
        System.out.println("Unsorted: " + unsorted);

        List<Integer> sorted = heapSort(unsorted);
        System.out.println("Sorted:   " + sorted);


        // ----------------------------------------------------------------
        // 8. TASK QUEUE — real-world simulation
        //    Demonstrates using a queue to hold work items before processing.
        // ----------------------------------------------------------------
        System.out.println("\n=== 8. Task Queue Simulation ===");

        Queue<String> taskQueue = new LinkedList<>();

        // Producer: add tasks
        taskQueue.offer("Send email");
        taskQueue.offer("Generate report");
        taskQueue.offer("Backup database");
        taskQueue.offer("Clear cache");

        System.out.println("Tasks queued: " + taskQueue.size());

        // Consumer: process tasks in order
        while (!taskQueue.isEmpty()) {
            String task = taskQueue.poll();
            System.out.println("  Done: " + task);
        }


        // ----------------------------------------------------------------
        // 9. OFFER vs ADD — when it matters
        //    For unbounded queues (LinkedList, PriorityQueue): identical behaviour.
        //    For BOUNDED queues (e.g. ArrayBlockingQueue in java.util.concurrent):
        //      add()   → throws IllegalStateException when full
        //      offer() → returns false when full (safe — use in loops/conditional logic)
        //
        //    Best practice: prefer offer() + poll() + peek() in application code.
        //    They never throw, so you handle failure explicitly rather than catching.
        // ----------------------------------------------------------------
        System.out.println("\n=== 9. Best Practice: offer/poll/peek over add/remove/element ===");

        Queue<Integer> safeQueue = new LinkedList<>();

        // Safe insert — check return value
        boolean inserted = safeQueue.offer(10);
        System.out.println("offer(10) succeeded: " + inserted);

        // Safe examine — no try-catch needed
        Integer head = safeQueue.peek();
        if (head != null) {
            System.out.println("Head element: " + head);
        }

        // Safe remove — no try-catch needed
        Integer polled = safeQueue.poll();
        if (polled != null) {
            System.out.println("Polled: " + polled);
        }

        // Queue is now empty — peek/poll return null instead of throwing
        System.out.println("peek() on empty: " + safeQueue.peek()); // null
        System.out.println("poll() on empty: " + safeQueue.poll()); // null


        // ----------------------------------------------------------------
        // 10. NULL RULE — do NOT insert null into a Queue
        //     LinkedList technically allows it, but null is used as the
        //     sentinel return value by poll() and peek(). Inserting null
        //     makes it impossible to distinguish "queue was empty" from
        //     "queue contained a null element."
        // ----------------------------------------------------------------
        System.out.println("\n=== 10. Why null Must Not Be Inserted ===");

        Queue<String> badIdea = new LinkedList<>();
        badIdea.offer("real value");
        badIdea.offer(null);           // LinkedList allows it, but don't do this

        System.out.println("poll(): " + badIdea.poll()); // "real value" — ok
        String result = badIdea.poll();
        System.out.println("poll(): " + result);         // null — was it empty, or was it null?
        // You CANNOT tell — this is why null must not be inserted.
    }


    // ----------------------------------------------------------------
    // Utility: heapSort — sort any Collection using a PriorityQueue
    // ----------------------------------------------------------------
    static <E extends Comparable<E>> List<E> heapSort(Collection<E> c) {
        Queue<E> pq = new PriorityQueue<>(c);   // PriorityQueue orders automatically
        List<E> result = new ArrayList<>();

        while (!pq.isEmpty())
            result.add(pq.remove());            // smallest always comes out first

        return result;
    }
}
