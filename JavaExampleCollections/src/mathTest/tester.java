/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mathTest;

/**
 *
 * @author leo
 */
public class tester {
    public static void main(String[] args){
        
        System.out.println( getRadical(100, 2) );
        System.out.println( Math.exp(2) );
    }

    private static double getRadical(double base, double n_exponent){
        double result = 0;
        result = Math.pow(base, Math.pow(n_exponent, -1) );
        return result;
    }

}
