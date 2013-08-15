/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package justtest;

/**
 *
 * @author leo
 */
public class JustTest {
    
    public static void main (String[] args){
        String name = "Leo Chen's Mama";
        name = name.replaceAll("'", "\\\\'");
        System.out.println(name);
    }
    
}
