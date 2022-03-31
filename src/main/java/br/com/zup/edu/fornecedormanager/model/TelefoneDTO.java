package br.com.zup.edu.fornecedormanager.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TelefoneDTO {

    @NotBlank
    @Size(min = 2, max = 2)
    private String ddd;

    @NotBlank
    @Size(max = 15)
    private String numero;

    public TelefoneDTO() {}

    public TelefoneDTO(@NotBlank @Size(min = 2, max = 2) String ddd,
                       @NotBlank @Size(max = 15) String numero) {
        this.ddd = ddd;
        this.numero = numero;
    }

    public Telefone paraTelefone(Fornecedor fornecedor) {
        String novoNumero = numero.replaceAll("[^0-9]", "");

        return new Telefone(ddd, novoNumero, fornecedor);
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumero() {
        return numero;
    }

}
