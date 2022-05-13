package liczniki2;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadModbus implements Runnable {

	ModbusFrames[] MasterFrame;
	ModbusRTU modbusRTU = new ModbusRTU();
	int nrLicznika;
	String czasPomiaru = "";
	boolean pomocnik = true;
	static double waty = 0.0;
    double stanLicznika = 0.0;
    double sredniaMoc = 0.0;
    int pomocniczyOdstepCzasowyZapytan;
    int functionNr = 3;
    int startDataAddress = 0;
    int dataLength = 3;
    public String commPortName = "COM8";
    // moje nowe rzeczy
 	static int slaveAddress = 104;

 	// koniec moich nowych rzeczy
    
    
	
	public ThreadModbus() {
	}
	
	
	
	
	this.dataLength = 3;
	
	int licznik = 0;

    public void all() {

        this.MasterFrame[licznik].setFrame(slaveAddress, this.functionNr, this.startDataAddress, this.dataLength);

        this.modbusRTU.setCommPort(this.commPortName, 9600, 8, 1, 0);

        this.modbusRTU.writeToPort(this.MasterFrame[licznik].modbusOutFrame, this.MasterFrame[licznik].frameLength);

        this.MasterFrame[licznik].checkFrame(this.functionNr, this.dataLength, this.modbusRTU.offset, this.modbusRTU.readBuffer);
        this.odczytanoDane = true;

        Thread.sleep(10L);
        Thread.sleep(10L);

        System.out.print("Odpowiedz z urzadzenia nr " + Integer.toString(slaveAddress));
        this.MasterFrame[licznik].checkFrame(this.functionNr, this.dataLength, this.modbusRTU.offset, this.modbusRTU.readBuffer);


        if (this.odczytanoDane) {
            if (this.pomocnik) {
                if (slaveAddress <= 100) {
                    this.MasterFrame[licznik].setWartoscPoprzednia(this.MasterFrame[licznik].getWartosc() / 100.0);
                } else if (slaveAddress <= 200) {
                    this.MasterFrame[licznik].setWartoscPoprzednia(this.MasterFrame[licznik].getWartosc() / 10.0);
                } else {
                    this.MasterFrame[licznik].setWartoscPoprzednia(this.MasterFrame[licznik].getWartosc() / (double) this.MasterFrame[licznik].getWspolczynnikX());
                }
            }

            if (slaveAddress <= 100) {
                this.MasterFrame[licznik].setWartoscObecna(this.MasterFrame[licznik].getWartosc() / 100.0);
            } else if (slaveAddress > 100 && slaveAddress <= 200) {
                this.MasterFrame[licznik].setWartoscObecna(this.MasterFrame[licznik].getWartosc() / 10.0);
            } else {
                this.MasterFrame[licznik].setWartoscObecna(this.MasterFrame[licznik].getWartosc() / (double) this.MasterFrame[licznik].getWspolczynnikX());
            }

            System.out.println("Pomiar = " + this.MasterFrame[licznik].getWartoscObecna() + "kWh.");
            waty = (this.MasterFrame[licznik].getWartoscObecna() - this.MasterFrame[licznik].getWartoscPoprzednia()) * 1000.0 * 3600.0 / (double) (this.pomocniczyOdstepCzasowyZapytan * 60);
            System.out.println("Watt = " + waty + "\n");
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            this.nrLicznika = slaveAddress;
            this.czasPomiaru = dateFormat.format(date);
            this.stanLicznika = this.MasterFrame[licznik].getWartoscObecna();
            this.sredniaMoc = waty;

            try {
                Dodaj.dodajRekord(this.nrLicznika, this.czasPomiaru, this.stanLicznika, this.sredniaMoc);
            } catch {
                System.out.println("Blad tworzenia folderu");
            }

            this.MasterFrame[licznik].setWartoscPoprzednia(this.MasterFrame[licznik].getWartoscObecna());
        }


    }
}