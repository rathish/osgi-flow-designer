package com.yobny.opensource.osgi.karafview.view.ui;

import java.io.File;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.constants.ViewConstants;

public class SelectBundleDialogue extends TitleAreaDialog {

	private Text lcTextTxt = null;
	
	private String bundlePath;
	
	private boolean OK_CLICKED;
	
	public SelectBundleDialogue(Shell parent) {
		super(parent);
		this.OK_CLICKED = true;
	}
	
	@Override
	protected Control createContents(Composite parent) {
		return super.createContents(parent);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		
		setTitle(ViewConstants.installBundleTitle);
		setTitleImage(Activator.getDefault().getImageDescriptor("icons\\component_wiz.gif").createImage());
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);
		
		Composite lbCompositeCntrl = new Composite(composite, SWT.NONE);
		
		GridLayout lbGridLayoutCntrl = new GridLayout();
		lbGridLayoutCntrl.numColumns = 3;
		lbCompositeCntrl.setLayout(lbGridLayoutCntrl);
		
		Label lcLableTxt = new Label(lbCompositeCntrl, SWT.NONE);
		lcLableTxt.setText(ViewConstants.installBundleLabel);
		
		lcTextTxt = new Text(lbCompositeCntrl, SWT.BORDER);
		GridData data = new GridData();
		data.widthHint = 300;
		lcTextTxt.setLayoutData(data);
		lcTextTxt.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				bundlePathSelected();
			}
		});
		
		Button lcSelectButton = new Button(lbCompositeCntrl, SWT.PUSH);
		lcSelectButton.setImage(Activator.getDefault().getImageDescriptor("icons\\packagefolder_obj.gif").createImage());
		lcSelectButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog ldFileDialog = new FileDialog(getShell(), SWT.OPEN);
				ldFileDialog.setText(ViewConstants.installBundleOpenDlg);
				String[] filterExt = { "*.jar" };
				ldFileDialog.setFilterExtensions(filterExt);
				lcTextTxt.setText(ldFileDialog.open());
				bundlePath = lcTextTxt.getText();
			}
		});
		return composite;
	}
	
	/**
	 * 
	 */
	private void bundlePathSelected() {
		if (new File(lcTextTxt.getText()).exists() == false) {
			this.setMessage(ViewConstants.installBundleInvalidPath, IMessageProvider.ERROR);
		} else {
			this.setMessage(lcTextTxt.getText() + ":" + ViewConstants.installBundleValidPath, IMessageProvider.INFORMATION);
			bundlePath = lcTextTxt.getText();
		}
	}
	
	@Override
	protected void cancelPressed() {
		this.OK_CLICKED = false;
		super.cancelPressed();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getBundlePath() {
		if (this.OK_CLICKED) {
			return bundlePath.replace("\\", "\\\\");
		} else {
			return "";
		}
	}
}
