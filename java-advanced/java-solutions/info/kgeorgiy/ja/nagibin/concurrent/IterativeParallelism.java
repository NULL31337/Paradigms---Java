package info.kgeorgiy.ja.nagibin.concurrent;

import info.kgeorgiy.java.advanced.concurrent.AdvancedIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IterativeParallelism implements AdvancedIP {
    private final ParallelMapper mapper;

    public IterativeParallelism(ParallelMapper mapper) {
        this.mapper = mapper;
    }

    public IterativeParallelism() {
        mapper = null;
    }

    @Override
    public String join(
            final int threads,
            final List<?> values
    ) throws InterruptedException {
        return threadSolve(threads, values,
                it -> it.map(Objects::toString).collect(Collectors.joining()),
                it -> it.collect(Collectors.joining()));
    }

    private <T, U> List<U> simpleCollector(
            final int threads,
            final List<? extends T> values,
            final Function<Stream<? extends T>, Stream<? extends U>> function
    ) throws InterruptedException {
        return threadSolve(threads, values, it -> function.apply(it).collect(Collectors.toList()),
                it -> it.flatMap(Collection::stream).collect(Collectors.toList()));
    }

    @Override
    public <T> List<T> filter(
            final int threads,
            final List<? extends T> values,
            final Predicate<? super T> predicate
    ) throws InterruptedException {
        return simpleCollector(threads, values, it -> it.filter(predicate));
    }

    @Override
    public <T, U> List<U> map(
            final int threads,
            final List<? extends T> values,
            final Function<? super T, ? extends U> f
    ) throws InterruptedException {
        return simpleCollector(threads, values, it -> it.map(f));
    }

    @Override
    public <T> T minimum(
            final int threads,
            final List<? extends T> values,
            final Comparator<? super T> comparator
    ) throws InterruptedException {
        final Function<Stream<? extends T>, ? extends T> currentFunction =
                it -> it.min(comparator).orElseThrow(NoSuchElementException::new);
        return threadSolve(threads, values, currentFunction, currentFunction);
    }

    @Override
    public <T> T maximum(
            final int threads,
            final List<? extends T> values,
            final Comparator<? super T> comparator
    ) throws InterruptedException {
        return minimum(threads, values, comparator.reversed());
    }

    @Override
    public <T> boolean any(
            final int threads,
            final List<? extends T> values,
            final Predicate<? super T> predicate
    ) throws InterruptedException {
        return !all(threads, values, predicate.negate());
    }

    @Override
    public <T> boolean all(
            final int threads,
            final List<? extends T> values,
            final Predicate<? super T> predicate
    ) throws InterruptedException {
        return threadSolve(threads, values, it -> it.allMatch(predicate), it -> it.allMatch(Boolean::booleanValue));
    }

    private <T, U> U threadSolve(
            final int threads,
            final List<? extends T> values,
            final Function<Stream<? extends T>, ? extends U> accumulator,
            final Function<Stream<? extends U>, ? extends U> combiner
    ) throws InterruptedException {
        ConcurrentUtils.checkThreadCount(threads);
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Value size must be not empty");
        }

        final int maxThreadsCount = Math.min(threads, values.size());
        final int partSize = values.size() / maxThreadsCount;
        int lastPartSize = values.size() % maxThreadsCount;
        int currentPosition = 0;
        final List<Stream<? extends T>> parts = new ArrayList<>();

        for (int i = 0; i < maxThreadsCount; i++) {
            int currentPartSize = partSize;

            if (lastPartSize > 0) {
                lastPartSize--;
                currentPartSize++;
            }

            parts.add(values.subList(currentPosition, currentPosition + currentPartSize).stream());
            currentPosition += currentPartSize;
        }

        List<U> result;
        if (mapper != null) {
            result = mapper.map(accumulator, parts);
        } else {
            result = new ArrayList<>(Collections.nCopies(maxThreadsCount, null));
            final List<Thread> threadsList = IntStream.range(0, maxThreadsCount).mapToObj(it -> {
                        final Thread currentThread = new Thread(() -> result.set(it, accumulator.apply(parts.get(it))));
                        currentThread.start();
                        return currentThread;
                    }
            ).toList();

            InterruptedException exceptions = null;
            for (final Thread i : threadsList) {
                try {
                    i.join();
                } catch (InterruptedException e) {
                    if (exceptions == null) {
                        exceptions = e;
                    } else {
                        exceptions.addSuppressed(e);
                    }
                }
            }
            if (exceptions != null) {
                throw exceptions;
            }
        }
        return combiner.apply(result.stream());
    }

    @Override
    public <T> T reduce(
            final int threads,
            final List<T> values,
            final Monoid<T> monoid
    ) throws InterruptedException {
        final Function<Stream<? extends T>, T> function = (Stream<? extends T> it) ->
                it.reduce(monoid.getIdentity(), monoid.getOperator(), monoid.getOperator());
        return threadSolve(threads, values, function, function);
    }

    @Override
    public <T, R> R mapReduce(
            final int threads,
            final List<T> values,
            final Function<T, R> lift,
            final Monoid<R> monoid
    ) throws InterruptedException {
        return reduce(threads, map(threads, values, lift), monoid);
    }
}
