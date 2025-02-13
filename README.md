# Exchange Rates API

Esta api retorna o fator de conversão entre duas moedas.
Exemplo: targetCode = BRL, baseCode=USD. O retorno será um valor x, sendo que x unidades de BRL equivalem a 1 USD.

#### Exemplo de chamada para o projeto rodando localmente na porta 8080:
`curl --location 'localhost:8080/api/v1/currency-rate?baseCode=USD&targetCode=BRL'`

## Requisitos
* Docker
* JDK e JRE 23 para execução local.
* Chave de API da Exchange API. (https://www.exchangerate-api.com/).

## Rodando Localmente

* Iniciar o banco de dados local usando o comando
  `docker compose up`.
* Adicionar o chave de API mencionada acima como valor da variável de ambiente `EXCHANGE_API_KEY`
* Iniciar a aplicação Spring com o seguinte argumento `--spring.profiles.active=local`

## Rodando com configurações de produção e banco de dados embutido
* Colocar a chave de API no arquivo `deploy/docker-compose.yml` substituindo o placeholder `<place your Exchange API KEY Here>`

* Executar o comando: `docker compose -f deploy/docker-compose.yml -d --build`