package br.com.zup.edu.fornecedormanager.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.fornecedormanager.model.Fornecedor;
import br.com.zup.edu.fornecedormanager.model.Telefone;
import br.com.zup.edu.fornecedormanager.model.TelefoneDTO;
import br.com.zup.edu.fornecedormanager.repository.FornecedorRepository;
import br.com.zup.edu.fornecedormanager.repository.TelefoneRepository;

@RestController
@RequestMapping(FornecedorController.BASE_URI + "/{fornecedorId}" + TelefoneController.BASE_URI)
public class TelefoneController {

    public final static String BASE_URI = "/telefones";

    private final FornecedorRepository fornecedorRepository;
    private final TelefoneRepository telefoneRepository;

    public TelefoneController(FornecedorRepository fornecedorRepository,
                              TelefoneRepository telefoneRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.telefoneRepository = telefoneRepository;
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable Long fornecedorId,
                                       @RequestBody @Valid TelefoneDTO telefoneDTO,
                                       UriComponentsBuilder uriComponentsBuilder) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                                                    .orElseThrow(
                                                        () -> new ResponseStatusException(
                                                            HttpStatus.NOT_FOUND,
                                                            "Não existe um fornecedor com o ID informado."
                                                        )
                                                    );
        Telefone telefone = telefoneRepository.save(telefoneDTO.toModel(fornecedor));

        URI location = uriComponentsBuilder.path(
            FornecedorController.BASE_URI + "/{fornecedorId}" + TelefoneController.BASE_URI
                    + "/{id}"
        ).buildAndExpand(fornecedor.getId(), telefone.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
