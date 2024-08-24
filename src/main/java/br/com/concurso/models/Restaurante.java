package br.com.concurso.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurantes")
public class Restaurante implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "endereco", nullable = false, length = 100)
	private String endereco;
	
	@Column(name = "nota", nullable = true)
	private BigDecimal nota;

	@Column(name = "qtd_avaliacoes", nullable = true)
	private int qtdAvaliacoes;
	
	@OneToOne(mappedBy = "restaurante")
	private Prato prato;
	
	
	@OneToMany(mappedBy = "restaurante")
	private List<AvaliacaoRestaurante> avaliacoes = new ArrayList<>();

}
