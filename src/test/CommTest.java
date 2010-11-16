package test;

import java.util.Enumeration;

import gnu.io.*;

public class CommTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Enumeration<CommPortIdentifier> commports = CommPortIdentifier
		.getPortIdentifiers();
		String targetPortName = "/dev/ttyS1";
		CommPortIdentifier t;
		CommPortIdentifier targetPortID = null;
		while (commports.hasMoreElements()) {
			t = commports.nextElement();
			if (t.getName().matches(targetPortName))
				targetPortID = t;
		}
		if (targetPortID == null)
			throw new Exception("Cannot Find Port Named " + targetPortName);
		SerialPort commport = (SerialPort) targetPortID.open("CommTest", 100);
		commport.setSerialPortParams(115200, SerialPort.DATABITS_8,
				SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
		commport.close();
		System.out.println((char) 0x21);
	}

}
