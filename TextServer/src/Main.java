import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		Thread a,b;
		
		b = new TextServer();
		a = new TextClient();
		
		b.start();
		System.out.println("now a");
		a.start();
		
		((TextServer)b).safeExit();
	}

}
