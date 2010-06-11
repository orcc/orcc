package net.sf.orcc.runtime.debug;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class implements a Telnet socket server. It will open a ServerSocket on
 * a port. As there is only one connection expected it also implements a single
 * thread to service that connection
 * 
 * @author Rob Esser
 * @version 7/6/2007
 * 
 */
public class SocketServerThread extends Thread {

	private InputStream inStream = null;

	/** the IP port number to listen on */
	private int ipPortNumber;

	private PrintStream outStream = null;

	private ServerSocket serverSocket = null;

	private Socket socket = null;

	/**
	 * Create a socket server
	 * 
	 * @param ipPortNumber
	 *            the ip port number to listen on
	 */
	public SocketServerThread(int ipPortNumber) {
		this.ipPortNumber = ipPortNumber;
		try {
			serverSocket = new ServerSocket(ipPortNumber);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + ipPortNumber
					+ ", " + e.getMessage());
			System.exit(-1);
		}
		start();
	}

	/**
	 * Attempt to cleanup nicely
	 */
	public void close() {
		try {
			if (inStream != null) {
				inStream.close();
			}
			if (outStream != null) {
				outStream.close();
			}
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Return the input stream associated with this socket
	 */
	public InputStream getInputStream() {
		if (socket == null) {
			return null;
		}
		if (inStream == null) {
			try {
				inStream = socket.getInputStream();
			} catch (IOException e) {
				System.err
						.println("*** ERROR Could not get input stream on Telnet socket");
			}
		}
		return inStream;
	}

	/**
	 * Return the output stream associated with this socket
	 */
	public PrintStream getPrintStream() {
		if (socket == null) {
			return null;
		}
		if (outStream == null) {
			try {
				outStream = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
				System.err
						.println("*** ERROR Could not get output stream on Telnet socket");
			}
		}
		return outStream;
	}

	/**
	 * return true is a connection has been made
	 */
	public boolean isConnected() {
		return socket != null;
	}

	/**
	 * the new thread method. It will wait for a client connection.
	 */
	@Override
	public void run() {
		try {
			socket = serverSocket.accept();
		} catch (Exception e) {
			System.err.println("*** ERROR Could not listen on socket port: "
					+ ipPortNumber);
			System.exit(-1);
		}
	}

}
