package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import Tools.MyList;

public class Connection extends Thread{
	
	
	/*Personal*/
	private String name;
	
	/* Internal*/
	private Server manager; // Parent
	
	private Socket s; //Connection
	
	private InputStream is;
	private OutputStream os;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private boolean run = true;
	
	private double lastAnswer = 0;
	private double lastSend = 0;
	
	private MyList<String> messages;
	
	/* Statics*/
	public static int criticalIdleTime;   //Time in ms after which a Connection is deemd close;
	
	public Connection(Server manager, String name){
		this.name = name;
		this.manager = manager;
		
		messages = new MyList();
		
		
		
		reader = new BufferedReader(new InputStreamReader(is));
		writer = new BufferedWriter(new OutputStreamWriter(os));
	}
	
	
	
	 /*
	  * handles the Communication
	  */
	public void run(){
		while(run){
			try{
			double time = System.currentTimeMillis();
				
			if(s == null) {
				say("Setting up " + name + "waiting for incomming Connection");
				
				s = manager.getConnection();
				
				say("Connected to :" + s.getInetAddress().getHostAddress());
				say("Creating Writer and Reader");
				
				is = s.getInputStream();
				os = s.getOutputStream();
					
				reader = new BufferedReader(new InputStreamReader(is));
				writer = new BufferedWriter(new OutputStreamWriter(os));
			}
				
			while(!s.isClosed() && run) {
				if(reader.ready()) {
					lastAnswer = System.currentTimeMillis();
						
					String msg = reader.readLine();
						
					say(msg);
					msg = Server.openMsg(msg);
				    messages.add(msg);
						
				}
				if(lastSend - lastAnswer > criticalIdleTime) {
					run = false;
				}
			}
	    	} catch (IOException e) {e.printStackTrace();  run = false;}
			
			safeExit();
		}
	}//End
	
	/*
	 * 
	 */
	public synchronized void sendMsg(String msg) {
		msg = Server.lockMsg(msg);
		
		lastSend = System.currentTimeMillis();
		
		if(writer != null) {
			try {
				writer.write(msg);
				writer.flush();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	/*
	 * returns wether messages has a first object
	 */
	public boolean hasMessage(){
		return !messages.isEmpty();
	}
	
	/*
	 * 
	 */
 	public void safeExit(){
		if(s != null) {
			try {
				s.close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	
	/*My System.out.print Methode	 
	 */
	public void say(String msg){
		System.out.println(msg);
	}
	
	
	// Gets'n sets
	public synchronized boolean getRun(){return run;}
	public synchronized void stopConnection(){
		this.run = false;
	}
	public synchronized Socket getSocket(){return s;}
	public String[] getMessages(){
		String[] e = new String[messages.getSize()];
		
		messages.toFirst();
		for(int i = 0; i<e.length; i++){
			e[i] = messages.getVal();
			messages.next();
		}
		return e;
	}
}
