/*----------------------------------------------------------------------------------------------*/
/*Copyright 2010 Brady O'Brien. All rights reserved.											*/
/*																								*/
/* Redistribution and use in source and binary forms, with or without modification, are			*/
/*permitted provided that the following conditions are met:										*/
/*																								*/
/*   1. Redistributions of source code must retain the above copyright notice, this list of		*/
/*      conditions and the following disclaimer.												*/
/*																								*/
/*   2. Redistributions in binary form must reproduce the above copyright notice, this list		*/
/*      of conditions and the following disclaimer in the documentation and/or other materials	*/
/*      provided with the distribution.															*/
/*																								*/
/*THIS SOFTWARE IS PROVIDED BY BRADY O'BRIEN ``AS IS'' AND ANY EXPRESS OR IMPLIED				*/
/*WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND		*/
/*FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BRADY O'BRIEN OR			*/
/*CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR			*/
/*CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR		*/
/*SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON		*/
/*ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING			*/
/*NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF			*/
/*ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.													*/
/*																								*/
/*The views and conclusions contained in the software and documentation are those of the		*/
/*authors and should not be interpreted as representing official policies, either expressed		*/
/*or implied, of Brady O'Brien.																	*/
/*----------------------------------------------------------------------------------------------*/

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
