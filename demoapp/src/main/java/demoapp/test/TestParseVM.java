package demoapp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import demoapp.clients.ApacheClientParser;
import demoapp.model.Organization;
import demoapp.model.VMInfo;

public class TestParseVM {

	public static void main(String args[]) throws Exception {
		
		
		
		File file = new File("src/main/java/demoapp/test/Vm.xml");
		FileInputStream fis = null;

		try {
				fis = new FileInputStream(file);
	
				System.out.println("Total file size to read (in bytes) : "
						+ fis.available());
	
				int content;
			
				System.out.println("running test");
				
				VMInfo info = new VMInfo();
				 VMInfo result = ApacheClientParser.parseVMInfo(fis);
			

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

