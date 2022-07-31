## Implantar em ambiente dev

Antes de executar a aplicação, você precisará configurar sua conexão com o banco de dados de sua preferência.<br/>
Se você ainda não tiver nenhuma preferência, recomendamos o uso de Postgres. Se você utiliza Docker, precisará apenas executar os comandos abaixo:

```sudo docker pull postgres```

```sudo docker container run -d --name question-recommender-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=questionrecommender -p 5432:5432 postgres:latest```

Agora você já poderá executar local a aplicação com os seguintes comandos:

```./mvnw compile quarkus:dev``` <br/>
ou <br/>
```mvn compile quarkus:dev```

Se você fizer uma correção ou adicionar algo novo em QR, considere abrir um pull request.



