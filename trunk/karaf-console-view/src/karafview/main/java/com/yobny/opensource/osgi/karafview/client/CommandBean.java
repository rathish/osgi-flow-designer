package com.yobny.opensource.osgi.karafview.client;

public class CommandBean {
	
	private boolean responseReceived;
	
	public CommandBean() {
		responseReceived = false;
	}
	
	public String getResponse() {
		return m_response;
	}

	public void appendResponse(int value) {
		m_response = m_response + (char) value;
	}
	
	public void appendResponse(String value) {
		m_response = m_response + value;
	}
	
	
	public boolean isResponseReceived() {
		return this.responseReceived;
	}
	
	private String m_response = "";
	
}
