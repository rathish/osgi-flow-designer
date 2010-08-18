package com.yobny.opensource.osgi.karafview.client;

import java.io.IOException;
import java.io.OutputStream;

public class CommandOutputStream extends OutputStream {

	private CommandBean commandBean;
	
	public CommandOutputStream(CommandBean commandBean) {
		this.commandBean = commandBean;
	}
	
	@Override
	public void write(int arg0) throws IOException {
		commandBean.appendResponse(arg0);
	}

	@Override
	public void write(byte[] b) throws IOException {
		commandBean.appendResponse(new String(b));
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		commandBean.appendResponse(new String(b));
	}
	
	
}
