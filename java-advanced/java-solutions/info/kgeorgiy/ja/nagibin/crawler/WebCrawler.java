package info.kgeorgiy.ja.nagibin.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.*;
import java.util.*;
import java.util.function.Function;

public class WebCrawler implements AdvancedCrawler {
    private final Downloader downloader;
    private final ExecutorService downloaders, extractors;
    private final ConcurrentMap<String, HostQueue> hosts = new ConcurrentHashMap<>();
    private final int perHost;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        if (downloaders <= 0 || extractors <= 0 || perHost <= 0) {
            throw new IllegalArgumentException(
                    "Number of downloaders, extractors and link per host must be greater then zero"
            );
        }

        this.downloader = downloader;
        this.downloaders = Executors.newFixedThreadPool(downloaders);
        this.extractors = Executors.newFixedThreadPool(extractors);
        this.perHost = perHost;
    }

    @Override
    public Result download(String url, int depth) {
        return new CrawlDownloader(false).download(url, depth);
    }

    @Override
    public Result download(String url, int depth, List<String> hosts) {
        hosts.forEach(it -> this.hosts.put(it, new HostQueue()));
        return new CrawlDownloader(true).download(url, depth);
    }

    private final class HostQueue {
        private final Queue<Runnable> waitingQueue = new ConcurrentLinkedQueue<>();
        private int uses = 0;

        private void add(Runnable task) {
            if (uses >= perHost) {
                waitingQueue.add(task);
            } else {
                uses++;
                downloaders.submit(task);
            }
        }

        private void runTaskFromQueue() {
            Runnable task = waitingQueue.poll();
            if (task == null) {
                uses--;
            } else {
                downloaders.submit(task);
            }
        }
    }

    private class CrawlDownloader {
        private final boolean ignoreNewHosts;
        private final Set<String> processedLinks = ConcurrentHashMap.newKeySet();
        private final Set<String> downloadedLinks = ConcurrentHashMap.newKeySet();
        private final Map<String, IOException> exceptions = new ConcurrentHashMap<>();
        private final Queue<String> layerLinks = new ConcurrentLinkedQueue<>();
        private final Phaser phaser = new Phaser(1);
        private int localDepth;

        CrawlDownloader(boolean ignoreNewHosts) {
            this.ignoreNewHosts = ignoreNewHosts;
        }

        public Result download(String url, int depth) {
            if (depth <= 0) {
                throw new IllegalArgumentException("Depth must be greater then zero");
            }
            localDepth = depth;
            processedLinks.add(url);
            layerLinks.add(url);

            for (; localDepth > 0; localDepth--) {
                List<String> currentLayerLinks = new ArrayList<>(layerLinks);
                layerLinks.clear();
                currentLayerLinks
                        .forEach(this::recursiveDownload);

                phaser.arriveAndAwaitAdvance();
            }

            return new Result(new ArrayList<>(downloadedLinks), exceptions);
        }

        private void recursiveDownload(String currentUrl) {
            try {
                String host = URLUtils.getHost(currentUrl);
                HostQueue hostQueue =  ignoreNewHosts ?
                        hosts.get(host) :
                        hosts.computeIfAbsent(host, it -> new HostQueue());
                if (hostQueue == null) {
                    return;
                }
                phaser.register();
                hostQueue.add(generateHostTask(currentUrl, hostQueue));
            } catch (MalformedURLException e) {
                exceptions.put(currentUrl, e);
            }
        }

        private Runnable generateHostTask(String currentUrl, HostQueue hostQueue) {
            return () -> {
                try {
                    Thread.sleep(1);
                    Document downloaded = downloader.download(currentUrl);
                    downloadedLinks.add(currentUrl);

                    if (localDepth != 1) {
                        phaser.register();
                        extractors.submit(generateExtractorTask(downloaded, currentUrl));
                    }
                } catch (IOException e) {
                    exceptions.put(currentUrl, e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    phaser.arriveAndDeregister();
                    hostQueue.runTaskFromQueue();
                }
            };
        }

        private Runnable generateExtractorTask(Document downloaded, String currentUrl) {
            return () -> {
                try {
                    for (String url : downloaded.extractLinks()) {
                        if (processedLinks.add(url)) {
                            layerLinks.add(url);
                        }
                    }
                } catch (IOException e) {
                    exceptions.put(currentUrl, e);
                } finally {
                    phaser.arriveAndDeregister();
                }
            };
        }
    }

    @Override
    public void close() {
        extractors.shutdownNow();
        downloaders.shutdownNow();
        try {
            if (!extractors.awaitTermination(1, TimeUnit.SECONDS)
                    || !downloaders.awaitTermination(1, TimeUnit.SECONDS)) {
                System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException e) {
            System.err.println("Pool did not terminate");
        }
    }

    private static final int DEFAULT_VALUE = 1;

    public static void main(String[] args) {
        if (args == null || args.length == 0 || args.length > 5 || args[0] != null) {
            System.err.println("Expected args in format: url [depth [downloads [extractors [perHost]]]]");
            return;
        }

        final Function<Integer, Integer> parseArgs = it -> it >= args.length ? DEFAULT_VALUE : Integer.parseInt(args[it]);

        try (final Crawler crawler = new WebCrawler(
                new CachingDownloader(),
                parseArgs.apply(2),
                parseArgs.apply(3),
                parseArgs.apply(4)
        )) {
            crawler.download(args[0], parseArgs.apply(1));
        } catch (final IOException e) {
            System.err.println("Error while initializing CachingDownloader: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Expected numbers in args but found: " + e.getMessage());
        }
    }
}
