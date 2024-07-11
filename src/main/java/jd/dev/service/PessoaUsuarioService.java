package jd.dev.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Iterator;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jd.dev.model.PessoaJuridica;
import jd.dev.model.Usuario;
import jd.dev.repository.PessoaRepository;
import jd.dev.repository.UsuarioRepository;

@Service
public class PessoaUsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {
		
		for(int i = 0; i < pessoaJuridica.getEnderecos().size();i++) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}
		
		pessoaJuridica= pessoaRepository.save(pessoaJuridica);
		
		Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());
		
		if (usuarioPj == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter TABLE usuarios_acesso drop CONSTRAINT " + constraint + "; commit;");
			}
			
			usuarioPj = new Usuario();
			usuarioPj.setDataAtualizacaoSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(pessoaJuridica);
			usuarioPj.setPessoa(pessoaJuridica);
			usuarioPj.setLogin(pessoaJuridica.getEmail());
			
			String senha = "teste";
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPj.setSenha(senhaCript);
			
			usuarioPj = usuarioRepository.save(usuarioPj);
			
			usuarioRepository.insereAcessoUsersPj(usuarioPj.getId());
			usuarioRepository.insereAcessoUsersPj(usuarioPj.getId(),"ROLE_ADMIN");
			
			
			StringBuilder messagenHtml = new StringBuilder();
			
			messagenHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			messagenHtml.append("<b>Login: </b>" + pessoaJuridica.getEmail() + "<br/>");
			messagenHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>") ;
			messagenHtml.append("<b>OBRIGADO </b>");
			
			
			try {
				serviceSendEmail.enviarEmailHmtl("Acesso Gerado para Loja Virtual", messagenHtml.toString() , pessoaJuridica.getEmail());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
		}
		
		return pessoaJuridica;
	}
	
}
