package br.com.mallah.challengealura.controller.dto;

import java.math.BigDecimal;

import br.com.mallah.challengealura.model.Categoria;

public class ResumoResponseCategoriaDTO {

	private Categoria categoria;
	
	private BigDecimal total;

	protected ResumoResponseCategoriaDTO() {}
	
	public Categoria getCategoria() {
		return categoria;
	}

	public BigDecimal getTotal() {
		return total;
	}
	
	public static ResumoResponseCategoriaDTO novaInstancia(Categoria categoria, BigDecimal total) {
		ResumoResponseCategoriaDTO dto = new ResumoResponseCategoriaDTO();
		dto.categoria = categoria;
		dto.total = total;
		return dto;
	}
}
