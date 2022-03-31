package br.com.zup.edu.fornecedormanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "telefones")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2)
    private String ddd;

    @Column(nullable = false, length = 15)
    private String numero;

    @ManyToOne(optional = false)
    private Fornecedor fonecedor;

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Telefone() {}

    public Telefone(String ddd, String numero, Fornecedor fonecedor) {
        this.ddd = ddd;
        this.numero = numero;
        this.fonecedor = fonecedor;
    }

    public Long getId() {
        return id;
    }

}
