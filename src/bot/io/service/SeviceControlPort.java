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

/*
 * 
 */
package bot.io.service;

import bot.io.IODevice;
import bot.io.lockException;

// TODO: Auto-generated Javadoc
/**
 * The Class SeviceControlPort.
 */
public class SeviceControlPort {
	
	/** The iodev. */
	private IODevice iodev;

	/** The locked. */
	private boolean locked;

	/** The owner. */
	private int owner;

	/** The ID. */
	private int ID;

	/** The HWID. */
	private int HWID;

	/** The mode. */
	private int mode;

	/** The value. */
	private int value;

	/**
	 * Instantiates a new sevice control port.
	 *
	 * @param ID the iD
	 * @param HWID the hWID
	 * @param mode the mode
	 * @param device the device
	 */
	public SeviceControlPort(int ID, int HWID, int mode, IODevice device) {
		this.ID = ID;
		this.HWID = HWID;
		this.mode = mode;
		owner = -1;
		locked = false;
		iodev = device;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.service.Port#getIn(int)
	 */
	/**
	 * Gets the in.
	 *
	 * @param bidder the bidder
	 * @return the in
	 * @throws Exception the exception
	 * @throws lockException the lock exception
	 */
	public int getIn(int bidder) throws Exception, lockException {
		if (locked && owner != bidder)
			throw new lockException(
			"bot.io.Port.getIn:Unable to getIn: owned by other process");
		if (mode < 0)
			throw new Exception("bot.io.Port.getIn:Unable to getIn: wrong mode");
		int t = iodev.get(HWID, mode);
		return t;
	}
	
	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public int getOwner() {
		return owner;
	}
	
	/**
	 * Checks if is locked.
	 *
	 * @return true, if is locked
	 */
	public boolean isLocked() {
		return locked;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.service.Port#lock(int)
	 */
	/**
	 * Lock.
	 *
	 * @param bidder the bidder
	 * @throws lockException the lock exception
	 */
	public void lock(int bidder) throws lockException {
		if (locked && owner != bidder)
			throw new lockException(
			"bot.io.Port.lock:Unable to lock: already locked");
		owner = bidder;
		locked = true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.service.Port#setMode(int, int)
	 */
	/**
	 * Sets the mode.
	 *
	 * @param mode the mode
	 * @param bidder the bidder
	 * @throws Exception the exception
	 * @throws lockException the lock exception
	 */
	public void setMode(int mode, int bidder) throws Exception, lockException {
		if (locked && owner != bidder)
			throw new lockException(
			"bot.io.Port.setMode:Unable to setMode: owned by other process");
		this.mode = mode;
		iodev.mode(HWID, mode);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.service.Port#setOut(int, int)
	 */
	/**
	 * Sets the out.
	 *
	 * @param value the value
	 * @param bidder the bidder
	 * @throws Exception the exception
	 * @throws lockException the lock exception
	 */
	public void setOut(int value, int bidder) throws Exception, lockException {
		if (locked && owner != bidder)
			throw new lockException(
			"bot.io.Port.setOut:Unable to setOut: owned by other process");
		if (mode >0 )
			throw new Exception(
			"bot.io.Port.setOut:Unable to setOut: wrong mode");
		this.value = value;
		iodev.set(HWID, mode, value);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.service.Port#unlock(int)
	 */
	/**
	 * Unlock.
	 *
	 * @param bidder the bidder
	 * @throws lockException the lock exception
	 */
	public void unlock(int bidder) throws lockException {
		if (locked && owner != bidder)
			throw new lockException(
			"bot.io.Port.unlock:Unable to unlock: owned by other process");
		owner = -1;
		locked = false;
	}
}
