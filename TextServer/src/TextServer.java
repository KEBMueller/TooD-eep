import java.io.IOException;
import java.net.ServerSocket;

public class TextServer implements Runnable{

	public static int port = 8000;
	public static int  retrys = 5;
	
	private boolean run = true;
	private ServerSocket ss;
	
	
	public TextServer(){
		int i = 0;
		for(i = 0; i < retrys; i++){
			try {
				ss = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end - for
		say("Took " + i + " Trys to creat ServerSocket");
	} // End  
	
	
	/*
	 * Mach ne vernünftige run!
	 */
	@Override
	public void run() {
			while(ss != null && run) {
				while(ss.isBound()){
					
				}
			}
	}
	
	
	/*
	 * My short way to write to the console
	 */
	public void say(String a){
		System.out.println(a);
	}
}
