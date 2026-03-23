create table pautas (
    id bigserial primary key,
    descricao varchar(255) not null,
    data_criacao timestamptz not null
);

create table associados (
    id bigserial primary key,
    cpf varchar(11) not null,
    data_criacao timestamptz not null,
    constraint uk_associado_cpf unique (cpf)
);

create table votacoes (
    id bigserial primary key,
    pauta_id bigint not null,
    data_abertura timestamptz not null,
    data_encerramento timestamptz not null,
    constraint fk_votacoes_pauta foreign key (pauta_id) references pautas (id),
    constraint uk_votacao_pauta unique (pauta_id)
);

create table votos (
    id bigserial primary key,
    votacao_id bigint not null,
    associado_id bigint not null,
    resposta varchar(3) not null,
    data_criacao timestamptz not null,
    constraint fk_votos_votacao foreign key (votacao_id) references votacoes (id),
    constraint fk_votos_associado foreign key (associado_id) references associados (id),
    constraint uk_voto_votacao_associado unique (votacao_id, associado_id),
    constraint ck_voto_resposta check (resposta in ('SIM', 'NAO'))
);

create index idx_votos_votacao_resposta on votos (votacao_id, resposta);
