package com.yobny.opensource.osgi.karafview.client;

import java.io.IOException;
import java.io.InputStream;

public class CommandInputStream extends InputStream {

	InputStream input;
	
	/**
	 * 
	 * @param input
	 */
	public CommandInputStream(InputStream input) {
		this.input = input;
	}
	
	@Override
	public int read() throws IOException {
		return 0;
	}
	
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return input.read(b, off, len);
	}
	
}
