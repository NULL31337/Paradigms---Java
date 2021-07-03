package queue;
import static queue.ArrayQueueADT.*;

public class ArrayQueueADTTest {
    public static void main(String[] args) {
        // N > 2
        final int N = 10;
        ArrayQueueADT queue = new ArrayQueueADT();
        System.out.println("Try push " + N + " elements");
        for (int i = 1; i <= N; i++) {
            enqueue(queue, i);
            System.out.println("Element: " + element(queue) + ", Size: " + size(queue));
        }
        System.out.println("Try erase " + (N - 2) + " elements");
        for (int i = 2; i < N; i++) {
            System.out.println("Erase element: " + dequeue(queue)  + ", Next element: " + element(queue) +
                    ", Size: " + size(queue));
        }
        System.out.println("Try isEmpty + clear");
        System.out.println("isEmpty: " + isEmpty(queue) + ", Size: " + size(queue));
        clear(queue);
        System.out.println("isEmpty: " + isEmpty(queue) + ", Size: " + size(queue));
    }
}