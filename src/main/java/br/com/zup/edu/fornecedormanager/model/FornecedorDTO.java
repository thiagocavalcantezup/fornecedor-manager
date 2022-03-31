package br.com.zup.edu.fornecedormanager.model;

import javax.validation.constraints.NotBlank;

public class FornecedorDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String produto;

    @NotBlank
    private String empresa;

    public FornecedorDTO() {}

    public FornecedorDTO(@NotBlank String nome, @NotBlank String produto,
                         @NotBlank String empresa) {
        this.nome = nome;
        this.produto = produto;
        this.empresa = empresa;
    }

    public Fornecedor paraFornecedor() {
        return new Fornecedor(nome, produto, empresa);
    }

    public String getNome() {
        return nome;
    }

    public String getProduto() {
        return produto;
    }

    public String getEmpresa() {
        return empresa;
    }

}
