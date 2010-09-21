package com.yobny.opensource.osgi.karafview.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import com.yobny.opensource.osgi.karafview.constants.PreferenceConstants;
import com.yobny.opensource.osgi.karafview.view.action.OBRListBundles;
import com.yobny.opensource.osgi.karafview.view.action.OBRListURLAction;
import com.yobny.opensource.osgi.karafview.view.ui.BundleContentProvider;
import com.yobny.opensource.osgi.karafview.view.ui.BundleLableProvider;


/**
* @author rathish
* 
*/
public class BundleRepoURLField extends FieldEditor {
	public Text pathField;

	public List listField;

	public Button browseButton;
	
	private TableViewer tableViewer;

	public BundleRepoURLField(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);
		
	}

	protected void doFillIntoGrid(Composite parent, int numColumns) {
		
		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		comp.setLayout(gridLayout);
		
		// Field URL List
		//
		Control control = getListPathField(comp);
		GridData gd = new GridData();
		gd.heightHint = 100;
		gd.widthHint = 350;
		gd.horizontalAlignment = GridData.FILL;
		control.setLayoutData(gd);
		control.setFont(parent.getFont());

		// Field Buttons
		//
		control = getButtonContainer(comp);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		control.setLayoutData(gd);
		control.setFont(parent.getFont());
		
		Composite compViewer = new Composite(parent, SWT.NONE);
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 1;
		compViewer.setLayout(gridLayout1);
		gd = new GridData();
		gd.heightHint = 200;
		gd.horizontalAlignment = GridData.FILL;
		compViewer.setLayoutData(gd);
		
		tableViewer = new TableViewer(compViewer, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		tableViewer.getControl().setLayoutData(gd);
		this.initializeColoums();
		tableViewer.setContentProvider(new BundleContentProvider());
		tableViewer.setLabelProvider(new BundleLableProvider());
	}
	private void initializeColoums() {
		String[] titles = { "", "PresentationName", "Version" };
		int[] bounds = { 50, 200, 100 };

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
	
	private Control getButtonContainer(Composite parent) {
		Composite buttonComp = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		buttonComp.setLayout(gridLayout);

		if (browseButton == null) {
			browseButton = new Button(buttonComp, SWT.PUSH);
			GridData gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			browseButton.setLayoutData(gd);
			browseButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					addBundleURLs();
				}
			});
			browseButton.setText("Add");

			Button removeButton = new Button(buttonComp, SWT.PUSH);
			removeButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					removeFromList();
				}
			});
			gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			removeButton.setLayoutData(gd);
			removeButton.setText("Remove");
			
			Button getButton = new Button(buttonComp, SWT.PUSH);
			getButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					OBRListBundles obrListBundles = new OBRListBundles(tableViewer);
					obrListBundles.run();
				}
			});
			gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			getButton.setLayoutData(gd);
			getButton.setText("Get");
		}
		return buttonComp;
	}

	
	private void addBundleURLs() {
		// Popup for URL Entry
		//
		
	}
	
	
	private Control getListPathField(Composite parent) {
		if (listField == null) {
			listField = new List(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
					| SWT.H_SCROLL);
			listField.setFont(parent.getFont());
		}
		return listField;
	}

	protected void doLoad() {
		OBRListURLAction listURLAction = new OBRListURLAction(this.listField);
		listURLAction.run();
	}
	
	
	protected Shell getShell() {
		if (browseButton == null)
			return null;
		return browseButton.getShell();
	}

	
	private void removeFromList() {
		String[] listValue = listField.getSelection();
		for (int pathcount = 0; pathcount < listValue.length; pathcount++)
			listField.remove(listValue[pathcount]);
	}

	
	protected void adjustForNumColumns(int numColumns) {
	}

	
	public int getNumberOfControls() {
		return 1;
	}
	
	
	protected void doLoadDefault() {
	}

	
	protected void doStore() {
		String paths = "";
		listField.selectAll();
		for (int pathCount = 0; pathCount < listField.getSelection().length; pathCount++) {
			if (pathCount == listField.getSelection().length - 1)
				paths = paths + listField.getSelection()[pathCount];
			else
				paths = paths + listField.getSelection()[pathCount]
						+ PreferenceConstants.IMPORT_PATH_SEP;

		}
		getPreferenceStore().setValue(getPreferenceName(), paths);
	}
}
