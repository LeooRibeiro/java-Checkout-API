package com.example.demo.config;

import org.springframework.stereotype.Component;

/**
 * Singleton gerenciado pelo Spring: existe uma instância compartilhada por todo
 * o contexto da aplicação (escopo padrão dos componentes Spring).
 */
@Component
public class ConfiguracaoLoja {
	public String nomeLoja() { return "Loja Exemplo"; }
}
