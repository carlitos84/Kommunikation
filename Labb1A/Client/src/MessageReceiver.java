import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MessageReceiver implements Runnable {

    private DatagramSocket socket;
    private InetAddress inet;
    private int port;
    private byte[] buffer;
    private DatagramPacket packet;

    public MessageReceiver(DatagramSocket socket, InetAddress inet, int port) {
        this.socket = socket;
        this.inet = inet;
        this.port = port;
        this.buffer = new byte[256];
    }

    @Override
    public void run() {
        while(true){
            packet = new DatagramPacket(buffer, buffer.length);
            try {
                synchronized (socket) {
                    socket.receive(packet);
                }
            } catch (IOException e) {
                System.out.println("Connection to server failed");
            }
            String received = new String(packet.getData(), 0, packet.getLength());
            synchronized (System.out) {
                System.out.println();
                System.out.println("Server: " + received);
            }
        }
    }
}
