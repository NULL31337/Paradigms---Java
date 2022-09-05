package info.kgeorgiy.ja.nagibin.arrayset;

import java.util.*;

public class ReverseList<E> extends AbstractList<E> implements RandomAccess {
    private final List<E> list;
    private final boolean isReverse;

    ReverseList(List<E> list, boolean isReverse) {
        this.list = Collections.unmodifiableList(list);
        this.isReverse = isReverse;
    }

    ReverseList(ReverseList<E> list, boolean needReverse) {
        this.list = list.list;
        this.isReverse = list.isReverse ^ needReverse;
    }

    public ReverseList(NavigableSet<E> set) {
        this(new ArrayList<>(set), false);
    }

    @Override
    public ReverseList<E> subList(int fromIndex, int toIndex) {
        int realToIndex, realFromIndex;
        if (isReverse) {
            realToIndex = getIndex(toIndex - 1);
            realFromIndex = getIndex(fromIndex) + 1;
        } else {
            realToIndex = getIndex(fromIndex);
            realFromIndex = getIndex(toIndex);
        }
        return new ReverseList<>(list.subList(realToIndex, realFromIndex), isReverse);
    }

    @Override
    public int size() {
        return list.size();
    }

    private int getIndex(int index) {
        return (isReverse ? (size() - index - 1) : index);
    }

    @Override
    public E get(int index) {
        return list.get(getIndex(index));
    }
}
