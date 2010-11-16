/*
 * 
 */
package bot.io.service;

import java.io.*;

import bot.io.IODevice;
import bot.io.Mode;

// TODO: Auto-generated Javadoc
/**
 * The Class ConsoleIODevice.
 */
public class ConsoleIODevice implements IODevice {
	
	/** The kb. */
	BufferedReader kb;

	/**
	 * Instantiates a new console io device.
	 */
	public ConsoleIODevice() {
		kb = new BufferedReader(new InputStreamReader(System.in));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.IODevice#get(int)
	 */
	public int get(int port, int mode) {
		boolean clear = false;
		int t = 0;
		while (!clear)
			try {
				System.out.print("Value for port # " + port + ":");
				String temp = kb.readLine();
				t = new Integer(temp).intValue();
				clear = true;
			} catch (Exception e) {
				System.out.println("Must input a number");
			}
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.IODevice#mode(int, int)
	 */
	public void mode(int port, int mode) {
		switch (mode) {
		case Mode.IN_ANALOG:
			System.out.println("Set port " + port + " to Analog Input");
			break;
		case Mode.IN_DIGITAL:
			System.out.println("Set port " + port + " to Digital Input");
			break;
		case Mode.OUT_SERVO:
			System.out.println("Set port " + port + " to Servo Output");
			break;
		case Mode.OUT_DIGITAL:
			System.out.println("Set port " + port + " to Digital Output");
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bot.io.IODevice#set(int, int)
	 */
	public void set(int port, int mode, int value) {
		System.out.println("Port # " + port + " Set to value: " + value);
	}
}
