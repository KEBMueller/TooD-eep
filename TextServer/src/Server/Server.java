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
	
	public static void main (String[] args){
		Thread TextServer = new Thread(new Server());
		say("Starting Server");
		TextServer.start();
		
	}
	
	/* Options*/
		/*Connection*/
	public static int port = 9999;
	public static String ip = "192.168.178.21";
	private static int  retrys = 5;
	private static int slots = 3;
	
	
	
	
	
	/* Internal Variables*/
	private boolean run = true;
	
	private ServerSocket ss;
	private MyList<Socket> sockets;
	private InputStream is;
	private BufferedReader br;
	private OutputStream os;
	private BufferedWriter bw;
	
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
	


	/*Verwalted den Serversocket und �berwacht/Verwalltet die Connections
	 * 
	 */
	@Override
	public void run() {
		
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
	

	/*
	 * Macht alles was "Server" machen muss f�r safe Exit. duh
	 */
	private void safeExit() {
		
		
	}
}
