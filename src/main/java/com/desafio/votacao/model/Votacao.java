package com.desafio.votacao.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "votacoes", uniqueConstraints = @UniqueConstraint(name = "uk_votacao_pauta", columnNames = "pauta_id"))
public class Votacao implements Serializable {

	private static final long serialVersionUID = -2754167037883542328L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "votacoes_id_seq")
	@SequenceGenerator(allocationSize = 1, name = "votacoes_id_seq", sequenceName = "votacoes_id_seq")
	private Long id;

	@Nonnull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pauta_id", referencedColumnName = "id")
	private Pauta pauta;

	@Column(name = "data_abertura")
	private OffsetDateTime dataAbertura;

	@Column(name = "data_encerramento")
	private OffsetDateTime dataEncerramento;
	
	public boolean isAberta(OffsetDateTime now) {
        return !now.isBefore(dataAbertura) && now.isBefore(dataEncerramento);
    }

}
