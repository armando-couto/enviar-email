package bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.EmailException;

@ManagedBean (name = "index")
public class IndexController {

	private Mensagem mensagem = new Mensagem();

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public void enviaEmail() {
		try {
			EmailUtils.enviaEmail(mensagem);
		} catch (EmailException ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro! Occoreu um erro ao enviar a mensagem.", "Erro"));
		}
	}

	public void limpaCampos() {
		mensagem = new Mensagem();
	}
}