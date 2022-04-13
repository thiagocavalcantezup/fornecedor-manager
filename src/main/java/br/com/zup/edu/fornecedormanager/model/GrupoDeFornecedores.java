package br.com.zup.edu.fornecedormanager.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grupos_de_fornecedores")
public class GrupoDeFornecedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String produto;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "grupo_de_fornecedores_id")
    private Set<Fornecedor> fornecedores = new HashSet<>();

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public GrupoDeFornecedores() {}

    public GrupoDeFornecedores(String produto) {
        this.produto = produto;
    }

    public void adicionar(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
    }

    public Long getId() {
        return id;
    }

}
