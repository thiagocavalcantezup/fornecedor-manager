package br.com.zup.edu.fornecedormanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(FornecedorController.BASE_URI + "/{fornecedorId}" + TelefoneController.BASE_URI)
public class TelefoneController {

    public final static String BASE_URI = "/telefones";

}
