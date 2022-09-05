package info.kgeorgiy.ja.nagibin.arrayset;

import java.util.*;

public class ArraySet<E> extends AbstractSet<E> implements NavigableSet<E> {
    private final ReverseList<E> list;
    private final Comparator<? super E> comparator;

    public ArraySet(ReverseList<E> list, Comparator<? super E> comparator) {
        this.list = list;
        this.comparator = comparator;
    }

    public ArraySet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        final NavigableSet<E> navigableSet = new TreeSet<>(comparator);
        navigableSet.addAll(collection);
        this.list = new ReverseList<>(navigableSet);
        this.comparator = comparator;
    }

    public ArraySet(Comparator<? super E> comparator) {
        this(Collections.emptyList(), comparator);
    }

    public ArraySet(Collection<? extends E> collection) {
        this(collection, null);
    }

    public ArraySet() {
        this(Collections.emptyList(), null);
    }


    private E get(int index) {
        E ans = getOrNull(index);
        if (ans != null) {
            return ans;
        }
        throw new NoSuchElementException();
    }

    private boolean isCorrectIndex(int index) {
        return 0 <= index && index < list.size();
    }

    private E getOrNull(int index) {
        return isCorrectIndex(index) ? list.get(index) : null;
    }

    private int binarySearch(E element) {
        return Collections.binarySearch(list, element, comparator);
    }

    private int findIndex(E element, boolean inclusive, boolean afterInsertPoint) {
        int index = binarySearch(element);
        if (index < 0) {
            return (-index - (afterInsertPoint ? 1 : 2));
        }
        if (inclusive) {
            return index;
        }
        return index + (afterInsertPoint ? 1 : - 1);
    }

    @Override
    public E lower(E e) {
        return getOrNull(findIndex(e, false, false));
    }

    @Override
    public E floor(E e) {
        return getOrNull(findIndex(e, true, false));
    }

    @Override
    public E ceiling(E e) {
        return getOrNull(findIndex(e, true, true));
    }

    @Override
    public E higher(E e) {
        return getOrNull(findIndex(e, false, true));
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException("ArraySet is immutable ordered set");
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException("ArraySet is immutable ordered set");
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        return new ArraySet<>(new ReverseList<>(list, true), Collections.reverseOrder(comparator));
    }

    @Override
    public Iterator<E> descendingIterator() {
        return descendingSet().iterator();
    }

    @SuppressWarnings("unchecked")
    private int compare(E left, E right) {
        return comparator != null ? comparator.compare(left, right) : ((Comparable<E>) left).compareTo(right);
    }

    private NavigableSet<E> subList(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        int fromIndex = findIndex(fromElement, fromInclusive, true);
        int toIndex = findIndex(toElement, toInclusive, false);
        return toIndex < fromIndex ? new ArraySet<>(comparator) :
                new ArraySet<>(list.subList(fromIndex, toIndex + 1), comparator);
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException("Invalid interval borders!");
        }
        return subList(fromElement, fromInclusive, toElement, toInclusive);
    }

    private NavigableSet<E> headTailSet(E element, boolean inclusive, boolean isHead) {
        if (isEmpty()) {
            return this;
        }
        return isHead ? subList(first(), true, element, inclusive) :
                subList(element, inclusive, last(), true);
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return headTailSet(toElement, inclusive, true);
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return headTailSet(fromElement, inclusive, false);
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public E first() {
        return get(0);
    }

    @Override
    public E last() {
        return get(size() - 1);
    }

    @Override
    public int size() {
        return list.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean contains(Object o) {
        return 0 <= binarySearch((E) o);
    }
}
