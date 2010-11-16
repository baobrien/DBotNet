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
