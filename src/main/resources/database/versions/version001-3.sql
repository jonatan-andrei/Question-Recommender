create index ix_user_integration on users(integration_user_id);

create index ix_user_anonymous on users(integration_anonymous_user_id);

create index ix_user_integration_anonymous on users(integration_user_id, integration_anonymous_user_id);