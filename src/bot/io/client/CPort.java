package bot.io.client;

import bot.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class CPort.
 */
public class CPort {
	
	/** The ID. */
	private int ID;

	/** The ch. */
	IOClientHandler ch;

	/**
	 * Instantiates a new c port.
	 *
	 * @param clienthandler the clienthandler
	 * @param ID the iD
	 */
	public CPort(IOClientHandler clienthandler, int ID) {
		this.ID = ID;
		ch = clienthandler;
	}

	/**
	 * In.
	 *
	 * @return the int
	 * @throws lockException the lock exception
	 * @throws Exception the exception
	 */
	public int in() throws lockException, Exception {
		return ch.in(ID);
	}

	/**
	 * Lock.
	 *
	 * @throws lockException the lock exception
	 */
	public void lock() throws lockException {
		ch.lock(ID);
	}

	/**
	 * Mode.
	 *
	 * @param value the value
	 * @throws lockException the lock exception
	 */
	public void mode(int value) throws lockException {
		ch.mode(ID, value);
	}

	/**
	 * Out.
	 *
	 * @param value the value
	 * @throws lockException the lock exception
	 * @throws Exception the exception
	 */
	public void out(int value) throws lockException, Exception {
		ch.out(ID, value);
	}
	
	/**
	 * Unlock.
	 *
	 * @throws lockException the lock exception
	 */
	public void unlock() throws lockException {
		ch.unlock(ID);
	}
}
