package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.CategoriaProduto;
import jd.dev.model.dto.CategoriaProdutoDto;
import jd.dev.repository.CategoriaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriaProdutoController {
    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarCategoria")
    public ResponseEntity<CategoriaProdutoDto> salvarCategoria (@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava {
        if (categoriaProduto.getEmpresa() == null || (categoriaProduto.getEmpresa().getId() == null  )){
            throw new ExceptionMentoriaJava("A empresa deve ser informada");
        }
        if (categoriaProduto.getId()==null && categoriaProdutoRepository.existeCategoria(categoriaProduto.getNomeDesc().toUpperCase())) {
            throw new ExceptionMentoriaJava("Não pode cadastrar categoria com mesmo nome.");
        }
        CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
        CategoriaProdutoDto categoriaProdutoDto = new CategoriaProdutoDto();
        //gerar testes

        //mapper
        categoriaProdutoDto.setId(categoriaSalva.getId());
        categoriaProdutoDto.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
        categoriaProdutoDto.setNomeDesc(categoriaSalva.getNomeDesc());

        return new ResponseEntity<CategoriaProdutoDto>(categoriaProdutoDto, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteCategoria")
    public ResponseEntity<?> deleteCategoria(@RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoriaJava {
        if (categoriaProdutoRepository.findById(categoriaProduto.getId()).isEmpty()) {
            throw new ExceptionMentoriaJava("Não existe categoria para este ID");
        }
        categoriaProdutoRepository.deleteById(categoriaProduto.getId());

        return new ResponseEntity<>("Categoria removida", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/consultarCategoria/{desc}")
    public ResponseEntity<List<CategoriaProduto>> consultarCategoriaDescricao(@PathVariable("desc") String desc) {
        List<CategoriaProduto> categoriaProdutos = categoriaProdutoRepository.consultarCategoriaDesc(desc.toUpperCase());

        return new ResponseEntity<List<CategoriaProduto>>(categoriaProdutos, HttpStatus.OK);
    }


}
