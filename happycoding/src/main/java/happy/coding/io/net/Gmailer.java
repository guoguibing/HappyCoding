package happy.coding.io.net;

import happy.coding.system.Dates;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class Gmailer extends EMailer {

	public Gmailer() {
		configSSL();
		defaultInstance();
	}

	public void configTLS() {
		props.put("mail.smtp.starttls.enable", "true");

		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.auth", "true");
	}

	public void configSSL() {
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	}

	private void defaultInstance() {
		props.setProperty("mail.debug", "false");

		final String userName = "happycodingprojects@gmail.com";
		final String password = "dailycoding@ntu";
		props.setProperty("mail.smtp.user", userName);
		props.setProperty("mail.smtp.password", password);

		props.setProperty("mail.from", userName);
		props.setProperty("mail.to", "gguo1@e.ntu.edu.sg");

		props.setProperty("mail.subject", "Program Notifier from Gmail");
		props.setProperty("mail.text", "Program was finished @" + Dates.now());

		Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
	}

	public static void main(String[] args) throws Exception {
		new Gmailer().send("Your program has been finished");
	}
}
