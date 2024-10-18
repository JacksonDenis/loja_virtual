package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.ContasPagar;
import jd.dev.repository.ContasPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Controller
public class ContasPagarController {
    @Autowired
    private ContasPagarRepository contasPagarRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarContaPagar")
    public ResponseEntity<ContasPagar> salvarContaPagar(@RequestBody @Valid ContasPagar contaPagar) throws ExceptionMentoriaJava {

        if (contaPagar.getId() == null) {
            List<ContasPagar> produtos = contasPagarRepository.buscarContaDesc(contaPagar.getDescricao().toUpperCase());
            if (!produtos.isEmpty()) {
                throw new ExceptionMentoriaJava("Já existe marca com essa descriçao " + contaPagar.getDescricao());
            }
        }


        ContasPagar contaSalva = contasPagarRepository.save(contaPagar);
        return new ResponseEntity<ContasPagar>(contaSalva, HttpStatus.OK);


    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteContaPagar")
    public ResponseEntity<?> deleteMarca(@RequestBody ContasPagar contaPagar) {

        contasPagarRepository.deleteById(contaPagar.getId());
        return new ResponseEntity("Conta Removida", HttpStatus.OK);

    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteContaPagarId/{id}")
    public ResponseEntity<?> deleteContaPagarId(@PathVariable("id") Long id) {

        contasPagarRepository.deleteById(id);
        return new ResponseEntity("Conta Removida", HttpStatus.OK);


    }

    @ResponseBody
    @GetMapping(value = "**/obterContaPagar/{id}")
    public ResponseEntity<ContasPagar> obterContaPagar(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        ContasPagar contaPagar = contasPagarRepository.findById(id).orElse(null);

        if (contaPagar == null) {
            throw new ExceptionMentoriaJava("Não encontrada conta com codigo: " + id);
        }

        return new ResponseEntity<ContasPagar>(contaPagar, HttpStatus.OK);


    }

    @ResponseBody
    @GetMapping(value = "**/buscarDescContaPagar/{desc}")
    public ResponseEntity<List<ContasPagar>> buscarDescContaPagar(@PathVariable("desc") String desc) {

        List<ContasPagar> contas = contasPagarRepository.buscarContaDesc(desc.toUpperCase());
        return new ResponseEntity<List<ContasPagar>>(contas, HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping(value = "**/buscarEmpresaContaPagar/{idEmpresa}")
    public ResponseEntity<List<ContasPagar>> buscarEmpresaContaPagar(@PathVariable("idEmpresa") Long idEmpresa) {

        List<ContasPagar> contas = contasPagarRepository.buscarContaEmpresa(idEmpresa);
        return new ResponseEntity<List<ContasPagar>>(contas, HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping(value = "**/buscarFornecedorContaPagar/{idFornecedor}")
    public ResponseEntity<List<ContasPagar>> buscarFornecedorContaPagar(@PathVariable("idFornecedor") Long idFornecedor) {

        List<ContasPagar> contas = contasPagarRepository.buscarContaFornecedor(idFornecedor);
        return new ResponseEntity<List<ContasPagar>>(contas, HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping(value = "**/buscarPessoaContaPagar/{idPessoa}")
    public ResponseEntity<List<ContasPagar>> buscarPessoaContaPagar(@PathVariable("idPessoa") Long idPessoa) {

        List<ContasPagar> contas = contasPagarRepository.buscarContaPessoa(idPessoa);
        return new ResponseEntity<List<ContasPagar>>(contas, HttpStatus.OK);

    }
}
