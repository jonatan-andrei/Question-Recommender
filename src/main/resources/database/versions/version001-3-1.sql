create index ix_post_integration on post(integration_post_id);

create index ix_post_integration_post_type on post(integration_post_id, post_type);

create index ix_post_user on post(user_id);

create index ix_post_publication_date on post(publication_date);

create index ix_post_user_post_id on post(post_id, user_id);