package fr.univ.m2m.core;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Outgoing class allows to send messages to another agent
*
 */
public class Outgoing extends Thread {
	
	//it corresponds to the server socket to contact
	private String host;
	private String port;
        private Object obj;
        private Encherisseur encherisseur;
	
	/**
	 * Constructor that sets the port
	 * @param host the host of the server socket
	 * @param port the port of the server socket
	 */
	public Outgoing(String host, String port, Encherisseur encherisseur) {
		
		this.host = host;
		this.port = port;
                this.encherisseur = encherisseur;
	} //constructor
	
	/**
	 * The run method connects to the server socket 
	 * and starts the communication
	 */
    public void run() {
         
        int portNumber = Integer.parseInt(port);
 
        try {
        		
        	//we create the socket to the server
            Socket socket = new Socket(host, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        
            String fromOtherAgent;
 
            while ((fromOtherAgent = in.readLine()) != null) {
                System.out.println("In: " + fromOtherAgent);
                if(fromOtherAgent.contains("NameObject")){
                    String objectName = (String) fromOtherAgent.subSequence(11, fromOtherAgent.length());
                    if(objectName.equals("TV"))
                        this.encherisseur.priceMax = 2000;
                    else if(objectName.equals("Voiture")) {
                        this.encherisseur.priceMax = 30000;
                     }
                    else{
                        this.encherisseur.priceMax = 300;
                    }
                }
                
                if(fromOtherAgent.contains("Bid")){
                    int objectPrice = Integer.parseInt((String) fromOtherAgent.subSequence(4, fromOtherAgent.length()));
                    if(objectPrice<=encherisseur.priceMax){
                        out.println("Take");
                    }
                }
                
                if (fromOtherAgent.equals("Start")) {

                	//start the protocol, send a cfp to the other agent
                	System.out.println("Join");
                	out.println("Join");
                	
                } //if
                
                if (fromOtherAgent.equals("StartAuction")) {
                	
                	//answer by accept-proposal or reject-proposal
                	System.out.println("Out: price-object");
                	// out.println("accept-proposal");
                	
                } //if
                
                if (fromOtherAgent.equals("EndAuction")) {
                	
                	//the task is done and we can inform the user
                	System.out.println("Enchere Termine. Vous avez gagne l'enchere!");
                	break;
                	
                } //if

                if (fromOtherAgent.equals("inform-result")) {
                	
                	//the task is done and we can inform the user
                	System.out.println("Task done");
                	break;
                	
                } //if
                
                if (fromOtherAgent.equals("failure")) {
                	
                	//the task fails and we can inform the user
                	System.out.println("Failure");
                	break;
                	
                } //if
                
            } //while
            
            //close the streams
            out.close();
            in.close();
            socket.close();
            
        } //try 
        catch (UnknownHostException e) {

        	System.err.println("Don't know about host " + host);
            System.exit(1);
            
        } //catch
        catch (IOException e) {

        	System.err.println("Couldn't get I/O for the connection to " +
                host);
            System.exit(1);
        
        } //catch
        
    } //run()
    
} //class Outgoing