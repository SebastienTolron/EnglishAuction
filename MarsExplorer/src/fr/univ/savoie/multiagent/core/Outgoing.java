package fr.univ.savoie.multiagent.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The Outgoing class allows to send messages to another agent
*
 */
public class Outgoing extends Thread {
	
	//it corresponds to the server socket to contact
	private String host;
	private String port;
	
	/**
	 * Constructor that sets the port
	 * @param host the host of the server socket
	 * @param port the port of the server socket
	 */
	public Outgoing(String host, String port) {
		
		this.host = host;
		this.port = port;
		
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
                
                if (fromOtherAgent.equals("Start")) {

                	//start the protocol, send a cfp to the other agent
                	System.out.println("Out: cfp");
                	out.println("cfp");
                	
                } //if
                
                if (fromOtherAgent.equals("propose")) {
                	
                	//answer by accept-proposal or reject-proposal
                	System.out.println("Out: accept-proposal");
                	out.println("accept-proposal");
                	
                } //if
                
                if (fromOtherAgent.equals("inform-done")) {
                	
                	//the task is done and we can inform the user
                	System.out.println("Task done");
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