package com.yobny.opensource.osgi.karafview;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Deque;
import java.util.LinkedList;

import org.apache.sshd.ClientChannel;
import org.apache.sshd.ClientSession;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.fusesource.jansi.AnsiConsole;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.yobny.opensource.osgi.karafview.client.CommandBean;
import com.yobny.opensource.osgi.karafview.client.CommandOutputStream;
import com.yobny.opensource.osgi.karafview.client.KarafConnection;
import com.yobny.opensource.osgi.karafview.constants.PreferenceConstants;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "systemtest_v1.0";

	// The shared instance
	private static Activator plugin;

	private Deque<CommandBean> commandQueue;

	private ClientSession clientSession;

	

	private BundleContext bundleContext;

	public ImageDescriptor getImageDescriptor(String imagePath) {
		try {
			return ImageDescriptor.createFromURL(new URL(Activator.getDefault()
					.getBundle().getEntry("/"), imagePath));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * The constructor
	 */
	public Activator() {
		commandQueue = new LinkedList<CommandBean>();
		
	}

	public void start(BundleContext context) throws Exception {
		bundleContext = context;
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public Bundle[] getBundles() {
		return this.bundleContext.getBundles();
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public Deque<CommandBean> getCommandQueue() {
		return this.commandQueue;
	}

	public void setupKarafConsole() {

	}

	public ClientSession getKarafConnection() {

		if (this.clientSession == null) {

			KarafConnection connection = new KarafConnection();

			this.clientSession = connection.connect(Activator.getDefault()
					.getPreferenceStore().getString(
							PreferenceConstants.karafServerHost), Integer
					.parseInt(Activator.getDefault().getPreferenceStore()
							.getString(PreferenceConstants.karafServerPort)),
					Activator.getDefault().getPreferenceStore().getString(
							PreferenceConstants.karafUserName), Activator
							.getDefault().getPreferenceStore().getString(
									PreferenceConstants.karafPassword),
					Integer.parseInt(Activator.getDefault()
							.getPreferenceStore().getString(
									PreferenceConstants.karafRetryAttem)),
					Integer.parseInt(Activator.getDefault()
							.getPreferenceStore().getString(
									PreferenceConstants.karafRetryDely)));
		}

		return this.clientSession;
	}

	private ClientChannel channel;

	public void execute(String command) {
		try {
			CommandOutputStream commandOutputStream = new CommandOutputStream(getCommandQueue().getFirst());
			channel = getKarafConnection().createChannel("exec");
			channel.setIn(new ByteArrayInputStream(command.getBytes()));
			channel.setOut(AnsiConsole.wrapOutputStream(commandOutputStream));
			channel.setErr(AnsiConsole.wrapOutputStream(commandOutputStream));
			channel.open();
			channel.waitFor(ClientChannel.CLOSED, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeChannel() {
		try {
			this.channel.close(true).await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
