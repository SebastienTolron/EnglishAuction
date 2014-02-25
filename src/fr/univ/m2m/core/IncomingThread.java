package fr.univ.m2m.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The IncomingThread corresponds to the protocol used by the agent
 *
 */
public class IncomingThread extends Thread {

    //the client socket
    private Socket socket = null;
    private ObjectAuction obj = null;
    private int count = 0;

    /**
     * Constructor set the client socket
     *
     * @param socket the client socket
     */
    public IncomingThread(Socket socket, ObjectAuction objet) {

        super("IncomingThread");
        this.socket = socket;
        this.obj = objet;

    } //constructor

    /**
     * The run method
     */
    public void run() {

        try {

            //create the incoming and the outgoing streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            //contains the incoming message
            String inputLine;

            //receiving the connection from the client, the server sends back
            //Start
            out.println("Start");

            //while the client continues to send messages
            while ((inputLine = in.readLine()) != null) {

                //what is received
                System.out.println("From client: " + inputLine);

                //the protocol, here the Contract Net
                if (inputLine.equals("Join")) {

                    out.println("StartAuction");

                    out.println("NameObject TV");

                    out.println("Bid " + obj.currentAuction);

                } //if

                if (inputLine.equals("Take")) {

                    count++;
                    System.out.println("Count = " + count);
                    if (count == 1) {
                        // on timeout sur la sockjet
                        try {
                            this.socket.setSoTimeout(10000);
                        } catch (Exception e) {

                        }
                        out.println("EndAuction");
                    } else {
                        obj.currentAuction += 50;
                        out.println("Bid " + obj.currentAuction);
                        count = 0;
                    }

                }

                //if
            } //while

            //close the streams
            out.close();
            in.close();
            socket.close();

        } //try
        catch (IOException e) {

            e.printStackTrace();

        } //catch

    } //run()

} //class IncomingThread
