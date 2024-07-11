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
	
	@Scheduled(initialDelay = 2000, fixedDelay = 86400000 )
	public void notificarUsuarioTrocaSenha() throws InterruptedException {
		List<Usuario> usuarios = usuarioRepository.usuariosSenhaVencida();
		for (Usuario usuario : usuarios) {
			StringBuilder msg = new StringBuilder();
			msg.append("Olá, ").append(usuario.getPessoa().getNome()).append("<br/>");
			msg.append("Está na hora de trocar de senha, já passou 90 dias de validade").append("<br/");
			msg.append("Troque sua senha a JD loja virtual");
			
			try {
				sendEmail.enviarEmailHmtl("Troca de senha", msg.toString(), usuario.getLogin());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			Thread.sleep(3000);
		}
	}

}
