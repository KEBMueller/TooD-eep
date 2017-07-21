package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Connection extends Thread{
	
	
	/*Personal*/
	private String name;
	
	/* Internal*/
	private Socket s;
	private InputStream is;
	private OutputStream os;
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private boolean run;
	
	public Connection(Socket s, String n){
		this.s = s;
		this.name = n;
		
		
		reader = new BufferedReader(new InputStreamReader(is));
		writer = new BufferedWriter(new OutputStreamWriter(os));
	}
	
	 /*
	  * 
	  */
	public void run(){
		while(run){
			try {
				double time = System.currentTimeMillis();
				say("Setting up " + name + "waiting for incomming Connection");
				s = new Socket(Server.ip,Server.port);
				
			} catch (IOException e) {e.printStackTrace();  run = false;}
		}
	}
	
	/*
	 * 
	 */
	public void say(String msg){
		System.out.println(msg);
	}
	
}
