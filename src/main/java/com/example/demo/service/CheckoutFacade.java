package com.example.demo.service;

import com.example.demo.config.ConfiguracaoLoja;
import com.example.demo.dto.CheckoutRequest;
import com.example.demo.model.FormaPagamento;
import com.example.demo.model.Pedido;
import com.example.demo.model.StatusPedido;
import com.example.demo.payment.EstrategiaPagamento;
import com.example.demo.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Facade: oferece uma operação simples e coordena os detalhes do checkout. */
@Service
public class CheckoutFacade {
	private final PedidoRepository pedidoRepository;
	private final Map<FormaPagamento, EstrategiaPagamento> estrategias;
	private final ConfiguracaoLoja configuracaoLoja;

	public CheckoutFacade(PedidoRepository pedidoRepository,
	                      List<EstrategiaPagamento> estrategias,
	                      ConfiguracaoLoja configuracaoLoja) {
		this.pedidoRepository = pedidoRepository;
		// O Spring fornece todas as estratégias; mapeamos cada uma pelo tipo de pagamento.
		this.estrategias = estrategias.stream().collect(Collectors.toMap(
				EstrategiaPagamento::formaPagamento, Function.identity()));
		this.configuracaoLoja = configuracaoLoja;
	}

	@Transactional
	public String finalizar(Long pedidoId, CheckoutRequest request) {
		Pedido pedido = pedidoRepository.findById(pedidoId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
		if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Este pedido já foi pago");
		}
		EstrategiaPagamento estrategia = estrategias.get(request.formaPagamento());
		String resultado = estrategia.processar(pedido.getValor());
		pedido.aprovarPagamento(request.formaPagamento());
		pedidoRepository.save(pedido);
		return configuracaoLoja.nomeLoja() + ": " + resultado;
	}
}
