package queue;

public class ArrayQueueModuleTest {
    public static void main(String[] args) {
        // N > 2
        final int N = 10;
        System.out.println("Try push " + N + " elements");
        for (int i = 1; i <= N; i++) {
            ArrayQueueModule.enqueue(i);
            System.out.println("Element: " + ArrayQueueModule.element() +
                    ", Size: " + ArrayQueueModule.size());
        }
        System.out.println(ArrayQueueModule.toStr());
        System.out.println("Try erase " + (N - 2) + " elements");
        for (int i = 2; i < N; i++) {
            System.out.println("Erase element: " + ArrayQueueModule.dequeue()  +
                    ", Next element: " + ArrayQueueModule.element() +
                    ", Size: " + ArrayQueueModule.size());
        }
        System.out.println(ArrayQueueModule.toStr());
        System.out.println("Try isEmpty + clear");
        System.out.println("isEmpty: " + ArrayQueueModule.isEmpty() + ", Size: " + ArrayQueueModule.size());
        ArrayQueueModule.clear();
        System.out.println("isEmpty: " + ArrayQueueModule.isEmpty() + ", Size: " + ArrayQueueModule.size());
    }
}