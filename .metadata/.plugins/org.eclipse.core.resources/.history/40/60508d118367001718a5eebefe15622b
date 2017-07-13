import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {

		boolean run = true;
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(7004);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(run){
			try {

				System.out.println("start");
				Socket s = null;
				BufferedReader reader = null;
				BufferedWriter writer = null;
				InputStream is;
				OutputStream os;
				s = socket.accept();
			
		
				if(socket != null) {
				
					System.out.println("bound");
					is = s.getInputStream();
					os = s.getOutputStream();
					reader = new BufferedReader( new InputStreamReader(is , "UTF-8"));
					writer = new BufferedWriter(new OutputStreamWriter(os));
			
					while(s.){
						if(reader.ready()) {
							String text = "error";
							text = reader.readLine();
							System.out.println(text);
							writer.write(text);
							if(text.equals("close") ) run = false;
						}
					} 
					System.out.println("closed");
				}

				if(!run) {
				s.shutdownInput();
				s.shutdownOutput();
				socket.close();
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}

}
