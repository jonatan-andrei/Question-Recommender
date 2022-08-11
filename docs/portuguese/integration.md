# Integração de dados

## Posts
TODO

### PUT /post/register-best-answer
Endpoint para marcar ou desmarcar uma resposta como a melhor para uma pergunta.
```
integrationQuestionId (String): Id da pergunta no sistema principal
integrationAnswerId (String): Id da resposta no sistema principal
selected (boolean): Se a resposta está selecionada
```

### PUT /post/register-best-answer
Endpoint para marcar ou desmarcar uma lista de respostas como a melhores para perguntas. Os dados que devem ser enviados são iguais ao endpoint PUT /post/register-best-answer.

### PUT /post/register-duplicate_question
Endpoint para marcar ou desmarcar uma pergunta como duplicada
```
integrationQuestionId (String): Id da pergunta no sistema principal
integrationDuplicateQuestionId (String): Id da pergunta da qual a integrationQuestionId é duplicada
```

### PUT /post/register-duplicate_question/list
Endpoint para marcar ou desmarcar uma lista de perguntas como duplicadas. Os dados que devem ser enviados são iguais ao endpoint PUT /post/register-duplicate_question.

### PUT /post/hidden
Endpoint para ocultar ou tornar novamente visível publicação
```
integrationPostId (String): Id da publicação no sistema principal
hidden (boolean): Se a publicação deve ficar oculta
```

## Usuários
TODO

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
integrationCategoryId (String): Id da categoria no sistema principal
integrationParentCategoryId (String): Id da categoria pai no sistema principal (opcional)
name (String): Nome da categoria
description (String): Descrição da categoria (opcional)
active (boolean): Se a categoria deve ser considerada 
```

### POST /category/list
Endpoint para integração de uma lista de categorias com Question Recommender. Os dados que devem ser enviados são iguais ao endpoint POST /category.

## Tags

### POST /tag
Endpoint para integração de uma tag com Question Recommender.
```
name (String): Nome da tag
description (String): Descrição da tag (opcional)
active (boolean): Se a tag deve ser considerada 
```

### POST /tag/list
Endpoint para integração de uma lista de tags com Question Recommender. Os dados que devem ser enviados são iguais ao endpoint POST /tag.

## Notificações
TODO

## Emails
TODO

## Lista de perguntas
TODO