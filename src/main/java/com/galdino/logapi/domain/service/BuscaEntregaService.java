package com.galdino.logapi.domain.service;

import com.galdino.logapi.domain.exception.EntidadeNaoEncontradaException;
import com.galdino.logapi.domain.exception.NegocioException;
import com.galdino.logapi.domain.model.Entrega;
import com.galdino.logapi.domain.repository.EntregaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BuscaEntregaService {
    private EntregaRepository entregaRepository;

    public Entrega buscar(Long entregaId) {
        return entregaRepository.findById(entregaId)
                                .orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada"));
    }
}
