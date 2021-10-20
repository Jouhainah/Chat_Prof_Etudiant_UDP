package Server;

import model.Etudiant;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerClass {
    private DatagramSocket datagramSocket;
    private static List<Etudiant> etudiantList;
    private byte[] buffer =new byte[256];
    int port ;
    public ServerClass(int port) {

        try {

            this.datagramSocket = new DatagramSocket(port);
            this.port = port;
            this.etudiantList = new ArrayList<Etudiant>();

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void recieveThenSend()  {
        while (true){
            try{
                DatagramPacket datagramPacket=new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String messageFromClient = new String(datagramPacket.getData(),0,datagramPacket.getLength());
                System.out.println("Message from client:"+ messageFromClient);

                if(messageFromClient.startsWith("##")) {
                    Etudiant etudiantSender = new Etudiant();
                    etudiantSender.setIPAddress(inetAddress);
                    etudiantSender.setPort(port);
                    etudiantSender.setEtatConnexion("Connected");
                    etudiantSender.setLogin(messageFromClient.substring(2));
                    etudiantList.add(etudiantSender);

                    byte[] loginMsgByte = (messageFromClient.substring(2)+"#connected").getBytes( "UTF-8" );
                    DatagramPacket msgLogin = new DatagramPacket(loginMsgByte, loginMsgByte.length,inetAddress,port);
                    datagramSocket.send(msgLogin);
                }

                datagramPacket=new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                String msgFromClient = new String(datagramPacket.getData(),0,datagramPacket.getLength());

                if(msgFromClient.startsWith("@#")){

                    String loginReciever =msgFromClient.split("@#")[1];
                    String message =msgFromClient.split("@#")[2];
                    datagramPacket = new DatagramPacket(buffer,buffer.length, InetAddress.getByName("localhost"),1234);
                    datagramSocket.send(datagramPacket);

                }

                if(msgFromClient.startsWith("#LI")){
                    String list ="";
                    for(Etudiant e : etudiantList) {
                        list = list +"\n"+ e.getLogin();
                    }
                    byte[] listTosend = ("\n #connected List "+list).getBytes( "UTF-8" );
                    DatagramPacket messageList = new DatagramPacket(listTosend, listTosend.length,
                            inetAddress,port);
                    datagramSocket.send(messageList);

                }

            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException {
        //DatagramSocket datagramSocket=new DatagramSocket(4040);
        etudiantList = new ArrayList<Etudiant>();
        ServerClass serverClass = new ServerClass(4040);
        serverClass.recieveThenSend();
    }

}
