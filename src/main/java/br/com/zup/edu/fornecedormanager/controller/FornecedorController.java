package br.com.zup.edu.fornecedormanager.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.fornecedormanager.model.Fornecedor;
import br.com.zup.edu.fornecedormanager.model.FornecedorDTO;
import br.com.zup.edu.fornecedormanager.repository.FornecedorRepository;

@RestController
@RequestMapping(FornecedorController.BASE_URI)
public class FornecedorController {

    public final static String BASE_URI = "/fornecedores";

    private final FornecedorRepository fornecedorRepository;

    public FornecedorController(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid FornecedorDTO request,
                                       UriComponentsBuilder uriComponentsBuilder) {
        Fornecedor fornecedor = fornecedorRepository.save(request.toModel());

        URI location = uriComponentsBuilder.path(FornecedorController.BASE_URI + "/{id}")
                                           .buildAndExpand(fornecedor.getId())
                                           .toUri();

        return ResponseEntity.created(location).build();
    }

}
