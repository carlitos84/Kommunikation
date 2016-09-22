import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.net.*;
import java.util.*;

public class QuoteServerThread extends Thread {

    protected DatagramSocket socket = null;
    protected boolean serverOn = true;
    protected String message;
    protected InetAddress activeClientIP;
    protected int activeClientPort;
    protected boolean clientInProgress = false;
    protected boolean gameSessionOn = false;
    protected boolean handshakeDone = false;
    protected GuessGame game;

    public QuoteServerThread() throws IOException {
        this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);
        game = new GuessGame();
    }

    public void run() {

        while (serverOn) {
            try {
                byte[] writebuf = new byte[256];
                byte[] readbuf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(readbuf, readbuf.length);
                socket.receive(packet);
                if (!clientInProgress) {
                    activeClientIP = packet.getAddress();
                    activeClientPort = packet.getPort();
                    clientInProgress = true;
                }
                message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("message recieve from a client: " + message);

                //Receive end
                if (!(packet.getAddress().equals(activeClientIP) && packet.getPort() == activeClientPort)) //
                {
                    writebuf = "BUSY".getBytes();
                }
                else if(message.length() >= 5 &&(message.substring(0,5)).equals("GUESS") && handshakeDone) // checks if clients message is GUESS
                {
                    if(message.length() == 5 || (message.length() == 6 && message.charAt(5) == ' ')) //ex. "GUESS "
                    {
                        writebuf = "No argument! try again.".getBytes();
                    }
                    else if( message.charAt(5) == ' ')
                    {
                        String guessArgument = message.substring(6,message.length());
                        if(validateNumber(guessArgument))
                        {
                            writebuf = game.makeGuess(Integer.parseInt(guessArgument)).getBytes();
                        }
                        else
                        {
                            writebuf = "Not a number! Try again.".getBytes();
                        }
                        socket.setSoTimeout(15000);
                    }
                    else //ex. GUESSa12
                    {
                        writebuf = "Wrong input. Connection dropped.".getBytes();
                        reset();
                    }
                }
                else if(message.equals("START") && handshakeDone)
                {
                    writebuf = "READY".getBytes();
                }
                else if (message.equals("HELLO")) {
                    writebuf = "OK".getBytes();
                    handshakeDone = true;
                    socket.setSoTimeout(10000);
                }
                else {
                    String errormsg = new String("Error! Wrong input connection is now dropped.");
                    writebuf = errormsg.getBytes();
                    reset();
                }

                // send the response to the client who reached to server
                packet = new DatagramPacket(writebuf, writebuf.length, packet.getAddress(), packet.getPort());
                System.out.println("Responding to client: " + new String(packet.getData(), 0, packet.getLength()));
                socket.send(packet);

            }
            catch(SocketTimeoutException e){
                e.printStackTrace();
                // send
                byte[] timeoutmsgbuf = new byte[256];
                timeoutmsgbuf = "Error: Timeout! Client did no response in time".getBytes();
                DatagramPacket packet = new DatagramPacket(timeoutmsgbuf, timeoutmsgbuf.length, activeClientIP, activeClientPort);

                try {
                    socket.setSoTimeout(0);
                    socket.send(packet);
                    System.out.println("TIMEOUT: client in process did not responding in time. Client is dropped.");
                }
                catch (IOException e1) {e1.printStackTrace();}
                finally {reset();}

            }
            catch(Exception e)
            {
                e.printStackTrace();
                serverOn = false;
            }
        }
        socket.close();
    }

    void reset()
    {
        clientInProgress = false;
        handshakeDone = false;
        try {
            socket.setSoTimeout(0);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private boolean validateNumber(String toValidate)
    {
        try{
            int guessNumber = Integer.parseInt(toValidate);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

}

