package demoapp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import demoapp.clients.ApacheClientParser;
import demoapp.model.Organization;
import demoapp.model.ReferenceType;

public class TestParseOrganization {


	public static void main(String args[]) throws Exception {
	
		
		File file = new File("src/main/java/demoapp/test/Organization.xml");
		FileInputStream fis = null;

		try {
				fis = new FileInputStream(file);
	
				System.out.println("Total file size to read (in bytes) : "
						+ fis.available());
	
	
				System.out.println("running test");
				
			   Organization result = ApacheClientParser.parseOrganization(fis);
			
			    System.out.println("result org  is  " + result);
			    
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
