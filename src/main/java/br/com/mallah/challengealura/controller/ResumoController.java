package br.com.mallah.challengealura.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mallah.challengealura.controller.dto.ResumoResponseDTO;
import br.com.mallah.challengealura.service.ResumoService;

@RestController
@RequestMapping("resumo")
public class ResumoController {
	
	@Autowired
	private ResumoService resumoService;

	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<ResumoResponseDTO> resumo(@PathVariable Integer ano, @PathVariable Integer mes) {
		return ResponseEntity.ok(resumoService.getResumo(ano, mes));
	}
	
}
