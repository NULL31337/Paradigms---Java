package info.kgeorgiy.ja.nagibin.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static info.kgeorgiy.ja.nagibin.hello.HelloUDPUtils.*;

public class HelloUDPServer implements HelloServer {
    private DatagramSocket datagramSocket;
    private ExecutorService executors;
    private ExecutorService listener;
    private int bufferSize;

    public static void main(String[] args) {
        if (isNotValid(args, 2)) {
            return;
        }

        try (HelloUDPServer helloUDPServer = new HelloUDPServer()) {
            helloUDPServer.start(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } catch (NumberFormatException e) {
            System.err.println("Args must be integer");
        }
    }

    @Override
    public void start(int port, int threads) {
        try {
            datagramSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.err.println("Socket could not be opened on port: " + port);
            return;
        }

        executors = Executors.newFixedThreadPool(threads);
        listener = Executors.newSingleThreadExecutor();

        try {
            bufferSize = datagramSocket.getReceiveBufferSize();
        } catch (SocketException ignored) {
        }

        listener.submit(generateListenerTask());
    }

    private Runnable generateListenerTask() {
        return () -> {
            while (!Thread.currentThread().isInterrupted() && !datagramSocket.isClosed()) {
                DatagramPacket datagramPacket = new DatagramPacket(new byte[bufferSize], bufferSize);
                try {
                    datagramSocket.receive(datagramPacket);
                } catch (IOException e) {
                    if (!datagramSocket.isClosed()) {
                        System.err.println("I/O error while receiving packet: " + e.getMessage());
                    }
                }
                executors.submit(generateExecutorTask(datagramPacket));
            }
        };
    }

    private Runnable generateExecutorTask(DatagramPacket datagramPacket) {
        return () -> {
            String message = "Hello, " + generateString(datagramPacket);
            setAndSend(datagramPacket, datagramSocket, message);
        };
    }

    @Override
    public void close() {
        executors.shutdownNow();
        listener.shutdownNow();
        datagramSocket.close();

        try {
            if (!executors.awaitTermination(1, TimeUnit.SECONDS)
                    || !listener.awaitTermination(1, TimeUnit.SECONDS)) {
                System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException e) {
            System.err.println("Pool did not terminate");
        }
    }
}
