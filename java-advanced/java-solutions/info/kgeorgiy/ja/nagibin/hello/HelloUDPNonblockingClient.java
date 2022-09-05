package info.kgeorgiy.ja.nagibin.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.stream.IntStream;

import static java.net.SocketOptions.SO_TIMEOUT;

public class HelloUDPNonblockingClient implements HelloClient {

    private record DatagramChannelAttachment(int threadId, int requestId) {
    }

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        try (Selector selector = Selector.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName(host), port);

            IntStream.range(0, threads)
                    .forEach(it -> {
                        try {
                            DatagramChannel datagramChannel = DatagramChannel.open();
                            datagramChannel.connect(inetSocketAddress);
                            datagramChannel.configureBlocking(false);
                            datagramChannel.register(selector, SelectionKey.OP_WRITE, new DatagramChannelAttachment(it, 0));
                        } catch (IOException e) {
                            System.err.println("TODO");
                        }
                    });

            while (!Thread.interrupted() && !selector.keys().isEmpty()) {
                selector.select(SO_TIMEOUT);
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                if (selectionKeySet.isEmpty()) {
                    selector.keys().forEach(it -> it.interestOps(SelectionKey.OP_WRITE));
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("TODO");
        } catch (IOException e) {
            System.err.println("TODO");
            System.err.println("TODO");
        }

    }
}
