package Server;
import Tools.MyList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	/* Statics*/
	public static int port = 9999;
	public static String ip = "192.168.178.21";
	private static int  retrys = 5;
	private static int slots = 3;
	
	
	
	
	/* Internal Variables*/
	private boolean run = true;
	
	private ServerSocket ss;
	private MyList<Connection> connections;
	
	/*
	 * Creates ServerSocket ss on Port 'port'
	 */
	public Server(){
		int i = 0;
		for(i = 0; i < retrys && ss == null; i++){
			try {
				say("creating serversocket");
				ss = new ServerSocket(port);
				say("created Serversocket");
			} catch (IOException e) {e.printStackTrace(); safeExit();}
		} // end - for
		say("Took " + i + " Trys to creat ServerSocket");
		
	} // End  
	


	/*
	 * handles the connection communication, effectually is the hearth of the server,
	 * 
	 * runs while run == true
	 * 
	 * ToDo: Thinking about a way to close the server without client interaction (creat terminal?)
	 * 		or: create rulesystem to identify higher and lower connections (Admins - User) and let the admins 
	 * 			manage the server?
	 * 
	 */
	@Override
	public void run() {
		int freeSlots = slots; // so i can change slots and then later change freeSlots in controlled manner 
		while(getRun()) {
			for(int i = 0; i < freeSlots && connections.getSize() < freeSlots; i++){say("Creating Socket :"+ i);
				Connection newC = new Connection(this, "" + i);
				connections.add(newC);
				newC.start();
			}
			
			if(connections.hasCurrent()){
				Connection current = connections.getObj();
				
				this.processConnection(current);
			}
			
		}
	}
	
	
	
	
	/*
	 * My short way to write to the console
	 */
	public static void say(String a){
		System.out.println(a);
	}
	
	
	/*
	 * Central message processing
	 * 
	 * Will most probably be moved to Connection for performance but for now 
	 * i go with a global processing methode.
	 */
	public synchronized void processMsg(String ip, int port, String msg){
		say("Ip : "+ip+" Port : " + port + " Message : " + msg);
	}
	
	/*
	 * Does everything in order to process the given Connection and every pend up message etc.
	 */
	public void processConnection(Connection c){
		if(c.hasMessage()) {
			String[] e = c.getMessages();
			Socket t = c.getSocket();
			String ip = t.getInetAddress().getHostAddress();
			int port = t.getPort();
			for(int i = 0; i < e.length; i++) {
				processMsg(ip,port,e[i]);
			}
		}
	}

	/*
	 * Macht alles was "Server" machen muss für safe Exit. duh~~
	 */
	public void safeExit() {
		
		
	}
	
	/*
	 * The encrypting method 
	 */
	public static String lockMsg(String msg){
		return msg;
	}
	
	/*
	 * The encrypting method
	 */
	public static String openMsg(String msg){
		return msg;
	}
	
	/*
	 * Getters
	 */
	public synchronized boolean getRun(){return run;}
	public static int getPort() {return port;}
	public Socket getConnection() throws IOException {
		if(ss != null) {
			return ss.accept();
		}
		return null;
	}

	
	public static void main (String[] args){
		Thread TextServer = new Thread(new Server());
		say("Starting Server");
		TextServer.start();
		
	}
}
