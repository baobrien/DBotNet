package bot.io.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import bot.io.Mode;
import bot.net.*;

public class IOSystemTest {

	/**
	 * @param args
	 * @throws Exception
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException,
	IOException, Exception {
		NetIF ni = new NetIF(new Socket("localhost", 5000), 20);
		ni.StartSpooler();
		IOClientHandler ich = new IOClientHandler(ni, 1);
		ich.lock(5);
		ich.mode(5, Mode.OUT_SERVO);
		ich.out(5, 10);
		ich.mode(5, Mode.IN_ANALOG);
		System.out.println(ich.in(5));
	}

}