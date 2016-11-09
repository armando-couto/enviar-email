package bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtils {

	private static final String HOSTNAME = "smtp.gmail.com";
	private static final String USERNAME = "xxxx@gmail.com";
	private static final String PASSWORD = "xxxxxx";
	private static final String EMAILORIGEM = "xxxx@gmail.com";

	public static Email conectaEmail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(HOSTNAME);
		email.setSmtpPort(587);
		email.setAuthentication(USERNAME, PASSWORD);
		email.setTLS(true);
		email.setFrom(EMAILORIGEM);
		return email;
	}

	@SuppressWarnings("unused")
	public static void enviaEmail(Mensagem mensagem) throws EmailException {

		Email email = new SimpleEmail();
		email = conectaEmail();
		email.setSubject(mensagem.getTitulo());
		email.setMsg(mensagem.getMensagem());
		email.addTo(mensagem.getDestino());
		String resposta = email.send();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail enviado com sucesso para: " + mensagem.getDestino(), "Informação"));
	}
}
