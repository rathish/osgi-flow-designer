package com.yobny.opensource.osgi.karafview.view.action;

import org.eclipse.jface.action.Action;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.client.CommandBean;

public class OBRAddURLAction extends Action {
	
	private String urlToAdd;
	
	public void addURL(String urlToAdd) {
		this.urlToAdd = urlToAdd;
	}
	
	@Override
	public void run() {
		// 1) Execute the command
		//
		CommandBean lstCommandBean = new CommandBean();
		Activator.getDefault().getCommandQueue().add(lstCommandBean);
		Activator.getDefault().execute("obr:addUrl " + urlToAdd + "\n");
		
		// 2) clear the command
		//
		Activator.getDefault().getCommandQueue().remove(lstCommandBean);
		Activator.getDefault().closeChannel();
	}
}
