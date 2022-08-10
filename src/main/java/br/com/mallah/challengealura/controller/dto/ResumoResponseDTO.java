package br.com.mallah.challengealura.controller.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mallah.challengealura.model.Categoria;

public class ResumoResponseDTO {
	
	private Integer ano;
	
	private Integer mes;

	private BigDecimal totalReceitas;
	
	private BigDecimal totalDespesas;
	
	private List<ResumoResponseCategoriaDTO> totalPorCategoria = new ArrayList<>();

	public Integer getAno() {
		return ano;
	}

	public Integer getMes() {
		return mes;
	}

	public BigDecimal getTotalReceitas() {
		return totalReceitas;
	}

	public BigDecimal getTotalDespesas() {
		return totalDespesas;
	}

	public List<ResumoResponseCategoriaDTO> getTotalPorCategoria() {
		return Collections.unmodifiableList(totalPorCategoria);
	}
	
	public void addTotalPorCategoria(Categoria categoria, BigDecimal total) {
		this.totalPorCategoria.add(ResumoResponseCategoriaDTO.novaInstancia(categoria, total));
	}
	
	public BigDecimal getSaldo() {
		return this.totalReceitas.subtract(totalDespesas);
	}
	
	public static ResumoResponseDTO novaInstancia(Integer ano, Integer mes, BigDecimal totalDespesas, BigDecimal totalReceitas) {
		ResumoResponseDTO dto = new ResumoResponseDTO();
		dto.ano = ano;
		dto.mes = mes;
		dto.totalDespesas = totalDespesas;
		dto.totalReceitas = totalReceitas;
		return dto;
	}
}
