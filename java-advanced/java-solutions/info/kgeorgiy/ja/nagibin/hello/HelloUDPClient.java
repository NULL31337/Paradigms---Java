package info.kgeorgiy.ja.nagibin.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static info.kgeorgiy.ja.nagibin.hello.HelloUDPUtils.*;

public class HelloUDPClient implements HelloClient {
    private final static Pattern PATTERN = Pattern.compile("\\D*(\\d+)\\D+(\\d+)\\D*");

    public static void main(String[] args) {
        if (isNotValid(args, 5)) {
            return;
        }

        try {
            new HelloUDPClient().run(
                    args[0],
                    Integer.parseInt(args[1]),
                    args[2],
                    Integer.parseInt(args[3]),
                    Integer.parseInt(args[4])
            );
        } catch (NumberFormatException e) {
            System.err.println("Args must be integer at position: 1, 3, 4 ");
        }
    }

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        try {
            ExecutorService executors = Executors.newFixedThreadPool(threads);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName(host), port);

            IntStream.range(0, threads)
                    .forEach(it -> executors.submit(generateExecutorTask(it, requests, prefix, inetSocketAddress)));

            executors.shutdownNow();
            try {
                if (!executors.awaitTermination(42, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            } catch (InterruptedException e) {
                System.err.println("Pool did not terminate");
            }
        } catch (UnknownHostException e) {
            System.err.println("Could not find IP for host: " + host);
        }

    }

    private Runnable generateExecutorTask(
            int threadIndex,
            int requests, String prefix,
            InetSocketAddress inetSocketAddress
    ) {
        return () -> {
            try (DatagramSocket datagramSocket = new DatagramSocket()) {
                datagramSocket.setSoTimeout(SOCKET_TIMEOUT);

                int bufferSize = datagramSocket.getReceiveBufferSize();
                DatagramPacket packet = new DatagramPacket(new byte[bufferSize], bufferSize, inetSocketAddress);

                for (int requestIndex = 0; requestIndex < requests; ++requestIndex) {
                    String request = prefix + threadIndex + "_" + requestIndex;
                    while (true) {
                        setAndSend(packet, datagramSocket, request);

                        try {
                            packet.setData(new byte[bufferSize]);
                            datagramSocket.receive(packet);
                        } catch (IOException e) {
                            System.err.println("I/O error while receiving packet: " + e.getMessage());
                        }

                        String response = generateString(packet);
                        Matcher matcher = PATTERN.matcher(response);

                        if (matcher.matches()
                                && matcher.group(1).equals(Integer.toString(threadIndex))
                                && matcher.group(2).equals(Integer.toString(requestIndex))) {
                            System.out.println("Received message: " + response);
                            break;
                        }
                    }
                }
            } catch (SocketException e) {
                System.err.println("Can not open socket: " + e.getMessage());
            }
        };
    }
}
