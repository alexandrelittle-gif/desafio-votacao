package com.desafio.votacao.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "associados", uniqueConstraints = @UniqueConstraint(name = "uk_associado_cpf", columnNames = "cpf"))
public class Associado implements Serializable {

	private static final long serialVersionUID = -1864152354567373361L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "associados_id_seq")
	@SequenceGenerator(allocationSize = 1, name = "associados_id_seq", sequenceName = "associados_id_seq")
	private Long id;

	@Column(name = "cpf", nullable = false, length = 11)
	@NaturalId
	@NotBlank
	private String cpf;

	@Column(name = "data_criacao", nullable = false)
	private OffsetDateTime dataCriacao;

}
