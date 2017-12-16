package com.app.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.app.config.resource.ResourceBundleFile;

public class SendMail {

	String SMTP_HOST_NAME = "";
	String SMTP_AUTH_USER = "";
	String SMTP_AUTH_PWD = "";
	String SMTP_PORT = "";

	String emailMsgTxt = "";
	String emailSubjectTxt = "";

	String emailFromAddress = "";
	String fileName = "";
	String strCCMail = "";

	public void transferToMailServer(String mail, String subj, String emailMsgTxt) throws MessagingException {
		String hostname = ResourceBundleFile.getValueFromKey("SMTP_HOST_NAME");
		String port = ResourceBundleFile.getValueFromKey("SMTP_PORT");
		String username1 = ResourceBundleFile.getValueFromKey("SMTP_AUTH_USER");
		String password1 = ResourceBundleFile.getValueFromKey("SMTP_AUTH_PWD");
		String from = ResourceBundleFile.getValueFromKey("emailFromAddress");
		String ccMail = ResourceBundleFile.getValueFromKey("emailCC");
		SMTP_HOST_NAME = hostname.trim();
		SMTP_PORT = port.trim();
		SMTP_AUTH_USER = username1.trim();
		SMTP_AUTH_PWD = password1.trim();
		emailFromAddress = from.trim();
		strCCMail = ccMail.trim();
		emailSubjectTxt = subj;

		postMail(mail, emailSubjectTxt, emailMsgTxt, emailFromAddress, strCCMail);

	}

	public void postMail(String recipients, String subject, String message, String from, String strCCMail)
			throws MessagingException {
		try {
			boolean debug = true;
			SMTP_HOST_NAME = ResourceBundleFile.getValueFromKey("SMTP_HOST_NAME");
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST_NAME);
			props.put("mail.smtp.port", SMTP_PORT);
			props.put("mail.smtp.auth", "true");
			// props.put("mail.smtp.starttls.enable","true");
			// props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			// props.put("mail.smtp.socketFactory.fallback", "true");
			// props.put("mail.smtp.ssl.enable", "true");

			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			session.setDebug(debug);

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
			msg.setRecipient(Message.RecipientType.CC, new InternetAddress(strCCMail));
			msg.setSubject(subject);
			msg.setContent(message, "text/html;charset=utf-8");
			Transport.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		String username = "";
		String password = "";
		public PasswordAuthentication getPasswordAuthentication() {
			String username1 = ResourceBundleFile.getValueFromKey("SMTP_AUTH_USER");
			String password1 = ResourceBundleFile.getValueFromKey("SMTP_AUTH_PWD");
			username = username1.trim();
			password = password1.trim();
			return new PasswordAuthentication(username, password);
		}
	}

}
