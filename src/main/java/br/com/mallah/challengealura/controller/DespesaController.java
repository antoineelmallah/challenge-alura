package br.com.mallah.challengealura.controller;

import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mallah.challengealura.controller.dto.DespesaRequestDTO;
import br.com.mallah.challengealura.model.Despesa;
import br.com.mallah.challengealura.repository.DespesaRepository;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private DespesaRepository despesaRepository;
	
	@PostMapping
	public ResponseEntity<String> salvar(@RequestBody @Valid DespesaRequestDTO request, UriComponentsBuilder uriBuilder) {
		Despesa entity = request.atualizar(new Despesa());
		
		if (jaExisteDespesaComMesmaDescricaoMesEAno(entity)) {
			return ResponseEntity.badRequest().body("Já existe receita com a mesma descrição no mesmo mês e ano.");
		}
		
		Despesa despesa = despesaRepository.save(entity);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(despesa.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	private boolean jaExisteDespesaComMesmaDescricaoMesEAno(Despesa despesa) {
		LocalDate data = despesa.getData();
		return despesaRepository.contarDespesasPorDescricaoEMes(despesa.getDescricao(), data.getMonthValue(), data.getYear()) > 0;
	}

}
