create index ix_question_follower on question_follower(user_id, question_id);

create index ix_recommended_list_page_question on recommended_list_page_question(recommended_list_page_id);

create index ix_recommended_list_page on recommended_list_page(recommended_list_id);

create index ix_recommended_list_page_page_number on recommended_list_page(recommended_list_id, page_number);

create index ix_question_notification on question_notification(question_id, user_id);

create index ix_question_notification_queue on question_notification_queue(question_id);
