package com.yobny.opensource.osgi.karafview.view.action;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.client.CommandBean;
import com.yobny.opensource.osgi.karafview.parser.ListCmdResponseParser;

/**
 * 
 * @author Rathish
 * 
 * RefreshAction Class extends the JFace Action class to refresh set of
 * modules currently deployed in the Karaf-Console.
 * 
 * Action invokes the 'refresh' command in the Karaf
 */
public class RefreshAction extends Action {
	
	private TableViewer tableView;
	
	private Table table;
	
	private IStatusLineManager subStatusLineManager;

	/**
	 * 
	 * @param name Name of the action to be displayed
	 * @param tooltip Tooltip message of the action to be displayed
	 * @param icon Icon image for the action
	 * @param statusManager
	 * @param tableView View on which the action execution results will be displayed.
	 */
	public RefreshAction(String name, String tooltip, ImageDescriptor icon,
			IStatusLineManager statusManager, TableViewer tableView) {
		this.setText(name);
		this.setToolTipText(tooltip);
		this.setImageDescriptor(icon);
		this.tableView = tableView;
		this.table = tableView.getTable();
		this.subStatusLineManager = statusManager;
		
	}

	@Override
	public void run() {
		super.run();
		this.subStatusLineManager.setErrorMessage("");
		try {
			// Clear the table view
			//
			this.table.clearAll();
			this.table.setTopIndex(0);
			this.table.removeAll();
			
			ArrayList<Object> listCmd = new ArrayList<Object>();
			CommandBean refCommandBean = new CommandBean();
			Activator.getDefault().getCommandQueue().add(refCommandBean);
			
			// ==============================
			// 1) Execute the refresh command.
			Activator.getDefault().execute("refresh\n");
			Activator.getDefault().getCommandQueue().remove(refCommandBean);
			
			// Clear the table view
			//
			this.table.clearAll();
			this.table.setTopIndex(0);
			this.table.removeAll();
			
			// 2) Execute the list command.
			CommandBean lstCommandBean = new CommandBean();
			Activator.getDefault().getCommandQueue().add(lstCommandBean);
			Activator.getDefault().execute("list\n");
			
			// 3) Parse the response from the Karaf.
			ListCmdResponseParser cmdResponseParser = new ListCmdResponseParser();
			cmdResponseParser.parse(Activator.getDefault().getCommandQueue()
					.getFirst().getResponse(), listCmd);
			
			// 4) Clean the command queue and close Karaf channel.
			Activator.getDefault().getCommandQueue().remove(lstCommandBean);
			Activator.getDefault().closeChannel();
			
			// 5) Set the input in the view.
			tableView.setInput(listCmd);
			this.subStatusLineManager.setMessage("Action: Karaf Admin 'refresh' completed");
		} catch (Exception exp) {
			this.subStatusLineManager.setErrorMessage("Action: Karaf Admin 'refresh' failed");
		}
	}
}
