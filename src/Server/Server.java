package Server;
import model.Etudiant;
import model.Groupe;
import model.Message;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private DatagramSocket datagramSocket;
    static List<Etudiant> etudiantList =new ArrayList<Etudiant>();
    static List<Message> messageList = new ArrayList<Message>();
    static List<Groupe> groupeList=new ArrayList<Groupe>();

    private byte[] buffer =new byte[256];
    int port ;
    Etudiant etudiantSender ;

    public Server(int port) throws SocketException {

            this.datagramSocket = new DatagramSocket(port);
            this.port = port;
    }

    public void recieveThenSend() throws IOException {
        while (true){
            try{
                DatagramPacket datagramPacket=new DatagramPacket(buffer,buffer.length);
                DatagramPacket finalDatagramPacket = datagramPacket;
                datagramSocket.receive(datagramPacket);

                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String messageFromClient = new String(datagramPacket.getData(),0,datagramPacket.getLength());
                String loginMsg ;
                byte[] bytes;
                if (messageFromClient.startsWith("##")){
                    /*byte[] logIn = ("Please enter your login : ##login").getBytes("UTF-8");
                    DatagramPacket msgLogIn = new DatagramPacket(logIn, logIn.length,
                            inetAddress, port);
                    datagramSocket.send(msgLogIn);*/
                    String login = messageFromClient.substring(2);
                    etudiantSender = etudiantList
                            .stream()
                            .filter(etd ->
                            {
                                return (etd.getPort()==finalDatagramPacket.getPort())&&(etd.getLogin().equals(login));
                            }).findFirst().orElse( null );
                    if( null == etudiantSender ) {
                        etudiantSender = new Etudiant();
                        etudiantSender.setIPAddress(datagramPacket.getAddress());
                        etudiantSender.setPort(datagramPacket.getPort());
                        etudiantSender.setEtatConnexion("Connected");
                        etudiantSender.setLogin(login);
                        //adding new client to the list
                        etudiantList.add(etudiantSender);
                        bytes = (etudiantSender.getLogin() + "#connected").getBytes("UTF-8");

                    }else{
                        bytes = (etudiantSender.getLogin() + "#already exists").getBytes("UTF-8");
                    }
                    DatagramPacket messageConnected = new DatagramPacket(bytes, bytes.length,
                            inetAddress, port);
                    System.err.println(etudiantSender.getLogin() + " Connected, response Sent");
                    datagramSocket.send(messageConnected);
                }
                /*datagramPacket=new DatagramPacket(buffer,buffer.length);
                datagramSocket.receive(datagramPacket);
                String msgFromClient = new String(datagramPacket.getData(),0,datagramPacket.getLength());
                System.out.println("Message from "+etudiantSender.getLogin()+" after Login: "+ msgFromClient);*/

                else if(messageFromClient.startsWith("@#")){
                    Etudiant etudiantSender  = etudiantList
                            .stream()
                            .filter(etd ->
                            {
                                return etd.getPort()== datagramPacket.getPort();
                            }).findFirst().orElse( null );
                    String loginReceiver = messageFromClient.split("@#")[1];
                    Etudiant etudiantReceiver  = etudiantList
                            .stream()
                            .filter(etd ->
                            {
                                return etd.getLogin().equals(loginReceiver);
                            }).findFirst().orElse( null );
                    byte[] messageTosend;
                    if (etudiantReceiver!=null)   {
                        String msg =messageFromClient.split("@#")[2];
                        Message message = new Message(etudiantSender,msg,etudiantReceiver);
                        messageList.add(message);
                        System.out.println(messageFromClient);
                        messageTosend = ("@"+etudiantSender.getLogin()+": " +msg ).getBytes( "UTF-8" );
                        continue;
                    } else {
                        messageTosend = "client doesnt exist".getBytes();
                }
                    DatagramPacket msgTosend = new DatagramPacket(messageTosend, messageTosend.length,
                            etudiantReceiver.getIPAddress(),etudiantReceiver.getPort());
                    datagramSocket.send(msgTosend);

                }

                else if (messageFromClient.startsWith("#LI")){
                    String list ="";
                    for(Etudiant e : etudiantList) {
                        list = list +"\n"+ e.getLogin();
                    }
                    byte[] listTosend = ("\n #connected List "+list).getBytes( "UTF-8" );
                    DatagramPacket messageList = new DatagramPacket(listTosend, listTosend.length,
                            inetAddress,port);
                    datagramSocket.send(messageList);

                } else if (messageFromClient.startsWith("#HI")) {
                    String history ="";
                    for (Message m : messageList) {
                        System.out.println(m);
                        if ((m.getEtudiantSender().getIPAddress().equals(datagramPacket.getAddress()) && m.getEtudiantSender().getPort() == datagramPacket.getPort()) ||
                                (m.getEtudiantReciver().getIPAddress().equals(datagramPacket.getAddress() ) &&m.getEtudiantReciver().getPort() == datagramPacket.getPort()))
                            history += "\n from: " + m.getEtudiantSender().getLogin()+ "  : "+m.getContenu();
                    }
                } else {
                    byte[] nonValid = ("\n command not valid ").getBytes( "UTF-8" );
                    DatagramPacket messageNonValid= new DatagramPacket(nonValid, nonValid.length,
                            inetAddress,port);
                    datagramSocket.send(messageNonValid);
                }

                //datagramPacket = new DatagramPacket(buffer,buffer.length, InetAddress.getByName("localhost"),1234);
                //datagramSocket.send(datagramPacket);

            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //DatagramSocket datagramSocket=new DatagramSocket(4040);
        etudiantList = new ArrayList<Etudiant>();
        while(true){
            Server server = new Server(4040);
            server.recieveThenSend();
        }

    }


}
