package br.com.zup.edu.fornecedormanager.exception;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.FieldError;

public class ErroPadronizado {

    @JsonProperty("codigo_http")
    private Integer codigoHttp;

    @JsonProperty("mensagem_http")
    private String mensagemHttp;

    @JsonProperty("mensagem_geral")
    private String mensagemGeral;

    @JsonProperty("mensagens")
    private List<String> mensagens;

    public ErroPadronizado(Integer codigoHttp, String mensagemHttp, String mensagemGeral) {
        this.codigoHttp = codigoHttp;
        this.mensagemHttp = mensagemHttp;
        this.mensagemGeral = mensagemGeral;
        this.mensagens = new ArrayList<>();
    }

    public void adicionarErro(FieldError fieldError) {
        mensagens.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
    }

    public void adicionarErro(String error) {
        mensagens.add(error);
    }

    public Integer getCodigoHttp() {
        return codigoHttp;
    }

    public String getMensagemHttp() {
        return mensagemHttp;
    }

    public String getMensagemGeral() {
        return mensagemGeral;
    }

    public List<String> getMensagens() {
        return mensagens;
    }

}
