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
