package com.desafio.votacao.model;

public enum VoteValue {
    SIM,
    NAO;

    public static VoteValue from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Voto é obrigatório");
        }

        String normalized = value.trim()
            .replace('ã', 'a')
            .replace('Ã', 'A')
            .replace('á', 'a')
            .replace('Á', 'A')
            .toUpperCase();

        return switch (normalized) {
            case "SIM" -> SIM;
            case "NAO" -> NAO;
            default -> throw new IllegalArgumentException("Voto deve ser SIM ou NAO");
        };
    }
}
