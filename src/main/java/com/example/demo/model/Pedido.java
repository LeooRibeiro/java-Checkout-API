package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "pedidos")
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	@Enumerated(EnumType.STRING)
	private FormaPagamento formaPagamento;

	protected Pedido() { }

	public Pedido(String descricao, BigDecimal valor) {
		this.descricao = descricao;
		this.valor = valor;
		this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
	}

	public void aprovarPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
		this.status = StatusPedido.PAGO;
	}

	public Long getId() { return id; }
	public String getDescricao() { return descricao; }
	public BigDecimal getValor() { return valor; }
	public StatusPedido getStatus() { return status; }
	public FormaPagamento getFormaPagamento() { return formaPagamento; }
}
