package com.example.demo.payment;

import com.example.demo.model.FormaPagamento;
import java.math.BigDecimal;

/** Strategy: cada forma de pagamento implementa a mesma operação de um jeito próprio. */
public interface EstrategiaPagamento {
	FormaPagamento formaPagamento();
	String processar(BigDecimal valor);
}
