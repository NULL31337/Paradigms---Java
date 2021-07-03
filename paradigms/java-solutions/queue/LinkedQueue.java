package queue;

public class LinkedQueue extends AbstractQueue {
    private Element head = null;
    private Element tail = null;
    private static class Element {
        private Element next = null;
        private final Object value;
        public Element (Object obj) {
            value = obj;
        }
    }

    @Override
    public void enqueue(Object element) {
        Element input = new Element(element);
        size++;
        if (head == null) {
            head = input;
        } else {
            tail.next = input;
        }
        tail = input;
    }

    @Override
    public Object element() {
        return head.value;
    }

    @Override
    public Object dequeue() {
        Object tmp = head.value;
        size--;
        head = (size == 0 ? tail = null : head.next);
        return tmp;
    }

    @Override
    protected AbstractQueue queueTypeCopy() {
        return new LinkedQueue();
    }
}
