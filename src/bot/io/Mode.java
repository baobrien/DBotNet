package bot.io;

public final class Mode {
	public static final int TYPE_IOSERVICE_COMMAND = 2;
	public static final int TYPE_IOSERVICE_REPLY = 3;
	public static final int COMMAND_IOSERVICE_LOCK = 0;
	public static final int COMMAND_IOSERVICE_UNLOCK = 1;
	public static final int COMMAND_IOSERVICE_SELECT = 2;
	public static final int COMMAND_IOSERVICE_MODE = 3;
	public static final int COMMAND_IOSERVICE_OUT = 4;
	public static final int COMMAND_IOSERVICE_IN = 5;
	public static final int REPLY_IOSERVICE_SACK = 0;
	public static final int REPLY_IOSERVICE_ERR_LOCK = 1;
	public static final int REPLY_IOSERVICE_ERR_MODE = 2;
	public static final int REPLY_IOSERVICE_ERR_RESEND = 3;
	public static final int REPLY_IOSERVICE_ERR_SELECTION = 4;
	public static final int REPLY_IOSERVICE_CLEAR = 5;
	public static final int REPLY_IOSERVICE_DATA = 6;
	public final static int IN_ANALOG = 1;
	public final static int IN_DIGITAL = 2;
	public final static int OUT_SERVO = -1;
	public final static int OUT_DIGITAL = -2;
}
