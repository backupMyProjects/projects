/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexamplecollections;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Required : 
 */
/**
 *
 * @author leo
 */
public class DNSIPgetter {

    public static void main(String[] args) throws Exception {
        // Get DNS IP
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext(env);
        String dnsServers = (String) ictx.getEnvironment().get("java.naming.provider.url");

        System.out.println("DNS Servers: " + dnsServers);
        

    }
}
