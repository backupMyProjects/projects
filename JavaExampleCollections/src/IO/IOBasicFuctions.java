/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author leo
 */
public class IOBasicFuctions {

    public void systemCommand() {
        Runtime rt = Runtime.getRuntime();
        try {
            Process p = rt.exec("cmd /c COPY D:\\a.txt BULLZIP");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true) {
                String strRead;
                strRead = br.readLine();
                if (strRead == null) {
                    break;
                }
                System.out.println(strRead);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
