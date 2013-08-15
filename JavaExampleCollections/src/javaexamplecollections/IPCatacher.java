package javaexamplecollections;

/**
 * Required : common-lan-2.4.jar
 */

/**
 *
 * @author leo
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import org.apache.commons.lang.StringUtils;
 
//import android.util.Log;
import LeoLib.tools.*;
 
public class IPCatacher{
    private static final String TAG = IPCatacher.class.getName();
 
    static String cachedIP = "127.0.0.1";
    static List<String> ftpServers = new ArrayList<String>();
    static
    {
        // initial ftp servers
        ftpServers.add("ftp.tw.debian.org");
        ftpServers.add("ftp.jp.debian.org");
        ftpServers.add("ftp2.jp.debian.org");
        ftpServers.add("ftp.kr.debian.org");
        ftpServers.add("ftp.be.debian.org");
        ftpServers.add("ftp.de.debian.org");
        ftpServers.add("ftp.us.debian.org");
    }
    
    public static void main(String[] args){
        Log.i(TAG+"."+Thread.currentThread().getName(), getMyIP());
    }
 
    public static String getMyIP()
    {
        int tryCount = 5;
        while (tryCount-- > 0)
        {
            if (!"127.0.0.1".equals(getIP()))
                return getIP();
        }
 
        return getIP();
    }
 
    private static String getIP()
    {
        if (!"127.0.0.1".equals(cachedIP))
        {
            return cachedIP;
        }
 
        Collections.shuffle(ftpServers);
        final StringBuffer sb = new StringBuffer();
        final String host = ftpServers.get(0);
        final Object lock = new Object();
        try
        {
            final Socket socket = new Socket(host, 21);
            socket.setSoTimeout(30 * 1000);
            final InputStream in = socket.getInputStream();
 
            final PrintWriter writer = new PrintWriter(
                    socket.getOutputStream(), true);
            new Thread()
            {
                public void run()
                {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in));
                    boolean isLogin = false;
                    boolean isPass = false;
                    boolean isStat = false;
                    while (true)
                    {
                        try
                        {
                            String line = StringUtils.trimToEmpty(reader
                                    .readLine());
                            sb.append(line);
                            sb.append("\n");
                            if (StringUtils.startsWith(line, "220") && !isLogin)
                            {
                                isLogin = true;
                                writer.println("USER ftp");
                                continue;
                            }
                            if (StringUtils.startsWith(line, "331") && !isPass)
                            {
                                isPass = true;
                                writer.println("PASS ftp@gmail.com");
                                continue;
                            }
 
                            if (StringUtils.startsWith(line, "230") && !isStat)
                            {
                                isStat = true;
                                writer.println("STAT");
                                continue;
                            }
 
                            if (StringUtils.startsWith(line, "211 "))
                            {
                                synchronized (lock)
                                {
                                    lock.notifyAll();
                                }
                                return;
                            }
 
                        } catch (IOException e)
                        {
                            //Log.e(TAG, e.getMessage(), e);
                            return;
                        }
                    }
                };
            }.start();
 
        } catch (Exception e)
        {
            //Log.e(TAG, "has problem when get ip from: " + host, e);
        }
 
        synchronized (lock)
        {
            try
            {
                lock.wait(5000);
            } catch (InterruptedException ignored)
            {
            }
        }
 
        Pattern ipPatt = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        Matcher m = ipPatt.matcher(sb.toString());
        //Log.w(TAG, sb.toString());
        while (m.find())
        {
            cachedIP = m.group();
        }
 
        if (!"127.0.0.1".equals(cachedIP))
        {
            return cachedIP;
        }
 
        //Log.w(TAG, "cannot get ip from " + host);
        return "127.0.0.1";
    }
    
    public List<InetAddress> getInterfaceAddresses123()
    {
        List<InetAddress> result = new ArrayList<InetAddress>();
        try
        {
            Enumeration<NetworkInterface> ifaces = NetworkInterface
                    .getNetworkInterfaces();
            while (ifaces.hasMoreElements())
            {
                Enumeration<InetAddress> addrs = ifaces.nextElement()
                        .getInetAddresses();
                while (addrs.hasMoreElements())
                {
                    result.add(addrs.nextElement());
                }
            }
        } catch (SocketException e)
        {
            //Log.e(TAG, e.getMessage(), e);
        }
        return result;
    }
}