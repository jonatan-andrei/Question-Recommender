create index ix_recommendation_settings on recommendation_settings(channel);

create index ix_recommended_email_question on recommended_email_question(recommended_email_id);

create index ix_recommended_email on recommended_email(integration_user_id);

create index ix_vote on vote(user_id, post_id);