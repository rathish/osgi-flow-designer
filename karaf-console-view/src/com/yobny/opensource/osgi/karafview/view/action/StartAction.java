package com.yobny.opensource.osgi.karafview.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.client.CommandBean;

public class StartAction extends Action {

	private String[] bundleIds;
	
	public StartAction(String name, String tooltip, ImageDescriptor icon,
			IStatusLineManager statusManager, String[] bundleIds) {
		
		this.setText(name);
		this.setToolTipText(tooltip);
		this.setImageDescriptor(icon);
		this.bundleIds = bundleIds;
	}
	
	@Override
	public void run() {
		super.run();
		
		CommandBean commandBean = new CommandBean();
		Activator.getDefault().getCommandQueue().add(commandBean);
		
		String strBundleIds = "";
		
		for (int index = 0; index<this.bundleIds.length; index++) {
			
			if (index == this.bundleIds.length) {
				strBundleIds = strBundleIds + this.bundleIds[index];
			} else  {
				strBundleIds = strBundleIds + this.bundleIds[index] + ",";
			}
		}
		
		Activator.getDefault().execute("start " + strBundleIds + "\n");
		Activator.getDefault().getCommandQueue().removeFirst();
		Activator.getDefault().closeChannel();
	}
}
