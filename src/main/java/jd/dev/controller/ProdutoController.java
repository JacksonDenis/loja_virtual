package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.Produto;
import jd.dev.repository.ProdutoRepository;
import jd.dev.service.ServiceSendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    private ServiceSendEmail serviceSendEmail;

    @ResponseBody
    @PostMapping(value = "**/salvarProduto")
    public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoriaJava, MessagingException, IOException {
        if (produto.getTipoUnidade() == null || produto.getTipoUnidade().trim().isEmpty()) {
            throw new ExceptionMentoriaJava("Tipo de unidade dese ser informada");
        }

        if (produto.getNome().length() < 10 ) {
            throw new ExceptionMentoriaJava("Nome do produto deve ter mais de 10 letras.");
        }

        if (produto.getQtdEstoque() < 1) {
            throw new ExceptionMentoriaJava("Deve ter pelo menos 1 produto em estoque");
        }

        if(produto.getImagens() == null || produto.getImagens().isEmpty()) {
            throw new ExceptionMentoriaJava("Deve ter imagem para o produto");
        }

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
        if (produto.getImagens() == null || produto.getImagens().isEmpty()) {
            throw new ExceptionMentoriaJava("Deve ser informada imagens para o produto");
        }
        if (produto.getImagens().size() <3) {
            throw new ExceptionMentoriaJava(("Deve ser informada pelo menos 3 imagens do produto"));
        }
        if (produto.getImagens().size() > 6) {
            throw new ExceptionMentoriaJava(("Tem que ser no maximo 6 imagens"));
        }

        if (produto.getId() == null) {
            for (int x = 0;x < produto.getImagens().size(); x++) {
                produto.getImagens().get(x).setProduto(produto);
                produto.getImagens().get(x).setEmpresa(produto.getEmpresa());

                String base64Image ="";

                if (produto.getImagens().get(x).getImagenOriginal().contains("data:image")) {
                    base64Image = produto.getImagens().get(x).getImagenOriginal().split(",")[1];
                } else {
                    base64Image = produto.getImagens().get(x).getImagenOriginal();
                }
                byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
                if (bufferedImage != null ) {
                    int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
                    int largura = Integer.parseInt(("800"));
                    int altura = Integer.parseInt(("600"));

                    BufferedImage resizedImage = new BufferedImage(largura, altura, type);
                    Graphics2D g = resizedImage.createGraphics();
                    g.drawImage(resizedImage, 0,0, largura, altura, null);
                    g.dispose();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(resizedImage,"png", baos);

                    String miniImgBase64 = "data:image/bng;base64" + DatatypeConverter.printBase64Binary(baos.toByteArray());

                    produto.getImagens().get(x).setImagenMiniatura(miniImgBase64);

                    bufferedImage.flush();
                    resizedImage.flush();
                    baos.flush();
                    baos.close();
                }
            }
        }


        Produto produtoSalvo = produtoRepository.save(produto);

        if (produto.getAlertaQtdEstoque() && produto.getQtdEstoque() <= 1 ) {
            StringBuilder html = new StringBuilder();
            html.append("h2").append("Produto: " + produto.getNome()).append(" com estoque baixo: " + produto.getQtdEstoque());
            html.append("<p> Id prod.:").append(produto.getId()).append("</p>");

            if (produto.getEmpresa().getEmail()!= null ) {
                serviceSendEmail.enviarEmailHmtl("Produto sem estoque" , html.toString(), produto.getEmpresa().getEmail());
            }
        }

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
