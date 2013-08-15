/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mailTest;

/**
 * Required : mail.jar (from sun)
 */

/**
 *
 * @author leo
 */
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendMail {

    /*
	public static void main(String[] args) {
		String host = "smtp.gmail.com";
		int port = 587;
		String username = "anubis.mummy.pop@gmail.com";
		String password = "a127359058";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("anubis.mummy.pop@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("anubis.mummy@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler," +
					"\n\n No spam to my email, please!");

			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, username, password);
			transport.sendMessage(message, message.getAllRecipients());
                        transport.close();

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
     */

    public static void main(String[] args){
        String userName = "anubis.mummy.pop@gmail.com";
        String passwd = "a127359058";
        Postman postman = new Postman(userName, passwd);
        
        String subject = "test 1";
        String message = "This is just a tes tfor what I want";
        String from = "anubis.mummy.pop@gmail.com";
        String to = "anubis.mummy.pop@gmail.com, anubis.mummy@gmail.com";
        String attachedPic = "D:/USING/Documents/working/NetBeansProjects/JavaExampleCollections/src/javaexamplecollections/avatar.gif";
        postman.sendMail(subject, message, attachedPic , from, to);
    }
}