package jd.dev.controller;

import jd.dev.ExceptionMentoriaJava;
import jd.dev.model.*;
import jd.dev.model.dto.ItemvendaDTO;
import jd.dev.model.dto.VendaCompraLojaDto;
import jd.dev.repository.EnderecoRepository;
import jd.dev.repository.NotaFiscaldeVendaRepository;
import jd.dev.repository.StatusRastreioRepository;
import jd.dev.repository.VendaCompraLojaVirtRepository;
import jd.dev.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class VendaCompraLojaVirtController {
    @Autowired
    private VendaCompraLojaVirtRepository vendaCompraLojaVirtRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private NotaFiscaldeVendaRepository notaFiscaldeVendaRepository;
    @Autowired
    private StatusRastreioRepository statusRastreioRepository;
    @Autowired
    private PessoaController pessoaController;
    @Autowired
    private VendaService vendaService;

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
        StatusRastreio statusRastreio = new StatusRastreio();
        statusRastreio.setCentroDistribuicao("Centro de transporte");
        statusRastreio.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
        statusRastreio.setCidade("local");
        statusRastreio.setEstado("local");
        statusRastreio.setStatus("Inicio compra");
        statusRastreio.setVendaCompraLojaVirtual(vendaCompraLojaVirtual);
        statusRastreioRepository.save(statusRastreio);

        vendaCompraLojaVirtual.getNotaFiscalVenda().setVendaCompraLojaVirtual(vendaCompraLojaVirtual);

        notaFiscaldeVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());

        return getVendaCompraLojaDtoResponseEntity(vendaCompraLojaVirtual, vendaCompraLojaVirtualdto);
    }

    @ResponseBody
    @GetMapping(value = "**/obterVenda/{id}")
    public ResponseEntity<VendaCompraLojaDto> obterVenda(@PathVariable("id") Long id) throws ExceptionMentoriaJava {

        VendaCompraLojaVirtual vendaCompraLoja = vendaCompraLojaVirtRepository.findByIdExclusao(id);

        if (vendaCompraLoja == null) {
            vendaCompraLoja = new VendaCompraLojaVirtual();
            VendaCompraLojaDto vendaCompraLojaVirtualdto = new VendaCompraLojaDto();
            return new ResponseEntity<VendaCompraLojaDto>(vendaCompraLojaVirtualdto, HttpStatus.OK);

        }

        VendaCompraLojaDto vendaCompraLojaVirtualdto = new VendaCompraLojaDto();

        return getVendaCompraLojaDtoResponseEntity(vendaCompraLoja, vendaCompraLojaVirtualdto);
    }

    @ResponseBody
    @GetMapping(value = "**/obterVendaPorProduto/{idProd}")
    public ResponseEntity<List<VendaCompraLojaDto>> obterVendaPorProduto(@PathVariable("idProd") Long idProd) throws ExceptionMentoriaJava {

        List<VendaCompraLojaVirtual> vendaCompraLoja = vendaCompraLojaVirtRepository.vendaPorProduto(idProd);

        if (vendaCompraLoja == null) {
            vendaCompraLoja = new ArrayList<VendaCompraLojaVirtual>();
        }

        return getListResponseEntity(vendaCompraLoja);
    }

    @ResponseBody
    @GetMapping(value = "**/consultaVendaDinamica/{valor}/{tipoconsulta}")
    public ResponseEntity<List<VendaCompraLojaDto>> consultaVendaDinamica(@PathVariable("valor") String valor, @PathVariable("tipoconsulta") String tipoconsulta) throws ExceptionMentoriaJava {

        List<VendaCompraLojaVirtual> vendaCompraLoja = null;

        if (tipoconsulta.equalsIgnoreCase("POR_ID_PROD")) {
            vendaCompraLoja =   vendaCompraLojaVirtRepository.vendaPorProduto(Long.parseLong(valor));

        }else if (tipoconsulta.equalsIgnoreCase("POR_NOME_PROD")) {
            vendaCompraLoja = vendaCompraLojaVirtRepository.vendaPorNomeProduto(valor.toUpperCase().trim());
        }
        else if (tipoconsulta.equalsIgnoreCase("POR_NOME_CLIENTE")) {
            vendaCompraLoja = vendaCompraLojaVirtRepository.vendaPorNomeCliente(valor.toUpperCase().trim());
        }
        else if (tipoconsulta.equalsIgnoreCase("POR_ENDERECO_COBRANCA")) {
            vendaCompraLoja = vendaCompraLojaVirtRepository.vendaPorEndereCobranca(valor.toUpperCase().trim());
        }
        else if (tipoconsulta.equalsIgnoreCase("POR_ENDERECO_ENTREGA")) {
            vendaCompraLoja = vendaCompraLojaVirtRepository.vendaPorEnderecoEntrega(valor.toUpperCase().trim());
        }

        return getListResponseEntity(vendaCompraLoja);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteVEndaTotalBanco/{idVenda}")
    public ResponseEntity<String> deleteVEndaTotalBanco(@PathVariable(value = "idVenda") Long idVenda) {
        vendaService.exclusaoTotalVendaBanco(idVenda);
        return new ResponseEntity<String>("Deletado com sucesso", HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "**/deleteVirtualVEndaTotalBanco/{idVenda}")
    public ResponseEntity<String> deleteVirtualVEndaTotalBanco(@PathVariable(value = "idVenda") Long idVenda) {
        vendaService.exclusaoTotalVendaSemApagar(idVenda);
        return new ResponseEntity<String>("Deletado virtualmente com sucesso", HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "**/ativarVirtualVEndaTotalBanco/{idVenda}")
    public ResponseEntity<String> ativarVirtualVEndaTotalBanco(@PathVariable(value = "idVenda") Long idVenda) {
        vendaService.ativarTotalVendaSemApagar(idVenda);
        return new ResponseEntity<String>("ativado virtualmente com sucesso", HttpStatus.OK);
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


    private ResponseEntity<List<VendaCompraLojaDto>> getListResponseEntity(List<VendaCompraLojaVirtual> vendaCompraLoja) {
        List<VendaCompraLojaDto> vendaCompraLojaDtoList = new ArrayList<VendaCompraLojaDto>();

        for (VendaCompraLojaVirtual vlc : vendaCompraLoja) {
            VendaCompraLojaDto vendaCompraLojaDto = new VendaCompraLojaDto();

            vendaCompraLojaDto.setValorTotal(vlc.getValorTotal());
            vendaCompraLojaDto.setPessoa(vlc.getPessoa().getNome());
            vendaCompraLojaDto.setNotaFiscalVenda(vlc.getNotaFiscalVenda().getNumero());
            vendaCompraLojaDto.setId(vlc.getId());
            vendaCompraLojaDto.setFormaPagamento(vlc.getFormaPagamento().getDescricao());

            for (ItemVendaLoja item: vlc.getItemVendaLojas()) {
                ItemvendaDTO itemvendaDTO = new ItemvendaDTO();
                itemvendaDTO.setQuantidade(item.getQuantidade());
                itemvendaDTO.setProduto(item.getProduto().getId());

                vendaCompraLojaDto.getItemvendaLoja().add(itemvendaDTO);
            }

            vendaCompraLojaDtoList.add(vendaCompraLojaDto);


        }

        return new ResponseEntity<List<VendaCompraLojaDto>>(vendaCompraLojaDtoList, HttpStatus.OK);
    }


}
