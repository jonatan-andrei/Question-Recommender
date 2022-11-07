create index ix_user_category on user_category(category_id, user_id);

create index ix_user_category_ignored on user_category(category_id, user_id, ignored);

create index ix_user_follower on user_follower(follower_id, user_id);

create index ix_total_activity_system on total_activity_system(post_classification_type);

create index ix_question_view on question_view(question_id, user_id);