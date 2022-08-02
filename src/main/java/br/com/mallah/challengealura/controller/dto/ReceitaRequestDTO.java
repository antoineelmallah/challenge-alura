package br.com.mallah.challengealura.controller.dto;

import static br.com.mallah.challengealura.utils.ConvercaoUtils.stringToDate;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.mallah.challengealura.model.Receita;

public class ReceitaRequestDTO {
	
	@NotBlank
	private String descricao;
	
	@NotNull
	private BigDecimal valor;
	
	@NotBlank
	@Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}")
	private String data;

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getData() {
		return data;
	}
	
	public Receita atualizar(Receita receita) {
		receita.setDescricao(descricao);
		receita.setValor(valor);
		receita.setData(stringToDate(data));
		return receita;
	}
}
