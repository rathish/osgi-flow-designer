package com.yobny.opensource.osgi.karafview.view.ui;


import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.view.action.InstallAction;
import com.yobny.opensource.osgi.karafview.view.action.ListAction;
import com.yobny.opensource.osgi.karafview.view.action.RefreshAction;
import com.yobny.opensource.osgi.karafview.view.action.StartAction;
import com.yobny.opensource.osgi.karafview.view.action.StopAction;

public class KarafAdminView extends ViewPart {

	private IStatusLineManager statusManager;
	
	private TableViewer tableViewer;
	
	@Override
	public void createPartControl(Composite arg0) {
		
		tableViewer = new TableViewer(arg0, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		this.initializeColoums();
		tableViewer.setContentProvider(new BundleContentProvider());
		tableViewer.setLabelProvider(new BundleLableProvider());

		contributeToActionBars();
	}

	private void initializeColoums() {
		
		String[] titles = { "", "Id", "State", "Blueprint", "Level", "Name", "Version" };
		int[] bounds = { 50, 30, 100, 100, 100, 280, 50 };

		for (int i = 0; i < titles.length; i++) {
			TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.NONE);
			column.getColumn().setText(titles[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
		}
		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}
	
	@Override
	public void setFocus() {

	}

	private void contributeToActionBars() {
		IActionBars consoleActionBars = getViewSite().getActionBars();
		statusManager = consoleActionBars.getStatusLineManager();
		fillLocalToolBar(consoleActionBars.getToolBarManager());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		try {
			
			manager.add(new StartAction("Start", "Start", ImageDescriptor
					.createFromURL(new URL(Activator.getDefault().getBundle()
							.getEntry("/"), "icons/start.gif")), statusManager, this.tableViewer));
			
			manager.add(new StopAction("Stop", "Stop", ImageDescriptor
					.createFromURL(new URL(Activator.getDefault().getBundle()
							.getEntry("/"), "icons/stop.gif")), statusManager, this.tableViewer));
			
			manager.add(new RefreshAction("Refresh", "Refresh", ImageDescriptor
					.createFromURL(new URL(Activator.getDefault().getBundle()
							.getEntry("/"), "icons/refresh.gif")), statusManager, this.tableViewer));
			
			
			manager.add(new InstallAction("Install", "Install", ImageDescriptor
					.createFromURL(new URL(Activator.getDefault().getBundle()
							.getEntry("/"), "icons/install.gif")), statusManager, getSite().getShell()));
			
			manager.add(new InstallAction("Uninstall", "Uninstall", ImageDescriptor
					.createFromURL(new URL(Activator.getDefault().getBundle()
							.getEntry("/"), "icons/uninstall.gif")), statusManager, getSite().getShell()));
			
			
			manager.add(new ListAction("List", "List", ImageDescriptor
					.createFromURL(new URL(Activator.getDefault().getBundle()
							.getEntry("/"), "icons/list.gif")), statusManager, this.tableViewer));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
