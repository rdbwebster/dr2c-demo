

package demoapp.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import demoapp.clients.ApacheClientParser;
import demoapp.model.Organization;
import demoapp.model.ReferenceType;
import demoapp.model.VMInfo;
import demoapp.model.Vdc;

public class TestParseRecoveryDetails {

    public static void main(String args[]) throws Exception {
		
			
			File file = new File("src/main/java/demoapp/test/RecoveryDetails.xml");
			FileInputStream fis = null;

			try {
					fis = new FileInputStream(file);
		
					System.out.println("Total file size to read (in bytes) : "
							+ fis.available());
		
		
					System.out.println("running test");
					
					Vdc vdc = new Vdc();
				    ApacheClientParser.parseVdcRecoveryDetails(fis, vdc);
				
				    System.out.println("Vdc  " + vdc);
				    
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
