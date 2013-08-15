/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaexamplecollections;

/**
 *
 * @author leo
 */
public class Stack {
    private String[] stack;
    private int size, index;
    public Stack(int input){
        this.size = input;
        stack = new String[input];
    }
    
    public int push(String input){
        int result = index;
        if ( index >= 0 && index < size ){
            stack[index] = input;
            index++;
        }
        return result;
    }
    
    public void checkItem(int index){
        System.out.println(stack[index]);
    }
    
}
