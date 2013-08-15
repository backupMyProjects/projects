/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package extendTest;

/**
 *
 * @author leo
 */
public class testOverload {
    public static void main(String args[]){  // 函式庫 library
        
        parent p = new parent();
        son s = new son();
        parent p1 = new son();
        
        testOverload t = new testOverload();
        t.func(p);
        t.func(s);
        t.func(p1);
    }
    
    public void func(parent p){
        p.test();
    }
    
    public void func(son s){
        s.test();
    }
}
