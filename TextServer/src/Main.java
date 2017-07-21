import java.io.IOException;

import Server.Server;

public class Main {

	public static void main(String[] args) {
		
		Thread a,b;
		
		b = new Server();
		a = new TextClient();
		
		b.start();
		System.out.println("now a");
		a.start();
		
		((Server)b).safeExit();
	}

}
