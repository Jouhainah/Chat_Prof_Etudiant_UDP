package Client;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientSender extends Thread{

    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;

    public ClientSender(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void run(){
        System.out.println("Welcome");
        Scanner scanner = new Scanner(System.in);
        while (true){
            try {
                String messageToSend = scanner.nextLine();
                buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket =new DatagramPacket(buffer,buffer.length,inetAddress,4040);
                datagramSocket.send(datagramPacket);
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }

//    public static void main(String[] args) throws UnknownHostException, SocketException {
//        DatagramSocket datagramSocket = new DatagramSocket();
//        InetAddress inetAddress = InetAddress.getByName("localhost");
//        ClientSender clientSender = new ClientSender(datagramSocket,inetAddress);
//        //System.out.println("send datagram packet to server.");
//        clientSender.send();
//    }
}
