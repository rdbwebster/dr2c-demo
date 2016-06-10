package demoapp.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import spark.ModelAndView;
import spark.Request;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.joox.Match;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.vmware.vcloud.sdk.VcloudClient;
import com.vmware.vcloud.sdk.constants.Version;
import com.vmware.vcloud.sdk.exception.UnauthorizedAccessException;

import demoapp.FakeSSLSocketFactory;

import static org.joox.JOOX.*;

/* This is a experimentation class for developing the apache http client 
 * 
 */

public class ApacheHttpRest {
	
public static String sessionId;
	
public static ModelAndView apacheLogin(Request request) {
// create HTTP Client
	//		HttpClient httpClient = HttpClientBuilder.create().build();
		//    CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
	    	
	
    HashMap model = new HashMap();
    Map map = request.params();
    System.out.println("see " + request.attributes().size());
    String username = request.attribute("username");
    String password = request.attribute("password");
    System.out.println("Username " + username + " Password " + password);
    ModelAndView mv = new ModelAndView(model, "templates/helo.vtl");
    model.put("username", username);
    model.put("password", password);
    model.put("template", "templates/greeting_card.vtl");
 
	try {
		    HttpClient httpClient = createHttpClient_AcceptsUntrustedCerts(true);
       
		    
            HttpPost httpPost = new HttpPost("https://10.134.3.58/api/sessions");
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("username", "administrator@System"));
            nvps.add(new BasicNameValuePair("password", "vmware123!"));
        
            
			// Add additional header to getRequest which accepts application/xml data
            httpPost.addHeader("accept", "application/*+xml;version=9.0");
 
			// Execute your request and catch response
		    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response2 = httpClient.execute(httpPost);
            
		
			// Check for HTTP response code: 200 = success
			if (response2.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response2.getStatusLine().getStatusCode());
		
			}
			
			String sessionId  = response2.getFirstHeader("x-vcloud-authorization").getValue();
			
 
			// Get-Capture Complete application/xml body response
			BufferedReader br = new BufferedReader(new InputStreamReader((response2.getEntity().getContent())));
			String output;
			System.out.println("============Output:============");
            System.out.println("x-vcloud-authorization: " + String.valueOf(sessionId));
			// Simply iterate through XML response and show on console.
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		
 
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
		   return mv;
	}
     
    /**
     * @author Crunchify.com
     * 
     */
     

