package fermoju.sisguias.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.cerberus.web.jsf.FacesUtil;

import fermoju.sisguias.domain.Serventia;
import fermoju.sisguias.domain.Usuario;

public class EmailUtil {

	private static final String urlSistema= "https://www2.tjce.jus.br/fermoju/";
	
	/**
	 * Retorna Url do servidor da aplicação no formato: <br/>
	 * "http://www.host.com:8080"
	 */
	public static String getServerUrl() {
		FacesUtil
				.getFacesContext();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		
		return (scheme + "://" + serverName + ":" + port+"/fermoju/");
	}

	public static void emailConfirmacaoCadastro(Usuario usuario,
			Serventia serventia, String senha) throws EmailException {
		String subject = "[FERMOJU][SISGUIAS] - Email de Confirmação de Cadastro";
		String emailConteudo = "Olá, <b>%s</b> "
				+ "<br />"
				+ "<br />"
				+ "Recebemos de você um pedido de cadastramento para uso do Sistema Sisguias do Fermoju."
				+ "<br />"
				+ "Seu cadastro foi confirmado por um dos administradores do Fermoju no Tribunal de Justiça do Estado do Ceará."
				+ "<br />"
				+ "<br />"
				+ "A partir de agora você tem acesso ao sistema, e para isso use os seguintes dados abaixo:"
				+ "<br />"
				+ "<br />"
				+ "Endereço do Sistema: <a href='%s'><b>%s</b></a> "
				+ "<br />"
				+ "<br />"
				+ "Login: <b>%s</b> "
				+ "<br />"
				+ "Senha: <b>%s</b> "
				+ "<br />"
				+ "<br />"
				+ "É expressamente aconselhado pela equipe do Fermoju que logo no primeiro acesso você altere sua senha, "
				+ "que é de uso pessoal e intrasferível."
				+ "<br />"
				+ "A senha enviada por esse email é gerada aleatoriamente, e nenhum dos funcionários do fermoju tem a acesso a mesma."
				+ "<br />"
				+ "<br />"
				+ "Agradecemos por sua atenção, e obrigado por seguir as diretrizes de uso do sistema.";
		
		emailConteudo = String.format(emailConteudo, usuario.getNome(),
				urlSistema, urlSistema, usuario
				.getCpf(), senha);
		
		HtmlEmail email = getConfigEmail(usuario,emailConteudo,subject);
		email.send();
	}
	
	/**
	 * Configura o e-mail de cadastro do Servidor que obtem acesso ao fermoju 
	 * @param usuario
	 * @param serventia
	 * @param senha
	 * @throws EmailException
	 */
	public static void emailConfirmacaoCadastroServidor(Usuario usuario,
			Serventia serventia) throws EmailException {
		String subject = "[FERMOJU][SISGUIAS] - Email de Confirmação de Cadastro";
		String emailConteudo = "Olá, <b>%s</b> "
				+ "<br />"
				+ "<br />"
				+ "Recebemos de você um pedido de cadastramento para uso do Sistema Sisguias do Fermoju."
				+ "<br />"
				+ "Seu cadastro foi confirmado por um dos administradores do Fermoju no Tribunal de Justiça do Estado do Ceará."
				+ "<br />"
				+ "<br />"
				+ "A partir de agora você tem acesso ao sistema, e para isso use os seguintes dados abaixo:"
				+ "<br />"
				+ "<br />"
				+ "Endereço do Sistema: <a href='%s'><b>%s</b></a> "
				+ "<br />"
				+ "<br />"
				+ "Login: <b>%s</b> "
				+ "<br />"
				+ "Senha: <b>%s</b> "
				+ "<br />"
				+ "<br />"
				+ "Agradecemos por sua atenção, e obrigado por seguir as diretrizes de uso do sistema.";
		
		emailConteudo = String.format(emailConteudo, usuario.getNome(),
				urlSistema, urlSistema, usuario
				.getMatricula(), "Sua SENHA de REDE");
		
		HtmlEmail email = getConfigEmail(usuario,emailConteudo,subject);
		email.send();
	}
	
	/**
	 * Obtem as configurações para envio do e-mail
	 * @return
	 */
	private static HtmlEmail getConfigEmail(Usuario usuario,String msg ,String subject) throws EmailException{
		HtmlEmail email = new HtmlEmail();
		//email.setHostName("200.141.192.244"); // Servidor de e-mail com problema
		email.setHostName("192.168.10.38"); // Servidor de e-mail provisório
		email.addTo(usuario.getEmail(), usuario.getNome());
		email.setFrom("naoresponda@tjce.jus.br", "Fermoju [Sisguias]");
		email.setHtmlMsg(msg);
		email.setSubject(subject);
		return email;
	}
	
