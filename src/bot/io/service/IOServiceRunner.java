package bot.io.service;

import java.net.Socket;

public class IOServiceRunner {
	public static void main(String[] args) throws Exception {

		try {
			IOService IOS = new IOService(new Socket("localhost", 5000), 1);
			IOS.MainLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
