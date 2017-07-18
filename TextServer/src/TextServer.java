import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TextServer extends Thread{
	
	public static void main (String[] args){
		Thread TextServer = new Thread(new TextServer());
		
		TextServer.start();
		
	}

	public static int port = 8000;
	public static int  retrys = 5;
	
	private boolean run = true;
	private ServerSocket ss;
	private Socket s;
	
	private InputStream is;
	private OutputStream os;
	
	private BufferedWriter bw;
	private BufferedReader br;
	
	
	public TextServer(){
		int i = 0;
		for(i = 0; i < retrys; i++){
			try {
				ss = new ServerSocket(port);
				s = ss.accept();
				
				os = s.getOutputStream();
				is = s.getInputStream();
			} catch (IOException e) {e.printStackTrace();}
		} // end - for
		say("Took " + i + " Trys to creat ServerSocket");
		
		if(ss != null) {
			bw = new BufferedWriter(new OutputStreamWriter(os));
			br = new BufferedReader(new InputStreamReader(is));
		}
	} // End  
	
	
	/*
	 * Mach ne vernünftige run!
	 */
	@Override
	public void run() {
			while(ss != null && run) {
				while(s.isBound()){
					try {
						if(br.ready()){
							String message = br.readLine();
							say(message);
							bw.write(message);
						}
					} catch (IOException e) {e.printStackTrace(); safeExit(); run = false;}
				}
				try {
					s = ss.accept();
				} catch (IOException e) {e.printStackTrace(); safeExit();  run = false;}
			}
	}
	
	/*
	 * 
	 */
	public synchronized boolean getRun(){return run;}
	
	
	public void safeExit() throws IOException {
		if(ss != null) {
			ss.close();
		}
		if(s != null) {
			s.close();
		}
	}
	
	/*
	 * My short way to write to the console
	 */
	public void say(String a){
		System.out.println(a);
	}
}
