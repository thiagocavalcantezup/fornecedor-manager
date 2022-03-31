package br.com.zup.edu.fornecedormanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.fornecedormanager.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}
