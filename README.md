# API de Checkout

API simples para criar pedidos e simular pagamentos por Pix, cartão ou boleto.

## Instalação e execução

1. Instale o **JDK 17** ou superior.
2. Clone o repositório:

   ```bash
   git clone https://github.com/LeooRibeiro/java-Checkout-API.git
   cd java-Checkout-API
   ```

3. Inicie a aplicação:

   ```bash
   ./gradlew bootRun
   ```

   No Windows, use `gradlew.bat bootRun`.

A API inicia em `http://localhost:8080`. Os dados são guardados em um banco H2
em memória e apagados quando a aplicação é encerrada.

## Como usar

Crie um pedido. A resposta inclui o identificador (`id`) que será usado no
checkout:

```http
POST /api/pedidos
Content-Type: application/json

{
  "descricao": "Curso de Java",
  "valor": 99.90
}
```

Faça o checkout usando o `id` retornado. As formas aceitas são `PIX`, `CARTAO`
e `BOLETO`:

```http
POST /api/pedidos/1/checkout
Content-Type: application/json

{
  "formaPagamento": "PIX"
}
```

Consulte os pedidos:

```http
GET /api/pedidos
GET /api/pedidos/1
```

## Design patterns

- **Strategy:** `EstrategiaPagamento` define o contrato de pagamento. As classes
  `PagamentoPix`, `PagamentoCartao` e `PagamentoBoleto` implementam esse
  contrato, permitindo escolher a forma de pagamento sem colocar todas as
  regras em uma única classe.
- **Facade:** `CheckoutFacade` reúne as etapas do checkout: localizar o pedido,
  escolher e executar a estratégia de pagamento e atualizar o status. O
  controller chama uma operação simples sem coordenar essas etapas.
- **Singleton:** `ConfiguracaoLoja` é um componente Spring. O escopo padrão do
  Spring mantém uma instância compartilhada por contexto da aplicação.
