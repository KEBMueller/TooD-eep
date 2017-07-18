import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		Thread a,b;
		
		try {
			a = new TextClient();

			b = new TextServer();
			
			b.start();
			System.out.println("now a");
			a.start();
		} catch (IOException e) {e.printStackTrace();}
	}

}
