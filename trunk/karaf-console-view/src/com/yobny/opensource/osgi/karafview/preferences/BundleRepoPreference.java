package com.yobny.opensource.osgi.karafview.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.yobny.opensource.osgi.karafview.Activator;

public class BundleRepoPreference extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public BundleRepoPreference() {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("BundleRepo Description");
		
	}
	
	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}
	
	

}
