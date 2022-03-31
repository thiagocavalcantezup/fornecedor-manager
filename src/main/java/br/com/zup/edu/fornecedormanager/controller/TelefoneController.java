package br.com.zup.edu.fornecedormanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.edu.fornecedormanager.repository.FornecedorRepository;
import br.com.zup.edu.fornecedormanager.repository.TelefoneRepository;

@RestController
@RequestMapping(FornecedorController.BASE_URI + "/{fornecedorId}" + TelefoneController.BASE_URI)
public class TelefoneController {

    public final static String BASE_URI = "/telefones";

    private final FornecedorRepository fornecedorRepository;
    private final TelefoneRepository telefoneRepository;

    public TelefoneController(FornecedorRepository fornecedorRepository,
                              TelefoneRepository telefoneRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.telefoneRepository = telefoneRepository;
    }

}
