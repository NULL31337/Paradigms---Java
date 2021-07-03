package queue;
import java.util.Arrays;

// Inv: n >= 0
// forall i: 0 <= i < n -> a[i] != null
public class ArrayQueueModule {
    private static int head;
    private static int size;
    private static Object[] elements = new Object[2];

    // Pre: element != null
    // Post: n' = n + 1 && a[n]' == element && forall i: 0 <= i < n -> a[i]' == a[i]
    public static void enqueue(Object element) {
        assert element != null;
        elements[(head + size) % elements.length] = element;
        size++;
        if (size == elements.length) {
            resize();
        }
    }

    // Pre: n > 0
    // Post: result == a[0]
    public static Object element() {
        return elements[head];
    }

    // Pre: n > 0
    // Post: n' = n - 1 && forall i: 0 <= i < n' -> a[i]' == a[i + 1]
    // result == a[0]
    public static Object dequeue() {
        assert size > 0;
        Object answer = elements[head];
        elements[head] = null;
        head++;
        head%= elements.length;
        size--;
        return answer;
    }

    // Post: result = n
    public static int size() {
        return size;
    }

    // Post: result = (n == 0)
    public static boolean isEmpty() {
        return size == 0;
    }

    // Post: n = 0
    public static void clear() {
        head = 0;
        size = 0;
        elements = new Object[2];
    }

    // Pre: n >= 0
    // Post: result = a
    public static Object[] toArray() {
        resize();
        Object[] newElements = new Object[size];
        System.arraycopy(elements, 0, newElements, 0, size);
        return newElements;
    }

    // Pre: n >= 0
    // Post: result = "[a[0], a[1], ..., a[n - 1]]"
    public static String toStr() {
        return (Arrays.toString(toArray()));
    }

    // Pre: element != null
    // Post: n' = n + 1 && a[n]' == element && forall i: 0 < i <= n -> a[i]' == a[i - 1]
    public static void push(Object element) {
        assert element != null;
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
        if (size == elements.length) {
            resize();
        }
    }

    // Pre: n > 0
    // Post: result == a[0]
    public static Object peek() {
        assert size > 0;
        return elements[(head + size - 1 + elements.length) % elements.length];
    }

    // Pre: n > 0
    // Post: n' = n - 1 && forall i: 0 <= i < n' -> a[i]' == a[i]
    // result == a[n]
    public static Object remove() {
        assert size > 0;
        size--;
        Object answer = elements[(head + size + elements.length) % elements.length];
        elements[(head + size + elements.length) % elements.length] = null;
        return answer;
    }

    // Post: elements.length' = size * 2
    // forall i: 0 <= i < size -> elements'[i] == a[i]
    // forall i: size <= i < elements.length' -> elements[i]' = null
    // size' == size == n && head == 0
    private static void resize() {
        if ((head != 0 || size == elements.length) && size != 0) {
            Object[] newElements = new Object[(size) * 2];
            System.arraycopy(elements, head, newElements, 0, Math.min(elements.length - head, size));
            if (elements.length - head < size) {
                System.arraycopy(elements, 0, newElements, elements.length - head,
                        head + size - elements.length);
            }
            elements = newElements;
            head = 0;
        }
    }
}
