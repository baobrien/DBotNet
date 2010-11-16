package bot.net;

import java.util.*;
import java.io.*;
import java.net.*;

public class NetIfSpooler extends Thread {
	private Vector<Vector<Packet>> spool;

	private Socket sock;

	private BufferedReader br;

	private PrintStream ps;
	private int err;
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
