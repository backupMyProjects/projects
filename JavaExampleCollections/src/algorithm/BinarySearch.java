/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algorithm;

/**
 *
 * @author leo
 */
public class BinarySearch {
    public int bSearch(int[] data, int target){
        int start = 0;
        int end = data.length -1;
        

        while( end <= start ){
            int mid = (start + end) / 2;
            if ( data[mid] == target ){
                return mid;
            }else if ( data[mid] > target ) {
                end = mid - 1;
            }else if ( data[mid] < target ) {
                start = mid + 1;
            }
        }
        return -1;
    }
}
