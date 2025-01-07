package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.AvaliacaoProduto;
import jd.dev.model.dto.AvalicaoProdutoDto;
import jd.dev.repository.AvaliacaoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AvaliacaoProdutoController {
    @Autowired
    private AvaliacaoProdutoRepository avaliacaoProdutoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarAvaliacaoProduto")
    public ResponseEntity<AvaliacaoProduto> salvarAvaliacaoProduto(@RequestBody @Valid AvaliacaoProduto avaliacaoProduto) throws ExceptionMentoriaJava {
        if (avaliacaoProduto.getEmpresa() == null || (avaliacaoProduto.getEmpresa() != null & avaliacaoProduto.getEmpresa().getId() <= 0)) {
            throw new ExceptionMentoriaJava("Informa a empresa dona do registro");
        }
        if (avaliacaoProduto.getProduto() == null || (avaliacaoProduto.getProduto() != null & avaliacaoProduto.getProduto().getId() <= 0)) {
            throw new ExceptionMentoriaJava("A avaliacao deve conter o produto associado");
        }
        if (avaliacaoProduto.getPessoa() == null || (avaliacaoProduto.getPessoa() != null & avaliacaoProduto.getPessoa().getId() <= 0)) {
            throw new ExceptionMentoriaJava("A avaliacao deve conter um cliente associado");
        }

        avaliacaoProduto = avaliacaoProdutoRepository.saveAndFlush(avaliacaoProduto);
        return new ResponseEntity<>(avaliacaoProduto, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteAvaliacaoProduto/{idAvaliacao}")
    public ResponseEntity<?> deleteAvaliacaoProduto (@PathVariable("idAvaliacao") Long idAvaliacao) {
        avaliacaoProdutoRepository.deleteById(idAvaliacao);
        return new ResponseEntity<>("Avaliacao removida", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/avalicaoProduto/{idProduto}")
    public ResponseEntity<List<AvalicaoProdutoDto>> avaliacaoProduto(@PathVariable("idProduto") Long idProduto) {
        List<AvalicaoProdutoDto> dtos = new ArrayList<AvalicaoProdutoDto>();
        List <AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoproduto(idProduto);

        return getListResponseEntity(dtos, avaliacaoProdutos);
    }

    @ResponseBody
    @GetMapping(value = "**/avalicaoProdutoPessoa/{idProduto}/{idPessoa}")
    public ResponseEntity<List<AvalicaoProdutoDto>> avalicaoProdutoPessoa(@PathVariable("idProduto") Long idProduto, @PathVariable("idPessoa") Long idPessoa) {
        List<AvalicaoProdutoDto> dtos = new ArrayList<AvalicaoProdutoDto>();
        List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoprodutoPessoa(idProduto, idPessoa);
        return getListResponseEntity(dtos, avaliacaoProdutos);
    }

    @ResponseBody
    @GetMapping(value = "**/avalicaoPessoa/{idPessoa}")
    public ResponseEntity<List<AvalicaoProdutoDto>> avalicaoPessoa(@PathVariable("idPessoa") Long idPessoa) {
        List<AvalicaoProdutoDto> dtos = new ArrayList<AvalicaoProdutoDto>();
        List <AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.avaliacaoPessoa(idPessoa);

        return getListResponseEntity(dtos, avaliacaoProdutos);
    }

    private ResponseEntity<List<AvalicaoProdutoDto>> getListResponseEntity(List<AvalicaoProdutoDto> dtos, List<AvaliacaoProduto> avaliacaoProdutos) {
        for (AvaliacaoProduto avaliacaoProduto : avaliacaoProdutos) {
            AvalicaoProdutoDto avalicaoProdutoDto = new AvalicaoProdutoDto();
            avalicaoProdutoDto.setId(avaliacaoProduto.getId());
            avalicaoProdutoDto.setDescricao(avaliacaoProduto.getDescricao());
            avalicaoProdutoDto.setEmpresa(avaliacaoProduto.getEmpresa().getId());
            avalicaoProdutoDto.setProduto(avaliacaoProduto.getProduto().getId());
            avalicaoProdutoDto.setPessoa(avaliacaoProduto.getPessoa().getId());
            avalicaoProdutoDto.setNota(avaliacaoProduto.getNota());

            dtos.add(avalicaoProdutoDto);
        }
        return new ResponseEntity<List<AvalicaoProdutoDto>>(dtos, HttpStatus.OK);
    }

}
