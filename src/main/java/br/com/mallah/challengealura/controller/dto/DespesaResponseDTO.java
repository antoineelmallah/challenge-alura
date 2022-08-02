package br.com.mallah.challengealura.controller.dto;

import static br.com.mallah.challengealura.utils.ConvercaoUtils.dateToString;

import java.math.BigDecimal;

import br.com.mallah.challengealura.model.Despesa;

public class DespesaResponseDTO {

	private String descricao;
	
	private BigDecimal valor;
	
	private String data;
	
	public DespesaResponseDTO(Despesa despesa) {
		this.descricao = despesa.getDescricao();
		this.valor = despesa.getValor();
		this.data = dateToString(despesa.getData());
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
