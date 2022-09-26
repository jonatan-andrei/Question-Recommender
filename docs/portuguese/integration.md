# Integração de dados

# Seu sistema -> Question Recommender

## Posts

### POST /post
Endpoint para salvar um post.
```
integrationPostId (String): Id da publicação no sistema principal.
integrationParentPostId (String): Id da pergunta ou resposta onde está a resposta ou comentário.
postType (String): Tipo da publicação. Valores possíveis: QUESTION, ANSWER, QUESTION_COMMENT, ANSWER_COMMENT.
publicationDate (LocalDateTime): Data da publicação.
title (String): Título da pergunta.
contentOrDescription (String): Conteúdo da resposta ou comentário ou descrição da pergunta.
url (String): Link da pergunta.
integrationCategoriesIds (List-String): Categorias da pergunta.
tags(List-String): Tags da pergunta.
integrationUserId: Id do usuário no sistema principal.
integrationAnonymousUserId: Id da sessão do usuário no sistema principal, caso seja a publicação seja anônima.
```

### POST /post/list
Endpoint para salvar uma lista de posts. Os dados que devem ser enviados são iguais ao endpoint POST /post.

### PUT /post
Endpoint para atualizar um post.
```
integrationPostId (String): Id da publicação no sistema principal.
postType (String): Tipo da publicação. Valores possíveis: QUESTION, ANSWER, QUESTION_COMMENT, ANSWER_COMMENT.
title (String): Título da pergunta.
contentOrDescription (String): Conteúdo da resposta ou comentário ou descrição da pergunta.
url (String): Link da pergunta.
integrationCategoriesIds (List-String): Categorias da pergunta.
tags(List-String): Tags da pergunta.
```

### POST post/register-views
Endpoint para registrar usuários que visualizaram uma pergunta.
```
integrationPostId (String): Id da publicação no sistema principal.
integrationUsersId (List-String): Ids de usuários que visualizaram a publicação. Enviar no máximo 100 por vez.
totalViews (Integer): Total de visualizações da pergunta. Inclui visualizações anônimas.
```

### PUT /post/register-best-answer
Endpoint para marcar ou desmarcar uma resposta como a melhor para uma pergunta.
```
integrationQuestionId (String): Id da pergunta no sistema principal.
integrationAnswerId (String): Id da resposta no sistema principal.
selected (boolean): Se a resposta está selecionada.
```

### PUT /post/register-best-answer/list
Endpoint para marcar ou desmarcar uma lista de respostas como a melhores para perguntas. Os dados que devem ser enviados são iguais ao endpoint PUT /post/register-best-answer.

### PUT /post/register-duplicate_question
Endpoint para marcar ou desmarcar uma pergunta como duplicada
```
integrationQuestionId (String): Id da pergunta no sistema principal.
integrationDuplicateQuestionId (String): Id da pergunta da qual a integrationQuestionId é duplicada.
```

### PUT /post/register-duplicate_question/list
Endpoint para marcar ou desmarcar uma lista de perguntas como duplicadas. Os dados que devem ser enviados são iguais ao endpoint PUT /post/register-duplicate_question.

### PUT /post/hidden
Endpoint para ocultar ou tornar novamente visível publicação
```
integrationPostId (String): Id da publicação no sistema principal.
hidden (boolean): Se a publicação deve ficar oculta.
```

### POST /post/register-vote
Endpoint para registrar que um usuário votou em uma publicação.
```
integrationPostId (String): Id da publicação no sistema principal.
integrationUserId (String): Id do usuário no sistema principal.
voteType (String): Tipo do voto. Valores possíveis: VOTE, DOWNVOTE, REMOVED.
voteDate (LocalDateTime): Data do voto.
```

### POST /post/register-vote/list
Endpoint para registrar lista de usuários que votaram em publicações. Os dados que devem ser enviados são iguais ao endpoint POST /post/register-vote.

### POST /post/register-question-follower
Endpoint para registrar que um usuário seguiu uma pergunta.
``` 
integrationQuestionId (String): Id da publicação no sistema principal.
integrationUserId (String): Id do usuário no sistema principal.
followed (boolean): Se usuário está seguindo.
startDate (LocalDateTime): Data que o usuário começou a seguir.
```

### POST /post/register-question-follower/list
Endpoint para registrar lista de usuários seguindo perguntas. Os dados que devem ser enviados são iguais ao endpoint POST /post/register-question-follower.

## Usuários

### POST /user
Endpoint para registrar um usuário.
```
integrationUserId (String): Id do usuário no sistema principal.
integrationAnonymousUserId (String): Id da sessão do usuário no sistema principal.
registrationDate (LocalDateTime): Data de cadastro do usuário.
userPreferences.emailNotificationEnable (boolean): Se o usuário permite notificações por email.
userPreferences.emailNotificationHour (Integer): A hora do dia para envio das notificações por email (opcional).
userPreferences.notificationEnable (boolean): Se o usuário permite notificações.
userPreferences.explicitIntegrationCategoriesIds (List-String): Categorias que o usuário demonstrou interesse explicitamente.
userPreferences.explicitTags (List-String): Tags que o usuário demonstrou interesse explicitamente.
userPreferences.ignoredIntegrationCategoriesIds (List-String): Categorias que o usuário quer ignorar.
userPreferences.ignoredTags (List-String): Tags que o usuário quer ignorar.
```

