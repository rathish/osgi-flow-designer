package com.yobny.opensource.osgi.karafview.view.action;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.bean.ListCmdPojo;
import com.yobny.opensource.osgi.karafview.client.CommandBean;
import com.yobny.opensource.osgi.karafview.parser.ListCmdResponseParser;

public class StopAction extends Action {

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
	public StopAction(String name, String tooltip, ImageDescriptor icon,
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
		TableItem[] tabeItems = table.getSelection();
		String stopBundleIds = ""; 
		for (int tableIndex=0; tableIndex<tabeItems.length; tableIndex++) {
			stopBundleIds = stopBundleIds + ((ListCmdPojo)tabeItems[tableIndex].getData()).getBundleId() + " ";
		}
		
		if (stopBundleIds.equals("")) {
			this.subStatusLineManager.setErrorMessage("No Bundle Selected for Stop");
		} else {
				try {
				
					ArrayList<Object> listCmd = new ArrayList<Object>();
					CommandBean stopCommandBean = new CommandBean();
					Activator.getDefault().getCommandQueue().add(stopCommandBean);
					
					// ==============================
					// 1) Execute the stop command.
					Activator.getDefault().execute("stop --force " + stopBundleIds + "\n");
					Activator.getDefault().getCommandQueue().remove(stopCommandBean);
					
					// 2) Execute the refresh command.
					CommandBean refCommandBean = new CommandBean();
					Activator.getDefault().getCommandQueue().add(refCommandBean);
					Activator.getDefault().execute("refresh\n");
					Activator.getDefault().getCommandQueue().remove(refCommandBean);
					
					// Clear the table view
					//
					this.table.clearAll();
					this.table.setTopIndex(0);
					this.table.removeAll();
					
					// 3) Execute the list command.
					CommandBean listCommandBean = new CommandBean();
					Activator.getDefault().getCommandQueue().add(listCommandBean);
					Activator.getDefault().execute("list\n");
					
					// 4) Parse the response from the Karaf.
					ListCmdResponseParser cmdResponseParser = new ListCmdResponseParser();
					cmdResponseParser.parse(Activator.getDefault().getCommandQueue()
							.getFirst().getResponse(), listCmd);
					
					// 5) Clean the command queue and close Karaf channel.
					Activator.getDefault().getCommandQueue().remove(listCommandBean);
					Activator.getDefault().closeChannel();
					
					// 6) Set the input in the view.
					tableView.setInput(listCmd);
					this.subStatusLineManager.setMessage("Action: Karaf Admin 'stopped' : Bundles " + stopBundleIds);
				} catch (Exception exp) {
					this.subStatusLineManager.setErrorMessage("Action: Karaf Admin 'stop' execution failed");
				}
			}
		}
}
