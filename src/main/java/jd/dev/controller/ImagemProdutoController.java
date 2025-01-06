package jd.dev.controller;

import jd.dev.model.ImagemProduto;
import jd.dev.model.dto.ImagemProtudoDTO;
import jd.dev.repository.ImagemProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ImagemProdutoController {
    @Autowired
    private ImagemProdutoRepository imagemProdutoRepository;
    @ResponseBody
    @PostMapping(value = "**/salvarImagemProduto")
    public ResponseEntity<ImagemProtudoDTO> salvarImagemProduto(@RequestBody ImagemProduto imagemProduto) {
        imagemProduto = imagemProdutoRepository.saveAndFlush(imagemProduto);

        ImagemProtudoDTO imagemProtudoDTO = new ImagemProtudoDTO();
        imagemProtudoDTO.setId(imagemProduto.getId());
        imagemProtudoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
        imagemProtudoDTO.setProduto(imagemProduto.getProduto().getId());
        imagemProtudoDTO.setImagenMiniatura(imagemProduto.getImagenMiniatura());
        imagemProtudoDTO.setImagenOriginal(imagemProduto.getImagenOriginal());

        return new ResponseEntity<ImagemProtudoDTO>(imagemProtudoDTO, HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteTodasImagensProduto/{idProduto}")
    public ResponseEntity<?> deleteTodasImagensProduto(@PathVariable("idProduto") Long idProduto) {
        imagemProdutoRepository.deleteImagens(idProduto);
        return new ResponseEntity<>("Imagems de produto Excluida" , HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteImagemObjeto")
    public ResponseEntity<?> deleteImagemObjeto(@RequestBody ImagemProduto imagemProduto) {
        if (!imagemProdutoRepository.existsById(imagemProduto.getId())) {
            return new ResponseEntity<>("Imagen ja foi removida " + imagemProduto.getId(), HttpStatus.OK);
        }
        imagemProdutoRepository.deleteById(imagemProduto.getId());
        return new ResponseEntity<>("Imagem Excluida" , HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteImagemPorProduto/{id}")
    public ResponseEntity<?> deleteImagemProdutoPorId(@PathVariable("id") Long id) {
        imagemProdutoRepository.deleteById(id);
        return new ResponseEntity<>("Imagem Excluida" , HttpStatus.OK);
    }
    @ResponseBody
    @GetMapping(value =  "**/obterImagemPorProduto/{idProduto}")
    public ResponseEntity<List<ImagemProtudoDTO>> obterImagemPorProduto(@PathVariable("idProduto") Long idProduto) {
        List<ImagemProtudoDTO> dtos = new ArrayList<ImagemProtudoDTO>();
        List <ImagemProduto> imagemProdutos = imagemProdutoRepository.buscarImagemProduto(idProduto);

        for (ImagemProduto imagemProduto : imagemProdutos) {
            ImagemProtudoDTO imagemProtudoDTO = new ImagemProtudoDTO();
            imagemProtudoDTO.setId(imagemProduto.getId());
            imagemProtudoDTO.setEmpresa(imagemProduto.getEmpresa().getId());
            imagemProtudoDTO.setProduto(imagemProduto.getProduto().getId());
            imagemProtudoDTO.setImagenOriginal(imagemProduto.getImagenOriginal());
            imagemProtudoDTO.setImagenMiniatura(imagemProduto.getImagenMiniatura());

            dtos.add(imagemProtudoDTO);
        }

        return new ResponseEntity<List<ImagemProtudoDTO>>(dtos, HttpStatus.OK);
    }
}
