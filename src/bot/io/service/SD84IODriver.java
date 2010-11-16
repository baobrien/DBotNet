/*
 * 
 */
package bot.io.service;

import gnu.io.*;
import java.io.*;
// TODO: Auto-generated Javadoc
/*
 * Driver to allow for usage of the Devantech SD84 IO Board. This class handles the low-level stuff. Not Complete.
 */
/**
 * The Class SD84IODriver.
 */
public class SD84IODriver {
	
	/** The Constant SET_SERVO_SPEED. */
	public static final byte SET_SERVO_SPEED = (byte) 0x03;
	
	/** The Constant SET_MODE. */
	public static final byte SET_MODE = (byte) 0x04;
	
	/** The Constant GET_MODE. */
	public static final byte GET_MODE = (byte) 0x05;
	
	/** The Constant GET_ANALOG_COUNT. */
	public static final byte GET_ANALOG_COUNT = (byte) 0x07;
	
	/** The Constant SET_ANALOG_COUNT. */
	public static final byte SET_ANALOG_COUNT = (byte) 0x06;
	
	/** The Constant GET_DIGITAL. */
	public static final byte GET_DIGITAL = (byte) 0x08;
	
	/** The Constant GET_ANALOG. */
	public static final byte GET_ANALOG = (byte) 0x09;
	
	/** The Constant MODE_OUT_0. */
	public static final byte MODE_OUT_0 = (byte) 0x15;
	
	/** The Constant MODE_OUT_1. */
	public static final byte MODE_OUT_1 = (byte) 0x16;
	
	/** The Constant MODE_DIGITAL_INPUT. */
	public static final byte MODE_DIGITAL_INPUT = (byte) 0x17;
	
	/** The Constant MODE_ANALOG_INPUT. */
	public static final byte MODE_ANALOG_INPUT = (byte) 0x18;
	
	/** The Constant MODE_SERVO_OUT. */
	public static final byte MODE_SERVO_OUT = (byte) 0x19;
	
	/** The Constant RETURN_OK. */
	public static final byte RETURN_OK = (byte) 0x00;
	
	/** The Constant RETURN_WARNING. */
	public static final byte RETURN_WARNING = (byte) 0x01;
	
	/** The Constant RETURN_RANGE. */
	public static final byte RETURN_RANGE = (byte) 0x02;
	
	/** The Constant RETURN_MODE. */
	public static final byte RETURN_MODE = (byte) 0x03;
	
	/** The Constant sync_0. */
	public static final byte sync_0 = (byte) 0xAA;
	
	/** The Constant sync_1. */
	public static final byte sync_1 = (byte) 0xA0;
	
	/** The Constant sync_2. */
	public static final byte sync_2 = (byte) 0x55;
	
	/** The Constant SET_SERVO_POSITION. */
	public static final byte SET_SERVO_POSITION = (byte) 0x01;
	
	/** The Constant GET_SERVO_POSITION. */
	public static final byte GET_SERVO_POSITION = (byte) 0x02;
	// public static final int[] PORT_MAP;

	/** The out. */
	private OutputStream out;
	
	/** The in. */
	private InputStream in;
	
	/** The serial. */
	private SerialPort serial;
	
	/** The Constant ANALOG_MODE_MAP. */
	public static final int[] ANALOG_MODE_MAP = { 21, 22, 20, 18, 28, 27, 29,
		26, 30, 11, 10, 9, 7, 38, 37, 39, 36, 40, 84, 83, 82, 80, 48, 47,
		49, 46, 50, 73, 72, 71, 69, 58, 57, 59, 56, 60 };
	
