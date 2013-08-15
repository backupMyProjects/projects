/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author leo
 */
public class tester {

    public static void main(String[] args) {
        File inFile = new File("/home/leo/Desktop/SWEngQuestion/test.txt");
        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = new FileInputStream(inFile);

            // Here BufferedInputStream is added for fast reading.
            reader = new BufferedReader(new FileReader(inFile));

            // dis.available() returns 0 if the file does not have more lines.
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }



            // dispose all the resources after using them.
            reader.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
