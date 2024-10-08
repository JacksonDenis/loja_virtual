package jd.dev.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Iterator;

import javax.mail.MessagingException;

import jd.dev.model.dto.ConsultaCnpjDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jd.dev.model.PessoaFisica;
import jd.dev.model.PessoaJuridica;
import jd.dev.model.Usuario;
import jd.dev.model.dto.CepDTO;
import jd.dev.repository.PessoaFisicaRepository;
import jd.dev.repository.PessoaRepository;
import jd.dev.repository.UsuarioRepository;

@Service
public class PessoaUsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ServiceSendEmail serviceSendEmail;

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {

		for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}

		pessoaJuridica = pessoaRepository.save(pessoaJuridica);

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

			usuarioRepository.insereAcessoUsers(usuarioPj.getId());
			usuarioRepository.insereAcessoUsersPj(usuarioPj.getId(), "ROLE_ADMIN");

			StringBuilder messagenHtml = new StringBuilder();

			messagenHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			messagenHtml.append("<b>Login: </b>" + pessoaJuridica.getEmail() + "<br/>");
			messagenHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			messagenHtml.append("<b>OBRIGADO </b>");

			try {
				serviceSendEmail.enviarEmailHmtl("Acesso Gerado para Loja Virtual", messagenHtml.toString(),
						pessoaJuridica.getEmail());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}

		}

		return pessoaJuridica;
	}

	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {

		for (int i = 0; i < pessoaFisica.getEnderecos().size(); i++) {
			pessoaFisica.getEnderecos().get(i).setPessoa(pessoaFisica);
			// pessoaFisica.getEnderecos().get(i).setEmpresa(pessoaFisica);

		}

		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);

		Usuario usuarioPf = usuarioRepository.findUserByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());

		if (usuarioPf == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("begin; alter TABLE usuarios_acesso drop CONSTRAINT " + constraint + "; commit;");
			}

			usuarioPf = new Usuario();
			usuarioPf.setDataAtualizacaoSenha(Calendar.getInstance().getTime());
			usuarioPf.setEmpresa(pessoaFisica.getEmpresa());
			usuarioPf.setPessoa(pessoaFisica);
			usuarioPf.setLogin(pessoaFisica.getEmail());

			String senha = "teste";
			String senhaCript = new BCryptPasswordEncoder().encode(senha);

			usuarioPf.setSenha(senhaCript);

			usuarioPf = usuarioRepository.save(usuarioPf);

			usuarioRepository.insereAcessoUsers(usuarioPf.getId());

			StringBuilder messagenHtml = new StringBuilder();

			messagenHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b><br/>");
			messagenHtml.append("<b>Login: </b>" + pessoaFisica.getEmail() + "<br/>");
			messagenHtml.append("<b>Senha: </b>").append(senha).append("<br/><br/>");
			messagenHtml.append("<b>OBRIGADO </b>");

			try {
				serviceSendEmail.enviarEmailHmtl("Acesso Gerado para Loja Virtual", messagenHtml.toString(),
						pessoaFisica.getEmail());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}

		}

		return pessoaFisica;
	}
	
	public CepDTO consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+cep+"/json", CepDTO.class).getBody();
	}


	public static ConsultaCnpjDto consultaCnpjReceitaWS(String cnpj) {
		return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + cnpj, ConsultaCnpjDto.class).getBody();
	}


}
