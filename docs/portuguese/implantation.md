### Implantação

Antes de executar a aplicação, você precisará configurar sua conexão com o banco de dados Postgres. 
Para alterar a conexão, utilize o arquivo application.properties na pasta src/resources.<br/>
Se você ainda não criou seu banco de dados e quer apenas executar a aplicação local, você pode utilizar Docker e precisará apenas executar os comandos abaixo:

```sudo docker pull postgres```

```sudo docker container run -d --name question-recommender-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=questionrecommender -p 5432:5432 postgres:latest```

Para realizar o deploy do Question Recommender, [confira este guia de como fazer o build e o deploy de uma aplicação Quarkus.](https://quarkus.io/guides/maven-tooling)<br/>
Para executar a aplicação em ambiente de desenvolvimento, use o comando:

```mvn compile quarkus:dev``` <br/>

Se você fizer uma correção ou adicionar algo novo em QR, considere abrir um pull request.



