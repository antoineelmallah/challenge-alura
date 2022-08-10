package br.com.mallah.challengealura.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mallah.challengealura.controller.dto.ReceitaRequestDTO;
import br.com.mallah.challengealura.controller.dto.ReceitaResponseDTO;
import br.com.mallah.challengealura.model.Receita;
import br.com.mallah.challengealura.repository.ReceitaRepository;

@RestController
@RequestMapping("receitas")
public class ReceitaController {
	
	@Autowired
	private ReceitaRepository receitaRepository;

	@PostMapping
	public ResponseEntity<String> salvar(@RequestBody @Valid ReceitaRequestDTO request, UriComponentsBuilder uriBuilder) {
		Receita entity = request.atualizar(new Receita());
		
		if (jaExisteReceitaComMesmaDescricaoMesEAno(entity)) {
			return ResponseEntity.badRequest().body("Já existe receita com a mesma descrição no mesmo mês e ano.");
		}
		
		Receita receita = receitaRepository.save(entity);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(receita.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<List<ReceitaResponseDTO>> recuperarTodas(@RequestParam(required = false) String descricao) {
		System.out.println("descricao: "+descricao);
		List<ReceitaResponseDTO> resposta = (Strings.isBlank(descricao) ? receitaRepository.findAll() : receitaRepository.findByDescricaoContaining(descricao)).stream()
			.map(r -> new ReceitaResponseDTO(r))
			.collect(Collectors.toList());
		return ResponseEntity.ok(resposta);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReceitaResponseDTO> detalhar(@PathVariable Long id) {
		Optional<Receita> receitaOptional = receitaRepository.findById(id);
		if (receitaOptional.isPresent()) {
			return ResponseEntity.ok(new ReceitaResponseDTO(receitaOptional.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody @Valid ReceitaRequestDTO request) {
		
		if (alteracaoDeixaraDuasReceitasComMesmaDescricaoMesEAno(id, request)) {
			return ResponseEntity.badRequest().body("Alteração deixaria mais de uma receita com a mesma descrição no mesmo mês e ano.");
		}
		
		Optional<Receita> receitaOptional = receitaRepository.findById(id);
		if (receitaOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Receita receita = receitaOptional.get();
		request.atualizar(receita);
		receitaRepository.save(receita);
		return ResponseEntity.ok("Receita alterada.");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> remover(@PathVariable Long id) {
		Optional<Receita> receitaOptional = receitaRepository.findById(id);
		if (receitaOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		receitaRepository.deleteById(id);
		return ResponseEntity.ok("Receita excluida");
	}
	
	private boolean jaExisteReceitaComMesmaDescricaoMesEAno(Receita receita) {
		LocalDate data = receita.getData();
		return receitaRepository.contarReceitasPorDescricaoEMes(receita.getDescricao(), data.getMonthValue(), data.getYear()) > 0;
	}

	private boolean alteracaoDeixaraDuasReceitasComMesmaDescricaoMesEAno(Long id, ReceitaRequestDTO request) {
		Receita receita = request.atualizar(new Receita());
		LocalDate data = receita.getData();
		return receitaRepository.contarReceitasPorDescricaoEMesComIdDiferente(id, receita.getDescricao(), data.getMonthValue(), data.getYear()) > 0;
	}

}
