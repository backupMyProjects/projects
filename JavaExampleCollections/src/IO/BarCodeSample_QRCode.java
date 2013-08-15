package IO;
/**
 * Required : Qrcode.jar
 */

import com.swetake.util.Qrcode;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.imageio.ImageIO;

/**
 *
 * @author leo
 */
public class BarCodeSample_QRCode {

    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
        BarCodeSample_QRCode bsQ = new BarCodeSample_QRCode();
        bsQ.generateQRCode("Hello QR code", "d:/qrCode.png", "PNG");
    }

    /**
     * 
     * @param input
     * @param filePath
     * @param imgType : support image type : PNG, jpg, gif, BMP
     * @throws IOException 
     */
    public void generateQRCode(String input, String filePath, String imgType) throws IOException {
        //String input = "hello world";

        Qrcode x = new Qrcode();
        x.setQrcodeErrorCorrect('M');
        x.setQrcodeEncodeMode('B');
        x.setQrcodeVersion(7);
        byte[] d = input.getBytes();
        // createGraphics
        BufferedImage bi = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();

        // set background
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, 140, 140);

        // 設定字型顏色 => BLACK
        g.setColor(Color.BLACK);
        if (d.length > 0 && d.length < 120) {
            boolean[][] s = x.calQrcode(d);

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        g.fillRect(j * 3, i * 3, 3, 3);
                    }
                }
            }
        }

        ImageIO.write(bi, imgType, new File( filePath ));
    }
}
