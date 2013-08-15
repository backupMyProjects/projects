/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexamplecollections;

import java.text.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author leo
 */
public class tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        /////////
//        getGCD();
//        getFeb();
//      dateFormat();

        String enc = System.getProperty("file.encoding");
        System.out.println(enc);
        
        // Object Test
        String input = new String("A");
        Stack stack = new Stack(3);
        int test = stack.push(input);
        stack.checkItem(test);
        input.replace("A", "B");
        stack.checkItem(test);
        
        

    }
    

    public static void getGCD() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("輸入兩數:");
        System.out.print("m = ");
        int m = scanner.nextInt();
        System.out.print("n = ");
        int n = scanner.nextInt();
        System.out.println("GCD: " + gcd(m, n));

    }

    private static int gcd(int m, int n) {
        if (n == 0) {
            return m;
        } else {
            return gcd(n, m % n);
        }
    }

    public static void getFeb() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("輸入Feb number:");
        System.out.print("m = ");
        int m = scanner.nextInt();
        System.out.println("Feb Number : " + feb(m));
    }

    private static int feb(int m) {
        System.out.println("Feb(" + m + ")");
        if (m == 0 || m == 1) {
            return m;
        } else if (m < 0) {
            return m;
        } else {
            return feb(m - 1) + feb(m - 2);
        }
    }

    private static void dateFormat() {
        try {
            Date afterDate = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).parse("2010/3/12 01:00:00");
            System.out.println((new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(afterDate));

            String time1 = "2010/3/12 01:00:00";

            Date date1 = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).parse(time1);

            if (date1.after(new Date())) {
                System.out.println("date1 is later then now");
            } else {
                System.out.println("Now is later then date1");
            }

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
//            cal.add(Calendar.MONTH, -1);
            Date testDate = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("Yesterday's date was " + dateFormat.format(testDate));


        } catch (ParseException ex) {
            Logger.getLogger(tester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void foreachTest() {
        HashMap<String, String> hm = new HashMap();
        hm.put("key1", "value1");
        hm.put("key2", "value2");
        hm.put("key3", "value3");

        for (String key : hm.keySet()) {
            System.out.println(key);
        }

        for (String value : hm.values()) {
            System.out.println(value);
        }

    }

    public static boolean mail(){
        boolean result = false;
        //--

        //--
        return result;
    }
}
