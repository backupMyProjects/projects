/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptionTest;

/**
 *
 * @author leo
 */
public class tester {

    public static void main(String args[]) throws MyException {

        String test = null;

        if ( test == null ) {
            throw new MyException("test is null", new CallbackHandler());
        }

        System.out.println("Hello");

    }
}
