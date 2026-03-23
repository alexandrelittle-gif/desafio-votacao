package com.desafio.votacao.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pautas")
public class Pauta implements Serializable {

	private static final long serialVersionUID = 7423459089215121957L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pautas_id_seq")
	@SequenceGenerator(allocationSize = 1, name = "pautas_id_seq", sequenceName = "pautas_id_seq")
	private Long id;

	@Column(name = "descricao", nullable = false, length = 255)
	private String descricao;

	@Column(name = "data_criacao")
	private OffsetDateTime dataCriacao;

}
