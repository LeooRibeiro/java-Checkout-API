package com.example.demo.payment;

import com.example.demo.model.FormaPagamento;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class PagamentoPix implements EstrategiaPagamento {
	@Override public FormaPagamento formaPagamento() { return FormaPagamento.PIX; }
	@Override public String processar(BigDecimal valor) { return "Pagamento Pix de R$ " + valor + " aprovado."; }
}
