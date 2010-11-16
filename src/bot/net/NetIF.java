package bot.net;

import bot.net.Packet;
import java.net.*;
import java.io.*;
import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * NetIF is an automatic interface to simplify bot network communications, and
 * it includes facilities to automate creation of services by inheritance.
 * 
 * @author Brady O'Brien
 * @see Packet
 */
public class NetIF {
	/**
	 * Socket used for network transactions.
	 */
	protected Socket sock;

	/**
	 * Used to communicate with the Socket.
	 */
	protected BufferedReader br;

	/**
	 * Used to communicate with the Socket.
	 */
	protected PrintStream ps;

	/** The node. */
	private int node;

	/**
	 * Vector of Vectors of Packets used to store packets of the type not
	 * needed. Index of first Vector refers to type field of Packets
	 */
	private Vector<Vector<Packet>> TypeHolder;

	/** Spool threat to automatically put data from the buffer into TypeHolder. */
	private NetIfSpooler spool;

	/**
	 * Construct NetIF using a socket, and the bot/process address.
	 *
	 * @param s Socket to be used throughout NetIF.
	 * @param Node Bot Number.
	 * @throws Exception If an error is found durng construction, there is now way to
	 * trap it.
	 */

	public NetIF(Socket s, int Node) throws Exception {
		int types = 32;
		sock = s;
		try {
			br = new BufferedReader(
					new InputStreamReader(sock.getInputStream()));
			ps = new PrintStream(sock.getOutputStream());
		} catch (Exception e) {
			System.err
			.println("Bot.Net.NetIF.NetIF: Error in rendering streams");
			throw e;
		}
		node = Node;
		TypeHolder = new Vector<Vector<Packet>>();
		for (int tf = 0; tf < types; tf++)
			TypeHolder.add(new Vector<Packet>());
		Send(new Packet(0,0,0,0,""));
	}

	/**
	 * Puts a packet on the buffer for its type.
	 * 
	 * @param p
	 *            packet to be put on the buffer.
	 */
	public void Catalog(Packet p) {
		try {
			p.Decode();
			TypeHolder.elementAt(p.type).add(p);
		} catch (Exception e) {
			System.err.println("Bot.Net.NetIF.Catalog: Error decoding Packet");
		}
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return sock.isConnected();
	}

	/**
	 * Called to loop and check packet input for a service.
	 * 
	 * @throws Exception
	 *             Thrown if a socket or packet error is detected and cannot be
	 *             trapped.
	 */
	public void MainLoop() throws Exception {
		Packet p = null;
		while (isConnected()) {
			try {
				p = this.Rcv();
				p.Decode();
			} catch (Exception e) {
				System.err
				.println("bot.net.NetIF.MainLoop: Error in socket reciving or Packet Decoding");
			}
			if (p.nodereceving == node)
				OnRcv(p);
		}
	}

	/**
	 * Vestigial code to be overridden by a child class service.
	 * 
	 * @param p
	 *            Packet to be used
	 */
	public void OnRcv(Packet p) {
	}

	/**
	 * Returns the first line gathered from the socket encapsulated in a packet.
	 * 
	 * @return Packet Received.
	 * @throws Exception
	 *             thrown if an error is detected while reading off the
	 *             BufferedReader for the Socket.
	 */
	public Packet Rcv() throws Exception {
		String t;
		t = br.readLine();
		while (t == null)
			t = br.readLine();
		return new Packet(t);
	}

	/**
	 * Receive the first packet addressed for this NetIF.
	 *
	 * @return First packet addressed for this NetIF
	 * @throws Exception if error found in decoding or receiving
	 */
	public Packet RcvChecked() throws Exception {
		Packet p = null;
		p = this.Rcv();
		p.Decode();
		while (p.nodereceving != node&&p.nodereceving!=0) {
			p = this.Rcv();
			p.Decode();
		}
		return p;
	}
	/**
	 * Used to send a specified packet on the interface. Destination selection
	 * is left to the sender, while source feilds are set by the method.
	 * 
	 * @param p
	 *            Packet to be sent.
	 * @throws Exception
	 *             Thrown if there is an error writing to the socket or
	 *             encoding.
	 */
	public void Send(Packet p) throws Exception {
		p.nodesending = node;
		p.Encode();
		ps.println(p.raw);
	}
	/**
	 * Starts the automatic spooler to handle network communications.
	 * 
	 * @throws Exception
	 *             Thrown if spool cannot be started or initalized.
	 */
	public void StartSpooler() throws Exception {
		spool = new NetIfSpooler(TypeHolder, sock);
		spool.start();
	}
	
	/**
	 * Type de spool.
	 *
	 * @param type the type
	 * @return the packet
	 */
	public Packet TypeDeSpool(int type) {
		Packet t;
		while (TypeHolder.elementAt(type).size() < 1) {
		}
		t = TypeHolder.elementAt(type).firstElement();
		TypeHolder.elementAt(type).remove(
				TypeHolder.elementAt(type).firstElement());
		return t;
	}
	/**
	 * Returns packet of only a certain type, cataloging all others that come
	 * in.
	 * 
	 * @param type
	 *            type to be returned
	 * @return first packet of that type on the buffer.
	 */
	public Packet TypeRcv(int type) {
		Packet t;
		while (TypeHolder.elementAt(type).size() == 0)
			try {
				t = this.RcvChecked();
				t.Decode();
				Catalog(t);
			} catch (Exception e) {
				System.err.println("Bot.Net.NetIF.TypeRcv: Error Rcving");
			}
		t = TypeHolder.elementAt(type).firstElement();
		TypeHolder.elementAt(type).remove(
				TypeHolder.elementAt(type).firstElement());
		return t;
	}
	
	/**
	 * X send.
	 *
	 * @param p the p
	 * @throws Exception the exception
	 */
	public void XSend(Packet p) throws Exception {
		p.Encode();
		ps.println(p.raw);
	}

}