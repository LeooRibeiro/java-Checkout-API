package com.example.demo.payment;

import com.example.demo.model.FormaPagamento;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PagamentoCartao implements EstrategiaPagamento {
	@Override public FormaPagamento formaPagamento() { return FormaPagamento.CARTAO; }
	@Override public String processar(BigDecimal valor) { return "Pagamento no cartão de R$ " + valor + " aprovado."; }
}
