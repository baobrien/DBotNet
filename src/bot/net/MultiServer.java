package bot.net;

import java.net.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * MultiServer is the class that accepts connections and coordinates
 * ServerThread creation.
 * 
 * @author Brady O'Brien
 * @version .1
 * @see ServerThread
 */
public class MultiServer implements Runnable {
	
	/** The Constant TYPE_AUTOCONF. */
	public final static int TYPE_AUTOCONF = 0;

	/** The Constant COMMAND_REQNODE. */
	public final static int COMMAND_REQNODE = 0;

	/** The Constant COMMAND_RETNODE. */
	public final static int COMMAND_RETNODE = 2;

	/** The Constant COMMAND_REQBOT. */
	public final static int COMMAND_REQBOT = 1;

	/** The Constant COMMAND_RETBOT. */
	public final static int COMMAND_RETBOT = 3;
	
	/**
	 * main is the main loop of execution for the entire server class.
	 *
	 * @param args arguments specified at command line and passed by JVM
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		int port = 5000;
		int bot = 0;
		ServerSocket ss = new ServerSocket(port);
		System.out.println("Socket Bound");
		Vector<ServerThread> sts = new Vector<ServerThread>();
		while (true) {
			ServerThread st = new ServerThread(ss.accept(), sts, bot);
			System.out.println("Client Connected");
			sts.add(st);
			st.start();
		}
	}
	
	/** The arguments. */
	private String[] arguments;
	
	/** The sport. */
	private int sport = 5000;
	
	/** The node i dcount. */
	public int nodeIDcount;
	
	/**
	 * Instantiates a new multi server.
	 */
	public MultiServer() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			MultiServer.main(arguments);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(int port) {
		Integer p = new Integer(port);
		arguments[1] = p.toString();
	}
}
