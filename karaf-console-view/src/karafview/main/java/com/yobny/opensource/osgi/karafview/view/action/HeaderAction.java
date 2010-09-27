package com.yobny.opensource.osgi.karafview.view.action;

import org.eclipse.jface.action.Action;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.client.CommandBean;

public class HeaderAction extends Action {

	private String headerList;
	
	public StringBuffer response;
	
	public HeaderAction(String headerList) {
		this.headerList = headerList;
	}
	
	@Override
	public void run() {
		CommandBean lstCommandBean = new CommandBean();
		Activator.getDefault().getCommandQueue().add(lstCommandBean);

		// ==============================
		// 1) Execute the list command.
		Activator.getDefault().execute("osgi:headers " + headerList + "\n");
		
		this.response = new StringBuffer(Activator.getDefault().getCommandQueue().getFirst().getResponse());
		
		// 2) Clean the command queue and close Karaf channel.
		Activator.getDefault().getCommandQueue().remove(lstCommandBean);
		Activator.getDefault().closeChannel();
	}
}
