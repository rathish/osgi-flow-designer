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

public class ListAction extends Action {

	private TableViewer tableView;
	
	private Table table;
	
	public ListAction(String name, String tooltip, ImageDescriptor icon,
			IStatusLineManager statusManager, TableViewer tableView) {
		
		this.setText(name);
		this.setToolTipText(tooltip);
		this.setImageDescriptor(icon);
		this.tableView = tableView;
		this.table = tableView.getTable();
	}

	@Override
	public void run() {
		super.run();
		this.table.clearAll();
		this.table.setTopIndex(0);
		this.table.removeAll();
		
		ArrayList<Object> listCmd = new ArrayList<Object>();
		CommandBean commandBean = new CommandBean();
		Activator.getDefault().getCommandQueue().add(commandBean);

		// Execute the list command
		//
		Activator.getDefault().execute("list\n");
		ListCmdResponseParser cmdResponseParser = new ListCmdResponseParser();
		cmdResponseParser.parse(Activator.getDefault().getCommandQueue()
				.getFirst().getResponse(), listCmd);
		Activator.getDefault().getCommandQueue().remove(commandBean);
		Activator.getDefault().closeChannel();
		
		tableView.setInput(listCmd);
	}

}
