package com.yobny.opensource.osgi.karafview.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.constants.PreferenceConstants;

public class KarafPreference extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public KarafPreference() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Karaf Connection Properties");
	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(PreferenceConstants.karafServerHost,
				PreferenceConstants.karafServerHost, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.karafServerPort,
				PreferenceConstants.karafServerPort, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.karafUserName,
				PreferenceConstants.karafUserName, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.karafPassword,
				PreferenceConstants.karafPassword, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.karafRetryAttem,
				PreferenceConstants.karafRetryAttem, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.karafRetryDely,
				PreferenceConstants.karafRetryDely, getFieldEditorParent()));

		addField(new BooleanFieldEditor(
				PreferenceConstants.karafConsoleRequired,
				PreferenceConstants.karafConsoleRequired,
				getFieldEditorParent()));
	}

	@Override
	public void init(IWorkbench arg0) {
		
	}
}
