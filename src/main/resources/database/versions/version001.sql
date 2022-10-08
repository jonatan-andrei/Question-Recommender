CREATE TABLE public.category (
	category_id int8 NOT NULL,
	active bool NULL,
	description varchar(1000) NULL,
	integration_category_id varchar(100) NULL,
	"name" varchar(100) NULL,
	number_answers_downvoted numeric(19, 2) NULL,
	number_answers_upvoted numeric(19, 2) NULL,
	number_comments_downvoted numeric(19, 2) NULL,
	number_comments_upvoted numeric(19, 2) NULL,
	number_questions_answered numeric(19, 2) NULL,
	number_questions_asked numeric(19, 2) NULL,
	number_questions_commented numeric(19, 2) NULL,
	number_questions_downvoted numeric(19, 2) NULL,
	number_questions_followed numeric(19, 2) NULL,
	number_questions_upvoted numeric(19, 2) NULL,
	number_questions_viewed numeric(19, 2) NULL,
	parent_category_id int8 NULL,
	CONSTRAINT category_pkey PRIMARY KEY (category_id),
	CONSTRAINT uk_46ccwnsi9409t36lurvtyljak UNIQUE (name),
	CONSTRAINT uk_4hu84pf4dflhtt262jnn2ewoa UNIQUE (integration_category_id)
);

CREATE TABLE public.post (
	post_id int8 NOT NULL,
	downvotes int4 NULL,
	hidden bool NULL,
	integration_date timestamp NULL,
	integration_post_id varchar(255) NULL,
	post_type varchar(255) NULL,
	publication_date timestamp NULL,
	update_date timestamp NULL,
	upvotes int4 NULL,
	user_id int8 NULL,
	CONSTRAINT post_pkey PRIMARY KEY (post_id),
	CONSTRAINT uk_kxyla25sha7yw9suyvrhwe6js UNIQUE (integration_post_id)
);

CREATE TABLE public.question_category (
	question_category_id int8 NOT NULL,
	category_id int8 NULL,
	question_id int8 NULL,
	CONSTRAINT question_category_pkey PRIMARY KEY (question_category_id)
);

CREATE TABLE public.question_follower (
	question_follower_id int8 NOT NULL,
	question_id int8 NULL,
	start_date timestamp NULL,
	user_id int8 NULL,
	CONSTRAINT question_follower_pkey PRIMARY KEY (question_follower_id)
);

CREATE TABLE public.question_notification (
	question_notification_id int8 NOT NULL,
	integration_question_id varchar(255) NULL,
	integration_user_id varchar(255) NULL,
	question_id int8 NULL,
	notification_date timestamp NULL,
	user_id int8 NULL,
	CONSTRAINT question_notification_pkey PRIMARY KEY (question_notification_id)
);

CREATE TABLE public.question_notification_queue (
	question_notification_queue_id int8 NOT NULL,
	date_was_ignored timestamp NULL,
	integration_question_id varchar(255) NULL,
	question_id int8 NULL,
	send_date timestamp NULL,
	CONSTRAINT question_notification_queue_pkey PRIMARY KEY (question_notification_queue_id)
);

CREATE TABLE public.question_tag (
	question_tag_id int8 NOT NULL,
	question_id int8 NULL,
	tag_id int8 NULL,
	CONSTRAINT question_tag_pkey PRIMARY KEY (question_tag_id)
);

CREATE TABLE public.question_view (
	question_view_id int8 NOT NULL,
	number_of_recommendations_in_email int4 NULL,
	number_of_recommendations_in_list int4 NULL,
	number_of_views int4 NULL,
	question_id int8 NULL,
	recommended_in_notification bool NULL,
	user_id int8 NULL,
	CONSTRAINT question_view_pkey PRIMARY KEY (question_view_id)
);

CREATE TABLE public.recommendation_settings (
	recommendation_settings_id int8 NOT NULL,
	channel int4 NULL,
	"name" int4 NULL,
	value numeric(19, 2) NULL,
	CONSTRAINT recommendation_settings_pkey PRIMARY KEY (recommendation_settings_id)
);

CREATE TABLE public.recommended_email (
	recommended_email_id int8 NOT NULL,
	integration_user_id varchar(255) NULL,
	send_date timestamp NULL,
	user_id int8 NULL,
	CONSTRAINT recommended_email_pkey PRIMARY KEY (recommended_email_id)
);

CREATE TABLE public.recommended_email_question (
	recommended_email_question_id int8 NOT NULL,
	integration_question_id varchar(255) NULL,
	question_id int8 NULL,
	recommended_email_id int8 NULL,
	score numeric(19, 2) NULL,
	CONSTRAINT recommended_email_question_pkey PRIMARY KEY (recommended_email_question_id)
);

CREATE TABLE public.recommended_list (
	recommended_list_id int8 NOT NULL,
	length_question_list_page int4 NULL,
	list_date timestamp NULL,
	total_number_of_pages int4 NULL,
	total_number_of_questions int4 NULL,
	user_id int8 NULL,
	CONSTRAINT recommended_list_pkey PRIMARY KEY (recommended_list_id)
);