	/**
	 * Configura o e-mail do cadastro do serventuário no seu primeiro acesso
	 * @param usuario
	 * @param serventia
	 * @param senha
	 * @throws EmailException
	 */
	public static void emailConfirmCadastroServentuario(Usuario usuario,Serventia serventia, String senha) throws EmailException {
			StringBuffer emailConteudo = new StringBuffer();
			SimpleDateFormat dtCadastro = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt","BR"));
			String subject = "[FERMOJU][SISGUIAS] - Email de Confirmação de Cadastro de Serventuário";
			//Obtem o nome do usuário que está confirmando o cadastro
			String usuarioHabilitacao = ((Usuario) FacesUtil.getSessionAttribute("usuario")).getNome();
			
			
			
	    	emailConteudo.append("  Email de Confirmação de Cadastro de Serventuário");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Senhor(a) Tabelião(ã) ");
	    	emailConteudo.append(" %s");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Comunicamos que foram recebidos os dados informados por V.Sa. no ");
	    	emailConteudo.append("formulário eletrônico de  Cadastro de Usuário do novo sistema Sisguia Extrajudicial Online.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Seu  cadastro foi confirmado pelo(a) funcionário(a) do Fermoju/TJCE, ");
	    	emailConteudo.append(usuarioHabilitacao);
	    	emailConteudo.append(" em ");
	    	emailConteudo.append(dtCadastro.format(usuario.getInicio()));
	    	emailConteudo.append(".");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("O novo Sisguia Extrajudicial Online estará acessível na página do Tribunal de Justiça " );
	    	emailConteudo.append("<a href='%s'><b>%s</b></a> ");
	    	emailConteudo.append(", a partir de 08 de dezembro de 2008.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Para ter acesso ao novo sistema o(a) Senhor(a) deverá informar os seguintes dados:");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Login: ");
	    	emailConteudo.append("<b>%s</b>");
	    	emailConteudo.append(" (número do CPF)");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Senha: ");
	    	emailConteudo.append("<b>%s</b>");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Embora a SENHA enviada por esse e-mail tenha sido gerada pelo sistema, aleatoriamente, "); 
	    	emailConteudo.append("e nenhum funcionário desse Tribunal de Justiça tenha acesso a mesma, recomendamos que seja ");
	    	emailConteudo.append("alterada logo após o 1º acesso, lembrando que ");
	    	emailConteudo.append("<b>a SENHA é de uso pessoal, sigilosa e intransferível.</b>");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Esclarecemos que o acesso ao novo sistema deverá ser feito somente na Data de Implantação definida ");
	    	emailConteudo.append("para esse Cartório (Comarca) definido no calendário de Implantação publicado em Portaria desse Tribunal de Justiça ");
	    	emailConteudo.append("e após a emissão da  \"Guia de Fechamento\" do sistema Sisguia Offline ");
	    	emailConteudo.append("referente ao período anterior a essa  data.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Exemplo: ");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("O Cartório do 1º Ofício do Registro Civil, Comarca de Caucaia Terceira Entrancia, só poderá acessar o novo Sisguia Extrajudicial ");
	    	emailConteudo.append("Online a partir de 15/12/208 (Data de Implantação definida para os cartórios dessa Comarca) e após a emissão da ");
	    	emailConteudo.append("Guia de Recolhimento do Fermoju referente ao período 08/12/2008 a 12/12/2008 (guia de fechamento do sistema  Sisguia Offline.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Se o Valor do Fermoju acumulado até a Data da Implantação desse Cartório não atingiu R$ 100,00 (cem reais), o Cartório deverá ");
	    	emailConteudo.append("fazer a atualização do Sisguias off line para a versão nova 4.8, que permite a geração da \"Guia de Fechamento\"  com  valor ");
	    	emailConteudo.append("menor que R$100,00. O arquivo de atualização estará disponível na página do Fermoju (Internet e Intranet) opção Sistemas/Download ");
	    	emailConteudo.append("(AtualizacaoSisguiaVersão4.8.exe).");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Para maiores esclarecimentos, consultar informações sobre o novo sistema disponíveis no site do Fermoju.");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Marcos Aurélio Vieira Madeiro");
	    	emailConteudo.append("<br />");
	    	emailConteudo.append("Secretário Executivo do Fermoju");
	    	
	    	HtmlEmail email = EmailUtil.getConfigEmail(usuario
	    									,String.format(emailConteudo.toString(),serventia.getDescricao() + " / " + serventia.getId().getComarca().getNome(),
						    						urlSistema, urlSistema, usuario.getCpf(), senha)
						    				,subject);
			email.send();
	}
	
	/**
	 * E-mail de confirmação da reinicilização de senha
	 * @param usuario
	 * @param serventia
	 * @param senha
	 * @throws EmailException
	 */
	public static void emailReinicializacaoSenha(Usuario usuario,Serventia serventia, String senha) throws EmailException {
		StringBuffer emailConteudo = new StringBuffer();
		String subject = "[FERMOJU][SISGUIAS] - Reinicialização de senha Cadastro de Usuário";
		
		emailConteudo.append("[FERMOJU] - Reinicialização de senha Cadastro de Usuário");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Senhor(a) Usuário(a) ");
    	emailConteudo.append("%s");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Comunicamos que foi recebido um pedido de reinicialização da Senha de ");
    	emailConteudo.append("acesso ao novo sistema Sisguia Extrajudicial Online do Fermoju.");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Sua nova senha é: ");
    	emailConteudo.append("<b>%s</b>");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Embora a SENHA enviada por esse e-mail tenha sido gerada pelo sistema, aleatoriamente, e nenhum ");
    	emailConteudo.append("funcionário desse Tribunal de Justiça tenha acesso a mesma, recomendamos que seja alterada logo ");
    	emailConteudo.append("após o 1º acesso, lembrando que <b>a SENHA é de uso pessoal, sigilosa e intransferível.</b>");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	emailConteudo.append("Agradecemos por seguir as diretrizes de uso do sistema recomendadas pelo Fermoju.");
    	emailConteudo.append("<br />");
    	emailConteudo.append("<br />");
    	
    	HtmlEmail email = EmailUtil.getConfigEmail(usuario
    									,String.format(emailConteudo.toString(),usuario.getNome(),senha)
					    				,subject);
		email.send();
}
}
