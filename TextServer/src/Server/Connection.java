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
	
	private InputStream is;// who nows when i will need the underlying streams, no harm in keeping referenzes
	private OutputStream os;
	private BufferedReader reader;
	private BufferedWriter writer;

	private MyList<String> messages; // all pend up messages
	
	private boolean run = true;
	
	private double lastAnswer = 0;
	private double lastSend = 0;
	
	/* Static*/
	public static int criticalIdleTime = 5000;   //Time in ms after which a Connection is considered closed;
	
	
	/*
	 * name is a unused field right now, but i am planning to use it in order to distinguish
	 * different Connections in Server
	 */
	public Connection(Server manager, String name){
		this.name = name;
		this.manager = manager;
		messages = new MyList();
	}
	
	
	
	 /*
	  * handles the Communication
	  * 
	  * while run == true 
	  * 
	  * Runs till the manager Server close it or the connection exceeds the criticalIdleTime
	  * after the Server send a request
	  * Soll solange laufen bis der manager (Server) ihn schlieﬂt. 
	  * Wenn die connection nach einer Nachricht vom server l‰nger als criticalIdleTime nicht 
	  * antwortet wird die connection als "lost" angesehn und die Connection schlieﬂt sich selbst
	  * 
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
	 * Sends a message, but encodes it first via Server.lockMsg()
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
	 * returns whether messages has a first object
	 * (whether there is a readable message)
	 */
	public boolean hasMessage(){
		return !messages.isEmpty();
	}
	
	/*
	 * Schlieﬂt hoffentlich alle Streams, sodass ich nicht immer neue ips etc benutzen muss
	 */
 	public void safeExit(){
		if(s != null) {
			try {
				writer.close();
				reader.close();
				is.close();
				os.close();
				s.close();
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	
	/*
	 * My System.out.print Methode	 
	 */
	public void say(String msg){
		System.out.println(msg);
	}
	
	
	// Gets'n sets
	public synchronized boolean getRun()     {return run;}
	public synchronized void stopConnection(){this.run = false;}
	public synchronized Socket getSocket()   {return s;}
	public String[] getMessages(){
		String[] e = new String[messages.getSize()];
		messages.toFirst();
		for(int i = 0; i<e.length; i++){
			e[i] = messages.getObj();
			messages.next();
		}
		return e;
	}
}
