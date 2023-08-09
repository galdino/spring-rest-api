package com.galdino.logapi.api.controller;

import com.galdino.logapi.domain.model.Cliente;
import com.galdino.logapi.domain.repository.ClienteRepository;
import com.galdino.logapi.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

//    @PersistenceContext
//    private EntityManager entityManager;

    private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;

    @GetMapping
    public List<Cliente> listar(){
//        var cliente1 = new Cliente();
//        cliente1.setId(1L);
//        cliente1.setNome("João3");
//        cliente1.setTelefone("1234567");
//        cliente1.setEmail("joao@joao");
//
//        var cliente2 = new Cliente();
//        cliente2.setId(2L);
//        cliente2.setNome("Maria");
//        cliente2.setTelefone("1234567");
//        cliente2.setEmail("maria@maria");
//
//        return Arrays.asList(cliente1, cliente2);
//        return entityManager.createQuery("SELECT c from Cliente c", Cliente.class).getResultList();
        return clienteRepository.findAll();
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId){
//        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        return clienteRepository.findById(clienteId)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
//        if(cliente.isEmpty()){
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(cliente.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        return catalogoClienteService.salvar(cliente);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){
        if(!clienteRepository.existsById(clienteId)){
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteId);
        cliente = catalogoClienteService.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId){
        if(!clienteRepository.existsById(clienteId)){
            return ResponseEntity.notFound().build();
        }

        catalogoClienteService.excluir(clienteId);
        return ResponseEntity.noContent().build();
    }
}
