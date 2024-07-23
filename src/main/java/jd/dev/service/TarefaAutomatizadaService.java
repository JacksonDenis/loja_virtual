package jd.dev.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jd.dev.model.Usuario;
import jd.dev.repository.UsuarioRepository;

@Service
public class TarefaAutomatizadaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private ServiceSendEmail sendEmail;
	
	@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo") /*Vai rodar todo dia as 11 horas da manhã horario de Sao paulo*/
	public void notificarUserTrocaSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		
		List<Usuario> usuarios = usuarioRepository.usuariosSenhaVencida();
		
		for (Usuario usuario : usuarios) {
			
			
			StringBuilder msg = new StringBuilder();
			msg.append("Olá, ").append(usuario.getPessoa().getNome()).append("<br/>");
			msg.append("Está na hora de trocar sua senha, já passou 90 dias de validade.").append("<br/>");
			msg.append("Troque sua senha a loja virtual do Alex - JDEV treinamento");
			
			sendEmail.enviarEmailHmtl("Troca de senha", msg.toString(), usuario.getLogin());
			
			Thread.sleep(3000);
			
		}
		
		
	}
}