### POST /user/list
Endpoint para registrar uma lista de usuários. Os dados que devem ser enviados são iguais ao endpoint POST /user.

### PUT /user
Endpoint para atualizar um usuário.
```
integrationUserId (String): Id do usuário no sistema principal.
active (boolean): Se usuário está ativo.
userPreferences.emailNotificationEnable (boolean): Se o usuário permite notificações por email.
userPreferences.emailNotificationHour (Integer): A hora do dia para envio das notificações por email (opcional).
userPreferences.notificationEnable (boolean): Se o usuário permite notificações.
userPreferences.explicitIntegrationCategoriesIds (List-String): Categorias que o usuário demonstrou interesse explicitamente.
userPreferences.explicitTags (List-String): Tags que o usuário demonstrou interesse explicitamente.
userPreferences.ignoredIntegrationCategoriesIds (List-String): Categorias que o usuário quer ignorar.
userPreferences.ignoredTags (List-String): Tags que o usuário quer ignorar.
```

### POST /user/register-follower
Endpoint para registrar um seguidor.
```
integrationUserId (String): Id do usuário no sistema principal.
integrationFollowerUserId (String): Id do usuário seguidor no sistema principal.
startDate (LocalDateTime): Data que o usuário começou a seguir.
followed (boolean): Se o usuário ainda segue o outro. O envio como 'false' removerá o vínculo.
```

### POST /user/register-follower/list
Endpoint para registrar uma lista de seguidores. Os dados que devem ser enviados são iguais ao endpoint POST /user/register-follower.

## Categorias

### POST /category
Endpoint para integração de uma categoria com Question Recommender.
```
integrationCategoryId (String): Id da categoria no sistema principal.
integrationParentCategoryId (String): Id da categoria pai no sistema principal (opcional).
name (String): Nome da categoria.
description (String): Descrição da categoria (opcional).
active (boolean): Se a categoria deve ser considerada.
```

### POST /category/list
Endpoint para integração de uma lista de categorias com Question Recommender. Os dados que devem ser enviados são iguais ao endpoint POST /category.

## Tags

### POST /tag
Endpoint para integração de uma tag com Question Recommender.
```
name (String): Nome da tag.
description (String): Descrição da tag (opcional).
active (boolean): Se a tag deve ser considerada.
```

### POST /tag/list
Endpoint para integração de uma lista de tags com Question Recommender. Os dados que devem ser enviados são iguais ao endpoint POST /tag.

## Lista de perguntas recomendadas

### GET /recommended-list
Endpoint para buscar lista de perguntas recomendadas para usuário.
#### Entrada (queryParams):
```
lengthQuestionListPage (Integer): Tamanho da lista. Se não informado será utilizado o valor default (opcional).
integrationUserId (String): Id do usuário no sistema principal.
recommendedListId (Long): Id da lista de recomendações. É utilizado na busca de uma nova página de uma lista já existente (opcional).
pageNumber (Integer): Número da página desejada (caso recommendedListId seja nulo informar 1 - primeira página).
dateOfRecommendations (LocalDateTime): Data para considerar na busca de perguntas (opcional).
```
#### Saída:
```
recommendedListId (Long): Id da lista de recomendações. É utilizado na busca de uma nova página de uma lista já existente.
totalNumberOfPages (Integer): Número total de páginas da lista.
questions (List): Lista de perguntas recomendadas:
    integrationQuestionId (String): Id da publicação no sistema principal.
    score (BigDecimal): Relevância da recomendação.
```

## Configurações

### POST /recommendation-settings
Endpoint para alterar configurações de recomendação.
```
recommendationSettings (Map<String, Integer>): Configurações de recomendações. Os valores possíveis são os do enum RecommendationSettingsType.
```

# Integração de dados

# Question Recommender -> Seu sistema

## Notificações de perguntas recomendadas
### POST /notification
Endpoint para integrar lista de notificações que devem ser enviadas.
```
notifications (list)
    integrationUserId (String): Id do usuário no sistema principal.
    integrationQuestionId (String): Id da publicação no sistema principal.
    score (BigDecimal): Relevância da recomendação.
```

## Email de perguntas recomendadas
### POST /recommended-email
Endpoint para integrar lista de emails que devem ser enviados.
```
emails (List)
    integrationUserId (String): Id do usuário no sistema principal.
        questions (List): Lista de perguntas recomendadas:
        integrationQuestionId (String): Id da publicação no sistema principal.
        score (BigDecimal): Relevância da recomendação.
```