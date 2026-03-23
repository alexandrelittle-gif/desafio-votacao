package com.desafio.votacao.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "votos", uniqueConstraints = @UniqueConstraint(name = "uk_voto_votacao_associado", columnNames = {"votacao_id", "associado_id"}))
public class Voto implements Serializable {
	
	private static final long serialVersionUID = 6734767578585363587L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "votos_id_seq")
	@SequenceGenerator(allocationSize = 1, name = "votos_id_seq", sequenceName = "votos_id_seq")
	private Long id;
	
	@Nonnull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "votacao_id", referencedColumnName = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Votacao votacao;
	
	@Nonnull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "associado_id", referencedColumnName = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Associado associado;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "resposta")
	private VoteValue resposta;
	
	@Column(name = "data_criacao")
	private OffsetDateTime dataCriacao;

}
