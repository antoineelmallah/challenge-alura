package br.com.mallah.challengealura.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.mallah.challengealura.controller.dto.DespesaRequestDTO;
import br.com.mallah.challengealura.model.Categoria;
import br.com.mallah.challengealura.model.Despesa;
import br.com.mallah.challengealura.repository.DespesaRepository;

@ExtendWith(MockitoExtension.class)
class DespesaControllerTest {

	@InjectMocks
	private DespesaController controller;
	
	@Mock
	private DespesaRepository despesaRepository;
	
	@Test
	void salvar() throws URISyntaxException {
		Despesa entity = Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 20), Categoria.ALIMENTACAO);
		Despesa entityPersisted = Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 20), Categoria.ALIMENTACAO);
		ReflectionTestUtils.setField(entityPersisted, "id", 1L);
		when(despesaRepository.contarDespesasPorDescricaoEMes("Uma descrição", 8, 2022)).thenReturn(0L);
		when(despesaRepository.save(argThat(new DespesaMatcher(entity)))).thenReturn(entityPersisted);
		UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
		UriComponents uriComponents = mock(UriComponents.class);
		when(uriBuilder.path("/topicos/{id}")).thenReturn(uriBuilder);
		when(uriBuilder.buildAndExpand(1l)).thenReturn(uriComponents);
		when(uriComponents.toUri()).thenReturn(new URI("http://localhost:8080/topicos/1"));
		DespesaRequestDTO despesaRequestDTO = new DespesaRequestDTO();
		ReflectionTestUtils.setField(despesaRequestDTO, "descricao", "Uma descrição");
		ReflectionTestUtils.setField(despesaRequestDTO, "valor", BigDecimal.valueOf(12.3));
		ReflectionTestUtils.setField(despesaRequestDTO, "data", "20/08/2022");
		ReflectionTestUtils.setField(despesaRequestDTO, "categoria", Categoria.ALIMENTACAO);
		ResponseEntity<String> result = controller.salvar(despesaRequestDTO, uriBuilder);
		assertNotNull(result);
		assertThat(result.getStatusCode())
			.isEqualByComparingTo(HttpStatus.CREATED);
	}
	
	@Test
	void salvar_despesaExistente() {
		when(despesaRepository.contarDespesasPorDescricaoEMes("Uma descrição", 8, 2022)).thenReturn(1L);
		UriComponentsBuilder uriBuilder = mock(UriComponentsBuilder.class);
		DespesaRequestDTO despesaRequestDTO = new DespesaRequestDTO();
		ReflectionTestUtils.setField(despesaRequestDTO, "descricao", "Uma descrição");
		ReflectionTestUtils.setField(despesaRequestDTO, "valor", BigDecimal.valueOf(12.3));
		ReflectionTestUtils.setField(despesaRequestDTO, "data", "20/08/2022");
		ReflectionTestUtils.setField(despesaRequestDTO, "categoria", Categoria.ALIMENTACAO);
		ResponseEntity<String> result = controller.salvar(despesaRequestDTO, uriBuilder);
		assertNotNull(result);
		assertThat(result.getStatusCode())
			.isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	class DespesaMatcher implements ArgumentMatcher<Despesa> {

		private Despesa despesa;
		
		public DespesaMatcher(Despesa despesa) {
			this.despesa = despesa;
		}

		@Override
		public boolean matches(Despesa argument) {
			if (despesa.getDescricao().equals(argument.getDescricao())
			 && despesa.getCategoria().equals(argument.getCategoria())
			 && despesa.getData().equals(argument.getData()))
				return true;
			return false;
		}
		
	}
}
