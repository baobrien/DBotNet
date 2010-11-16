package bot.io;

// TODO: Auto-generated Javadoc
/**
 * The Interface IODevice.
 */
public interface IODevice {

	/**
	 * Gets the.
	 *
	 * @param port the port
	 * @param mode the mode
	 * @return the int
	 * @throws Exception the exception
	 */
	public abstract int get(int port, int mode) throws Exception;

	/**
	 * Mode.
	 *
	 * @param port the port
	 * @param mode the mode
	 * @throws Exception the exception
	 */
	public abstract void mode(int port, int mode) throws Exception;

	/**
	 * Sets the.
	 *
	 * @param port the port
	 * @param mode the mode
	 * @param value the value
	 * @throws Exception the exception
	 */
	public abstract void set(int port, int mode, int value) throws Exception;

}