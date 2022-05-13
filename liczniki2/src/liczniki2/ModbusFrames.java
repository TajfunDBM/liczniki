package liczniki2;

public class ModbusFrames {
	
	public byte[] modbusOutFrame = new byte[16];
	public int frameLength;
	int slaveAdd;
	double wartosc;
	
	public ModbusFrames() {
    }
	
    public void setFrame(int slaveAdd, int functionNr, int startAdd, int dataLength) {
        this.slaveAdd = slaveAdd;
        int[] modbusCRC16 = new int[2];
        this.modbusOutFrame[0] = (byte)slaveAdd;
        this.modbusOutFrame[1] = (byte)functionNr;
        this.modbusOutFrame[2] = (byte)(startAdd >> 8);
        this.modbusOutFrame[3] = (byte)(startAdd << 8 >> 8);
        this.modbusOutFrame[4] = (byte)(dataLength >> 8);
        this.modbusOutFrame[5] = (byte)(dataLength << 8 >> 8);
        modbusCRC16 = CRC16.getCRC(this.modbusOutFrame, 6);
        this.modbusOutFrame[6] = (byte)(modbusCRC16[0] << 8 >> 8);
        this.modbusOutFrame[7] = (byte)(modbusCRC16[1] << 8 >> 8);
        this.frameLength = 8;
    }

    public double getWartosc() {
        return this.wartosc;
    }
}
