/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package extendTest;

/**
 *
 * @author leo
 */
public class tester {
    public static void main(String args[]){
        a a1 = new a();
        b b1 = new b();
        a1.aM();
        b1.aM();
        b1.bM();

        a a2 = new b();
        a2.aM();
        //a2.bM(); // does not at the scope of class a.
        
        //b b2 = new a();
    }
}
