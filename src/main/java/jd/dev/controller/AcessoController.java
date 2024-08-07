package jd.dev.controller;
   
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.Acesso;
import jd.dev.repository.AcessoRepository;
import jd.dev.service.AcessoService;

@Controller
@RestController
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;
	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava {
		
		if (acesso.getId() == null) {
			List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
			if (!acessos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe acesso com a descricao " + acesso.getDescricao());
			}
		}
		
		Acesso acessoSalvo = acessoService.save(acesso);
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
		
		
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteAcesso")
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) {
		
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity("Acesso Removido", HttpStatus.OK);
		
		
	}
	
	@CrossOrigin(origins = "https://www.jdtech.com.br")
	@Secured ({"ROLE_GERENTE", "ROLE_ADMIN"})
	@ResponseBody
	@DeleteMapping(value = "**/deleteAcessoId/{id}")
	public ResponseEntity<?> deleteAcessoId(@PathVariable("id") Long id) {
		
		acessoRepository.deleteById(id);
		return new ResponseEntity("Acesso Removido", HttpStatus.OK);
		
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava {
		
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		
		if (acesso == null) {
			throw new ExceptionMentoriaJava("Não encontro acesso com codigo: " + id);
		}
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
		
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscarDesc/{desc}")
	public ResponseEntity<List<Acesso>> buscarDesc(@PathVariable("desc") String desc) {
		
		List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc);
		return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);		
		
	}
	

}
