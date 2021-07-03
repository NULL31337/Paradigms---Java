package queue;

public class ArrayQueueClassTest {
    public static void main(String[] args) {
        // N > 2
        final int N = 10;
        ArrayQueue queue = new ArrayQueue();
        System.out.println("Try push " + N + " elements");
        for (int i = 1; i <= N; i++) {
            ArrayQueueModule.enqueue(i);
            System.out.println("Element: " + queue.element() + ", Size: " + queue.size());
        }
        System.out.println("Try erase " + (N - 2) + " elements");
        for (int i = 2; i < N; i++) {
            System.out.println("Erase element: " + queue.dequeue()  + ", Next element: " + queue.element() +
                    ", Size: " + queue.size());
        }
        System.out.println("Try isEmpty + clear");
        System.out.println("isEmpty: " + queue.isEmpty() + ", Size: " + queue.size());
        queue.clear();
        System.out.println("isEmpty: " + queue.isEmpty() + ", Size: " + queue.size());
    }
}