package com.desafio.votacao.client;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import com.desafio.votacao.exception.ResourceNotFoundException;

@Component
public class FakeCpfVoteEligibilityClient implements CpfVoteEligibilityClient {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public CpfVoteEligibilityResult check(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || !isValidCpf(cpf)) {
            throw new ResourceNotFoundException("CPF inválido para elegibilidade de voto");
        }

        return new CpfVoteEligibilityResult(RANDOM.nextBoolean() ? "ABLE_TO_VOTE" : "UNABLE_TO_VOTE");
    }

    private boolean isValidCpf(String cpf) {
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        int firstDigit = calculateDigit(cpf, 9, 10);
        int secondDigit = calculateDigit(cpf, 10, 11);
        return firstDigit == Character.getNumericValue(cpf.charAt(9))
            && secondDigit == Character.getNumericValue(cpf.charAt(10));
    }

    private int calculateDigit(String cpf, int length, int weightStart) {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (weightStart - i);
        }
        int remainder = 11 - (sum % 11);
        return remainder > 9 ? 0 : remainder;
    }
}
