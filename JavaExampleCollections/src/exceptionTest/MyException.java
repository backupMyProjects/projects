/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptionTest;

/**
 *
 * @author leo
 */
class MyException extends Exception {

    public MyException(Object msgObj, Object callbakObj) {
        System.out.println(msgObj);
    }
}
