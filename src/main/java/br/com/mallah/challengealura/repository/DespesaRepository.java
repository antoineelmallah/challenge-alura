package br.com.mallah.challengealura.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.mallah.challengealura.model.Despesa;
import br.com.mallah.challengealura.repository.dto.GastoMensalPorCategoriaDTO;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	@Query("select count(c) from Despesa c where lower(c.descricao) = lower(:descricao) and month(c.data) = :mes and year(c.data) = :ano")
	Long contarDespesasPorDescricaoEMes(String descricao, int mes, int ano);

	@Query("select count(c) from Despesa c where lower(c.descricao) = lower(:descricao) and month(c.data) = :mes and year(c.data) = :ano and c.id != :id")
	Long contarDespesasPorDescricaoEMesComIdDiferente(Long id, String descricao, int mes, int ano);

	List<Despesa> findByDescricaoContaining(String descricao);

	@Query("select c from Despesa c where month(c.data) = :mes and year(c.data) = :ano")
	List<Despesa> findByAnoEMes(Integer ano, Integer mes);

	@Query("select sum(c.valor) from Despesa c where month(c.data) = :mes and year(c.data) = :ano")
	BigDecimal sumValorByAnoEMes(Integer ano, Integer mes);

	@Query(value = "select c.categoria, sum(c.valor) as total from despesas c where month(c.data) = :mes and year(c.data) = :ano group by c.categoria", nativeQuery = true)
	List<GastoMensalPorCategoriaDTO> sumValorByCategoria(Integer ano, Integer mes);

}
