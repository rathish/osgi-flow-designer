package com.yobny.opensource.osgi.karafview.view.action;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.List;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.bean.ListUrlCmdPojo;
import com.yobny.opensource.osgi.karafview.client.CommandBean;
import com.yobny.opensource.osgi.karafview.parser.ListUrlCmdResponseParser;

public class OBRListURLAction extends Action {

	private List listField;
	
	public OBRListURLAction(List listField) {
		this.listField = listField;
	}
	
	@Override
	public void run() {
		
		ArrayList<Object> listCmd = new ArrayList<Object>();
		
		// 1) Execute the command
		//
		CommandBean lstCommandBean = new CommandBean();
		Activator.getDefault().getCommandQueue().add(lstCommandBean);
		Activator.getDefault().execute("obr:listUrl\n");
		
		// 2) Get command response
		//
		ListUrlCmdResponseParser cmdResponseParser = new ListUrlCmdResponseParser();
		cmdResponseParser.parse(Activator.getDefault().getCommandQueue()
					.getFirst().getResponse(), listCmd);
		// 3) clear the command
		//
		Activator.getDefault().getCommandQueue().remove(lstCommandBean);
		Activator.getDefault().closeChannel();
		
		// 4) Populate the response in the list
		//
		Iterator<Object> iterator = listCmd.iterator();
		while (iterator.hasNext()) {
			listField.add(((ListUrlCmdPojo)iterator.next()).getUri());
		}
	}
}
