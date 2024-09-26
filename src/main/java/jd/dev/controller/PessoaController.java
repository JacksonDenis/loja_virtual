package jd.dev.controller;

import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.Endereco;
import jd.dev.model.PessoaFisica;
import jd.dev.model.PessoaJuridica;
import jd.dev.model.dto.CepDTO;
import jd.dev.repository.EnderecoRepository;
import jd.dev.repository.PessoaFisicaRepository;
import jd.dev.repository.PessoaRepository;
import jd.dev.service.PessoaUsuarioService;
import jd.dev.util.ValidaCNPJ;
import jd.dev.util.ValidaCPF;

@RestController
public class PessoaController {
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	@Autowired
	private PessoaUsuarioService pessoaUsuarioService;
	@Autowired
	private EnderecoRepository enderecoRepository ;
	
	@ResponseBody
	@GetMapping(value = "**/consultaPfNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome) {
		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorNomePF(nome.trim().toUpperCase());
		return new ResponseEntity<List<PessoaFisica>>(fisicas,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPfCpf/{cpf}")
	public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf) {
		List<PessoaFisica> cpfs = pessoaFisicaRepository.existeCPFCadastradoList(cpf);
		return new ResponseEntity<List<PessoaFisica>>(cpfs,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPjCNPJ/{cnpj}")
	public ResponseEntity<List<PessoaJuridica>> consultaPjCNPJ(@PathVariable("cpcnpjf") String cnpj) {
		List<PessoaJuridica> cnpjs = pessoaRepository.existeCNPJCadastradoList(cnpj);
		return new ResponseEntity<List<PessoaJuridica>>(cnpjs,HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaPjNome/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaPjNome(@PathVariable("nome") String nome) {
		List<PessoaJuridica> juridicas = pessoaRepository.pesquisaPorNomePJ(nome.trim().toUpperCase());
		return new ResponseEntity<List<PessoaJuridica>>(juridicas,HttpStatus.OK);
	}
	
	
	
	@ResponseBody
	@GetMapping(value = "**/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep) {
		CepDTO cepDTO = pessoaUsuarioService.consultaCep(cep);
		return new ResponseEntity<CepDTO>(cepDTO, HttpStatus.OK);
	}
	
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
		
		if(pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				CepDTO cepDTO = pessoaUsuarioService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
				pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
				
			}
		} else {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				Endereco enderecoTemp =  enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId()).get();
				if (!enderecoTemp.getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
					CepDTO cepDTO = pessoaUsuarioService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
					pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
					pessoaJuridica.getEnderecos().get(p).setRuaLogra(cepDTO.getLogradouro());
					pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
				}
			}
			
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
		
		if (pessoaFisica.getId() == null && pessoaFisicaRepository.existeCPFCadastrado(pessoaFisica.getCpf()) != null ) {
			throw new ExceptionMentoriaJava("Ja existe CPF cadastrado com o numero: " + pessoaFisica.getCpf());
		}
		
		
		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoriaJava("CPF : " + pessoaFisica.getCpf() + " está inválido. ");
			 
		}
				
		
		pessoaFisica = pessoaUsuarioService.salvarPessoaFisica(pessoaFisica);
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
				
	}
	
}
