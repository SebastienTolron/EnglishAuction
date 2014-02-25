package fr.univ.m2m.core;


public class Encherisseur {

	//identifier for the agent
	private String id;
        
        public int priceMax;
	
	//server side of the Agent to accept new communications with new agents
	private Incoming incoming = null;
	
	/**
	 * Constructor
	 * @param args 3 arguments: <id> <incoming port> <outgoing port>
	 */
	public Encherisseur(String[] args) {
	
		//set the agent id
		id = args[0];
		
		//print some details
		System.out.println("Agent " + id + " is executed");
		
		//set the socket server for incoming messages
		incoming = new Incoming(args[1]);
		incoming.start();
			
		//if the agent id is Cartman, it initiates the protocol
		//with Kenny
		if (id.equals("Encherisseur")) {
			
			Outgoing outgoing = new Outgoing("10.7.254.252", "1234", this);
			outgoing.start();
			
		} //if
		
	} //constructor
	
	/**
	 * Entry point for the Agent
	 * @param args 3 arguments have to be provided: id, incoming port
	 */
	public static void main(String[] args) {
	
		if (args.length == 2) 
			new Encherisseur(args);
		else {
		
			System.out.println("Agent usage:");
			System.out.println("java Agent <id> <incoming port>");
			
		} //else
		
	} //main()
	
} //class Agent
