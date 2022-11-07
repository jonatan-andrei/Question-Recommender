create index ix_question_tag ON question_tag(tag_id, question_id);

create index ix_user_tag on user_tag(tag_id, user_id);

create index ix_user_tag_ignored on user_tag(tag_id, user_id, ignored);

create index ix_category_integration on category(integration_category_id);

CREATE index ix_question_category ON question_category(category_id, question_id);