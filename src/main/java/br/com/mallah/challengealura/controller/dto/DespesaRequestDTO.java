package br.com.mallah.challengealura.controller.dto;

import static br.com.mallah.challengealura.utils.ConvercaoUtils.stringToDate;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.mallah.challengealura.model.Categoria;
import br.com.mallah.challengealura.model.Despesa;

public class DespesaRequestDTO {
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private BigDecimal valor;
	
	@NotBlank
	@Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}")
	private String data;
	
	private Categoria categoria;

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getData() {
		return data;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public Despesa criarEntidade() {
		return Despesa.novaInstancia(descricao, stringToDate(data), categoria);
	}
	
	public Despesa atualizarEntidade(Despesa despesa) {
		despesa.setDescricao(descricao);
		despesa.setValor(valor);
		despesa.setData(stringToDate(data));
		return despesa;
	}
}
