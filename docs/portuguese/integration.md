# Integração de dados

## Posts
TODO

### PUT /post/hidden
Endpoint para ocultar ou tornar novamente visível publicação
```
integrationPostId (String): Id da publicação no sistema principal
hidden (boolean): Se a publicação deve ficar oculta
```

## Usuários
TODO

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
integrationTagId (String): Id da tag no sistema principal
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