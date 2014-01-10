package happy.coding.io.net;

import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic Email Class, Read configuration from email.properties file
 * 
 * @author guoguibing
 * 
 */
public class EMailer {
	protected final static Logger logger = LoggerFactory.getLogger(EMailer.class);
	protected String from;
	protected String to;
	protected String cc;
	protected String bcc;
	protected String subject;
	protected String text;
	protected String attachment;

	protected Properties props = new Properties();

	protected void config(String filename) throws Exception {
		props.load(new FileInputStream(filename));
	}

	public void send() throws Exception {
		if (text == null)
			text = props.getProperty("mail.text");
		if (attachment == null)
			attachment = props.getProperty("mail.attachment");
		send(text, attachment);
	}

	public void config163() {
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "smtp.163.com");
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.smtp.auth", "true");
	}

	public void send(String text) throws Exception {
		send(text, null);
	}

	public void send(String text, String attachment) throws Exception {
		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("mail.smtp.user"), props
						.getProperty("mail.smtp.password"));
			}

		});

		if (from == null)
			from = props.getProperty("mail.from");
		if (to == null)
			to = props.getProperty("mail.to");
		if (cc == null)
			cc = props.getProperty("mail.cc");
		if (bcc == null)
			bcc = props.getProperty("mail.bcc");
		if (subject == null)
			subject = props.getProperty("mail.subject");

		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		if (cc != null)
			msg.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
		if (bcc != null)
			msg.setRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));

		msg.setSubject(subject);
		msg.setSentDate(new Date());

		if (attachment != null) {
			MimeBodyPart tp = new MimeBodyPart();
			tp.setText(text);

			MimeBodyPart ap = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(attachment);
			ap.setDataHandler(new DataHandler(fds));
			ap.setFileName(fds.getName());

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(tp);
			mp.addBodyPart(ap);

			msg.setContent(mp);
		} else {
			msg.setText(text);
		}

		Transport.send(msg);

		logger.debug("Have sent an email notification to {}. ", to);
	}

	public Properties getProps() {
		return props;
	}
}
