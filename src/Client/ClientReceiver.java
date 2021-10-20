package Client;

import java.io.IOException;
import java.net.*;

public class ClientReceiver extends Thread {

    private  DatagramSocket datagramSocket;
    private  byte[] buffer = new byte[256];

    public ClientReceiver(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void run(){
        while (true){
            try {
                DatagramPacket datagramPacket =new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                String messageFromServer = new String(datagramPacket.getData(),0,datagramPacket.getLength());
                System.out.println(messageFromServer);
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }

//    public static void main(String[] args) throws SocketException {
//        DatagramSocket datagramSocket = new DatagramSocket(1234);
//        ClientReceiver clientReceiver   = new ClientReceiver(datagramSocket);
//        clientReceiver.recieve();
//    }
}
