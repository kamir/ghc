/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wikiapiclient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.login.FailedLoginException;

public class WikiAPIClient {
    
    
 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FailedLoginException, KeyManagementException, NoSuchAlgorithmException {
        
        
        
        
        System.setProperty("javax.net.ssl.trustStore","/Library/Java/JavaVirtualMachines/jdk1.7.0_80.jdk/Contents/Home/jre/lib/security/cacerts");
        
        
        
        
        
                // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
        // Install the all-trusting trust manager
        final SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        
        
        
        
        
        
        
        
        
        
        WikiORIGINAL w1 = new WikiORIGINAL();



        WikiORIGINAL w2 = new WikiORIGINAL();

        w2 = new WikiORIGINAL("www.semanpix.de/opendata/wiki", "");
        
        // w2.login("kamir", "8cwrr");
        
        String pt2 = w2.getPageText("Main_Page");

        String[] links = w2.getLinksOnPage("Main_Page");
        System.out.println( links.length );
        
        HashMap<String,String> iwl = w2.getInterWikiLinks("Main_Page");
        System.out.println( iwl.size() );

    }
    
}
