package br.com.mallah.challengealura.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.mallah.challengealura.model.Receita;

public class ReceitaResponseDTO {

	private String descricao;
	
	private BigDecimal valor;
	
	private LocalDate data;
	
	public ReceitaResponseDTO(Receita receita) {
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
		this.data = receita.getData();
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public LocalDate getData() {
		return data;
	}
	
}
