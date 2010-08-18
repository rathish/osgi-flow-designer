package com.yobny.opensource.osgi.karafview.client;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.sshd.ClientChannel;
import org.apache.sshd.ClientSession;
import org.apache.sshd.SshClient;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.common.RuntimeSshException;
import org.fusesource.jansi.AnsiConsole;

public class KarafConnection {

	private String hostname;

	private int port;

	private String userName;

	private String password;

	private int retryAttempts;

	private int retryDelay;

	private InputStream inputStream;

	private OutputStream outputStream;
	
	private String shellType;

	private boolean connected;

	private SshClient client = null;

	private ClientChannel channel;

	private ClientSession session = null;

	private ConnectFuture future;

	private boolean openned;

	public KarafConnection() {

	}
	/**
	 * 
	 * @param hostName
	 * @param port
	 * @param userName
	 * @param password
	 * @param retryAttempts
	 * @param retryDelay
	 * @param inputStream
	 * @param outputStream
	 * @param shellType
	 * @return
	 */
	public ClientSession connect(String hostName, int port, String userName,
			String password, int retryAttempts, int retryDelay) {

		this.hostname = hostName;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.retryAttempts = retryAttempts;
		this.retryDelay = retryDelay;
		
		System.out.println(this.hostname);
		System.out.println(this.port);
		System.out.println(this.userName);
		System.out.println(this.password);
		System.out.println(this.retryAttempts);
		System.out.println(this.retryDelay);
		
		return this.makeConnection();
	}

	public ClientSession connect(String hostName, int port, String userName,
			String password, int retryAttempts, int retryDelay, InputStream inputStream, OutputStream outputStream, String shellType) {

		this.hostname = hostName;
		this.port = port;
		this.userName = userName;
		this.password = password;
		this.retryAttempts = retryAttempts;
		this.retryDelay = retryDelay;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.shellType = shellType;
		
		System.out.println(this.hostname);
		System.out.println(this.port);
		System.out.println(this.userName);
		System.out.println(this.password);
		System.out.println(this.retryAttempts);
		System.out.println(this.retryDelay);
		
		return this.makeConnection();
	}
	/**
	 * 
	 * @return
	 */
	private ClientSession makeConnection() {

		// Start the client
		//
		client = SshClient.setUpDefaultClient();
		client.start();
		System.out.println("Client Started");
		int retries = 0;
		try {
			do {
				future = client.connect(this.hostname, this.port);
				future.await();
				System.out.println("Future Awaited");
				try {
					session = future.getSession();
					System.out.println("Getting session" + session);
				} catch (RuntimeSshException ex) {
					if (retries++ < retryAttempts) {
						Thread.sleep(retryDelay * 1000);
						System.out.println(ex.getMessage());
					} else {
						throw ex;
					}
				}
			} while (session == null);
			if (!session.authPassword(userName, password).await().isSuccess()) {
				throw new Exception("Authentication failure");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.connected = false;
		}
		this.connected = true;
		return session;
	}

	/**
	 * 
	 * @return
	 */
	public boolean close() {
		try {
			channel.close(true).await();
			session.close(true).await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		future.cancel();
		client.stop();
		openned = false;
		return true;
	}

	/**
	 * 
	 */
	public void open() {
		try {
			this.openned = false;
			channel = session.createChannel(this.shellType);
			channel.setIn(new CommandInputStream(this.inputStream));
			channel.setOut(AnsiConsole
					.wrapOutputStream(outputStream));
			channel.setErr(AnsiConsole.wrapOutputStream(this.outputStream));
			channel.open();
			channel.waitFor(ClientChannel.CLOSED, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return this.connected;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isOpenned() {
		return this.openned;
	}
}
