/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mailTest;

/**
 * Required : mail.jar (from sun)
 */

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author leo
 */
public class Postman {

    private String userName, passwd;
    private String host = "smtp.gmail.com";
    private int port = 587;
    private String filePath;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public Postman(String userName, String passwd){
        this.userName = userName;
        this.passwd = passwd;
    }

    public void sendMail(String subject, String messageBody, String attachedPic, String fromBox, String toBox) {
        //String host = "smtp.gmail.com";
        //int port = 587;
        //String username = "anubis.mummy.pop@gmail.com";
        //String password = "a127359058";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromBox));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toBox) );
            /*
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("anubis.mummy@gmail.com") );
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("jjjgalliano@gmail.com") );
             */
            message.setSubject(subject);

            // Message Body
            //message.setText(messageBody);

            // 文字部份，注意 img src 部份要用 cid:接下面附檔的header
            MimeBodyPart textPart = new MimeBodyPart();
            StringBuffer html = new StringBuffer();
            html.append(messageBody);
            html.append("<img src='cid:image'/><br>");
            textPart.setContent(html.toString(), "text/html; charset=UTF-8");

            // 圖檔部份，注意 html 用 cid:image，則header要設<image>
            MimeBodyPart picturePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachedPic);
            picturePart.setDataHandler(new DataHandler(fds));
            picturePart.setFileName(fds.getName());
            picturePart.setHeader("Content-ID", "<image>");

            // set up all the content
            Multipart email = new MimeMultipart();
            email.addBodyPart(textPart);
            email.addBodyPart(picturePart);
            message.setContent(email);

            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, userName, passwd);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
