# Integração de dados

## Tags

### POST /tag
Endpoint para integração de uma tag com Question Recommender.
```
integration_tag_id (String): Id da tag no sistema principal
name (String): Nome da tag
description (String): Descrição da tag (opcional)
active (boolean): Se a tag deve ser considerada 
```

### POST /tag/list
Endpoint para integração de uma lista de tags com Question Recommender. Os dados que devem ser enviados são iguais ao endpoint POST /tag.
