package com.yobny.opensource.osgi.karafview.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleFactory;
import org.eclipse.ui.console.IConsoleManager;

import com.yobny.opensource.osgi.karafview.Activator;
import com.yobny.opensource.osgi.karafview.constants.PreferenceConstants;

public class KarafConsoleFactory implements IConsoleFactory {

	private static int consoleIndex = 0;

	@Override
	public void openConsole() {
		startConsole();
	}

	/**
	 * 
	 */
	private void startConsole() {
		consoleIndex++;
		KarafConsole karafConsole = new KarafConsole("Karaf Console - "
				+ consoleIndex + " Connected to ["
				+ Activator.getDefault().getPreferenceStore().getString(
						PreferenceConstants.karafServerHost)
				+ ":"
				+ Activator.getDefault().getPreferenceStore().getString(
						PreferenceConstants.karafServerPort) + "]", null);
		IConsoleManager manager = ConsolePlugin.getDefault()
				.getConsoleManager();
		manager.addConsoles(new IConsole[] { karafConsole });
	}
}
