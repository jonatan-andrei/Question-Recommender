create index ix_answer_question on answer(question_id);

create index ix_answer on answer(post_id, question_id);

create index ix_question_comment on question_comment(post_id, question_id);

create index ix_answer_comment on answer_comment(post_id, answer_id);

create index ix_tag_name on tag(name);