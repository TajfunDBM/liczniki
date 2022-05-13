package liczniki2;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ModbusRTU implements SerialPortEventListener {
	
	SerialPort RTUMaster;
	
	
	
    public void setCommPort(String CommPortName, int baudRate, int dataBits, int stopBits, int parity) {
        this.openPort(CommPortName);

            this.RTUMaster.setSerialPortParams(baudRate, dataBits, stopBits, parity);

            this.RTUMaster.notifyOnOutputEmpty(true);


    }
    
    
}