# Configurações do sistema

As configurações necessárias para o Question Recommender podem ser feitas no arquivo application.properties (src/main/resources/application.properties).<br/>

### Application port - Porta da aplicação
`` quarkus.http.port ``<br/>
A porta que a aplicação será executada. <br/>
``Valor default: 8079``

`` quarkus.http.test-port ``<br/>
A porta que a aplicação será executada nos testes integrados. <br/>
``Valor default: 8888``

### Database connection - Conexão com o banco de dados
Parâmetros para que você configure sua conexão com o banco de dados de sua preferência, em ambiente de desenvolvimento ou produção.<br/>
``Valores default: Conexão com um banco local Postgres``

### Hibernate
`` quarkus.hibernate-orm.database.generation ``<br/>
Parâmetro que indica como o Hibernate irá interagir com a base de dados da aplicação.<br/>
``Valor default: update``

### Scheduled - Processos agendados
`` cron.recommended-email ``<br/>
Parâmetro que indica de quanto em quanto tempo será executada a rotina para geração de recomendações através de e-mail.<br/>
``Valor default: 0 0 * * * ?`` (1 hora) <br/>

`` cron.question-notification ``<br/>
Parâmetro que indica de quanto em quanto tempo será executada a rotina para geração de recomendações através de notificação.<br/>
``Valor default: 0 * * * * ?`` (1 minuto) <br/>

## Recommended email proxy
Parâmetros para que você configure sua conexão com o sistema principal, para onde o Question Recommender enviará as recomendações para serem enviadas por e-mail, caso este canal esteja ativo.<br/>
``Valores default: Conexão com o QR Data Converter (aplicação para testes do Question Recommender)``

## Question notification proxy
Parâmetros para que você configure sua conexão com o sistema principal, para onde o Question Recommender enviará as recomendações para serem enviadas por notificação, caso este canal esteja ativo.<br/>
``Valores default: Conexão com o QR Data Converter (aplicação para testes do Question Recommender)``

# Configurações de recomendação
Os parâmetros para configuração do sistema de recomendação podem ser modificados através do endpoint PUT /recommendation-settings.<br/>
Os parâmetros possíveis para cada canal podem ser consultados [aqui](https://github.com/jonatan-andrei/Question-Recommender/blob/main/src/main/java/jonatan/andrei/domain/RecommendationChannelType.java).