	/**
	 * Int prepare lht.
	 *
	 * @param conv the conv
	 * @return the byte[]
	 */
	private static byte[] intPrepareLHT(int conv) {//convert int into 2-bytes that can be sent down wire.
		String ta = Integer.toHexString(conv);
		if (ta.length() < 4)
			ta = "0" + ta;
		if (ta.length() < 3)
			ta = "00" + ta;
		if (ta.length() < 2)
			ta = "000" + ta;
		if (ta.length() < 1)
			ta = "0000" + ta;
		char[] t = ta.toCharArray();
		char[] lh = { t[2], t[3] };
		char[] hh = { t[0], t[1] };
		byte l = (byte) Integer.parseInt(new String(lh), 16);
		byte h = (byte) Integer.parseInt(new String(hh), 16);
		byte[] cnv = { l, h };
		return cnv;
	}
	
	/**
	 * Instantiates a new s d84 io driver.
	 *
	 * @param portname the portname
	 * @throws Exception the exception
	 */
	public SD84IODriver(String portname) throws Exception {
		CommPortIdentifier portID = CommPortIdentifier
		.getPortIdentifier(portname);
		serial = (SerialPort) portID.open("IOService", 100);
		serial.setSerialPortParams(115200, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
		out = serial.getOutputStream();
		in = serial.getInputStream();
	}
	
	/**
	 * Handle response.
	 *
	 * @param response the response
	 * @param port the port
	 * @throws Exception the exception
	 */
	private void handleResponse(byte[] response, int port) throws Exception {
		byte rc = response[0];
		switch (rc) {
		case RETURN_OK:
			break;
		case RETURN_WARNING:
			throw new Exception("Warning from SD84 Board Port " + port);
		case RETURN_RANGE:
			throw new Exception("Range Error from SD84 Board Port " + port);
		case RETURN_MODE:
			throw new Exception("Mismatched Mode Error from SD84 Board Port "
					+ port);
		default:
			break;
		}
	}
	
	/**
	 * Sets the digital.
	 *
	 * @param port the port
	 * @param value the value
	 * @throws Exception the exception
	 */
	public void setDigital(int port, boolean value) throws Exception {
		if (value)
			setMode(port, MODE_OUT_1);
		else
			setMode(port, MODE_OUT_0);
	}
	
	/**
	 * Sets the aD count.
	 *
	 * @param count the new aD count
	 * @throws Exception the exception
	 */
	public void setADCount(int count) throws Exception {
		byte[] command = { sync_0,sync_1,sync_2,SET_ANALOG_COUNT,0,1,(byte)count};
		setCR(command,0);
		
	}
	
	/**
	 * Sets the mode.
	 *
	 * @param port the port
	 * @param mode the mode
	 * @throws Exception the exception
	 */
	public void setMode(int port, int mode) throws Exception {

		byte[] command = { sync_0, sync_1, sync_2, SET_MODE, (byte) port, 1,
				(byte) mode };
		setCR(command,port);
	}
	
	/**
	 * Sets the servo position.
	 *
	 * @param port the port
	 * @param value the value
	 * @throws Exception the exception
	 */
	public void setServoPosition(int port, int value) throws Exception {
		byte[] d = intPrepareLHT(value);
		byte[] command = { sync_0, sync_1, sync_2, SET_SERVO_POSITION,
				(byte) port, 2, d[0], d[1] };
		setCR(command,port);
	}
	
	/**
	 * Sets the servo speed.
	 *
	 * @param port the port
	 * @param value the value
	 * @throws Exception the exception
	 */
	public void setServoSpeed(int port, int value) throws Exception {
		byte d = new Integer(value).byteValue();
		byte p = new Integer(port).byteValue();
		byte[] command = { sync_0, sync_1, sync_2, SET_SERVO_SPEED, p, 1, d };
		setCR(command,port);
	}
	
	/**
	 * Sets the cr.
	 *
	 * @param command the command
	 * @param port the port
	 * @throws Exception the exception
	 */
	public void setCR(byte[] command,int port) throws Exception{
		byte[] response = { 0 };
		out.write(command);
		in.read(response, 0, 1);
		handleResponse(response, port);
	}

}
