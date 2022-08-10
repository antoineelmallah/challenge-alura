package br.com.mallah.challengealura.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "despesas")
public class Despesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descricao;
	
	private BigDecimal valor;
	
	private LocalDate data;
	
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	protected Despesa() {}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public static Despesa novaInstancia(String descricao, LocalDate data, Categoria categoria) {
		Despesa despesa = new Despesa();
		despesa.descricao = descricao;
		despesa.data = data;
		despesa.categoria = categoria != null ? categoria : Categoria.OUTRAS;
		return despesa;
	}
	
}
