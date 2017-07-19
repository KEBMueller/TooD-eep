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

	public static int port = 9996;
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
		for(i = 0; i < retrys && ss == null; i++){
			try {
				ss = new ServerSocket(port);
				say("a");
			} catch (IOException e) {e.printStackTrace(); safeExit();}
		} // end - for
		say("Took " + i + " Trys to creat \n ServerSocket s:" + (s == null));
		
	} // End  
	
	
	/*
	 * Mach ne vernünftige run!
	 */
	@Override
	public void run() {
		try {
		double time = System.currentTimeMillis();
			while(ss != null &&run) {
				if(System.currentTimeMillis() - time > 10000){
					run = false;
				}
				
				if(s == null || s.isClosed()){
					say("Connecting");
					s = ss.accept();
					say("Connected");
				
					os = s.getOutputStream();
					is = s.getInputStream();
					bw = new BufferedWriter(new OutputStreamWriter(os));
					br = new BufferedReader(new InputStreamReader(is));
				}
					
				double t = System.currentTimeMillis();
				while(!s.isClosed()){
					
						if(br.ready()){
							String message = br.readLine();
							say(message);
							bw.write(message);
							if(message.equals("exit")) {
								run = false;
								safeExit();
								say("exiting");
							}
						}
				}
			}
		} catch (IOException e) {e.printStackTrace(); safeExit(); run = false;}
	}
	
	/*
	 * 
	 */
	public synchronized boolean getRun(){return run;}
	
	
	public void safeExit() {
		try {
			if(ss != null) {
					ss.close();
			}
			if(s != null) {
				s.close();
			}
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/*
	 * My short way to write to the console
	 */
	public void say(String a){
		System.out.println(a);
	}
}
