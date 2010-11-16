package bot.net;

import java.io.*;
import java.net.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * ServerThreads are created by MultiServer to handle multiple connections and
 * coordinate repeating of data.
 * 
 * @author Brady O'Brien
 */
public class ServerThread extends Thread {
	/**
	 * Socket of communications for the ServerThread.
	 */
	public NetIF ni;

	/**
	 * References all other ServerThreads.
	 */
	public Vector<ServerThread> sts;

	/**
	 * Index of This in Vector sts.
	 */
	public int index;

	/**
	 * read errors, 5 or more disconnect socket and kill ServerThread.
	 */
	private int err;
	
	/** The bot. */
	private int bot;
	
	/**
	 * sets Socket and Renders BufferedReader and PrintStream from Socket.
	 *
	 * @param s Socket created by ServerSocket accept method used to stream
	 * data to and from the client.
	 * @param CurrentThreads Vector Created by MultiServer to reference all active
	 * ServerThreads.
	 * @param bot the bot
	 * @throws Exception Thrown when a BufferedReader or PrintStream cannot be
	 * pulled from the Socket.
	 */
	public ServerThread(Socket s, Vector<ServerThread> CurrentThreads, int bot)
	throws Exception {
		ni = new NetIF(s, 0);
		sts = CurrentThreads;
		index = sts.indexOf(this);
	}
	
	/**
	 * Kill.
	 */
	public void kill() {
		try {
			ni.sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sts.remove(this);
		this.stop();
	}
	
	/** The my node. */
	int myNode;
	/**
	 * Inherited from Thread, code to be executed in new thread.
	 */
	public void run() {
		Packet p = null;
		while (ni.isConnected())
			try {
				p = ni.Rcv();
				p.Decode();
				myNode=p.nodesending;
				System.out.println(p.raw);
				for (ServerThread stt:sts)
					if(stt.myNode==p.nodereceving||p.nodereceving==0)
						stt.ni.XSend(p);
			} catch (Exception e) {
				System.err
				.println("Error: ServerThread BufferedReader not Read");
				e.printStackTrace();
				p = null;
				err++;
				if (err >= 5)
					kill();
			}
			kill();

	}
}
