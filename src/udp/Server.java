package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        // write your code here
        // OPEN AN UDP SOCKET
        int portNumber = 8081;
        String hostName = "localhost"; // 127.0.0.1
        DatagramSocket socket = new DatagramSocket(portNumber);

        // CREATE A DATAGRAM PACKET AND RECEIVE DATA FROM THE THE SOCKET
        byte[] recvBuffer = new byte[1024];

        while (socket.isBound()) {
            DatagramPacket receivedPacket = new DatagramPacket(recvBuffer, recvBuffer.length);

            System.out.println("Waiting for packet...");
            socket.receive(receivedPacket); // blocking method!

            String receivedString = new String(receivedPacket.getData(), 0, receivedPacket.getLength());
            System.out.println("Received: " + receivedString);

            //Send a message to our client
            InetAddress address = receivedPacket.getAddress();
            int port = receivedPacket.getPort();
            System.out.println("port = " + port);
            String response = "Hello i'm replying with " + receivedString;
            byte[] responseBytes = response.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, port);
            socket.send(responsePacket);

        }

        // CLOSE THE SOCKET
        socket.close();

    }
}
