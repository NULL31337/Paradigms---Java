package queue;

import java.util.Arrays;

// Inv: n >= 0
// forall i: 0 <= i < n -> a[i] != null
public class ArrayQueueADT {
    private int head;
    private int size;
    private static Object[] elements = new Object[2];

    // Pre: element != null && queue != null
    // Post: n' = n + 1 && a[n]' == element && forall i: 0 <= i < n -> a[i]' == a[i]
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;
        elements[(queue.head + queue.size) % elements.length] = element;
        queue.size++;
        if (queue.size == elements.length) {
            resize(queue);
        }
    }

    // Pre: n > 0 && queue != null
    // Post: result == a[0]
    public static Object element(ArrayQueueADT queue) {
        return elements[queue.head];
    }

    // Pre: n > 0&& queue != null
    // Post: n' == n - 1 && forall i: 0 <= i < n' -> a[i]' == a[i + 1]
    // result == a[0]
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object answer = elements[queue.head];
        elements[queue.head] = null;
        queue.head++;
        queue.head%= elements.length;
        queue.size--;
        return answer;
    }
    // Pre: queue != null
    // Post: result = n
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // Pre: queue != null
    // Post: result = (n == 0)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // Pre: queue != null
    // Post: n = 0
    public static void clear(ArrayQueueADT queue) {
        queue.head = 0;
        queue.size = 0;
        elements = new Object[2];
    }

    // Pre: n >= 0
    // Post: result = a
    public static Object[] toArray(ArrayQueueADT queue) {
        resize(queue);
        Object[] newElements = new Object[queue.size];
        System.arraycopy(elements, 0, newElements, 0,  queue.size);
        return newElements;
    }

    // Pre: n >= 0
    // Post: result = "[a[0], a[1], ..., a[n - 1]]"
    public static String toStr(ArrayQueueADT queue) {
        return (Arrays.toString(toArray(queue)));
    }

    // Pre: element != null
    // Post: n' = n + 1 && a[n]' == element && forall i: 0 < i <= n -> a[i]' == a[i - 1]
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null;
        queue.head = (queue.head - 1 + elements.length) % elements.length;
        elements[queue.head] = element;
        queue.size++;
        if (queue.size == elements.length) {
            resize(queue);
        }
    }

    // Pre: n > 0
    // Post: result == a[0]
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return elements[(queue.head + queue.size - 1 + elements.length) % elements.length];
    }

    // Pre: n > 0
    // Post: n' = n - 1 && forall i: 0 <= i < n' -> a[i]' == a[i]
    // result == a[n]
    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;
        queue.size--;
        Object answer = elements[(queue.head + queue.size + elements.length) % elements.length];
        elements[(queue.head + queue.size + elements.length) % elements.length] = null;
        return answer;
    }

    // Post: elements.length' = size * 2
    // forall i: 0 <= i < size -> elements'[i] == a[i]
    // forall i: size <= i < elements.length' -> elements[i]' = null
    // size' == size == n && head == 0
    private static void resize(ArrayQueueADT queue) {
        if ((queue.head != 0 || queue.size == elements.length) && queue.size != 0) {
            Object[] newElements = new Object[(queue.size) * 2];
            System.arraycopy(elements, queue.head, newElements,
                    0, Math.min(elements.length - queue.head, queue.size));
            if (elements.length - queue.head < queue.size) {
                System.arraycopy(elements, 0, newElements, elements.length - queue.head,
                        queue.head + queue.size - elements.length);
            }
            elements = newElements;
            queue.head = 0;
        }
    }
}
