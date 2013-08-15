/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm;

/**
 *
 * @author leo
 */
public class tester {
    public static void main (String[] args) {
        //-- binary search test --//
        BinarySearch bs = new BinarySearch();
        int target = 12;
        int[] data = {2,4,6,8,10,12,14,15,18};
        int result = bs.bSearch(data, target);
        System.out.println("result : "+result);
    }
}
