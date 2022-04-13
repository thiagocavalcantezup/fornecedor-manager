package br.com.zup.edu.fornecedormanager.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String produto;

    @Column(nullable = false)
    private String empresa;

    public LocalDateTime criadoEm = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "fornecedor", orphanRemoval = true)
    @Column(nullable = false)
    private Set<Telefone> telefones = new HashSet<>();

    /**
     * @deprecated Construtor de uso exclusivo do Hibernate
     */
    @Deprecated
    public Fornecedor() {}

    public Fornecedor(String nome, String produto, String empresa) {
        this.nome = nome;
        this.produto = produto;
        this.empresa = empresa;
    }

    public void adicionar(Telefone telefone) {
        telefone.setFornecedor(this);
        this.telefones.add(telefone);
    }

    public Long getId() {
        return id;
    }

}
