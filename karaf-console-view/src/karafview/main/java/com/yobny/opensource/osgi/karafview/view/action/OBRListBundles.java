package com.yobny.opensource.osgi.karafview.view.action;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TableViewer;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.client.CommandBean;
import com.yobny.opensource.osgi.karafview.parser.RepoBundleListCmdResponseParser;

public class OBRListBundles extends Action {

	private TableViewer tableViewer;
	
	public OBRListBundles(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
	}
	
	@Override
	public void run() {
		ArrayList<Object> listCmd = new ArrayList<Object>();
		
		// 1) Execute the command
		//
		CommandBean lstCommandBean = new CommandBean();
		Activator.getDefault().getCommandQueue().add(lstCommandBean);
		Activator.getDefault().execute("obr:list\n");
		
		// 2) Get command response
		//
		RepoBundleListCmdResponseParser bundleListCmdResponseParser = new RepoBundleListCmdResponseParser();
		bundleListCmdResponseParser.parse(Activator.getDefault().getCommandQueue()
					.getFirst().getResponse(), listCmd);
		// 3) clear the command
		//
		Activator.getDefault().getCommandQueue().remove(lstCommandBean);
		Activator.getDefault().closeChannel();
		
		tableViewer.setInput(listCmd);
	}
}
