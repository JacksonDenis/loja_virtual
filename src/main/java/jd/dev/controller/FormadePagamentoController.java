package jd.dev.controller;

import jd.dev.model.FormaPagamento;
import jd.dev.repository.FormadePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FormadePagamentoController {
    @Autowired
    private FormadePagamentoRepository formadePagamentoRepository;
    @ResponseBody
    @PostMapping(value = "**/salvarFormaPagamento")
    public ResponseEntity<FormaPagamento> salvarFormaPagamento (@RequestBody @Valid FormaPagamento formaPagamento) {
        formaPagamento=formadePagamentoRepository.save(formaPagamento);
        return new ResponseEntity<FormaPagamento>(formaPagamento, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteFormaPagamento")
    public ResponseEntity<?> salvarFormaPagamento (@RequestBody @Valid Long id) {
      formadePagamentoRepository.deleteById(id);
        return new ResponseEntity<>("Deletado com sucesso", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listarFormaDePagamento")
    public ResponseEntity<List<FormaPagamento>> listarFormaDePagamento () {
        List<FormaPagamento> formaPagamentos = formadePagamentoRepository.findAll();
        return new ResponseEntity<List<FormaPagamento>>(formaPagamentos, HttpStatus.OK);
    }

}
