import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TextClient extends Thread{
	
	public static void main(String[] args){
		Thread Chat;
		say("Client");
		try {
			Chat = new TextClient();

			Chat.start();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	private Socket s;
	
	private InputStreamReader ir;
	private BufferedReader br;
	
	private OutputStreamWriter ow;
	private BufferedWriter bw;
	
	public TextClient() throws UnknownHostException, IOException {
		s = new Socket("192.168.178.21",  TextServer.port);
		ir = new InputStreamReader(s.getInputStream());
		br = new BufferedReader(ir);
		
		ow = new OutputStreamWriter(s.getOutputStream());
		bw = new BufferedWriter(ow);
		
	}

	public void run(){
		try {
			bw.write("Hello");
			bw.write("exit");
			s.close(); 
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public static void say (String m) {
		System.out.println(m);
	}
}
