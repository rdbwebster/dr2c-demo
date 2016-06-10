package demoapp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import demoapp.clients.ApacheClientParser;
import demoapp.model.Vdc;

public class TestParseVdc {



	public static void main(String args[]) throws Exception {
	
		
		File file = new File("src/main/java/demoapp/test/Vdc.xml");
		FileInputStream fis = null;

		try {
				fis = new FileInputStream(file);
	
				System.out.println("Total file size to read (in bytes) : "
						+ fis.available());
	
	
				System.out.println("running test");
				
			   Vdc result = ApacheClientParser.parseVdc(fis);
			
			    System.out.println("result vdc is  " + result);
			
			    
			    fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
