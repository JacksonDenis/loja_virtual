package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.NotaItemProduto;
import jd.dev.repository.NotaItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Controller
public class NotaItemController {
    @Autowired
    NotaItemRepository notaItemRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarNotaItemProduto")
    public ResponseEntity<NotaItemProduto>
    salvarNotaItemProduto(@RequestBody @Valid NotaItemProduto notaItemProduto)
            throws ExceptionMentoriaJava {


        if (notaItemProduto.getId() == null) {

            if (notaItemProduto.getProduto() == null || notaItemProduto.getProduto().getId() <= 0) {
                throw new ExceptionMentoriaJava("O produto deve ser informado.");
            }


            if (notaItemProduto.getNotaFiscalCompra() == null || notaItemProduto.getNotaFiscalCompra().getId() <= 0) {
                throw new ExceptionMentoriaJava("A nota fisca deve ser informada.");
            }


            if (notaItemProduto.getEmpresa() == null || notaItemProduto.getEmpresa().getId() <= 0) {
                throw new ExceptionMentoriaJava("A empresa deve ser informada.");
            }

            List<NotaItemProduto> notaExistente = notaItemRepository.
                    buscaNotaItemPorProdutoNota(notaItemProduto.getProduto().getId(),
                            notaItemProduto.getNotaFiscalCompra().getId());

            if (!notaExistente.isEmpty()) {
                throw new ExceptionMentoriaJava("Já existe este produto cadastrado para esta nota.");
            }

        }

        if (notaItemProduto.getQuantidade() <=0) {
            throw new ExceptionMentoriaJava("A quantidade do produto deve ser informada.");
        }


        NotaItemProduto notaItemSalva = notaItemRepository.save(notaItemProduto);

        notaItemSalva = notaItemRepository.findById(notaItemProduto.getId()).get();

        return new ResponseEntity<NotaItemProduto>(notaItemSalva, HttpStatus.OK);


    }



    @ResponseBody
    @DeleteMapping(value = "**/deleteNotaItemPorId/{id}")
    public ResponseEntity<?> deleteNotaItemPorId(@PathVariable("id") Long id) {


        notaItemRepository.deleteByIdNotaItem(id);

        return new ResponseEntity("Nota Item Produto Removido",HttpStatus.OK);
    }



}
