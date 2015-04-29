package fiap.sd.udp.simplechatudp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * Helper class to Console I/O
 * @author fabianmartins
 *
 */
public class Console {
	
	// Reader da Console
	private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	// Leitura da identificacao do sistema operacional
	private final OperatingSystemMXBean OSmxbean = ManagementFactory.getOperatingSystemMXBean();
	private String osName;
	
	private static Console instance;
	public static Console getConsole() {
		if (instance == null) instance = new Console();
		return instance;
	}
	
	private Console() {
		acquireOSName();
	}
	
	public String getConsoleDetails() {
		return "CONSOLE UTILITY for "+getOSFullQualification();
	}
	
	public void printConsoleDetails() {
		this.println(getConsoleDetails());
	}
	
	private void acquireOSName() {
		String osName = OSmxbean.getName();
		int indexFirstSpace = OSmxbean.getName().indexOf(" ");
		if (indexFirstSpace==-1) this.osName = osName;
		else this.osName = osName.substring(0,indexFirstSpace);
	}	
	
	public String getOSName() {
		return osName;
	}
	
	public String getOSFullQualification() {
		return   OSmxbean.getName()+" "+
	             OSmxbean.getVersion()+" "+
				 OSmxbean.getArch()+" "+
				 OSmxbean.getAvailableProcessors()+" core(s)";
	}
	

	
	/**
	 * Prints an object's String representation WITHOUT CRLF
	 * @param msg
	 */
	public void print(Object msg) {
		System.out.print(msg);
	}
	
	/**
	 * Prints an object's String representation with CRLF
	 * @param value
	 */
	public void println(Object value) {
		System.out.println(value);
	}
	
	/**
	 * Reads the console input without previous message 
	 * @return
	 */
	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Reads the console input following the showed message
	 * 
	 * @param message
	 * @return
	 */
	public String readLine(Object message) {
		instance.print(message);
		return instance.readLine();
	}
	
	
	/**
	 * Clears the screen
	 */
	public void clear() {
		this.print('\u000C');
	}
	
}
