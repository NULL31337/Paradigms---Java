package queue;
/*
Model:
    [a1, a2, ...an]
    n -- размер дека
Inv:
    n >= 0
    forall i = 1..n: a[i] != null
Immutable:
    forall i = 1..n: a'[i] == a[i] && n' == n && a'.getClass() == a.getClass()
*/
public interface Queue {

    // Pre: element != null
    // Post: n' = n + 1 && a[n]' == element && forall i: 0 <= i < n -> a[i]' == a[i]
    void enqueue(Object element);

    // Pre: n > 0
    // Post: result = a[0] && Immutable
    Object element();

    // Pre: n > 0
    // Post: n' = n - 1 && forall i: 0 <= i < n' -> a[i]' == a[i + 1]
    // result == a[0]
    Object dequeue();

    // Post: result = n && Immutable
    int size();

    // Post: result = (n == 0) && Immutable
    boolean isEmpty();

    // Post: n = 0
    // a'.getClass() == a.getClass()
    void clear();

    // Pre: o > 0 && o != null && o -> Integer
    // Post: result = b
    // b.size == n / o - 1
    // b.getClass() == a.getClass()
    // forall i: i > 0 && i * o < a.size() (b[i - 1] == a[i * o])
    Queue getNth(int o);

    // Pre: n > 0 && n != null && n -> Integer
    // Post: i = 1..n': a[i] != null && a'[i] == a[i + (int)(i / o)]
    // n' == n - n / o && a'.getClass() == a.getClass()
    void dropNth(int o);

    // Pre: o > 0 && o != null && o -> Integer
    // Post: result = getNth(o)
    // a.dropNth(o)
    Queue removeNth(int o);

}
