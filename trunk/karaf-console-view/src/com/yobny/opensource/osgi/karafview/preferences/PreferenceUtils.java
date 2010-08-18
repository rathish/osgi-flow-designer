package com.yobny.opensource.osgi.karafview.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class PreferenceUtils extends FieldEditor {

	public PreferenceUtils(String strLbl, Composite composite) {
		super("strlbl", strLbl, composite);
	}

	private Label utilLbl;

	public int getNumberOfControls() {
		return 1;
	}

	protected void doLoad() {
	}

	protected void doLoadDefault() {
	}

	protected void doStore() {
	}

	protected void doFillIntoGrid(Composite composite, int i) {
		utilLbl = getLabelControl(composite);
		GridData griddata = new GridData();
		griddata.horizontalSpan = i;
		griddata.horizontalAlignment = 4;
		griddata.grabExcessHorizontalSpace = false;
		griddata.verticalAlignment = 2;
		griddata.grabExcessVerticalSpace = false;
		utilLbl.setLayoutData(griddata);
		
	}

	protected void adjustForNumColumns(int i) {
		((GridData) utilLbl.getLayoutData()).horizontalSpan = i;
	}
}
