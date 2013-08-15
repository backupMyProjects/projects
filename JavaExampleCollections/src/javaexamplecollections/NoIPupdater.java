package javaexamplecollections;

/**
 * Required : 
 * LeoLib.jar
 * all httpclient-4.1.1 lib
 */
import LeoLib.tools.Log;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author leo
 */
public class NoIPupdater {
    private static final String TAG = NoIPupdater.class.getName();
    //private final String TAG = NoIPupdater.class.getName();

    public static void main(String[] args) throws Exception {
        Log.setLogLevel(Log.INFO);
        Log.setFormat(Log.Format.FORMAT4);
        
        String targetHost = "ycube.myftp.org";
        
        String myIP = IPCatacher.getMyIP();
        Log.i(TAG, "current IP : "+myIP);
        //updateNoIP("jenova.myftp.org", myIP);
        updateNoIP(targetHost, myIP);
    }

    public static void updateNoIP(String hostName, String myIP) throws Exception {
        String protocal = "http";
        String noipHost = "dynupdate.no-ip.com";
        int port = 80;
        String account = "anubis_mummy@yahoo.com.tw";
        String password = "qpwoeiruty";
        String queryPath = "/nic/update";
        String queryInput = "hostname=" + hostName+"&myip=" + myIP;
        
        HttpHost targetHost = new HttpHost(noipHost, port, protocal);
        DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getCredentialsProvider().setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials(account, password));
        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);
        // Add AuthCache to the execution context
        BasicHttpContext localcontext = new BasicHttpContext();
        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
        URI uri = URIUtils.createURI(protocal, noipHost, -1, queryPath, queryInput, null);
        HttpGet httpget = new HttpGet(uri);
        HttpResponse response = httpclient.execute(targetHost, httpget, localcontext);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            byte[] tmp = new byte[2048];
            while (instream.read(tmp) != -1) {
                String s = new String(tmp);
                Log.i(TAG,s);
            }
        }
        EntityUtils.consume(entity);
    }
    
}
