package Client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    static ClientReceiver clientReceiver;
    static ClientSender clientSender ;
    Boolean login = false;

    public Client() throws IOException {
        InetAddress inetAddress = InetAddress.getByName("localhost");
        DatagramSocket datagramSocket = new DatagramSocket();
        while (!login) {
            Scanner scanner = new Scanner(System.in);
            String messageToSend = scanner.nextLine();
            if (messageToSend.startsWith("##")) {
                login = true;
            }
        }

            this.clientSender = new ClientSender(datagramSocket, inetAddress);
            this.clientReceiver = new ClientReceiver(datagramSocket);

    }
    public static void main(String[] args) throws IOException {

        Client client = new Client() ;
        clientSender.start();
        clientReceiver.start();
    }

}