public static String apacheOrgsCall(String sessionId) {
     
    		String value = "Success";
    		try {
    			
    			
    		    HttpClient httpClient = createHttpClient_AcceptsUntrustedCerts(true);
    		       
    
    			// Create new getRequest with below mentioned URL
    			HttpGet getRequest = new HttpGet("https://10.134.3.58/api/org/");
     
    			// Add additional header to getRequest which accepts application/xml data
    			getRequest.addHeader("accept", "application/*+xml;version=9.0");
     
    			// Add vcd cloud session id
    			getRequest.addHeader("x-vcloud-authorization", sessionId);
    		     
    			
    			// Execute your request and catch response
    			HttpResponse response = httpClient.execute(getRequest);
     
    			// Check for HTTP response code: 200 = success
    			if (response.getStatusLine().getStatusCode() != 200) {
    				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
    			}
     
    		
    			
    			// load into BufferedInputSteam so we can reset and read again
    			BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
    			bis.mark(0);
    			
    		
    			// Get-Capture Complete application/xml body response
    			BufferedReader br = new BufferedReader(new InputStreamReader(bis));
    			String output;
    			System.out.println("============Output:============");
     
    			// Simply iterate through XML response and show on console.
    			while ((output = br.readLine()) != null) {
    				System.out.println(output);
    			}
    			
    			// reset the stream to the beginning for JOOX to process
    			bis.reset();
    			
    			// Parse response with JOOX
    			
    			 
    			System.out.println("\nMade Apache http call, parsing result");
    				
    			 
    	//		 Match $orgs = $(response.getEntity().getContent());
    			 Match $orgs = $(bis);
    		     
    		     $orgs.find("Org").forEach(org -> {
    		    	  String name = $(org).attr("name");
    		         String href = $(org).attr("href");
    		     //    String name = $(org).find("name").text();

    		         System.out.println("See Org Data: " );;
    		         System.out.format("%s  %s : %n", name, href);
    		     });
    		     
    			 
    			
    	
    	/*		
    			 
    			// Get-Capture Complete application/xml body response
    			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
    			String output;
    			System.out.println("============Output:============");
     
    			// Simply iterate through XML response and show on console.
    			while ((output = br.readLine()) != null) {
    				System.out.println(output);
    			}
    		*/	
    			
     
    		} catch (ClientProtocolException e) {
    			e.printStackTrace();
    			value = "Error";
     
    		} catch (IOException e) {
    			e.printStackTrace();
    			value = "Error";
    		}
    		
    	    catch (Exception k) {
	      		k.printStackTrace();
		  	value = "Error";
		   }
    		
    		return value;
    	}
    
 public static HttpClient createHttpClient_AcceptsUntrustedCerts(boolean login) throws Exception  {
    	    HttpClientBuilder b = HttpClientBuilder.create();
    	 
    	    // setup a Trust Strategy that allows all certificates.
    	    //
    	    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
    	        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    	            return true;
    	        }
    	    }).build();
    	    b.setSslcontext( sslContext);
    	 
    	    // don't check Hostnames, either.
    	    //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
    	    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
    	 
    	    // here's the special part:
    	    //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
    	    //      -- and create a Registry, to register it.
    	    //
    	    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
    	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
    	            .register("http", PlainConnectionSocketFactory.getSocketFactory())
    	            .register("https", sslSocketFactory)
    	            .build();
    	 
    	    // now, we create connection-manager using our Registry.
    	    //      -- allows multi-threaded use
    	    PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
    	    b.setConnectionManager( connMgr);
    	 
    	    HttpClient client = null;
    	    
    	    if (login) {
    	           CredentialsProvider provider = new BasicCredentialsProvider();
    	            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("administrator@System", "vmware123!");
    	           provider.setCredentials(AuthScope.ANY, credentials);
    	             client = b.setDefaultCredentialsProvider(provider).build();
    	             
    	    }
    	    else {
    	    // finally, build the HttpClient;
    	    //      -- done!
    	     client = b.build();
    	    }
    	    
    	    return client;
    	}
    	
 public static String restCall() {
    	String output ="";
    	try {

    		Client client = Client.create();

    		WebResource webResource = client
    		   .resource("http://10.134.3.58/api/versions");

    	
    		ClientResponse response = webResource.accept("application/*+xml;version=9.0")
                       .get(ClientResponse.class);

    		if (response.getStatus() != 200) {
    		   throw new RuntimeException("Failed : HTTP error code : "
    			+ response.getStatus());
    		}

    		output = response.getEntity(String.class);

    		System.out.println("Output from Server .... \n");
    		System.out.println(output);
    		

    	  } catch (Exception e) {

    		e.printStackTrace();

    	  }
    	    return output;
    	}



static void printNode(Node rootNode, String spacer) {
    System.out.println(spacer + rootNode.getNodeName() + " -> " + rootNode.getNodeValue());
    NodeList nl = rootNode.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++)
        printNode(nl.item(i), spacer + "   ");
}


 
 /*
  * This logs into vcd using the java sdk client and then issues an orgs rest call using
  * the http client and parses the response using JOOP.
  * This approach can be used to call vCD extension api's and process results without
  * having to generate javabeans from the xml schemas.
  */
 
 public static void main (String args[]) throws Exception {
	 
	 
	 VcloudClient client;
     
     VcloudClient.setLogLevel(Level.SEVERE);
     
     client = new VcloudClient("https://10.134.3.58", Version.V9_0);
     
     client.registerScheme("https", 443, FakeSSLSocketFactory.getInstance());
     try {
       client.login("administrator@System",  "vmware123!");
      
     } catch (UnauthorizedAccessException ex)
     {
     	 System.out.println("Login Error");

     }
     
     System.out.println("Session Token: " + client.getVcloudToken());
     apacheOrgsCall(client.getVcloudToken());
     
 }
 

 
}