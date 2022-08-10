package br.com.mallah.challengealura.repository.dto;

import java.math.BigDecimal;

import br.com.mallah.challengealura.model.Categoria;

public interface GastoMensalPorCategoriaDTO {

	public Categoria getCategoria();

	public BigDecimal getTotal();
	
}
