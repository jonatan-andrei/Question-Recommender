# Configurações

As configurações necessárias para o Question Recommender podem ser feitas no arquivo application.properties (caminho src/main/resources/application.properties).<br/>

### Application port - Porta da aplicação
`` quarkus.http.port ``<br/>
A porta que a aplicação será executada. <br/>
``Valor default: 8079``

### Database connection - Conexão com o banco de dados
Parâmetros para que você configure sua conexão com o banco de dados de sua preferência, em ambiente de desenvolvimento ou produção.<br/>
``Valores default: Conexão com um banco local Postgres``

### Hibernate
`` quarkus.hibernate-orm.database.generation ``<br/>
Parâmetro que indica como o Hibernate irá interagir com a base de dados da aplicação. Com o valor default, ele irá gerar as tabelas no banco de dados, caso não existam. Não é necessário modificá-lo. <br/>
``Valor default: drop-and-create``

### Main application settings - Configurações do sistema principal
`` software.settings.language ``<br/>
Indica o idioma do seu sistema. [Utilize este padrão](http://www.lingoes.net/en/translator/langcode.htm). <br/>
``Valor default: en``

`` software.settings.enable_tags ``<br/>
Indica se o conteúdo do seu sistema é classificado por tags. Se habilitado, tags serão utilizadas nas recomendações.<br/>
``Valor default: true``

`` software.settings.enable_categories ``<br/>
Indica se o conteúdo do seu sistema é classificado por categorias. Se habilitado, categorias serão utilizadas nas recomendações.<br/>
``Valor default: true``

`` software.settings.enable_words ``<br/>
Se habilitado, palavras serão utilizadas nas recomendações.<br/>
``Valor default: true``

### Integration settings - Configurações da integração
`` integration.settings.enable_user_notifications ``<br/>
Indica se você deseja enviar notificações pelo sistema ao usuário.<br/>
``Valor default: true``

`` integration.settings.type_sending_user_notifications ``<br/>
Indica como as notificações serão integradas ao seu sistema para envio ao usuário. É possível desabilitar (NONE) ou receber via rest (REST) ou mensageria (MESSAGE).<br/>
``Valor default: REST``

`` integration.settings.enable_email_notifications ``<br/>
Indica se você deseja enviar emails pelo sistema ao usuário.<br/>
``Valor default: true``

`` integration.settings.type_sending_email_notifications  ``<br/>
Indica como os emails serão integradas ao seu sistema para envio ao usuário. É possível desabilitar (NONE) ou receber via rest (REST) ou mensageria (MESSAGE).<br/>
``Valor default: REST``

`` integration.settings.number_of_recommended_question_per_email_minimum``<br/>
Número mínimo de perguntas recomendadas para enviar email ao usuário.<br/>
``Valor default: 5``

`` integration.settings.number_of_recommended_question_per_email_maximum``<br/>
Número máximo de perguntas recomendadas para enviar email ao usuário.<br/>
``Valor default: 5``

`` integration.settings.default_email_notification_time``<br/>
Hora que iniciará o o processamento para envio de emails aos usuários. Também é possível configurar por usuário na tabela 'user'.<br/>
``Valor default: 17``

`` integration.settings.default_length_questions_list_page``<br/>
Tamanho padrão da lista de perguntas recomendadas. Também é possível especificar em cada busca.<br/>
``Valor default: 20``

### Recommendation settings - Configurações de recomendação
`` recommendation.settings.type_of_recommendations_question_list_page ``<br/>
Indica como deve gerar as recomendações de lista de perguntas. Apenas de acordo com os interesses informados pelo usuário (EXPLICIT) ou também de acordo com o que se descobriu pela atividade dele (EXPLICIT_AND_IMPLICIT).<br/>
``Valor default: EXPLICIT_AND_IMPLICIT``

`` recommendation.settings.type_of_recommendations_user_notifications ``<br/>
Indica como deve gerar as recomendações por notificação. Apenas de acordo com os interesses informados pelo usuário (EXPLICIT) ou também de acordo com o que se descobriu pela atividade dele (EXPLICIT_AND_IMPLICIT).<br/>
``Valor default: EXPLICIT_AND_IMPLICIT``

`` recommendation.settings.type_of_recommendations_email_notifications ``<br/>
Indica como deve gerar as recomendações por email. Apenas de acordo com os interesses informados pelo usuário (EXPLICIT) ou também de acordo com o que se descobriu pela atividade dele (EXPLICIT_AND_IMPLICIT).<br/>
``Valor default: EXPLICIT_AND_IMPLICIT``

`` recommendation.settings.recommend_answered_questions_question_list_page``<br/>
Define se deve considerar nas recomendações de lista, perguntas que já tenham respostas.<br/>
``Valor default: true``

`` recommendation.settings.recommend_answered_questions_email_notifications``<br/>
Define se deve considerar nas recomendações por email, perguntas que já tenham respostas.<br/>
``Valor default: true``

`` recommendation.settings.days_of_user_inactivity_to_suspend_notifications``<br/>
Se o usuário ficar esse período sem gerar nenhum conteúdo, não serão mais geradas recomendações por notificação para ele.<br/>
``Valor default: 60``

`` recommendation.settings.days_of_user_inactivity_to_suspend_emails``<br/>
Se o usuário ficar esse período sem gerar nenhum conteúdo, não serão mais geradas recomendações por email para ele.<br/>
``Valor default: 60``





