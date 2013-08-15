package javaexamplecollections;

/**
 * Required : 
 * all httpclient-4.1.1 lib
 */
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author leo
 */
public class HttpClient {

    public static void main(String[] args) throws Exception {
        testHttpClient();
    }

    public static void testHttpClient() throws Exception {
        //URI uri = URIUtils.createURI("http", "www.google.com", -1, "/search","q=httpclient&btnG=Google+Search&aq=f&oq=", null);
        URI uri = URIUtils.createURI("http", "checkip.dyndns.org", -1, null, null, null);
        HttpGet httpget = new HttpGet(uri);
        System.out.println(httpget.getURI());
        
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            byte[] tmp = new byte[2048];
            while (instream.read(tmp) != -1) {
                String s = new String(tmp);
                System.out.println(s);
            }
        }
    }
}
