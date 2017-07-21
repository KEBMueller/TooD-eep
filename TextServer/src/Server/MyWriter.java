package Server;

import java.io.BufferedWriter;
import java.io.Writer;

public class MyWriter extends BufferedWriter {

	public MyWriter(Writer out) {
		super(out);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.io.Writer#write(java.lang.String)
	 */
	public void write(String msg) {
		this.write(msg);
	}

}
