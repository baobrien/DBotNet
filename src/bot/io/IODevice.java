package bot.io;

public interface IODevice {

	public abstract int get(int port, int mode) throws Exception;

	public abstract void mode(int port, int mode) throws Exception;

	public abstract void set(int port, int mode, int value) throws Exception;

}