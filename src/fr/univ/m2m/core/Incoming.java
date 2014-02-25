package fr.univ.m2m.core;



import java.net.*;
import java.io.*;
 
/**
 * The Incoming class corresponds to the server socket
 *
 */
public class Incoming extends Thread {
	
	//the server socket port
	int port;
	
	/**
	 * Constructor sets the port for the server socket
	 * @param port the server socket port
	 */
	public Incoming(String port) {
		
		this.port = Integer.parseInt(port);
		
	} //constructor
	
	/**
	 * the run method waits for socket connections to initiate a thread
	 * for this specific communication
	 */
    public void run() {
 
    	//ensure we are waiting for any new connections
        boolean listening = true;
         
        try (ServerSocket serverSocket = new ServerSocket(port)) {
        	
            while (listening) 
                new IncomingThread(serverSocket.accept()).start();
            
        } //try
        catch (IOException e) {

        	System.err.println("Could not listen on port " + port);
            System.exit(-1);
            
        } //catch
        
    } //run()
    
} //class Incoming