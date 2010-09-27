package com.yobny.opensource.osgi.karafview.view.action;

import org.eclipse.jface.action.Action;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.client.CommandBean;

public class OBRRemoveURLAction extends Action {

	private String[] urisToRemove;
	
	public OBRRemoveURLAction(String[] urisToRemove) {
		this.urisToRemove = urisToRemove;
	}
	
	@Override
	public void run() {
		
		// 1) Execute the command
		//
		CommandBean lstCommandBean = new CommandBean();
		Activator.getDefault().getCommandQueue().add(lstCommandBean);
		
		String command = "obr:removeUrl";
		for (int index = 0; index < urisToRemove.length; index++){
			command = command + " " + urisToRemove[index]; 
		}
		Activator.getDefault().execute(command+"\n");
		
		// 2) clear the command
		//
		Activator.getDefault().getCommandQueue().remove(lstCommandBean);
		Activator.getDefault().closeChannel();
	}
}
