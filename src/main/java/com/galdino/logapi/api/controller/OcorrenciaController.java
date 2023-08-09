package com.galdino.logapi.api.controller;

import com.galdino.logapi.api.OcorrenciaAssembler;
import com.galdino.logapi.api.model.OcorrenciaModel;
import com.galdino.logapi.api.model.input.OcorrenciaInput;
import com.galdino.logapi.domain.model.Entrega;
import com.galdino.logapi.domain.model.Ocorrencia;
import com.galdino.logapi.domain.service.BuscaEntregaService;
import com.galdino.logapi.domain.service.RegistroOcorrenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas/{entregaId}/ocorrencias")
public class OcorrenciaController {
    private RegistroOcorrenciaService registroOcorrenciaService;
    private OcorrenciaAssembler ocorrenciaAssembler;
    private BuscaEntregaService buscaEntregaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaModel registrar(@PathVariable Long entregaId, @Valid @RequestBody OcorrenciaInput ocorrenciaInput) {
        Ocorrencia ocorrencia = registroOcorrenciaService.registrar(entregaId, ocorrenciaInput.getDescricao());

        return ocorrenciaAssembler.toModel(ocorrencia);
    }

    @GetMapping
    public List<OcorrenciaModel> listar(@PathVariable Long entregaId) {
        Entrega entrega = buscaEntregaService.buscar(entregaId);

        return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
    }
}
