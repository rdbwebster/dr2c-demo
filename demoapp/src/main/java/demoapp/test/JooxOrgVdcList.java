package demoapp.test;

import static org.joox.JOOX.$;

import java.io.File;
import java.io.IOException;

import org.joox.FastFilter;
import org.joox.Match;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import demoapp.model.Organization;

public class JooxOrgVdcList {

	public static void main(String ars[]) throws IOException, SAXException {
		
		
		
		
	//	Document document = $("src/main/java/demoapp/test/orgVdcList.xml").document();
	//	System.out.println(document.getTextContent());
		
	
		
	//	Match x7 = $(document).xpath("//Org/Link[@type = application/vnd.vmware.vcloud.vdc+xml ");
		
	//	x7.forEach(action);
		
		/*
		 * $(new File("./pom.xml"))
    .find("groupId")
    .filter(ctx -> $(ctx).siblings("version")
                         .matchText(".*-SNAPSHOT")
                         .isEmpty())
    .each(ctx -> {
        System.out.println(
        $(ctx).text() + ":" +
        $(ctx).siblings("artifactId").text() + ":" +
        $(ctx).siblings("version").text());
    });
    */
		 
		
		
	//	$(new File("src/main/java/demoapp/test/orgVdcList.xml"))
	//    .find("Org").text();
	 //   .filter(ctx -> $(ctx).siblings("Link").matchText("application/vnd.vmware.vcloud.vdc+xml").isNotEmpty())
	 //   .each(ctx -> {
	 //       System.out.println(
	 //       $(ctx).text()); //  + ":" +
	//        $(ctx).siblings("artifactId").text() + ":" +
	 //       $(ctx).siblings("version").text());
//	    });
		
	//	 Match $orgs = $(bis);
		
		System.out.println("running test");
		/*
		 Match $org = $(new File("src/main/java/demoapp/test/orgVdcList.xml"));
	     
		  $org.find("Link").forEach(org -> {
	            String name = $(org).attr("name");           // attribute
	            String type = $(org).attr("type");           // attribute
	            String href = $(org).attr("href");           // attribute
	         //   String type = $(org).find("type").text();    // find for child elements
	         //   String href = $(org).find("href").text();
	           

	            System.out.format("%s : - %s - %s %n",  name, type, href);
	        });
		  */
		
		// Match match = $(document).find("element").filter(attr("attrName", "value"));
		
		//for (Element element : match.each()) {
		//	  System.out.println($(element));
		//	}
		
		// http://stackoverflow.com/questions/6673756/finding-elements-by-attributes-in-a-dom-document
		
		
		// Match x5 = $(document).find("book").filter(ids(1, 2));
			
		     Match $doc = $(new File("src/main/java/demoapp/test/orgVdcList.xml"));
		     
	//	     $doc.xpath("//Link[@type = application/vnd.vmware.vcloud.vdc+xml]").forEach(link-> {
		     
		     $doc.find("Link").forEach(link -> {
		    	   String name = $(link).attr("name");           // attribute
		            String type = $(link).attr("type");           // attribute
		            String href = $(link).attr("href");           // attribute
		         //   String type = $(org).find("type").text();    // find for child elements
		           
                 if ($(link).attr("type").equals("application/vnd.vmware.vcloud.vdc+xml"))
                  	System.out.format("%s : - %s - %s %n",  name, type, href);
		        });
		     
			/*
			 $(new File("src/main/java/demoapp/test/orgVdcList.xml")) 
			    .find("Link").matchAttr("name", "/").forEach(ctx -> {
			 .find("Link").even().forEach(ctx -> {
			    .filter(ctx -> $(ctx).attr("type", "/xml$/").isNotEmpty())
			 
					.each(ctx -> {
	
		            String name = $(ctx).attr("name");           // attribute
		            String type = $(ctx).attr("type");           // attribute
		            String href = $(ctx).attr("href");           // attribute
		            String type = $(org).find("type").text();    // find for child elements
		           

		            System.out.format("%s : - %s - %s %n",  name, type, href);
		        });
				 */
	
   }
}

