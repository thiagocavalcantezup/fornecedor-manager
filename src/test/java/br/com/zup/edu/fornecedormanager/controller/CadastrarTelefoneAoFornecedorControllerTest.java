package br.com.zup.edu.fornecedormanager.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.zup.edu.fornecedormanager.model.Fornecedor;
import br.com.zup.edu.fornecedormanager.model.Telefone;
import br.com.zup.edu.fornecedormanager.repository.FornecedorRepository;
import br.com.zup.edu.fornecedormanager.repository.TelefoneRepository;
import br.com.zup.edu.fornecedormanager.util.MensagemDeErro;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
public class CadastrarTelefoneAoFornecedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;

    private Fornecedor fornecedor;

    @BeforeEach
    void setUp() {
        telefoneRepository.deleteAll();
        fornecedorRepository.deleteAll();

        fornecedor = new Fornecedor("Thiago", "Refrigerante", "Thiago Bebidas e Alimentos Ltda.");
        fornecedor = fornecedorRepository.save(fornecedor);
    }

    @Test
    void deveCadastrarUmTelefoneComDadosValidos() throws Exception {
        // cenário (given)
        //
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        TelefoneRequest telefoneRequest = new TelefoneRequest("11", "98765-4321");

        String payload = objectMapper.writeValueAsString(telefoneRequest);

        MockHttpServletRequestBuilder request = post(
            "/fornecedores/{id}/telefones", fornecedor.getId()
        ).contentType(APPLICATION_JSON).content(payload).header("Accept-Language", "pt-br");

        // ação (when) e corretude (then)
        //
        mockMvc.perform(request)
               .andExpect(status().isCreated())
               .andExpect(redirectedUrlPattern(baseUrl + "/fornecedores/*/telefones/*"));

        List<Telefone> telefones = telefoneRepository.findAll();

        assertEquals(1, telefones.size());
    }

    @Test
    void naoDeveCadastrarUmTelefoneComDadosInvalidos() throws Exception {
        // cenário (given)
        //
        TelefoneRequest telefoneRequest = new TelefoneRequest(null, null);

        String payload = objectMapper.writeValueAsString(telefoneRequest);

        MockHttpServletRequestBuilder request = post(
            "/fornecedores/{id}/telefones", fornecedor.getId()
        ).contentType(APPLICATION_JSON).content(payload).header("Accept-Language", "pt-br");

        // ação (when) e corretude (then)
        //
        String response = mockMvc.perform(request)
                                 .andExpect(status().isBadRequest())
                                 .andReturn()
                                 .getResponse()
                                 .getContentAsString(UTF_8);

        MensagemDeErro mensagemDeErro = objectMapper.readValue(response, MensagemDeErro.class);
        List<String> mensagens = mensagemDeErro.getMensagens();

        assertEquals(2, mensagens.size());
        assertThat(
            mensagens,
            containsInAnyOrder(
                "O campo ddd não deve estar em branco", "O campo numero não deve estar em branco"
            )
        );
    }

    @Test
    void naoDeveCadastrarUmTelefoneCasoOFornecedorNaoExista() throws Exception {
        // cenário (given)
        //
        TelefoneRequest telefoneRequest = new TelefoneRequest("11", "98765-4321");

        String payload = objectMapper.writeValueAsString(telefoneRequest);

        MockHttpServletRequestBuilder request = post(
            "/fornecedores/{id}/telefones", 1234
        ).contentType(APPLICATION_JSON).content(payload).header("Accept-Language", "pt-br");

        // ação (when) e corretude (then)
        //
        mockMvc.perform(request).andExpect(status().isNotFound());
    }

}
