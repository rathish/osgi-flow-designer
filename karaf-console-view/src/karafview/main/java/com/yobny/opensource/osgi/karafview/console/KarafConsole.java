package com.yobny.opensource.osgi.karafview.console;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IOConsole;
import org.eclipse.ui.console.IOConsoleInputStream;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.TextConsolePage;
import org.eclipse.ui.part.IPageBookViewPage;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.client.KarafConnection;
import com.yobny.opensource.osgi.karafview.constants.PreferenceConstants;

public class KarafConsole extends IOConsole {

	TextConsolePage fPage;

	private IOConsoleInputStream fInput;

	private IOConsoleOutputStream fOut;

	public KarafConsole(String name, ImageDescriptor imageDescriptor) {
		super(name, imageDescriptor);
		fInput = getInputStream();
		fOut = newOutputStream();
	}

	@Override
	public IPageBookViewPage createPage(IConsoleView view) {
		fPage = (TextConsolePage) super.createPage(view);
		return fPage;
	}

	public class ConnectJob extends Job {
		KarafConnection connection;
		public ConnectJob(String name) {
			super(name);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected IStatus run(IProgressMonitor arg0) {
			String karafHostName = Activator.getDefault().getPreferenceStore()
					.getString(PreferenceConstants.karafServerHost);
			String karafPort = Activator.getDefault().getPreferenceStore()
					.getString(PreferenceConstants.karafServerPort);
			String karafUsername = Activator.getDefault().getPreferenceStore()
					.getString(PreferenceConstants.karafUserName);
			String karafPassword = Activator.getDefault().getPreferenceStore()
					.getString(PreferenceConstants.karafPassword);
			String karafRetryAttempts = Activator.getDefault()
					.getPreferenceStore().getString(
							PreferenceConstants.karafRetryAttem);
			String karafRetryDelay = Activator.getDefault()
					.getPreferenceStore().getString(
							PreferenceConstants.karafRetryDely);

			connection = new KarafConnection();
			connection.connect(karafHostName, Integer.parseInt(karafPort),
					karafUsername, karafPassword, Integer
							.parseInt(karafRetryAttempts), Integer
							.parseInt(karafRetryDelay), (InputStream) fInput,
					(OutputStream) fOut, "shell");
			connection.open();

			return Status.OK_STATUS;
		}
		
		@Override
		protected void canceling() {
			super.canceling();
			connection.close();
		}
	}

	@Override
	protected void init() {
		super.init();
		// Get the Karaf Connection Parameters from the
		// preferences store.
		//
		if (Activator.getDefault().getPreferenceStore().getBoolean(
				PreferenceConstants.karafConsoleRequired) == true) {
			
			fOut.setColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
			
			// Connect to the Karaf console
			//
			ConnectJob connectJob = new ConnectJob("Karaf Connection");
			connectJob.setSystem(true);
			connectJob.schedule();
		} else {
			try {
				fOut.setColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED));
				fOut.write("Executing in Not-Connected-Mode, modify the preference to get Karaf Console");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
