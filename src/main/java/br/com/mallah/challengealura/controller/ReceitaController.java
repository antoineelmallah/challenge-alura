package br.com.mallah.challengealura.controller;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mallah.challengealura.controller.dto.ReceitaRequestDTO;
import br.com.mallah.challengealura.model.Receita;
import br.com.mallah.challengealura.repository.ReceitaRepository;

@RestController
@RequestMapping("receitas")
public class ReceitaController {
	
	@Autowired
	private ReceitaRepository receitaRepository;

	@PostMapping
	public ResponseEntity<String> salvar(@RequestBody @Valid ReceitaRequestDTO request) {
		Receita entity = request.toEntity();
		
		if (jaExisteReceitaComMesmaDescricaoMesEAno(entity)) {
			return ResponseEntity.badRequest().body("Já existe receita com a mesma descrição no mesmo mês e ano.");
		}
		
		receitaRepository.save(entity);
		
		return ResponseEntity.ok("Receita adicionada com sucesso.");
	}
	
	private boolean jaExisteReceitaComMesmaDescricaoMesEAno(Receita receita) {
		LocalDate data = receita.getData();
		return receitaRepository.contarReceitasPorDescricaoEMes(receita.getDescricao(), data.getMonthValue(), data.getYear()) > 0;
	}
	
}
