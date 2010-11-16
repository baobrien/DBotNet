package bot.io.client;

import bot.io.*;

public class CPort {
	private int ID;

	IOClientHandler ch;

	public CPort(IOClientHandler clienthandler, int ID) {
		this.ID = ID;
		ch = clienthandler;
	}

	public int in() throws lockException, Exception {
		return ch.in(ID);
	}

	public void lock() throws lockException {
		ch.lock(ID);
	}

	public void mode(int value) throws lockException {
		ch.mode(ID, value);
	}

	public void out(int value) throws lockException, Exception {
		ch.out(ID, value);
	}
	public void unlock() throws lockException {
		ch.unlock(ID);
	}
}
