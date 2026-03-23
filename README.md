# Desafio Votação

API REST para cadastro de pautas, abertura de sessões de votação, recebimento de votos e apuração do resultado.

## Tecnologias

- Java 25
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- PostgreSQL
- Flyway
- Lombok
- Springdoc OpenAPI
- JUnit 5 / Mockito

## Como rodar

```bash
docker compose up -d
mvn spring-boot:run
```

## Endpoints

### Pautas
- `POST /api/v1.0/pautas`
- `GET /api/v1.0/pautas`

### Associados
- `POST /api/v1.0/associados`
- `GET /api/v1.0/associados`

### Votação
- `POST /api/v1.0/pautas/{pautaId}/votacao`
- `POST /api/v1.0/pautas/{pautaId}/voto`
- `GET /api/v1.0/pautas/{pautaId}/apuracao`

## Exemplos

### Criar pauta
```json
{
  "descricao": "Aprovar orçamento anual"
}
```

### Criar associado
```json
{
  "cpf": "52998224725"
}
```

### Abrir sessão
```json
{
  "duracaoMinutos": 5
}
```

### Votar
```json
{
  "idAssociado": 1,
  "voto": "SIM"
}
```

## Como executar

### Subindo o banco
```bash
docker compose up -d
```

### Rodando a aplicação
```bash
./mvnw spring-boot:run
```

### Build
```bash
./mvnw clean package
```

## Documentação

- Swagger UI: `/swagger-ui.html`
- OpenAPI JSON: `/v3/api-docs`
- Healthcheck: `/actuator/health`



