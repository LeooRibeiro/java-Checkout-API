# API de Checkout — Design Patterns em Java

Projeto de estudo com Spring Boot que simula a criação de pedidos e o pagamento
por Pix, cartão ou boleto. Os pagamentos são simulados: não há integração com
bancos ou gateways reais. Os dados ficam em um banco H2 em memória e são apagados
quando a aplicação é encerrada.

## Padrões aplicados

- **Strategy:** `EstrategiaPagamento` define o contrato; `PagamentoPix`,
  `PagamentoCartao` e `PagamentoBoleto` implementam comportamentos diferentes.
- **Facade:** `CheckoutFacade` reúne busca do pedido, seleção da estratégia,
  processamento e atualização do status. O controller não precisa conhecer
  esses detalhes.
- **Singleton:** `ConfiguracaoLoja` é um componente Spring. Por padrão, o Spring
  mantém uma única instância de cada componente por contexto da aplicação e a
  compartilha com os objetos que dependem dela.

## Requisitos e execução

É necessário ter Java 17 ou superior. Na pasta do projeto, execute:

```bash
./gradlew bootRun
```

No Windows, use `gradlew.bat bootRun`. A API inicia em `http://localhost:8080`.
O console do H2 fica em `http://localhost:8080/h2-console` (JDBC URL
`jdbc:h2:mem:checkoutdb`, usuário `sa`, senha em branco).

## Exemplos de uso

Criar um pedido:

```bash
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"descricao":"Curso de Java","valor":99.90}'
```

Escolher a forma de pagamento (use o `id` retornado na criação):

```bash
curl -X POST http://localhost:8080/api/pedidos/1/checkout \
  -H "Content-Type: application/json" \
  -d '{"formaPagamento":"PIX"}'
```

Valores aceitos para `formaPagamento`: `PIX`, `CARTAO` e `BOLETO`.

Consultar pedidos:

```bash
curl http://localhost:8080/api/pedidos
curl http://localhost:8080/api/pedidos/1
```

## Rotas

| Método | Rota | Ação |
| --- | --- | --- |
| `POST` | `/api/pedidos` | Cria pedido |
| `GET` | `/api/pedidos` | Lista pedidos |
| `GET` | `/api/pedidos/{id}` | Consulta pedido |
| `POST` | `/api/pedidos/{id}/checkout` | Processa pagamento |
