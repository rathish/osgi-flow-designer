package com.yobny.opensource.osgi.karafview.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.yobny.opensource.osgi.karafview.view.ui.BundleContentProvider;
import com.yobny.opensource.osgi.karafview.view.ui.BundleLableProvider;

public class BundleRepoListField extends FieldEditor {

	TableViewer tableViewer;
	
	public BundleRepoListField(Composite parent) {
		init("", "");
		createControl(parent);
	}
	
	@Override
	protected void adjustForNumColumns(int numColumns) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		this.initializeColoums();
		tableViewer.setContentProvider(new BundleContentProvider());
		tableViewer.setLabelProvider(new BundleLableProvider());
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
	protected void doLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doLoadDefault() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doStore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNumberOfControls() {
		// TODO Auto-generated method stub
		return 1;
	}

	
}
