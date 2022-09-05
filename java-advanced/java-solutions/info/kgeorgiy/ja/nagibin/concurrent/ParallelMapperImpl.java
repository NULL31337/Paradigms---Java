package info.kgeorgiy.ja.nagibin.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;


public class ParallelMapperImpl implements ParallelMapper {
    private static final int MAX_TASKS_COUNT = 1000;
    private final Queue<Runnable> tasksPool;
    private final List<Thread> threadsPool;

    public ParallelMapperImpl(final int threads) {
        ConcurrentUtils.checkThreadCount(threads);
        tasksPool = new ArrayDeque<>();
        threadsPool = IntStream.range(0, threads).mapToObj(it -> {
                    final Thread currentThread = new Thread(() -> {
                        try {
                            while (!Thread.interrupted()) {
                                Runnable task;
                                synchronized (tasksPool) {
                                    while (tasksPool.isEmpty()) tasksPool.wait();
                                    task = tasksPool.poll();
                                    tasksPool.notifyAll();
                                }
                                task.run();
                            }
                        } catch (InterruptedException ignored) {
                        } finally {
                            Thread.currentThread().interrupt();
                        }
                    });
                    currentThread.start();
                    return currentThread;
                }
        ).toList();
    }

    private static class ResultsCollector<T> {
        private final List<T> list;
        private int counter;

        public ResultsCollector(final int listSize) {
            this.list = new ArrayList<>(Collections.nCopies(listSize, null));
            this.counter = listSize;
        }

        public synchronized void set(final int position, final T element) {
            list.set(position, element);
            synchronized (this) {
                if (--counter <= 0) {
                    notify();
                }
            }
        }

        public synchronized List<T> getResult() throws InterruptedException {
            while (counter > 0) wait();
            return list;
        }
    }


    @Override
    public <T, R> List<R> map(
            final Function<? super T, ? extends R> f,
            final List<? extends T> args
    ) throws InterruptedException {
        final ResultsCollector<R> result = new ResultsCollector<>(args.size());
        for (int i = 0; i < args.size(); i++) {
            final int finalI = i;
            final Runnable task = () -> result.set(finalI, f.apply(args.get(finalI)));
            synchronized (tasksPool) {
                while (tasksPool.size() == MAX_TASKS_COUNT) task.wait();
                tasksPool.add(task);
                tasksPool.notifyAll();
            }
        }
        return result.getResult();
    }

    @Override
    public void close() {
        threadsPool.forEach(Thread::interrupt);
        for (Thread thread : threadsPool) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
    }
}
