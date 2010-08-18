package com.yobny.opensource.osgi.karafview.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;

public class RefreshAction extends Action {

	public RefreshAction(String name, String tooltip, ImageDescriptor icon, IStatusLineManager statusManager) {
		this.setText(name);
		this.setToolTipText(tooltip);
		this.setImageDescriptor(icon);
	}
	
	@Override
	public void run() {
		super.run();
	}
}
