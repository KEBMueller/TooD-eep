import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TextClient extends Thread{
	
	private Socket s;
	
	private InputStreamReader reader;
	private BufferedReader bufRead;
	
	public TextClient() throws UnknownHostException, IOException {
		s = new Socket("192.168.178.21", 4);
		reader = new InputStreamReader(s.getInputStream());
		bufRead = new BufferedReader(reader);
	}

	public void run(){
		while(s.isClosed() == false){
			try {
				if(bufRead.ready()) {
						System.out.println(bufRead.readLine());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
