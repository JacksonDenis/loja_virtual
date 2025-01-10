package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.Endereco;
import jd.dev.model.ItemVendaLoja;
import jd.dev.model.PessoaFisica;
import jd.dev.model.VendaCompraLojaVirtual;
import jd.dev.model.dto.ItemvendaDTO;
import jd.dev.model.dto.VendaCompraLojaDto;
import jd.dev.repository.EnderecoRepository;
import jd.dev.repository.NotaFiscaldeVendaRepository;
import jd.dev.repository.VendaCompraLojaVirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class VendaCompraLojaVirtController {
    @Autowired
    private VendaCompraLojaVirtRepository vendaCompraLojaVirtRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private NotaFiscaldeVendaRepository notaFiscaldeVendaRepository;
    @Autowired
    private PessoaController pessoaController;

    @ResponseBody
    @PostMapping(value = "**/salvarVendaLoja")
    public ResponseEntity<VendaCompraLojaDto> salvarVendaLoja (@RequestBody @Valid  VendaCompraLojaVirtual vendaCompraLojaVirtual) throws ExceptionMentoriaJava {
        VendaCompraLojaDto vendaCompraLojaVirtualdto = new VendaCompraLojaDto();

        vendaCompraLojaVirtual.getPessoa().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        PessoaFisica pessoaFisica = pessoaController.salvarPessoaFisica(vendaCompraLojaVirtual.getPessoa()).getBody();
        vendaCompraLojaVirtual.setPessoa(pessoaFisica);

        vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        Endereco enderecoEntrega = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());
        vendaCompraLojaVirtual.setEnderecoEntrega(enderecoEntrega);

        vendaCompraLojaVirtual.getEndereCobranca().setPessoa(pessoaFisica);
        vendaCompraLojaVirtual.getEndereCobranca().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        Endereco enderecoCobranca = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());
        vendaCompraLojaVirtual.setEndereCobranca(enderecoCobranca);

        for (int i = 0; i < vendaCompraLojaVirtual.getItemVendaLojas().size(); i++) {
            vendaCompraLojaVirtual.getItemVendaLojas().get(i).setEmpresa(vendaCompraLojaVirtual.getEmpresa());
            vendaCompraLojaVirtual.getItemVendaLojas().get(i).setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        }


        vendaCompraLojaVirtual = vendaCompraLojaVirtRepository.saveAndFlush(vendaCompraLojaVirtual);
        vendaCompraLojaVirtual.getNotaFiscalVenda().setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        notaFiscaldeVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());

        return getVendaCompraLojaDtoResponseEntity(vendaCompraLojaVirtual, vendaCompraLojaVirtualdto);
    }

    @ResponseBody
    @GetMapping(value = "**/obterVenda/{id}")
    public ResponseEntity<VendaCompraLojaDto> obterVenda(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        VendaCompraLojaVirtual vendaCompraLoja = vendaCompraLojaVirtRepository.findById(id).orElse(null);

        VendaCompraLojaDto vendaCompraLojaVirtualdto = new VendaCompraLojaDto();

        return getVendaCompraLojaDtoResponseEntity(vendaCompraLoja, vendaCompraLojaVirtualdto);
    }

    private ResponseEntity<VendaCompraLojaDto> getVendaCompraLojaDtoResponseEntity(@RequestBody @Valid VendaCompraLojaVirtual vendaCompraLojaVirtual, VendaCompraLojaDto vendaCompraLojaVirtualdto) {
        vendaCompraLojaVirtualdto.setNotaFiscalVenda(vendaCompraLojaVirtual.getNotaFiscalVenda().getNumero());
        vendaCompraLojaVirtualdto.setId(vendaCompraLojaVirtual.getId());
        vendaCompraLojaVirtualdto.setFormaPagamento(vendaCompraLojaVirtual.getFormaPagamento().getDescricao());
        vendaCompraLojaVirtualdto.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
        vendaCompraLojaVirtualdto.setPessoa(vendaCompraLojaVirtual.getPessoa().getNome());
        vendaCompraLojaVirtualdto.setEnderecoEntrega(vendaCompraLojaVirtual.getEnderecoEntrega().getId());

        for (ItemVendaLoja item: vendaCompraLojaVirtual.getItemVendaLojas()) {
            ItemvendaDTO itemvendaDTO = new ItemvendaDTO();
            itemvendaDTO.setQuantidade(item.getQuantidade());
            itemvendaDTO.setProduto(item.getProduto().getId());

            vendaCompraLojaVirtualdto.getItemvendaLoja().add(itemvendaDTO);
        }

        return new ResponseEntity<VendaCompraLojaDto>(vendaCompraLojaVirtualdto, HttpStatus.OK);
    }


}
