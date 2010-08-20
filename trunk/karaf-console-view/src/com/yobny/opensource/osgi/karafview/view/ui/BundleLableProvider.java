package com.yobny.opensource.osgi.karafview.view.ui;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.bean.ListCmdPojo;

public class BundleLableProvider extends LabelProvider implements
		ITableLabelProvider {

	private static final Image BUNDLE = Activator.getDefault()
			.getImageDescriptor("icons/bundle.png").createImage();
	
	private static final Image BUNDLE_STOPPED = Activator.getDefault()
		.getImageDescriptor("icons/bundle_stopped.png").createImage();
	
	@Override
	public Image getColumnImage(Object arg0, int arg1) {
		ListCmdPojo cmdPojo = (ListCmdPojo) arg0;
		if ((arg1 == 0) && (cmdPojo.getBundleState().equals("Active"))) {
			return BUNDLE;
		} else if ((arg1 == 0) && (cmdPojo.getBundleState().equals("Resolved"))) {
			return BUNDLE_STOPPED;
		}
		return null;
	}

	@Override
	public String getColumnText(Object arg0, int arg1) {
		ListCmdPojo cmdPojo = (ListCmdPojo) arg0;
		switch (arg1) {
		case 0:
			return "";
		case 1:
			return cmdPojo.getBundleId();
		case 2:
			return cmdPojo.getBundleState();
		case 3:
			return cmdPojo.getBundleBluePrint();
		case 4:
			return cmdPojo.getBundleLevel();
		case 5:
			return cmdPojo.getBundleName();
		case 6:
			return cmdPojo.getBundleVersion();
		default:
			throw new RuntimeException("Unexpected colum number:" + arg1);
		}
	}
}
