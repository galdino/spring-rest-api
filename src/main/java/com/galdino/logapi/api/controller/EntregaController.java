package com.galdino.logapi.api.controller;

import com.galdino.logapi.api.EntregaAssembler;
import com.galdino.logapi.api.model.DestinatarioModel;
import com.galdino.logapi.api.model.EntregaModel;
import com.galdino.logapi.api.model.input.EntregaInput;
import com.galdino.logapi.domain.model.Entrega;
import com.galdino.logapi.domain.repository.EntregaRepository;
import com.galdino.logapi.domain.service.FinalizacaoEntregaService;
import com.galdino.logapi.domain.service.SolicitacaoEntregaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
    private SolicitacaoEntregaService solicitacaoEntregaService;
    private EntregaRepository entregaRepository;
    private EntregaAssembler entregaAssembler;
    private FinalizacaoEntregaService finalizacaoEntregaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
        Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
        return entregaAssembler.toModel(solicitacaoEntregaService.solicitar(novaEntrega));
    }

    @GetMapping
    public List<EntregaModel> listar() {
        return entregaAssembler.toCollectionModel(entregaRepository.findAll());
    }

    @GetMapping("/{entregaId}")
    public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId) {
        return entregaRepository.findById(entregaId)
//                                .map(entrega -> ResponseEntity.ok(entregaModelBuilder(entrega)))
//                                .map(entrega -> {
//                                    EntregaModel entregaModel = modelMapper.map(entrega, EntregaModel.class);
//                                    return ResponseEntity.ok(entregaModel);
//                                })
                                .map(entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega)))
                                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{entregaId}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long entregaId) {
        finalizacaoEntregaService.finalizar(entregaId);
    }

//    private EntregaModel entregaModelBuilder(Entrega entrega) {
//        EntregaModel entregaModel = new EntregaModel();
//        entregaModel.setId(entrega.getId());
//        entregaModel.setNomeCliente(entrega.getCliente().getNome());
//        entregaModel.setDestinatario(new DestinatarioModel());
//        entregaModel.getDestinatario().setNome(entrega.getDestinatario().getNome());
//        entregaModel.getDestinatario().setLogradouro(entrega.getDestinatario().getLogradouro());
//        entregaModel.getDestinatario().setNumero(entrega.getDestinatario().getNumero());
//        entregaModel.getDestinatario().setComplemento(entrega.getDestinatario().getComplemento());
//        entregaModel.getDestinatario().setBairro(entrega.getDestinatario().getBairro());
//        entregaModel.setTaxa(entrega.getTaxa());
//        entregaModel.setStatus(entrega.getStatus());
//        entregaModel.setDataPedido(entrega.getDataPedido());
//        entregaModel.setDataFinalizacao(entrega.getDataFinalizacao());
//
//        return entregaModel;
//    }
}
