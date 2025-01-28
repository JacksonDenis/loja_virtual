package jd.dev.controller;

import jd.dev.model.CupomDesconto;
import jd.dev.repository.CupomDescontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CupomDescontoController {
    @Autowired
    private CupomDescontoRepository cupomDescontoRepository;
    @PostMapping(value = "**/salvarCupomDesconto")
    @ResponseBody
    public ResponseEntity<CupomDesconto> salvarCupomDesconto(@RequestBody CupomDesconto cupomDesconto) {
        cupomDesconto = cupomDescontoRepository.save(cupomDesconto);
        return new ResponseEntity<CupomDesconto>(cupomDesconto, HttpStatus.OK);
    }

    @GetMapping(value = "**/listarCupons")
    @ResponseBody
    public ResponseEntity<List<CupomDesconto>> listarCupons () {
        List<CupomDesconto> cupomDescontos = cupomDescontoRepository.findAll();
        return new ResponseEntity<List<CupomDesconto>>(cupomDescontos, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/listaCupomDescEmpresa/{idEmpresa}")
    public ResponseEntity<List<CupomDesconto>> listaCupomDesc(@PathVariable("idEmpresa") Long idEmpresa){

        return new ResponseEntity<List<CupomDesconto>>(cupomDescontoRepository.cupDescontoPorEmpresa(idEmpresa), HttpStatus.OK);
    }

}
