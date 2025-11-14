package com.alura.model;

public interface ExchangeService {
    double convert(String base, String target, double amount) throws Exception;
}
