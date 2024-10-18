package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.MarcaProduto;
import jd.dev.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RestController
public class MarcaProdutoController {

    @Autowired
    private MarcaRepository marcaRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarMarca")
    public ResponseEntity<MarcaProduto> salvarMarca(@RequestBody @Valid MarcaProduto marcaProduto) throws ExceptionMentoriaJava {

        if (marcaProduto.getId() == null) {
            List<MarcaProduto> produtos = marcaRepository.buscarMarcaProduto(marcaProduto.getNomeDesc().toUpperCase());
            if (!produtos.isEmpty()) {
                throw new ExceptionMentoriaJava("Já existe marca com a nome " + marcaProduto.getNomeDesc());
            }
        }

        MarcaProduto marcaSalva = marcaRepository.save(marcaProduto);
        return new ResponseEntity<MarcaProduto>(marcaSalva, HttpStatus.OK);


    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteMarca")
    public ResponseEntity<?> deleteMarca(@RequestBody MarcaProduto marcaProduto) {

        marcaRepository.deleteById(marcaProduto.getId());
        return new ResponseEntity("Marca Removida", HttpStatus.OK);

    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteMarcaId/{id}")
    public ResponseEntity<?> deleteMarcaId(@PathVariable("id") Long id) {

        marcaRepository.deleteById(id);
        return new ResponseEntity("Marca Removida", HttpStatus.OK);


    }

    @ResponseBody
    @GetMapping(value = "**/obterMarca/{id}")
    public ResponseEntity<MarcaProduto> obterMarca(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        MarcaProduto marcaProduto = marcaRepository.findById(id).orElse(null);

        if (marcaProduto == null) {
            throw new ExceptionMentoriaJava("Não encontrada marca com codigo: " + id);
        }

        return new ResponseEntity<MarcaProduto>(marcaProduto, HttpStatus.OK);


    }

    @ResponseBody
    @GetMapping(value = "**/buscarDescMarca/{desc}")
    public ResponseEntity<List<MarcaProduto>> buscarDescMarca(@PathVariable("desc") String nomeDescr) {

        List<MarcaProduto> produtos = marcaRepository.buscarMarcaProduto(nomeDescr.toUpperCase());
        return new ResponseEntity<List<MarcaProduto>>(produtos, HttpStatus.OK);

    }

}
