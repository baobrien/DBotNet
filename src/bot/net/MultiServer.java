package bot.net;

import java.net.*;
import java.util.*;

/**
 * MultiServer is the class that accepts connections and coordinates
 * ServerThread creation.
 * 
 * @author Brady O'Brien
 * @version .1
 * @see ServerThread
 */
public class MultiServer implements Runnable {
	public final static int TYPE_AUTOCONF = 0;

	public final static int COMMAND_REQNODE = 0;

	public final static int COMMAND_RETNODE = 2;

	public final static int COMMAND_REQBOT = 1;

	public final static int COMMAND_RETBOT = 3;
	/**
	 * main is the main loop of execution for the entire server class.
	 * 
	 * @param args
	 *            arguments specified at command line and passed by JVM
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
	private String[] arguments;
	private int sport = 5000;
	public int nodeIDcount;
	public MultiServer() {
	}
	public void run() {
		try {
			MultiServer.main(arguments);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setPort(int port) {
		Integer p = new Integer(port);
		arguments[1] = p.toString();
	}
}
