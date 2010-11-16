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

package bot.io.client;

import bot.io.Mode;
import bot.io.lockException;
import bot.net.*;

// TODO: Auto-generated Javadoc
/**
 * The Class IOClientHandler.
 */
public class IOClientHandler {
	
	/** The servicenode. */
	int servicenode;

	/** The netiface. */
	NetIF netiface;

	/**
	 * Instantiates a new iO client handler.
	 *
	 * @param net the net
	 * @param serviceNode the service node
	 * @throws Exception the exception
	 */
	public IOClientHandler(NetIF net, int serviceNode) throws Exception {
		netiface = net;
		servicenode = serviceNode;
	}

	/**
	 * In.
	 *
	 * @param port the port
	 * @return the int
	 * @throws lockException the lock exception
	 * @throws Exception the exception
	 */
	public int in(int port) throws lockException, Exception {
		SSend(Mode.TYPE_IOSERVICE_COMMAND, Mode.COMMAND_IOSERVICE_IN, port);
		Packet p = netiface.TypeDeSpool(Mode.TYPE_IOSERVICE_REPLY);
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_RESEND)
			in(port);
		if (p.cmd == Mode.REPLY_IOSERVICE_DATA)
			return new Integer(p.data).intValue();
		return -1;
	}

	/**
	 * Lock.
	 *
	 * @param port the port
	 * @throws lockException the lock exception
	 */
	public void lock(int port) throws lockException {
		SSend(Mode.TYPE_IOSERVICE_COMMAND, Mode.COMMAND_IOSERVICE_LOCK, port);
		Packet p = netiface.TypeDeSpool(Mode.TYPE_IOSERVICE_REPLY);
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_LOCK)
			throw new lockException(port + ", Already locked");
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_RESEND)
			lock(port);
		if (p.cmd == Mode.REPLY_IOSERVICE_CLEAR)
			return;
	}

	/**
	 * Mode.
	 *
	 * @param port the port
	 * @param mode the mode
	 * @throws lockException the lock exception
	 */
	public void mode(int port, int mode) throws lockException {
		SSend2(Mode.TYPE_IOSERVICE_COMMAND, Mode.COMMAND_IOSERVICE_MODE, mode,port);
		Packet p = netiface.TypeDeSpool(Mode.TYPE_IOSERVICE_REPLY);
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_LOCK)
			throw new lockException(port + "locked by someone else");
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_RESEND)
			mode(port, mode);
		if (p.cmd == Mode.REPLY_IOSERVICE_CLEAR)
			return;
	}

	/**
	 * Out.
	 *
	 * @param port the port
	 * @param value the value
	 * @throws lockException the lock exception
	 * @throws Exception the exception
	 */
	public void out(int port, int value) throws lockException, Exception {
		SSend2(Mode.TYPE_IOSERVICE_COMMAND, Mode.COMMAND_IOSERVICE_OUT, value,port);
		Packet p = netiface.TypeDeSpool(Mode.TYPE_IOSERVICE_REPLY);
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_LOCK)
			throw new lockException(port + "locked by someone else");
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_RESEND)
			out(port, value);
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_MODE)
			throw new Exception("Incorrect mode");
		if (p.cmd == Mode.REPLY_IOSERVICE_CLEAR)
			return;
	}


	/**
	 * S send.
	 *
	 * @param type the type
	 * @param command the command
	 * @param data the data
	 */
	private void SSend(int type, int command, int data) {
		try {
			netiface.Send(new Packet(0, servicenode, type, command,
					new Integer(data).toString()));
		} catch (Exception e) {
		}
	}
	
	/**
	 * S send2.
	 *
	 * @param type the type
	 * @param command the command
	 * @param data the data
	 * @param data2 the data2
	 */
	private void SSend2(int type, int command, int data,int data2) {
		try {
			Packet p = new Packet(0,servicenode,type,command,"");
			p.mdata = new String[2];
			p.mdata[0]=Integer.toString(data);
			p.mdata[1]=Integer.toString(data2);
			netiface.Send(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Unlock.
	 *
	 * @param port the port
	 * @throws lockException the lock exception
	 */
	public void unlock(int port) throws lockException {
		SSend(Mode.TYPE_IOSERVICE_COMMAND, Mode.COMMAND_IOSERVICE_UNLOCK, port);
		Packet p = netiface.TypeDeSpool(Mode.TYPE_IOSERVICE_REPLY);
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_LOCK)
			throw new lockException(port + "locked by someone else");
		if (p.cmd == Mode.REPLY_IOSERVICE_ERR_RESEND)
			unlock(port);
		if (p.cmd == Mode.REPLY_IOSERVICE_CLEAR)
			return;
	}
}
