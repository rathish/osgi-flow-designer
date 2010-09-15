package com.yobny.opensource.osgi.karafview.preferences;

import org.eclipse.jface.preference.FieldEditor;
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
import org.eclipse.swt.widgets.Text;

import com.yobny.opensource.osgi.karafview.constants.PreferenceConstants;

/**
* @author rathish
* 
*/
public class BundleRepoURLField extends FieldEditor {
	public Text pathField;

	public List listField;

	public Button browseButton;

	public BundleRepoURLField(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);

	}

	protected void doFillIntoGrid(Composite parent, int numColumns) {
		
		GridData gd = new GridData();
		
		// Add URL List
		//
		Control control = getListPathField(parent);
		gd.heightHint = 50;
		gd.widthHint = 250;
		gd.horizontalAlignment = GridData.FILL;
		control.setLayoutData(gd);
		control.setFont(parent.getFont());

		// Add URL Manage Buttons
		//
		control = getButtonContainer(parent);
		gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		control.setLayoutData(gd);
		control.setFont(parent.getFont());
	}

	private Control getButtonContainer(Composite parent) {
		Composite buttonComp = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		buttonComp.setLayout(gridLayout);

		// Add Button
		//
		if (browseButton == null) {
			browseButton = new Button(buttonComp, SWT.PUSH);
			GridData gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			browseButton.setLayoutData(gd);
			browseButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {

					
				}
			});
			browseButton.setText("Add");

			// Remove button
			//
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
			
			// Get Button
			//
			Button getButton = new Button(buttonComp, SWT.PUSH);
			getButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					//getBundleLists();
				}
			});
			gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			getButton.setLayoutData(gd);
			getButton.setText("Get");
		}
		return buttonComp;
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
			
	}

	protected Shell getShell() {
		if (browseButton == null)
			return null;
		return browseButton.getShell();
	}

	private void openFileDialog(boolean multiSelect) {
		/*FTPConnectionObj conn = Activator.getDefault().getFtpConnectionObject();
		FileFolderSelectionDialog selectionDlg = new FileFolderSelectionDialog(
				getShell(), multiSelect, 1, conn);
		selectionDlg.open();*/

		/*Object obj[] = selectionDlg.getResult();
		for (int pathcount = 0; pathcount < obj.length; pathcount++) {
			if (obj[pathcount] != null) {
				String lcstrFilePath = (String) obj[pathcount];
				if (!lcstrFilePath.equals("")) {
					if (listTrue)
						listField.add(lcstrFilePath);
					else
						pathField.setText(lcstrFilePath);
				}
			}
		}*/
	}

	private void removeFromList() {
		String[] listValue = listField.getSelection();
		for (int pathcount = 0; pathcount < listValue.length; pathcount++)
			listField.remove(listValue[pathcount]);
	}

	protected void adjustForNumColumns(int numColumns) {
		/*
		 * if (numColumns > 1) { Control control = getLabelControl(); int left =
		 * numColumns; if (control != null) {
		 * ((GridData)control.getLayoutData()).horizontalSpan = 1; left = left -
		 * 1; } ((GridData)pathField.getLayoutData()).horizontalSpan = left; }
		 * else { Control control = getLabelControl(); if (control != null) {
		 * ((GridData)control.getLayoutData()).horizontalSpan = 1; }
		 * ((GridData)pathField.getLayoutData()).horizontalSpan = 1; }
		 */
	}

	public int getNumberOfControls() {
		return 3;
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