CREATE TABLE public.recommended_list_page (
	recommended_list_page_id int8 NOT NULL,
	page_number int4 NULL,
	recommended_list_id int8 NULL,
	CONSTRAINT recommended_list_page_pkey PRIMARY KEY (recommended_list_page_id)
);

CREATE TABLE public.recommended_list_page_question (
	recommended_list_page_question_id int8 NOT NULL,
	integration_question_id varchar(255) NULL,
	question_id int8 NULL,
	recommended_list_page_id int8 NULL,
	score numeric(19, 2) NULL,
	CONSTRAINT recommended_list_page_question_pkey PRIMARY KEY (recommended_list_page_question_id)
);

CREATE TABLE public.tag (
	tag_id int8 NOT NULL,
	active bool NULL,
	description varchar(1000) NULL,
	"name" varchar(100) NULL,
	number_answers_downvoted numeric(19, 2) NULL,
	number_answers_upvoted numeric(19, 2) NULL,
	number_comments_downvoted numeric(19, 2) NULL,
	number_comments_upvoted numeric(19, 2) NULL,
	number_questions_answered numeric(19, 2) NULL,
	number_questions_asked numeric(19, 2) NULL,
	number_questions_commented numeric(19, 2) NULL,
	number_questions_downvoted numeric(19, 2) NULL,
	number_questions_followed numeric(19, 2) NULL,
	number_questions_upvoted numeric(19, 2) NULL,
	number_questions_viewed numeric(19, 2) NULL,
	CONSTRAINT tag_pkey PRIMARY KEY (tag_id),
	CONSTRAINT uk_1wdpsed5kna2y38hnbgrnhi5b UNIQUE (name)
);

CREATE TABLE public.test_result (
	test_result_id int8 NOT NULL,
	days_after_dump_considered int4 NULL,
	dump_name varchar(255) NULL,
	integrated_dump_percentage numeric(19, 2) NULL,
	number_of_questions int4 NULL,
	number_of_recommended_questions int4 NULL,
	number_of_users int4 NULL,
	percentage_of_correct_recommendations numeric(19, 2) NULL,
	settings varchar(80000) NULL,
	test_date timestamp NULL,
	total_activity_system varchar(80000) NULL,
	CONSTRAINT test_result_pkey PRIMARY KEY (test_result_id)
);

CREATE TABLE public.test_result_user (
	test_result_user_id int8 NOT NULL,
	error bool NULL,
	integration_user_id varchar(255) NULL,
	number_of_questions int4 NULL,
	number_of_recommended_questions int4 NULL,
	percentage_of_correct_recommendations numeric(19, 2) NULL,
	questions varchar(80000) NULL,
	recommended_questions varchar(80000) NULL,
	test_result_id int8 NULL,
	user_tags varchar(80000) NULL,
	CONSTRAINT test_result_user_pkey PRIMARY KEY (test_result_user_id)
);

CREATE TABLE public.total_activity_system (
	total_activity_system_id int8 NOT NULL,
	number_answers_downvoted numeric(19, 2) NULL,
	number_answers_upvoted numeric(19, 2) NULL,
	number_comments_downvoted numeric(19, 2) NULL,
	number_comments_upvoted numeric(19, 2) NULL,
	number_questions_answered numeric(19, 2) NULL,
	number_questions_asked numeric(19, 2) NULL,
	number_questions_commented numeric(19, 2) NULL,
	number_questions_downvoted numeric(19, 2) NULL,
	number_questions_followed numeric(19, 2) NULL,
	number_questions_upvoted numeric(19, 2) NULL,
	number_questions_viewed numeric(19, 2) NULL,
	post_classification_type varchar(255) NULL,
	CONSTRAINT total_activity_system_pkey PRIMARY KEY (total_activity_system_id)
);

CREATE TABLE public.user_category (
	user_category_id int8 NOT NULL,
	category_id int8 NULL,
	explicit_recommendation bool NULL,
	ignored bool NULL,
	number_answers_downvoted numeric(19, 2) NULL,
	number_answers_upvoted numeric(19, 2) NULL,
	number_comments_downvoted numeric(19, 2) NULL,
	number_comments_upvoted numeric(19, 2) NULL,
	number_questions_answered numeric(19, 2) NULL,
	number_questions_asked numeric(19, 2) NULL,
	number_questions_commented numeric(19, 2) NULL,
	number_questions_downvoted numeric(19, 2) NULL,
	number_questions_followed numeric(19, 2) NULL,
	number_questions_upvoted numeric(19, 2) NULL,
	number_questions_viewed numeric(19, 2) NULL,
	user_id int8 NULL,
	CONSTRAINT user_category_pkey PRIMARY KEY (user_category_id)
);

