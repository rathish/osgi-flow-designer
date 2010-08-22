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

	public boolean listTrue = false;

	public BundleRepoURLField(String name, String labelText, Composite parent,
			boolean list) {
		init(name, labelText);
		this.listTrue = list;
		createControl(parent);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite,
	 *      int)
	 */
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Control control = getLabelControl(parent);
		GridData gd = new GridData();
		control.setLayoutData(gd);
		control.setFont(parent.getFont());
		gd = new GridData();
		if (this.listTrue == false)
			control = getPathField(parent);
		else {
			control = getListPathField(parent);
			gd.heightHint = 50;
		}

		gd.widthHint = 250;
		gd.horizontalAlignment = GridData.FILL;
		control.setLayoutData(gd);
		control.setFont(parent.getFont());

		if (this.listTrue == false)
			control = getBrowseFiled(parent);
		else
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

		if (browseButton == null) {
			browseButton = new Button(buttonComp, SWT.PUSH);
			GridData gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			browseButton.setLayoutData(gd);
			browseButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {

					openFileDialog(listTrue);
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

	private Control getPathField(Composite parent) {
		if (pathField == null) {
			pathField = new Text(parent, SWT.BORDER);
			pathField.setFont(parent.getFont());
		}
		return pathField;
	}

	private Control getBrowseFiled(Composite parent) {
		if (browseButton == null) {
			browseButton = new Button(parent, SWT.PUSH);
			browseButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {

					openFileDialog(listTrue);
				}
			});
			browseButton.setText("/..");
		}
		return browseButton;
	}

	protected void doLoad() {
		if (listTrue == false)
			pathField.setText(getPreferenceStore().getString(
					getPreferenceName()));
		else {
			String paths = getPreferenceStore().getString(getPreferenceName());
			String[] importPaths = paths
					.split(PreferenceConstants.IMPORT_PATH_SEP);
			for (int pathCount = 0; pathCount < importPaths.length; pathCount++) {
				if (importPaths[pathCount].equals("") == false)
					listField.add(importPaths[pathCount]);
			}
		}
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
		if (listTrue == false)
			getPreferenceStore().setValue(getPreferenceName(),
					pathField.getText());
		else {
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

}
