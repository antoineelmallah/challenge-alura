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

import br.com.mallah.challengealura.controller.dto.DespesaRequestDTO;
import br.com.mallah.challengealura.controller.dto.DespesaResponseDTO;
import br.com.mallah.challengealura.controller.dto.ReceitaResponseDTO;
import br.com.mallah.challengealura.model.Despesa;
import br.com.mallah.challengealura.repository.DespesaRepository;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private DespesaRepository despesaRepository;
	
	@PostMapping
	public ResponseEntity<String> salvar(@RequestBody @Valid DespesaRequestDTO request, UriComponentsBuilder uriBuilder) {
		Despesa entity = request.criarEntidade();
		
		if (jaExisteDespesaComMesmaDescricaoMesEAno(entity)) {
			return ResponseEntity.badRequest().body("Já existe despesa com a mesma descrição no mesmo mês e ano.");
		}
		
		Despesa despesa = despesaRepository.save(entity);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(despesa.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<List<DespesaResponseDTO>> recuperarTodas(@RequestParam(required = false) String descricao) {
		List<DespesaResponseDTO> list = (Strings.isBlank(descricao) ? despesaRepository.findAll() : despesaRepository.findByDescricaoContaining(descricao)).stream()
			.map(d -> new DespesaResponseDTO(d))
			.collect(Collectors.toList());
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DespesaResponseDTO> detalhar(@PathVariable Long id) {
		Optional<Despesa> despesaOptional = despesaRepository.findById(id);
		if (despesaOptional.isPresent()) {
			return ResponseEntity.ok(new DespesaResponseDTO(despesaOptional.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody @Valid DespesaRequestDTO request) {
		
		if (alteracaoDeixaraDuasDespesasComMesmaDescricaoMesEAno(id, request)) {
			return ResponseEntity.badRequest().body("Alteração deixaria mais de uma despesa com a mesma descrição no mesmo mês e ano.");
		}
		
		Optional<Despesa> despesaOptional = despesaRepository.findById(id);
		if (despesaOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Despesa despesa = despesaOptional.get();
		request.atualizarEntidade(despesa);
		despesaRepository.save(despesa);
		return ResponseEntity.ok("Despesa alterada.");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> remover(@PathVariable Long id) {
		Optional<Despesa> despesaOptional = despesaRepository.findById(id);
		if (despesaOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		despesaRepository.deleteById(id);
		return ResponseEntity.ok("Despesa excluida");
	}

	private boolean jaExisteDespesaComMesmaDescricaoMesEAno(Despesa despesa) {
		LocalDate data = despesa.getData();
		return despesaRepository.contarDespesasPorDescricaoEMes(despesa.getDescricao(), data.getMonthValue(), data.getYear()) > 0;
	}

	private boolean alteracaoDeixaraDuasDespesasComMesmaDescricaoMesEAno(Long id, DespesaRequestDTO request) {
		Despesa despesa = request.criarEntidade();
		LocalDate data = despesa.getData();
		return despesaRepository.contarDespesasPorDescricaoEMesComIdDiferente(id, despesa.getDescricao(), data.getMonthValue(), data.getYear()) > 0;
	}

}
