package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.Produto;
import jd.dev.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarProduto")
    public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava {
        if (produto.getId() == null) {
            List<Produto> produtos = produtoRepository.buscarProdutoNome(produto.getNome().toUpperCase());
            if (!produtos.isEmpty()) {
                throw new ExceptionMentoriaJava("Ja existe acesso com o nome: " + produto.getNome());
            }
        }

        if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0 ) {
            throw new ExceptionMentoriaJava("Empresa responsavel deve ser informada");
        }
        if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0 ) {
            throw new ExceptionMentoriaJava("Categoria Produto deve ser informada");
        }
        if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0 ) {
            throw new ExceptionMentoriaJava("Marca deve ser informada");
        }


        Produto produtoSalvo = produtoRepository.save(produto);
        return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteProduto")
    public ResponseEntity<?> deleteProduto(@RequestBody Produto produto) {

        produtoRepository.deleteById(produto.getId());
        return new ResponseEntity("Produto Removido", HttpStatus.OK);

    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteProdutoId/{id}")
    public ResponseEntity<?> deleteProdutoId(@PathVariable("id") Long id) {

        produtoRepository.deleteById(id);
        return new ResponseEntity("Produto Removido", HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping(value = "**/obterProduto/{id}")
    public ResponseEntity<Produto> obterProduto(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        Produto produto = produtoRepository.findById(id).orElse(null);

        if (produto == null) {
            throw new ExceptionMentoriaJava("Não encontro produto com codigo: " + id);
        }

        return new ResponseEntity<Produto>(produto, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarProdutoNome/{nome}")
    public ResponseEntity<List<Produto>>buscarProdutoNome(@PathVariable("nome") String desc) {

        List<Produto> produtos = produtoRepository.buscarProdutoNome(desc);
        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);

    }
}
