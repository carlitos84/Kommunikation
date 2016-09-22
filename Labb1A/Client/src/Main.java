import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;



public class Main{

    public static void main(String args[]) {
        int port = 4445;
        //Argumenttest
        if (args.length != 1) {
            System.out.println("Usage: java QuoteClient <hostname>");
            return;
        }

        // get a datagram socket
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();

            InetAddress address = InetAddress.getByName(args[0]);

            Thread receiveT = new Thread(new MessageReceiver(socket, address, port));
            receiveT.start();

            Scanner scan = new Scanner(System.in);

            //Send messages
            boolean quit = false;
            byte[] writeBuf;
            while(!quit) {

                synchronized (System.out) {
                    System.out.print(">");
                }

                writeBuf = scan.nextLine().getBytes();
                if(writeBuf.equals(new String("exit").getBytes())){
                    quit = true;
                    System.out.println("Exit in progress");
                }
                else {
                    DatagramPacket packet = new DatagramPacket(writeBuf, writeBuf.length, address, port);
                    socket.send(packet);

                }
            }
        }catch(Exception e) {
            System.out.println("Failed");
        }finally {
            if(socket != null) {
                socket.close();
            }
        }
    }
}
