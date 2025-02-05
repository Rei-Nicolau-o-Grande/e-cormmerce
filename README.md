# Documentação do Sistema de Microserviços de E-Cormmerce com Kafka, MongoDB, Spring Boot e Java

Este projeto é um exemplo de um sistema de **e-commerce baseado em microserviços**, utilizando **Spring Boot**, **Apache Kafka** para comunicação assíncrona, **MongoDB** para persistência de dados, **Service Discovery** para registro dinâmico dos serviços e **Spring Cloud Gateway** para roteamento de requisições.

## Arquitetura do Sistema

O sistema é projetado para ser escalável, desacoplado e resiliente. Os microserviços se comunicam por meio de **tópicos Kafka**, enquanto o **Spring Cloud Gateway** atua como um ponto central de entrada, e o **Service Discovery** permite a descoberta dinâmica de serviços.

### Microserviços Principais

1.  **Order Service**: Responsável por receber e processar pedidos.
2.  **Product Service**: Responsável por gerenciar o estoque de produtos.
3.  **Notification Service**: Responsável por enviar notificações aos clientes.

### Componentes Adicionais

-   **Spring Cloud Gateway**: Atua como um API Gateway para rotear e balancear requisições.
-   **Service Discovery** (Eureka/Consul/Zookeeper): Gerencia o registro e descoberta de serviços dinamicamente.
-   **Apache Kafka**: Facilita a comunicação assíncrona entre os microserviços.
-   **MongoDB**: Banco de dados NoSQL usado para armazenar informações dos serviços.

----------

## Fluxo de Comunicação

O fluxo de comunicação entre os microserviços ocorre da seguinte forma:

1.  **Order Service**:

    -   Recebe um pedido do cliente via API Gateway.
    -   Publica uma mensagem no tópico `order-created` no Kafka.
2.  **Product Service**:

    -   Cria, Atualiza, Buscar e Desativa  o Produto.
    -   Consome mensagens do tópico `order-created`.
    -   Verifica o estoque e atualiza o banco de dados.
    -   Publica uma mensagem no tópico `order-confirmed` (sucesso) ou `order-fail` (falha).
3.  **Notification Service**:

    -   Consome mensagens dos tópicos `order-confirmed` e `order-fail`.
    -   Envia notificações ao cliente com base no status do pedido.
4.  **Service Discovery & Gateway**:

    -   O Gateway recebe as requisições e as direciona para os microserviços apropriados.
    -   O Service Discovery garante que os microserviços possam se localizar dinamicamente.

----------

## Configuração do Kafka

Os seguintes tópicos são utilizados para comunicação entre os microserviços:

-   **order-created**: Tópico onde os pedidos são publicados pelo `Order Service`.
-   **order-confirmed**: Tópico onde os pedidos confirmados são publicados pelo `Product Service`.
-   **order-fail**: Tópico onde os pedidos falhos são publicados pelo `Product Service`.

----------

## Considerações Finais

-   O uso de **Kafka** garante um sistema altamente desacoplado e escalável.
-   O **Service Discovery** permite a descoberta dinâmica de serviços, facilitando o balanceamento de carga e a resiliência do sistema.
-   O **Spring Cloud Gateway** centraliza o gerenciamento de APIs e facilita a segurança e controle de acesso.

Este sistema pode ser expandido com novas funcionalidades, como autenticação, caching e monitoramento distribuído.

## UI (User Interface)
-  Link: https://github.com/Rei-Nicolau-o-Grande/UI-E-Cormmerce
