package org.camguard.server.services;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.camguard.server.models.ConfigurationCamGuard;

public class MailService {
	public static void sendAlert(File imageFile) throws MessagingException, IOException {
		ConfigurationCamGuard config = ConfigurationService.getInstance().getConfiguration();
		    
	    Properties props = new Properties();
	    props.put("mail.smtp.host", config.smtpServer);
        props.put("mail.smtp.port", config.smtpPort);
        props.put("mail.smtp.auth", "true");        
	    props.put("mail.smtp.starttls.enable", config.smtpTls ?  "true" : "false");
	    Session session = Session.getInstance(props);	    
	    MimeMessage msg = new MimeMessage(session);
	    
	    msg.setFrom(new InternetAddress(config.senderEmail));
	    
	    InternetAddress[] targets = new InternetAddress[config.alertEmails.length];
	    int x=0;
	    for (String email : config.alertEmails) {
	    	targets[x] = new InternetAddress(email);
			x++;
		}	    
	    msg.setRecipients(Message.RecipientType.TO, targets);
	    msg.setSubject("CAMERA Alert");
	    Multipart multipart = new MimeMultipart("related");

        MimeBodyPart htmlPart = new MimeBodyPart();
        final String imageId = "777imageInsideId";
        
        htmlPart.setText("<html><body>" +
        		"<div>Camera alert</div>" +
        		"<img src='cid:" + imageId + "' />" +
        		"</body></html>"
        		, "utf-8", "html");
        multipart.addBodyPart(htmlPart);

        MimeBodyPart imgPart = new MimeBodyPart();
        // imageFile is the file containing the image
        imgPart.attachFile(imageFile);
        imgPart.setContentID(imageId);
        multipart.addBodyPart(imgPart);

        msg.setContent(multipart);
	    
	    // set the message content here
	    Transport.send(msg, config.smtpLogin, config.smtpPassword);		
		
	}
	
	public static void main(String[] args) throws IOException, MessagingException {
		ConfigurationService.getInstance().loadInstance(new File("./configuration.json"));
		sendAlert(new File("./src/test/resources/org/camguard/server/utils/sample.jpg"));
	}
}
