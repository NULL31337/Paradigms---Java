package info.kgeorgiy.ja.nagibin.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class HelloUDPUtils {

    /**
     * constant for socket timeout
     */
    public static final int SOCKET_TIMEOUT = 322;

    /**
     * Return true if count of args equals length and any args is nonNull.
     *
     * @param args   arguments to be checked
     * @param length length of args
     * @return {@code true} if count of args equals length and any args is nonNull
     */
    public static boolean isNotValid(String[] args, int length) {
        if (args == null || args.length != length || Arrays.stream(args).anyMatch(Objects::isNull)) {
            System.err.println("Expected " + length + " not null args");
            return true;
        }
        return false;
    }

    /**
     * Generate string by datagramPacket.
     *
     * @param datagramPacket {@link DatagramPacket} from generate
     * @return {@link String} generated from datagramPacket
     */
    public static String generateString(DatagramPacket datagramPacket) {
        return new String(
                datagramPacket.getData(),
                datagramPacket.getOffset(),
                datagramPacket.getLength(),
                StandardCharsets.UTF_8);
    }

    /**
     * @param datagramPacket {@link DatagramPacket} to send
     * @param datagramSocket {@link DatagramSocket} to send
     * @param string         {@link String} to send
     */
    public static void setAndSend(DatagramPacket datagramPacket, DatagramSocket datagramSocket, String string) {
        try {
            datagramPacket.setData(string.getBytes(StandardCharsets.UTF_8));
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            if (!datagramSocket.isClosed()) {
                System.err.println("I/O error while sending packet: " + e.getMessage());
            }
        }
    }
}
