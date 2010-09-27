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
 * ListAction Class extends the JFace Action class to list
 * set of modules currently deployed in the Karaf-Console.
 * 
 * Action invokes the 'list' command in the Karaf, and
 * parses the results returned from the Karaf.
 */
public class ListAction extends Action {

	private TableViewer tableView;
	
	private Table table;
	
	private IStatusLineManager lineManager;
	
	/**
	 * 
	 * @param name Name of the action to be displayed
	 * @param tooltip Tooltip message of the action to be displayed
	 * @param icon Icon image for the action
	 * @param statusManager 
	 * @param tableView View on which the action execution results will be displayed.
	 */
	public ListAction(String name, String tooltip, ImageDescriptor icon,
			IStatusLineManager statusManager, TableViewer tableView) {
		this.setText(name);
		this.setToolTipText(tooltip);
		this.setImageDescriptor(icon);
		this.tableView = tableView;
		this.table = tableView.getTable();
		this.lineManager = statusManager; 
	}

	@Override
	public void run() {
		super.run();
		this.lineManager.setErrorMessage("");
		try {
			// Clear the table view
			//
			this.table.clearAll();
			this.table.setTopIndex(0);
			this.table.removeAll();
			
			ArrayList<Object> listCmd = new ArrayList<Object>();
			CommandBean lstCommandBean = new CommandBean();
			Activator.getDefault().getCommandQueue().add(lstCommandBean);
	
			// ==============================
			// 1) Execute the list command.
			Activator.getDefault().execute("list\n");
			
			// 2) Parse the response from the Karaf.
			ListCmdResponseParser cmdResponseParser = new ListCmdResponseParser();
			cmdResponseParser.parse(Activator.getDefault().getCommandQueue()
					.getFirst().getResponse(), listCmd);
			
			// 3) Clean the command queue and close Karaf channel.
			Activator.getDefault().getCommandQueue().remove(lstCommandBean);
			Activator.getDefault().closeChannel();
			
			LsAction lsAction = new LsAction(cmdResponseParser.bundleIds);
			lsAction.run();
			System.out.println(lsAction.response.toString());
			
			
			
			// 4) Set the input in the view.
			tableView.setInput(listCmd);
			this.lineManager.setMessage("Action: Karaf Admin 'list' completed");
		} catch (Exception exp) {
			this.lineManager.setErrorMessage("Action: Karaf Admin 'list' failed");
		}
	}
}
