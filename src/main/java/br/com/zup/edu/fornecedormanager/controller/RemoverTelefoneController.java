package br.com.zup.edu.fornecedormanager.controller;

import br.com.zup.edu.fornecedormanager.model.Fornecedor;
import br.com.zup.edu.fornecedormanager.model.Telefone;
import br.com.zup.edu.fornecedormanager.repository.FornecedorRepository;
import br.com.zup.edu.fornecedormanager.repository.TelefoneRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@RestController
public class RemoverTelefoneController {
    private final TelefoneRepository telefoneRepository;
    private final FornecedorRepository fornecedorRepository;

    public RemoverTelefoneController(TelefoneRepository telefoneRepository, FornecedorRepository fornecedorRepository) {
        this.telefoneRepository = telefoneRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @DeleteMapping("/fornecedores/{idFornecedor}/telefones/{idTelefone}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long idFornecedor, @PathVariable Long idTelefone){

        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fornecedor nao cadastrado"));

        Telefone telefone = telefoneRepository.findById(idTelefone)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Telefone nao cadastrado"));

        if(!telefone.pertence(fornecedor)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Este telefone nao pertence ao fornecedor");
        }

        fornecedor.remover(telefone);
        telefoneRepository.delete(telefone);

        return ResponseEntity.noContent().build();
    }
}
