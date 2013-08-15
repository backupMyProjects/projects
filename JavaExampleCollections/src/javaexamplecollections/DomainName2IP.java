/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexamplecollections;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Required : 
 */
/**
 *
 * @author leo
 */
public class DomainName2IP {
    public static void main(String[] args) throws UnknownHostException{
        
        // Get IPaddress from domain name
        InetAddress giriAddress = java.net.InetAddress.getByName("maleo.myftp.org");
        String address = giriAddress.getHostAddress();
        System.out.println( address );
    }
    
}
