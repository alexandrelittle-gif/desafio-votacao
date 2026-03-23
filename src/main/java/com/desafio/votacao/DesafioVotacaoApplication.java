package com.desafio.votacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(value = "com.desafio.votacao.repository", repositoryBaseClass = BaseJpaRepositoryImpl.class)
public class DesafioVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioVotacaoApplication.class, args);
	}

}
