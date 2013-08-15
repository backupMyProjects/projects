/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package variableTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author leo
 */
public class tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int x = 5;
        
        testScope(x);
        System.out.println(x);
        
        tester m = new tester();
        m.testScope(x);
        System.out.println(x);

        Set set = new HashSet();
        set.add("test");
        set.add("test");
        Iterator it = set.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }

        testEquals();
        testList();
        testArray();
        testString();
        testInt();
        
    }

    public static void testScope(int x){
        int i = 0;
        x = i;
        System.out.println(x);
    }

    public static void testEquals(){
        String a1 = "a";
        String a2 = "a";
        if ( a1 == a2 ){
            System.out.println("Yes, a1 == a2");
        }
        if ( a1.equals(a2) ){
            System.out.println("Yes, a1 equals a2");
        }
    }

    private static void testList(){
        List temp = new ArrayList();

        temp.add("test0");//0
        temp.add("test1");//1
        temp.add("test2");//2
        temp.add("test3");//3
        temp.add("test4");//4

        temp.remove(3);// test4
        temp.remove("test2");

        for( int i = 0 ; i < temp.size() ; i++ ) {
            System.out.println(temp.get(i));
        }

    }

    public static void testArray(){
        String[] test = {"hello", "day"};
        System.out.println(test[0]+" "+test[1]);
    }

    private static void testString(){
        String test = "test is = test";
        String[] temp = test.split("=");
        System.out.println("|"+temp[0]+"|");
        
        System.out.println( "" == "" );
        
        String test1 = "000000012";
        StringBuilder sb = new StringBuilder();
        sb.append(test1.substring(0, 3) + "/");
        sb.append(test1.substring(3, 5) + "/");
        sb.append(test1.substring(5, 7) + "/");
        sb.append(test1.substring(7, 9) + "_avatar_small.jpg");
        System.out.println(sb.toString());
    }

    private static void testInt(){
        String test1 = "10";
        int test2 = Integer.parseInt(test1);
        System.out.println(test2);
            
        Integer test = 12;
        System.out.println(String.format("%012d", test));
        System.out.println(String.format("%1$,09d", -3123));
    }

}
