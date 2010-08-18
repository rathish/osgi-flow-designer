package com.yobny.opensource.osgi.karafview.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.client.CommandBean;
import com.yobny.opensource.osgi.karafview.view.ui.SelectBundleDialogue;

public class InstallAction extends Action {

	private Shell shell;
	
	public InstallAction(String name, String tooltip, ImageDescriptor icon, IStatusLineManager statusManager, Shell shell) {
		this.setText(name);
		this.setToolTipText(tooltip);
		this.setImageDescriptor(icon);
		this.shell = shell;
	}
	
	@Override
	public void run() {
		super.run();
		
		SelectBundleDialogue bundleDialogue = new SelectBundleDialogue(this.shell);
		bundleDialogue.open();
		if (bundleDialogue.getBundlePath().equals("") == false) {
			CommandBean commandBean = new CommandBean();
			Activator.getDefault().getCommandQueue().add(commandBean);
			
			Activator.getDefault().execute("install " + "\"file:" + bundleDialogue.getBundlePath() + "\"\n");
			
			System.out.println(Activator.getDefault().getCommandQueue()
				.getFirst().getResponse());
			
			Activator.getDefault().getCommandQueue().remove(commandBean);
			Activator.getDefault().closeChannel();
		}
	}
}
