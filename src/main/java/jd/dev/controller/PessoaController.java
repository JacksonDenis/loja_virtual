package jd.dev.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.PessoaFisica;
import jd.dev.model.PessoaJuridica;
import jd.dev.repository.PessoaRepository;
import jd.dev.service.PessoaUsuarioService;
import jd.dev.util.ValidaCNPJ;
import jd.dev.util.ValidaCPF;

@RestController
public class PessoaController {
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private PessoaUsuarioService pessoaUsuarioService;
	
	@SuppressWarnings("unused")
	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPessoaJuridica(@RequestBody @Valid   PessoaJuridica pessoaJuridica) throws ExceptionMentoriaJava{	
		
		if (pessoaJuridica == null) {
			throw new ExceptionMentoriaJava("Pessoa juridica nao pode ser null");
		} 
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existeCNPJCadastrado(pessoaJuridica.getCnpj()) != null ) {
			throw new ExceptionMentoriaJava("Ja existe CNPJ cadastrado com o numero: " + pessoaJuridica.getCnpj());
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existeInscEstadualadastrado(pessoaJuridica.getInscEstadual()) != null ) {
			throw new ExceptionMentoriaJava("Ja existe Inscricao Estadual cadastrado com o numero: " + pessoaJuridica.getInscEstadual());
		}
		
		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoriaJava("Cnpj : " + pessoaJuridica.getCnpj() + " está inválido. ");
			 
		}
				
		
		pessoaJuridica = pessoaUsuarioService.salvarPessoaJuridica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
				
	}
	
	@SuppressWarnings("unused")
	@ResponseBody
	@PostMapping(value = "**/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPessoaFisica(@RequestBody @Valid   PessoaFisica pessoaFisica) throws ExceptionMentoriaJava{	
		
		if (pessoaFisica == null) {
			throw new ExceptionMentoriaJava("Pessoa juridica nao pode ser null");
		} 
		
		if (pessoaFisica.getId() == null && pessoaRepository.existeCPFCadastrado(pessoaFisica.getCpf()) != null ) {
			throw new ExceptionMentoriaJava("Ja existe CPF cadastrado com o numero: " + pessoaFisica.getCpf());
		}
		
		
		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("CPF : " + pessoaFisica.getCpf() + " está inválido. ");
			 
		}
				
		
		pessoaFisica = pessoaUsuarioService.salvarPessoaFisica(pessoaFisica);
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
				
	}
	
}
