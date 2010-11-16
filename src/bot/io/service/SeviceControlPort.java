package bot.io.service;

import bot.io.IODevice;
import bot.io.lockException;

public class SeviceControlPort {
	private IODevice iodev;

	private boolean locked;

	private int owner;

	private int ID;

	private int HWID;

	private int mode;

	private int value;

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
	public int getIn(int bidder) throws Exception, lockException {
		if (locked && owner != bidder)
			throw new lockException(
			"bot.io.Port.getIn:Unable to getIn: owned by other process");
		if (mode < 0)
			throw new Exception("bot.io.Port.getIn:Unable to getIn: wrong mode");
		int t = iodev.get(HWID, mode);
		return t;
	}
	public int getOwner() {
		return owner;
	}
	public boolean isLocked() {
		return locked;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.service.Port#lock(int)
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
	public void unlock(int bidder) throws lockException {
		if (locked && owner != bidder)
			throw new lockException(
			"bot.io.Port.unlock:Unable to unlock: owned by other process");
		owner = -1;
		locked = false;
	}
}
