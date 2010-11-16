/*
 * 
 */
package bot.io.service;

import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The Class IOServiceRunner.
 */
public class IOServiceRunner {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {

		try {
			IOService IOS = new IOService(new Socket("localhost", 5000), 1);
			IOS.MainLoop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
