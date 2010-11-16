package bot.net;

import java.util.*;
import java.io.*;
import java.net.*;

// TODO: Auto-generated Javadoc
/**
 * The Class NetIfSpooler.
 */
public class NetIfSpooler extends Thread {
	
	/** The spool. */
	private Vector<Vector<Packet>> spool;

	/** The sock. */
	private Socket sock;

	/** The br. */
	private BufferedReader br;

	/** The ps. */
	private PrintStream ps;
	
	/** The err. */
	private int err;
	
	/**
	 * Instantiates a new net if spooler.
	 *
	 * @param buffer the buffer
	 * @param s the s
	 * @throws Exception the exception
	 */
	public NetIfSpooler(Vector<Vector<Packet>> buffer, Socket s)
	throws Exception {
		spool = buffer;
		sock = s;
		try {
			br = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			ps = new PrintStream(sock.getOutputStream());
		} catch (Exception e) {
			System.err
			.println("bot.net.NetIfSpooler:Streams not rendered from Socket");
			throw e;
		}

	}
	
	/**
	 * Catalog.
	 *
	 * @param p the p
	 */
	public void Catalog(Packet p) {
		try {
			p.Decode();
			spool.elementAt(p.type).add(p);
		} catch (Exception e) {
			System.err
			.println("Bot.Net.NetIFSpoller.Catalog: Error decoding Packet");
			err++;
		}
		if(err>=5)this.stop();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		String t = null;
		while (sock.isConnected()) {
			try {
				t = br.readLine();
			} catch (Exception e) {
				System.err.println("bot.net.NetIfSpooler: Read Error");
				t = null;
				err++;
				if (err >= 5)
					this.stop();
			}
			try {
				Catalog(new Packet(t));
			} catch (Exception e) {
				System.err
				.println("bot.net.NetifSpooler.run: Error decoding packet");
			}
		}
	}

}
