package br.com.zup.edu.fornecedormanager.model;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.zup.edu.fornecedormanager.repository.FornecedorRepository;

public class GrupoDeFornecedoresDTO {

    @NotBlank
    private String produto;

    @NotEmpty
    private Set<Long> fornecedorIds;

    public GrupoDeFornecedoresDTO() {}

    public GrupoDeFornecedoresDTO(@NotBlank String produto, @NotEmpty Set<Long> fornecedorIds) {
        this.produto = produto;
        this.fornecedorIds = fornecedorIds;
    }

    public GrupoDeFornecedores toModel(FornecedorRepository fornecedorRepository) {
        GrupoDeFornecedores grupoDeFornecedores = new GrupoDeFornecedores(produto);

        fornecedorIds.stream()
                     .map(
                         id -> fornecedorRepository.findById(id)
                                                   .orElseThrow(
                                                       () -> new ResponseStatusException(
                                                           HttpStatus.NOT_FOUND,
                                                           "Não existe um fornecedor com o id informado."
                                                       )
                                                   )
                     )
                     .forEach(grupoDeFornecedores::adicionar);

        return grupoDeFornecedores;
    }

    public String getProduto() {
        return produto;
    }

    public Set<Long> getFornecedorIds() {
        return fornecedorIds;
    }

}
