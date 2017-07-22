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
	 * Creates Serversocket ss on Port 'port'
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
	 * Verwalted und überwacht den ServerSocket und die Connections
	 * 
	 */
	@Override
	public void run() {
		int freeSlots = slots;
		while(getRun()) {
			for(int i = 0; i < freeSlots && connections.getSize() < freeSlots; i++){say("Creating Socket :"+ i);
				Connection newC = new Connection(this, "" + i);
				connections.add(newC);
				newC.start();
			}
			
			if(connections.hasCurrent()){
				Connection current = connections.getVal();
				
				this.processConnection(current);
			}
			
		}
	}
	
	/*
	 * Getters
	 */
	public synchronized boolean getRun(){return run;}
	public static int getPort() {return port;}
	
	
	/*
	 * My short way to write to the console
	 */
	public static void say(String a){
		System.out.println(a);
	}
	
	public synchronized void processMsg(String ip, int port, String msg){
		say("Ip : "+ip+" Port : " + port + " Message : " + msg);
	}
	
	/*
	 * Does everything in order to process all pendup messages from the given Connection
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
	 * 
	 */
	public static String lockMsg(String msg){
		return msg;
	}
	
	/*
	 * 
	 */
	public static String openMsg(String msg){
		return msg;
	}
	
	/*
	 * 
	 */
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
