package com.example.demo.payment;

import com.example.demo.model.FormaPagamento;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PagamentoBoleto implements EstrategiaPagamento {
	@Override public FormaPagamento formaPagamento() { return FormaPagamento.BOLETO; }
	@Override public String processar(BigDecimal valor) { return "Boleto de R$ " + valor + " gerado."; }
}
