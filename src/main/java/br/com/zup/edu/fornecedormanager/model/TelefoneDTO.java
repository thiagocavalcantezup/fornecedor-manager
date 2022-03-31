package br.com.zup.edu.fornecedormanager.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TelefoneDTO {

    @NotBlank
    @Size(max = 2)
    private String ddd;

    @NotBlank
    @Size(max = 15)
    private String numero;

    public TelefoneDTO() {}

    public TelefoneDTO(@NotBlank @Size(max = 2) String ddd,
                       @NotBlank @Size(max = 15) String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }

    public Telefone paraTelefone(Fornecedor fornecedor) {
        return new Telefone(ddd, numero, fornecedor);
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumero() {
        return numero;
    }

}
