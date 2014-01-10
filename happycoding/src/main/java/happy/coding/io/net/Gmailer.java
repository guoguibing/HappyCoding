package happy.coding.io.net;

import happy.coding.system.Dates;

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

		props.setProperty("mail.smtp.user", "happycodingprojects@gmail.com");
		props.setProperty("mail.smtp.password", "dailycoding");

		props.setProperty("mail.from", "happycodingprojects@gmail.com");
		props.setProperty("mail.to", "gguo1@e.ntu.edu.sg");

		props.setProperty("mail.subject", "Program Notifier from Gmail");
		props.setProperty("mail.text", "Program is finished @" + Dates.now());
	}

	public static void main(String[] args) throws Exception {
		new Gmailer().send("Your program has been finished");
	}
}
