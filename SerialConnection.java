import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;

/**
 * SerialConnection encapsulates all of the information and logic necessary to interact with a device connected via USB to this computer. To use this class, it is necessary to
 * include the RXTXcomm.jar library in your classpath. These methods are contained within the gnu.io package.
 * This class was modified from the code example on the Arduino website (https://playground.arduino.cc/Interfacing/Java).
 * 
 * @since       1.8
 */
public class SerialConnection implements SerialPortEventListener 
{
	SerialPort serialPort;
	
	// List all possible port names here
	private static String PORT_NAMES[] = 
	{ 
			"/dev/tty.usbserial-A9007UX1", // OS X
            "/dev/ttyACM0",                // Raspberry Pi
			"/dev/ttyUSB0",                // Linux
			"COM3",                        // Windows
	};
	/**
	 * TODO interpret this comment
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	// Stream being written to the serial device
	private OutputStream output;
	// TODO interpret this comment
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	// Baud (bits/second) of serial connection.
	private static final int DATA_RATE = 9600;

	public void initialize(String os) throws SerialConnectionException
	{
        // Determine OS and set the proper port for Raspberry Pi.
        // http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		if (!(os.equals("Windows 10")))
		{
			System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");
		}

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// Find an instance of a serial port as set in PORT_NAMES
		while (portEnum.hasMoreElements()) 
		{
			CommPortIdentifier currPortId = (CommPortIdentifier)portEnum.nextElement();
			for (String portName : PORT_NAMES) 
			{
				if (currPortId.getName().equals(portName)) 
				{
					portId = currPortId;
					break;
				}
			}
		}

		// If no connection is found, as listed in PORT_NAMES, throw an exception.
		if (portId == null) 
		{
			throw new SerialConnectionException("Could not find serial port");
		}

		try 
		{
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort)portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			//serialPort.addEventListener(this);
			//serialPort.notifyOnDataAvailable(true);
		} 
		catch (Exception e) 
		{
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() 
	{
		if (serialPort != null) 
		{
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) 
	{
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) 
		{
			try 
			{
				String inputLine=input.readLine();
				System.out.println(inputLine);
			} 
			catch (Exception e) 
			{
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	public String getData() throws SerialConnectionException
	{
		String retval = "";
		try 
		{
			retval=input.readLine();
			//System.out.println(inputLine);
		} 
		catch (Exception e)
		{
			throw new SerialConnectionException("Unable to read from arduino");
		}

		return retval;
	}

	public void sendData(String data)
	{
		try
		{
			//this.output = serialPort.getOutputStream();
			System.out.println("Writing data");
			this.output.write(data.getBytes());
			System.out.println("Flushing data");
			this.output.flush();
			System.out.println("Flushing finished");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void beginMeasuring(String os) throws Exception 
	{
		//SerialConnection main = new SerialConnection();
		initialize(os);
		/*
		Thread t=new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
		};
		t.start();
		*/
		//System.out.println("Started");
	}
}