package pua.net.pruebas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PruebaCsv {
	public void registroCSV(File archivo) {
		try {
			FileReader f=new FileReader(archivo);
			BufferedReader br=new BufferedReader(f);
			boolean s=true;
			int count=0;
			
			while(s) {
				String tx=br.readLine();
				if(tx!=null) {
					if(count>0) {
						String[] valores=tx.split(",");
						System.out.println(tx);
					} else {
						count++;
					}
					
				} else {
					s=false;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
