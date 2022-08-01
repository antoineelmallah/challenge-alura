package br.com.mallah.challengealura.controller.dto;

import static br.com.mallah.challengealura.utils.ConvercaoUtils.dateToString;

import java.math.BigDecimal;

import br.com.mallah.challengealura.model.Receita;

public class ReceitaResponseDTO {

	private String descricao;
	
	private BigDecimal valor;
	
	private String data;
	
	public ReceitaResponseDTO(Receita receita) {
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
		this.data = dateToString(receita.getData());
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getData() {
		return data;
	}
	
}
