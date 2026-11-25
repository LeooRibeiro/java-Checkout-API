package com.example.demo.controller;

import com.example.demo.dto.CheckoutRequest;
import com.example.demo.dto.CriarPedidoRequest;
import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.service.CheckoutFacade;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	private final PedidoRepository pedidoRepository;
	private final CheckoutFacade checkoutFacade;

	public PedidoController(PedidoRepository pedidoRepository, CheckoutFacade checkoutFacade) {
		this.pedidoRepository = pedidoRepository;
		this.checkoutFacade = checkoutFacade;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido criar(@Valid @RequestBody CriarPedidoRequest request) {
		return pedidoRepository.save(new Pedido(request.descricao(), request.valor()));
	}

	@GetMapping
	public List<Pedido> listar() { return pedidoRepository.findAll(); }

	@GetMapping("/{id}")
	public Pedido buscar(@PathVariable Long id) {
		return pedidoRepository.findById(id).orElseThrow(() ->
				new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}

	@PostMapping("/{id}/checkout")
	public CheckoutResponse checkout(@PathVariable Long id, @Valid @RequestBody CheckoutRequest request) {
		// O controller delega o fluxo à Facade e fica focado na camada HTTP.
		return new CheckoutResponse(checkoutFacade.finalizar(id, request));
	}

	public record CheckoutResponse(String mensagem) { }
}
