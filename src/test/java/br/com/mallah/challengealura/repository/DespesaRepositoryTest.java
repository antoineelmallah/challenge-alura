package br.com.mallah.challengealura.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mallah.challengealura.model.Categoria;
import br.com.mallah.challengealura.model.Despesa;
import br.com.mallah.challengealura.repository.dto.GastoMensalPorCategoriaDTO;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class DespesaRepositoryTest {

	@Autowired
	private DespesaRepository repository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	void contarDespesasPorDescricaoEMes() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMes("Uma descrição", 8, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(1);
	}
	
	@Test
	void contarDespesasPorDescricaoEMes_nenhumaDespesaCadastrada() {
		Long contator = repository.contarDespesasPorDescricaoEMes("Uma descrição", 8, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}
	
	@Test
	void contarDespesasPorDescricaoEMes_descricaoDiferente() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMes("Uma outra descrição", 8, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}
	
	@Test
	void contarDespesasPorDescricaoEMes_mesDiferente() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMes("Uma descrição", 1, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}
	
	@Test
	void contarDespesasPorDescricaoEMes_anoDiferente() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMes("Uma descrição", 8, 2002);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}
	
	@Test
	void contarDespesasPorDescricaoEMesComIdDiferente() {
		// Cenário
		Despesa despesa = testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMesComIdDiferente(despesa.getId() + 1, "Uma descrição", 8, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(1);
	}

	@Test
	void contarDespesasPorDescricaoEMesComIdDiferente_nenhumaDespesaCadastrada() {
		Long contator = repository.contarDespesasPorDescricaoEMesComIdDiferente(1L, "Uma descrição", 8, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}

	@Test
	void contarDespesasPorDescricaoEMesComIdDiferente_idIgual() {
		// Cenário
		Despesa despesa = testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMesComIdDiferente(despesa.getId(), "Uma descrição", 8, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}

	@Test
	void contarDespesasPorDescricaoEMesComIdDiferente_descricaoDiferente() {
		// Cenário
		Despesa despesa = testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMesComIdDiferente(despesa.getId() + 1, "Uma outra descrição", 8, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}

	@Test
	void contarDespesasPorDescricaoEMesComIdDiferente_mesDiferente() {
		// Cenário
		Despesa despesa = testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMesComIdDiferente(despesa.getId() + 1, "Uma descrição", 9, 2022);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}

	@Test
	void contarDespesasPorDescricaoEMesComIdDiferente_anoDiferente() {
		// Cenário
		Despesa despesa = testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		
		Long contator = repository.contarDespesasPorDescricaoEMesComIdDiferente(despesa.getId() + 1, "Uma descrição", 8, 2021);
		assertThat(contator)
			.isNotNull()
			.isEqualTo(0);
	}
	
	@Test
	void findByDescricaoContaining() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Descrição", LocalDate.of(2022, 8, 2), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma nova descrição", LocalDate.of(2022, 8, 3), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma outra descrição", LocalDate.of(2022, 8, 4), Categoria.ALIMENTACAO));
		
		List<Despesa> contator = repository.findByDescricaoContaining("Uma");
		assertThat(contator)
			.isNotNull()
			.hasSize(3)
			.allMatch(d -> d.getDescricao().contains("Uma"));
	}

	@Test
	void findByDescricaoContaining_noMeioDaString() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Descrição", LocalDate.of(2022, 8, 2), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma nova descrição", LocalDate.of(2022, 8, 3), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma outra descrição", LocalDate.of(2022, 8, 4), Categoria.ALIMENTACAO));
		
		List<Despesa> contator = repository.findByDescricaoContaining("nova");
		assertThat(contator)
			.isNotNull()
			.hasSize(1)
			.allMatch(d -> d.getDescricao().contains("nova"));
	}

	@Test
	void findByDescricaoContaining_noFimDaString() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma descrição", LocalDate.of(2022, 8, 1), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Descrição", LocalDate.of(2022, 8, 2), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma nova descrição", LocalDate.of(2022, 8, 3), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma outra descrição", LocalDate.of(2022, 8, 4), Categoria.ALIMENTACAO));
		
		List<Despesa> contator = repository.findByDescricaoContaining("descrição");
		assertThat(contator)
			.isNotNull()
			.hasSize(3)
			.allMatch(d -> d.getDescricao().contains("descrição"));
	}

	@Test
	void findByAnoEMes() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma", LocalDate.of(2021, 2, 1), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Outra", LocalDate.of(2021, 3, 2), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma nova descrição", LocalDate.of(2022, 8, 3), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma outra descrição", LocalDate.of(2022, 8, 4), Categoria.ALIMENTACAO));
		
		List<Despesa> contator = repository.findByAnoEMes(2022, 8);
		assertThat(contator)
			.isNotNull()
			.hasSize(2)
			.allMatch(d -> d.getDescricao().contains("descrição"));
	}

	@Test
	void findByAnoEMes_nenhumaDespesaEncontrada() {
		// Cenário
		testEntityManager.persist(Despesa.novaInstancia("Uma", LocalDate.of(2021, 2, 1), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Outra", LocalDate.of(2021, 3, 2), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma nova descrição", LocalDate.of(2022, 8, 3), Categoria.ALIMENTACAO));
		testEntityManager.persist(Despesa.novaInstancia("Uma outra descrição", LocalDate.of(2022, 8, 4), Categoria.ALIMENTACAO));
		
		List<Despesa> contator = repository.findByAnoEMes(2022, 7);
		assertThat(contator)
			.isNotNull()
			.isEmpty();
	}

	@Test
	void sumValorByAnoEMes() {
		// Cenário
		Despesa despesa1 = Despesa.novaInstancia("Uma", LocalDate.of(2021, 2, 1), Categoria.ALIMENTACAO);
		despesa1.setValor(BigDecimal.valueOf(10.5));
		Despesa despesa2 = Despesa.novaInstancia("Outra", LocalDate.of(2021, 3, 2), Categoria.ALIMENTACAO);
		despesa2.setValor(BigDecimal.valueOf(3.6));
		Despesa despesa3 = Despesa.novaInstancia("Uma nova descrição", LocalDate.of(2022, 8, 3), Categoria.ALIMENTACAO);
		despesa3.setValor(BigDecimal.valueOf(1.9));
		Despesa despesa4 = Despesa.novaInstancia("Uma outra descrição", LocalDate.of(2022, 8, 4), Categoria.ALIMENTACAO);
		despesa4.setValor(BigDecimal.valueOf(55.35));
		testEntityManager.persist(despesa1);
		testEntityManager.persist(despesa2);
		testEntityManager.persist(despesa3);
		testEntityManager.persist(despesa4);
		
		BigDecimal sum = repository.sumValorByAnoEMes(2022, 8);
		assertThat(sum)
			.isNotNull()
			.isEqualByComparingTo(BigDecimal.valueOf(57.25));
	}

	@Test
	void sumValorByAnoEMes_nenhumaDespesaEncontrada() {
		// Cenário
		Despesa despesa1 = Despesa.novaInstancia("Uma", LocalDate.of(2021, 2, 1), Categoria.ALIMENTACAO);
		despesa1.setValor(BigDecimal.valueOf(10.5));
		Despesa despesa2 = Despesa.novaInstancia("Outra", LocalDate.of(2021, 3, 2), Categoria.ALIMENTACAO);
		despesa2.setValor(BigDecimal.valueOf(3.6));
		Despesa despesa3 = Despesa.novaInstancia("Uma nova descrição", LocalDate.of(2022, 8, 3), Categoria.ALIMENTACAO);
		despesa3.setValor(BigDecimal.valueOf(1.9));
		Despesa despesa4 = Despesa.novaInstancia("Uma outra descrição", LocalDate.of(2022, 8, 4), Categoria.ALIMENTACAO);
		despesa4.setValor(BigDecimal.valueOf(55.35));
		testEntityManager.persist(despesa1);
		testEntityManager.persist(despesa2);
		testEntityManager.persist(despesa3);
		testEntityManager.persist(despesa4);
		
		BigDecimal sum = repository.sumValorByAnoEMes(2022, 7);
		assertThat(sum)
			.isNull();
	}

	@Test
	void sumValorByAlimentacao() {
		sumValorByCategoria(Categoria.ALIMENTACAO, BigDecimal.valueOf(44.6));
	}

	@Test
	void sumValorByEducacao() {
		sumValorByCategoria(Categoria.EDUCACAO, BigDecimal.valueOf(88.35));
	}

	@Test
	void sumValorByImprevistos() {
		sumValorByCategoria(Categoria.IMPREVISTOS, BigDecimal.valueOf(1.1));
	}

	@Test
	void sumValorByOutras() {
		sumValorByCategoria(Categoria.OUTRAS, BigDecimal.valueOf(11.6));
	}

	private void sumValorByCategoria(Categoria categoria, BigDecimal valor ) {
		// Cenário
		Despesa despesa1 = Despesa.novaInstancia("alimentacao 1", LocalDate.of(2021, 2, 1), Categoria.ALIMENTACAO);
		despesa1.setValor(BigDecimal.valueOf(10.5));
		Despesa despesa2 = Despesa.novaInstancia("alimentacao 2", LocalDate.of(2021, 3, 2), Categoria.ALIMENTACAO);
		despesa2.setValor(BigDecimal.valueOf(3.6));
		Despesa despesa3 = Despesa.novaInstancia("alimentacao 3", LocalDate.of(2022, 8, 3), Categoria.ALIMENTACAO);
		despesa3.setValor(BigDecimal.valueOf(1.9));
		Despesa despesa4 = Despesa.novaInstancia("educacao 1", LocalDate.of(2022, 8, 4), Categoria.EDUCACAO);
		despesa4.setValor(BigDecimal.valueOf(55.35));
		Despesa despesa5 = Despesa.novaInstancia("imprevistos 1", LocalDate.of(2022, 8, 4), Categoria.IMPREVISTOS);
		despesa5.setValor(BigDecimal.valueOf(1.1));
		Despesa despesa6 = Despesa.novaInstancia("alimentacao 4", LocalDate.of(2022, 8, 5), Categoria.ALIMENTACAO);
		despesa6.setValor(BigDecimal.valueOf(2.3));
		Despesa despesa7 = Despesa.novaInstancia("outras 1", LocalDate.of(2022, 8, 6), Categoria.OUTRAS);
		despesa7.setValor(BigDecimal.valueOf(11.1));
		Despesa despesa8 = Despesa.novaInstancia("outras 2", LocalDate.of(2022, 8, 6), Categoria.OUTRAS);
		despesa8.setValor(BigDecimal.valueOf(0.5));
		Despesa despesa9 = Despesa.novaInstancia("educacao 2", LocalDate.of(2022, 8, 14), Categoria.EDUCACAO);
		despesa9.setValor(BigDecimal.valueOf(33));
		Despesa despesa10 = Despesa.novaInstancia("alimentacao 5", LocalDate.of(2022, 8, 20), Categoria.ALIMENTACAO);
		despesa10.setValor(BigDecimal.valueOf(40.4));
		
		testEntityManager.persist(despesa1);
		testEntityManager.persist(despesa2);
		testEntityManager.persist(despesa3);
		testEntityManager.persist(despesa4);
		testEntityManager.persist(despesa5);
		testEntityManager.persist(despesa6);
		testEntityManager.persist(despesa7);
		testEntityManager.persist(despesa8);
		testEntityManager.persist(despesa9);
		testEntityManager.persist(despesa10);
		
		List<GastoMensalPorCategoriaDTO> gastoPorCategoria = repository.sumValorByCategoria(2022, 8);
		assertThat(gastoPorCategoria)
			.isNotNull()
			.hasSize(4);
		BigDecimal total = gastoPorCategoria.stream()
			.filter(g -> categoria.equals(g.getCategoria()))
			.map(g -> g.getTotal())
			.findFirst()
			.orElse(null);
		assertThat(total).isEqualByComparingTo(valor);
	}
}
