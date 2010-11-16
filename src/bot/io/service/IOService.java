package bot.io.service;

import java.net.*;
import java.util.*;

import bot.io.IODevice;
import bot.io.Mode;
import bot.io.lockException;
import bot.net.*;

public class IOService extends NetIF {


	Vector<SeviceControlPort> ports;

	IODevice iodev;

	public IOService(Socket s, int Node) throws Exception {

		super(s, Node);
		SeviceControlPort t;
		iodev = new ConsoleIODevice();
		ports = new Vector<SeviceControlPort>();
		for (int i = 0; i < 32; i++) {
			t = new SeviceControlPort(i, i, Mode.IN_ANALOG, iodev);
			ports.add(i, t);
		}

	}

	public void handle_in(Packet p) {
		int selected = -1;
		int t = 0;
		try {
			selected = Integer.getInteger(p.data);
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_RESEND, p);
			return;
		}
		try {
			t = ports.elementAt(selected).getIn(p.nodesending);
			replyWithData(Mode.REPLY_IOSERVICE_DATA, p, new Integer(t)
			.toString());
		} catch (lockException e) {
			reply(Mode.REPLY_IOSERVICE_ERR_LOCK, p);
			e.getStackTrace();
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_MODE, p);
			e.printStackTrace();
		}
	}

	public void handle_lock(Packet p) {
		int lockport = 0;
		try {
			lockport = Integer.getInteger(p.data);
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_RESEND, p);
		}
		try {
			ports.elementAt(lockport).lock(p.nodesending);
			reply(Mode.REPLY_IOSERVICE_CLEAR, p);
		} catch (lockException e) {
			reply(Mode.REPLY_IOSERVICE_ERR_LOCK, p);
		}
	}

	public void handle_mode(Packet p) {
		int selectd = -1;
		int i=-1;
		try {
			selectd = new Integer(p.mdata[0]).intValue();
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_RESEND, p);
			return;
		}
		try {
			i = new Integer(p.mdata[1]).intValue();
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_RESEND, p);
			return;
		}
		try {
			ports.elementAt(i).setMode(selectd, p.nodesending);
			reply(Mode.REPLY_IOSERVICE_CLEAR, p);
		} catch (lockException e) {
			reply(Mode.REPLY_IOSERVICE_ERR_LOCK, p);
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_RESEND, p);
		}
	}

	public void handle_out(Packet p) {
		int selectd = -1;
		int i=-1;
		try {
			selectd = new Integer(p.mdata[0]).intValue();
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_RESEND, p);
			return;
		}
		try {
			i = new Integer(p.mdata[1]).intValue();
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_RESEND, p);
			return;
		}
		try {
			ports.elementAt(i).setOut(selectd, p.nodesending);
			reply(Mode.REPLY_IOSERVICE_CLEAR, p);
		} catch (lockException e) {
			reply(Mode.REPLY_IOSERVICE_ERR_LOCK, p);
		} catch (Exception e) {
			e.printStackTrace();
			reply(Mode.REPLY_IOSERVICE_ERR_MODE, p);
		}

	}


	public void handle_unlock(Packet p) {
		int lockport = 0;
		try {
			lockport = Integer.getInteger(p.data);
		} catch (Exception e) {
			reply(Mode.REPLY_IOSERVICE_ERR_RESEND, p);
		}
		try {
			ports.elementAt(lockport).unlock(p.nodesending);
			reply(Mode.REPLY_IOSERVICE_CLEAR, p);
		} catch (lockException e) {
			reply(Mode.REPLY_IOSERVICE_ERR_LOCK, p);
		}
	}

	public void OnRcv(Packet p) {
		if (p.type == Mode.TYPE_IOSERVICE_COMMAND)
			switch (p.cmd) {
			case Mode.COMMAND_IOSERVICE_LOCK:
				handle_lock(p);
				break;
			case Mode.COMMAND_IOSERVICE_UNLOCK:
				handle_unlock(p);
				break;
			case Mode.COMMAND_IOSERVICE_OUT:
				handle_out(p);
				break;
			case Mode.COMMAND_IOSERVICE_MODE:
				handle_mode(p);
				break;
			case Mode.COMMAND_IOSERVICE_IN:
				handle_in(p);
				break;
			default:
				super.Catalog(p);
			}
		else
			super.Catalog(p);
	}
	private void reply(int reply_type, Packet p) {
		try {
			super.Send(new Packet(0, p.nodesending, Mode.TYPE_IOSERVICE_REPLY,
					reply_type, ""));
		} catch (Exception e1) {
		}
	}
	private void replyWithData(int reply_type, Packet p, String Data) {
		try {
			super.Send(new Packet(0, p.nodesending, Mode.TYPE_IOSERVICE_REPLY,
					reply_type, Data));
		} catch (Exception e1) {
		}
	}
}