CREATE TABLE public.user_follower (
	user_follower_id int8 NOT NULL,
	follower_id int8 NULL,
	start_date timestamp NULL,
	user_id int8 NULL,
	CONSTRAINT user_follower_pkey PRIMARY KEY (user_follower_id)
);

CREATE TABLE public.user_tag (
	user_tag_id int8 NOT NULL,
	explicit_recommendation bool NULL,
	ignored bool NULL,
	number_answers_downvoted numeric(19, 2) NULL,
	number_answers_upvoted numeric(19, 2) NULL,
	number_comments_downvoted numeric(19, 2) NULL,
	number_comments_upvoted numeric(19, 2) NULL,
	number_questions_answered numeric(19, 2) NULL,
	number_questions_asked numeric(19, 2) NULL,
	number_questions_commented numeric(19, 2) NULL,
	number_questions_downvoted numeric(19, 2) NULL,
	number_questions_followed numeric(19, 2) NULL,
	number_questions_upvoted numeric(19, 2) NULL,
	number_questions_viewed numeric(19, 2) NULL,
	tag_id int8 NULL,
	user_id int8 NULL,
	CONSTRAINT user_tag_pkey PRIMARY KEY (user_tag_id)
);

CREATE TABLE public.users (
	user_id int8 NOT NULL,
	active bool NULL,
	anonymous bool NULL,
	email_notification_enable bool NULL,
	email_notification_hour int4 NULL,
	integration_anonymous_user_id varchar(100) NULL,
	integration_date timestamp NULL,
	integration_user_id varchar(100) NULL,
	last_activity_date timestamp NULL,
	number_answers_downvoted numeric(19, 2) NULL,
	number_answers_upvoted numeric(19, 2) NULL,
	number_comments_downvoted numeric(19, 2) NULL,
	number_comments_upvoted numeric(19, 2) NULL,
	number_questions_answered numeric(19, 2) NULL,
	number_questions_asked numeric(19, 2) NULL,
	number_questions_commented numeric(19, 2) NULL,
	number_questions_downvoted numeric(19, 2) NULL,
	number_questions_followed numeric(19, 2) NULL,
	number_questions_upvoted numeric(19, 2) NULL,
	number_questions_viewed numeric(19, 2) NULL,
	question_notification_enable bool NULL,
	registration_date timestamp NULL,
	username varchar(255) NULL,
	CONSTRAINT uk_nvpahci1ws3i9oyoljxs9dcki UNIQUE (integration_user_id),
	CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

CREATE TABLE public.vote (
	vote_id int8 NOT NULL,
	post_id int8 NULL,
	user_id int8 NULL,
	vote_date timestamp NULL,
	vote_type varchar(255) NULL,
	CONSTRAINT vote_pkey PRIMARY KEY (vote_id)
);

CREATE TABLE public.answer (
	best_answer bool NULL,
	"content" varchar(24000) NULL,
	question_id int8 NULL,
	post_id int8 NOT NULL,
	CONSTRAINT answer_pkey PRIMARY KEY (post_id),
	CONSTRAINT fkgbthr3g8nq17pjpwrnd3592x FOREIGN KEY (post_id) REFERENCES public.post(post_id)
);

CREATE TABLE public.answer_comment (
	answer_id int8 NULL,
	"content" varchar(24000) NULL,
	post_id int8 NOT NULL,
	CONSTRAINT answer_comment_pkey PRIMARY KEY (post_id),
	CONSTRAINT fkg9aerdduy125bftk8uu81bi14 FOREIGN KEY (post_id) REFERENCES public.post(post_id)
);

CREATE TABLE public.question (
	answers int4 NULL,
	best_answer_id int8 NULL,
	description varchar(24000) NULL,
	duplicate_question_id int8 NULL,
	followers int4 NULL,
	tags varchar(255) NULL,
	title varchar(1000) NULL,
	url varchar(500) NULL,
	"views" int4 NULL,
	post_id int8 NOT NULL,
	CONSTRAINT question_pkey PRIMARY KEY (post_id),
	CONSTRAINT fkcaocljkajnu997s1ys76ls3hy FOREIGN KEY (post_id) REFERENCES public.post(post_id)
);

CREATE TABLE public.question_comment (
	"content" varchar(24000) NULL,
	question_id int8 NULL,
	post_id int8 NOT NULL,
	CONSTRAINT question_comment_pkey PRIMARY KEY (post_id),
	CONSTRAINT fk5hec5wmtkoq1545ojsxr3xc1m FOREIGN KEY (post_id) REFERENCES public.post(post_id)
);

CREATE SEQUENCE public.category_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.follower_user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.post_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.question_category_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.question_follower_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.question_notification_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.question_notification_queue_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.question_tag_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.question_view_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.recommendation_settings_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.recommended_email_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.recommended_email_question_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.recommended_list_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.recommended_list_page_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.recommended_list_page_question_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.tag_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.test_result_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.test_result_user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.total_activity_system_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.user_category_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.user_tag_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE public.vote_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;