package com.yobny.opensource.osgi.karafview.view.ui;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.yobny.opensource.osgi.karafview.bean.ListCmdPojo;

public class BundleContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object arg0) {
		@SuppressWarnings("unchecked")
		ArrayList<ListCmdPojo> arrayList = (ArrayList<ListCmdPojo>)arg0;
		return arrayList.toArray();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		
	}

}
