package demoapp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import demoapp.clients.ApacheClientParser;
import demoapp.model.VMInfo;

public class TestParseReplicationGroupRefs {

public static void main(String args[]) throws Exception {
		
		
		
		File file = new File("src/main/java/demoapp/test/RepReferences.xml");
		FileInputStream fis = null;

		try {
				fis = new FileInputStream(file);
	
				System.out.println("Total file size to read (in bytes) : "
						+ fis.available());
	
			
				System.out.println("running test");
				
				 ArrayList<String> result = ApacheClientParser.parseReplicationGroupRefs(fis);
			
				 
				 System.out.println("result size " + result.size());
				 for (String href : result) {
					 System.out.println("hRef " + href);
				 }

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
