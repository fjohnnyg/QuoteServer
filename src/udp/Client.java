package udp;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class Client {

    public static void main(String[] args) throws IOException {


        int portNumber;
        String hostName; // 127.0.0.1
        DatagramSocket socket = new DatagramSocket();
        // CREATE A DATAGRAM PACKET AND SEND IT FROM THE SOCKET
        byte[] message = "Hello World this is another message".getBytes();
        //reads a string from console
        String input = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(Message.PORT_NUMBER);
        portNumber = Integer.parseInt(br.readLine());

        System.out.println(Message.HOST);
        hostName = br.readLine();

        File file = new File("src/udp/quotes.txt");
        Scanner sc = new Scanner(file);

        List<String> quotes = new ArrayList<>();

        while (sc.hasNextLine()) {
            quotes.add(sc.nextLine());
        }
        sc.close();

        while (!input.equals("exit")) {
            System.out.println(Message.ASK_SENTENCE);
            input = br.readLine();
            int randomSentence = (int) (Math.random() * (quotes.size()));
            if (input.equals("hit me"))
                message = quotes.get(randomSentence).getBytes();
            else message = Message.ERROR_MESSAGE.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(message, message.length, InetAddress.getByName(hostName), portNumber);
            socket.send(sendPacket);

            System.out.println(quotes.get(randomSentence));

            // RECEIVE A PACKET FROM THE SERVER
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket);
            String receivedString = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(receivedString);
        }
    }
}
