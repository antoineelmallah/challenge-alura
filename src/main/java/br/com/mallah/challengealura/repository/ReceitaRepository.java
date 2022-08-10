package br.com.mallah.challengealura.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.mallah.challengealura.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	@Query("select count(c) from Receita c where lower(c.descricao) = lower(:descricao) and month(c.data) = :mes and year(c.data) = :ano")
	Long contarReceitasPorDescricaoEMes(String descricao, int mes, int ano);

	@Query("select count(c) from Receita c where lower(c.descricao) = lower(:descricao) and month(c.data) = :mes and year(c.data) = :ano and c.id != :id")
	Long contarReceitasPorDescricaoEMesComIdDiferente(Long id, String descricao, int mes, int ano);

	List<Receita> findByDescricaoContaining(String descricao);

	@Query("select c from Receita c where month(c.data) = :mes and year(c.data) = :ano")
	List<Receita> findByAnoEMes(Integer ano, Integer mes);

	@Query("select sum(c.valor) from Receita c where month(c.data) = :mes and year(c.data) = :ano")
	BigDecimal sumValorByAnoEMes(Integer ano, Integer mes);

}
