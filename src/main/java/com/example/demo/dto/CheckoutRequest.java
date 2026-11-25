package com.example.demo.dto;

import com.example.demo.model.FormaPagamento;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(@NotNull FormaPagamento formaPagamento) { }
