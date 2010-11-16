package bot.io;

// TODO: Auto-generated Javadoc
/**
 * The Class Mode.
 */
public final class Mode {
	
	/** The Constant TYPE_IOSERVICE_COMMAND. */
	public static final int TYPE_IOSERVICE_COMMAND = 2;
	
	/** The Constant TYPE_IOSERVICE_REPLY. */
	public static final int TYPE_IOSERVICE_REPLY = 3;
	
	/** The Constant COMMAND_IOSERVICE_LOCK. */
	public static final int COMMAND_IOSERVICE_LOCK = 0;
	
	/** The Constant COMMAND_IOSERVICE_UNLOCK. */
	public static final int COMMAND_IOSERVICE_UNLOCK = 1;
	
	/** The Constant COMMAND_IOSERVICE_SELECT. */
	public static final int COMMAND_IOSERVICE_SELECT = 2;
	
	/** The Constant COMMAND_IOSERVICE_MODE. */
	public static final int COMMAND_IOSERVICE_MODE = 3;
	
	/** The Constant COMMAND_IOSERVICE_OUT. */
	public static final int COMMAND_IOSERVICE_OUT = 4;
	
	/** The Constant COMMAND_IOSERVICE_IN. */
	public static final int COMMAND_IOSERVICE_IN = 5;
	
	/** The Constant REPLY_IOSERVICE_SACK. */
	public static final int REPLY_IOSERVICE_SACK = 0;
	
	/** The Constant REPLY_IOSERVICE_ERR_LOCK. */
	public static final int REPLY_IOSERVICE_ERR_LOCK = 1;
	
	/** The Constant REPLY_IOSERVICE_ERR_MODE. */
	public static final int REPLY_IOSERVICE_ERR_MODE = 2;
	
	/** The Constant REPLY_IOSERVICE_ERR_RESEND. */
	public static final int REPLY_IOSERVICE_ERR_RESEND = 3;
	
	/** The Constant REPLY_IOSERVICE_ERR_SELECTION. */
	public static final int REPLY_IOSERVICE_ERR_SELECTION = 4;
	
	/** The Constant REPLY_IOSERVICE_CLEAR. */
	public static final int REPLY_IOSERVICE_CLEAR = 5;
	
	/** The Constant REPLY_IOSERVICE_DATA. */
	public static final int REPLY_IOSERVICE_DATA = 6;
	
	/** The Constant IN_ANALOG. */
	public final static int IN_ANALOG = 1;
	
	/** The Constant IN_DIGITAL. */
	public final static int IN_DIGITAL = 2;
	
	/** The Constant OUT_SERVO. */
	public final static int OUT_SERVO = -1;
	
	/** The Constant OUT_DIGITAL. */
	public final static int OUT_DIGITAL = -2;
}
