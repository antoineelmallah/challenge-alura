package br.com.mallah.challengealura.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mallah.challengealura.controller.dto.ResumoResponseDTO;
import br.com.mallah.challengealura.repository.DespesaRepository;
import br.com.mallah.challengealura.repository.ReceitaRepository;
import br.com.mallah.challengealura.repository.dto.GastoMensalPorCategoriaDTO;

@Service
public class ResumoService {

	@Autowired
	private ReceitaRepository receitaRepository;
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	public ResumoResponseDTO getResumo(Integer ano, Integer mes) {
		
		BigDecimal totalReceitas = receitaRepository.sumValorByAnoEMes(ano, mes);
		
		if (totalReceitas == null)
			totalReceitas = BigDecimal.ZERO;
		
		BigDecimal totalDespesas = despesaRepository.sumValorByAnoEMes(ano, mes);
		
		if (totalDespesas == null)
			totalDespesas = BigDecimal.ZERO;
		
		ResumoResponseDTO result = ResumoResponseDTO.novaInstancia(ano, mes, totalDespesas, totalReceitas);
		
		List<GastoMensalPorCategoriaDTO> sumValorByCategoria = despesaRepository.sumValorByCategoria(ano, mes);
		
		sumValorByCategoria.stream()
			.forEach(d -> result.addTotalPorCategoria(d.getCategoria(), d.getTotal()));
		
		return result;
		
	}
	
}
