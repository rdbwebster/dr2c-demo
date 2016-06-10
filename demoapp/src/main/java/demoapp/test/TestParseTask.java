package demoapp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import demoapp.clients.ApacheClientParser;
import demoapp.model.Organization;
import demoapp.model.Task;
import demoapp.model.VMInfo;
import demoapp.model.Vapp;


public class TestParseTask {


	public static void main(String args[]) throws Exception {
		
		
		
		File file = new File("src/main/java/demoapp/test/Task.xml");
		FileInputStream fis = null;

		try {
				fis = new FileInputStream(file);
	
				System.out.println("Total file size to read (in bytes) : "
						+ fis.available());
	
				int content;
			
				System.out.println("running test");
				
				
				Task result = ApacheClientParser.parseTask(fis);
			
			   

				 fis.close();
				 
				 System.out.println("Parsed Task is " + result);
				  
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

