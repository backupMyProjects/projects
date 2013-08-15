/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

/**
 * Required : businessrefinery.barcode.jar
 */

import com.businessrefinery.barcode.Barcode;
import com.businessrefinery.barcode.QRCode;

/**
 *
 * @author leo
 */
public class BarCodeSample_evaluation {
    public static void main(String[] args) throws Exception{
        
        /*
        // 1. Create linear barcode object.
        Barcode barcode = new Barcode();
        // 2. Set barcode properties.
        barcode.setSymbology(Barcode.CODE128);
         */
        
        QRCode barcode = new QRCode();
        barcode.setCode("Test");
        
        // 3. Draw barcode, and output to gif format
        barcode.drawImage2File("d:/code128.gif");
    }
    
}
