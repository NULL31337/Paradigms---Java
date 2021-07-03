package queue;


public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        while(!isEmpty()){
            dequeue();
        }
    }

    public Queue getNth(int n) {
        return nth(n, Mode.get);
    }

    public Queue removeNth(int n){
        return nth(n, Mode.remove);
    }

    public void dropNth(int n){
        nth(n, Mode.drop);
    }

    private AbstractQueue nth(int n, Mode mode) {
        AbstractQueue tmp = queueTypeCopy();
        int cnt = 1, size = this.size;
        while (cnt - 1 != size) {
            Object element = this.dequeue();
            if (cnt % n == 0 && (mode == Mode.get || mode == Mode.remove)){
                tmp.enqueue(element);
            }
            if (cnt % n != 0 || (mode != Mode.remove  && mode != Mode.drop)) {
                this.enqueue(element);
            }
            cnt++;
        }
        return tmp;
    }

    private enum Mode{
        get,
        remove,
        drop
    }

    protected abstract AbstractQueue queueTypeCopy();
}
