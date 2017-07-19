import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TextClient extends Thread{
	
	public static void main(String[] args){
		TextClient Chat;
		say("Client");
		Chat = new TextClient();

		Chat.start();
	}
	
	private Socket s;
	
	private InputStreamReader ir;
	private BufferedReader br;
	
	private OutputStreamWriter ow;
	private BufferedWriter bw;
	
	public TextClient(){
		
	}

	public void run(){
		try {
			s = new Socket("192.168.178.21",  TextServer.port);
			Thread.sleep(20);
		ir = new InputStreamReader(s.getInputStream());
		br = new BufferedReader(ir);
		
		ow = new OutputStreamWriter(s.getOutputStream());
		bw = new BufferedWriter(ow);
			bw.write("Hello\n");
			bw.write("exit\n");
			say("exit");
			s.close(); 
		} catch (IOException | InterruptedException e1) {e1.printStackTrace();}
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
	
	public static void say (String m) {
		System.out.println(m);
	}
}
