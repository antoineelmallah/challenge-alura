package br.com.mallah.challengealura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.mallah.challengealura.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	@Query("select count(c) from Despesa c where lower(c.descricao) = lower(:descricao) and month(c.data) = :mes and year(c.data) = :ano")
	Long contarDespesasPorDescricaoEMes(String descricao, int mes, int ano);

	@Query("select count(c) from Despesa c where lower(c.descricao) = lower(:descricao) and month(c.data) = :mes and year(c.data) = :ano and c.id != :id")
	Long contarDespesasPorDescricaoEMesComIdDiferente(Long id, String descricao, int monthValue, int year);

}
