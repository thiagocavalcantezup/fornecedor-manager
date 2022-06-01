package br.com.zup.edu.fornecedormanager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.edu.fornecedormanager.model.Fornecedor;
import br.com.zup.edu.fornecedormanager.model.Telefone;
import br.com.zup.edu.fornecedormanager.repository.FornecedorRepository;
import br.com.zup.edu.fornecedormanager.repository.TelefoneRepository;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
public class RemoverTelefoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private Fornecedor fornecedor1;
    private Fornecedor fornecedor2;

    @BeforeEach
    void setUp() {
        telefoneRepository.deleteAll();
        fornecedorRepository.deleteAll();

        fornecedor1 = new Fornecedor("Thiago", "Café", "Café Oliveira Ltda.");
        fornecedor2 = new Fornecedor("José", "Chá", "Chá Comigo");
        fornecedorRepository.saveAll(List.of(fornecedor1, fornecedor2));
    }

    @Test
    void deveRemoverUmTelefone() throws Exception {
        // cenário (given)
        //
        Telefone telefone = new Telefone(fornecedor1, "81", "98765-4321");
        fornecedor1.getTelefones().add(telefone);
        fornecedorRepository.save(fornecedor1);
        telefoneRepository.save(telefone);

        MockHttpServletRequestBuilder request = delete(
            "/fornecedores/{idFornecedor}/telefones/{idTelefone}", fornecedor1.getId(),
            telefone.getId()
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        mockMvc.perform(request).andExpect(status().isNoContent());

        assertFalse(
            telefoneRepository.existsById(telefone.getId()),
            "Não deve existir um telefone com esse id."
        );
    }

    @Test
    void naoDeveRemoverUmTelefoneDeUmFornecedorNaoCadastrado() throws Exception {
        // cenário (given)
        //
        MockHttpServletRequestBuilder request = delete(
            "/fornecedores/{idFornecedor}/telefones/{idTelefone}", Integer.MAX_VALUE,
            Integer.MAX_VALUE
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        Exception resolvedException = mockMvc.perform(request)
                                             .andExpect(status().isNotFound())
                                             .andReturn()
                                             .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());
        ResponseStatusException responseStatusException = (ResponseStatusException) resolvedException;
        assertEquals("Fornecedor nao cadastrado", responseStatusException.getReason());
    }

    @Test
    void naoDeveRemoverUmTelefoneNaoCadastrado() throws Exception {
        // cenário (given)
        //
        MockHttpServletRequestBuilder request = delete(
            "/fornecedores/{idFornecedor}/telefones/{idTelefone}", fornecedor1.getId(),
            Integer.MAX_VALUE
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        Exception resolvedException = mockMvc.perform(request)
                                             .andExpect(status().isNotFound())
                                             .andReturn()
                                             .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());
        ResponseStatusException responseStatusException = (ResponseStatusException) resolvedException;
        assertEquals("Telefone nao cadastrado", responseStatusException.getReason());
    }

    @Test
    void naoDeveRemoverUmTelefoneQueNaoPertenceAoFornecedor() throws Exception {
        // cenário (given)
        //
        Telefone telefone = new Telefone(fornecedor1, "81", "98765-4321");
        fornecedor1.getTelefones().add(telefone);
        fornecedorRepository.save(fornecedor1);
        telefoneRepository.save(telefone);

        MockHttpServletRequestBuilder request = delete(
            "/fornecedores/{idFornecedor}/telefones/{idTelefone}", fornecedor2.getId(),
            telefone.getId()
        ).contentType(APPLICATION_JSON);

        // ação (when) e corretude (then)
        //
        Exception resolvedException = mockMvc.perform(request)
                                             .andExpect(status().isUnprocessableEntity())
                                             .andReturn()
                                             .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(ResponseStatusException.class, resolvedException.getClass());
        ResponseStatusException responseStatusException = (ResponseStatusException) resolvedException;
        assertEquals(
            "Este telefone nao pertence ao fornecedor", responseStatusException.getReason()
        );
    }

}
