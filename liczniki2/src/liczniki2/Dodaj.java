package liczniki2;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class Dodaj {
	
    public Dodaj() {
    }
    
    public static void dodajRekord(int nrLicznika, String czasPomiaru, double stanLicznika, double sredniaMoc) {
    	
    	try {
    	      File myObj = new File("wynik.txt");
    	      if (myObj.createNewFile()) {
    	        System.out.println("File created: " + myObj.getName());
    	      } else {
    	        System.out.println("File already exists.");
    	      }
    	    } catch (IOException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }
    	
    	try {
    	      FileWriter myWriter = new FileWriter("wynik.txt");
    	      myWriter.write("Nr licznika: "+nrLicznika+"; Czas pomiaru: "+czasPomiaru+"; Stan licznika: "+stanLicznika+"; Srednia moc: "+sredniaMoc+";");
    	      myWriter.close();
    	      System.out.println("Successfully wrote to the file.");
    	    } catch (IOException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }
    }
    
}
