import java.io.BufferedReader;
import Server.Server;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client extends Thread{
	
	private Socket s;
	
	private InputStreamReader ir;
	private BufferedReader br;
	
	private OutputStreamWriter ow;
	private BufferedWriter bw;
	
	public Client(){
		s = null;
	}

	public void run(){
		try {
			say("Connecting");
			s = new Socket("192.168.178.21",  Server.getPort());
			say("Connected");
			Thread.sleep(200);
		ir = new InputStreamReader(s.getInputStream());
		br = new BufferedReader(ir);
		
		ow = new OutputStreamWriter(s.getOutputStream());
		bw = new BufferedWriter(ow);
			say("exi");
			s.close(); 
		} catch (IOException | InterruptedException e1) {e1.printStackTrace();}
	}
	
	
	/*
	 * 
	 */
	public void safeExit() {
		try {
			say("closing s")
			s.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/*
	 * 
	 */
	public void send(String msg){
		if(bw != null){
			try {
				bw.write(msg);
			} catch (IOException e) {e.printStackTrace(); say("Could not send :"+ msg);}
		}
	}
	
	/*
	 * my println
	 */
	public static void say (String m) {
		System.out.println(m);
	}
	
	public static void main(String[] args){
		Client Chat;
		say("Client");
		Chat = new Client();

		Chat.start();
	}
}
