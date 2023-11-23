# Wallet Microservice - Compra e Venda de CDBs

## Descrição

Este microsserviço faz parte de um projeto Java utilizando o framework Spring Boot para criar um sistema para compra e
venda de CDBs para clientes.
Esta aplicação é responsável por gerenciar a negociação de CDBs dos clientes. Ela comunica com o microsservico de
clientes para buscar informações de saldo em conta e enviar atualizações sobre a mesma.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot (3.1.5)**
- **Swagger**
- **[Docker](https://docs.docker.com/desktop/)**
- **Gradle**
- **JUnit e Mockito**

## Diagrama

O projeto basicamente segue uma arquitetura de três camadas (Controller, Service, Repository) para garantir a separação
de responsabilidades e a modularidade do código. Aqui está uma breve descrição de cada camada:

- **Controller**: Responsável por lidar com as requisições HTTP, interagir com o serviço apropriado e retornar as
  respostas adequadas.
- **Service**: Contém a lógica de negócios da aplicação, coordenando as operações entre o controlador e o repositório.
- **Repository**: Responsável pela interação direta com o banco de dados, utilizando Spring Data JPA para operações de
  CRUD.

A imagem abaixo representa esquematicamente a comunicação entre as camadas do microsserviço e também a comunicação com o
banco de dados, bem como as tabelas de cliente e conta.

![Diagrama de comunicação da aplicação](./images/CDB-wallet.png)

A comunicação com o microsserviço de clientes é feito via REST.

## Configuração

1. Clone o repositório.
2. Certifique-se de ter o Docker instalado em sua máquina.
3. Execute o seguinte comando para criar e iniciar o contêiner PostgreSQL:

```bash
docker-compose -f docker-compose.yml up -d
```

4. Execute a aplicação usando o Gradle: `./gradlew bootRun`.

## Documentação da API

A API é documentada usando o Swagger. Para acessar a documentação, inicie a aplicação e vá
para [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html).

### Endpoints

#### `POST /trading/buy`

- Compra papéis de CDB para um cliente e cria uma carteira para o mesmo.

```json
{
  "document": "12345678910",
  "amount": 1
}
```

#### `POST /trading/sell`

- Vende todos os papéis de CDB de um cliente e exclui todas suas carteiras.

```json
{
  "document": "12345678910"
}
```

### Logs

A aplicação possui logs de ratreamento de todas as transações que são feitas. Ao realizar a compra ou venda de CDBs, um
id no formato _UUID_ é criado para a transação o qual é possível acompanhar em todo o fluxo de compra ou venda.

## Testes

Os testes unitários são implementados usando JUnit e Mockito. Execute os testes usando o comando:

```bash
./gradlew test
```