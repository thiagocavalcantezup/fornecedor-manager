package br.com.zup.edu.fornecedormanager.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.fornecedormanager.model.GrupoDeFornecedores;
import br.com.zup.edu.fornecedormanager.model.GrupoDeFornecedoresDTO;
import br.com.zup.edu.fornecedormanager.repository.FornecedorRepository;
import br.com.zup.edu.fornecedormanager.repository.GrupoDeFornecedoresRepository;

@RestController
@RequestMapping(GrupoDeFornecedoresController.BASE_URI)
public class GrupoDeFornecedoresController {

    public final static String BASE_URI = "/grupos-de-fornecedores";

    private final GrupoDeFornecedoresRepository gdfRepository;
    private final FornecedorRepository fornecedorRepository;

    public GrupoDeFornecedoresController(GrupoDeFornecedoresRepository gdfRepository,
                                         FornecedorRepository fornecedorRepository) {
        this.gdfRepository = gdfRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid GrupoDeFornecedoresDTO gdfDTO,
                                       UriComponentsBuilder ucb) {
        GrupoDeFornecedores gdf = gdfRepository.save(gdfDTO.toModel(fornecedorRepository));

        URI location = ucb.path(BASE_URI + "/{id}").buildAndExpand(gdf.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        GrupoDeFornecedores gdf = gdfRepository.findById(id)
                                               .orElseThrow(
                                                   () -> new ResponseStatusException(
                                                       HttpStatus.NOT_FOUND,
                                                       "Não existe um grupo de fornecedores com o "
                                                               + "id informado."
                                                   )
                                               );

        gdfRepository.delete(gdf);

        return ResponseEntity.noContent().build();

    }

}
