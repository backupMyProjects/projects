/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

/**
 *
 * @author leo
 */
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import java.io.*;
import javax.media.jai.RenderedOp;

public class Tiff2Jpg {

    public boolean converted(String sourceFile, String targetFile) {
        boolean isSuccess = false;
        try {
            /*
            FileInputStream in = new FileInputStream(sourceFile);
            BufferedImage image = ImageIO.read(in);
            
            File jpgFile = new File(targetFile);
            isSuccess = ImageIO.write(image, "jpg", jpgFile);
            image.flush();
            in.close();
            */
            
            RenderedOp source = JAI.create("fileload", sourceFile);
            FileOutputStream stream = null;
            stream = new FileOutputStream(targetFile );
            com.sun.media.jai.codec.JPEGEncodeParam JPEGparam = new com.sun.media.jai.codec.JPEGEncodeParam();
            ImageEncoder encoder = ImageCodec.createImageEncoder("jpeg",stream,JPEGparam);
            encoder.encode(source);
        } catch (IOException ex) {
        }
        return isSuccess;

    }

    public static void main(String[] args) {
        Tiff2Jpg t2j = new Tiff2Jpg();
        boolean a = t2j.converted("d:/test.tif ", "d:/test.jpg ");
        System.out.println(a);
    }
}
