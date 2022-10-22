ALTER TABLE public.recommendation_settings DROP COLUMN channel;

ALTER TABLE public.recommendation_settings DROP COLUMN "name";

ALTER TABLE public.recommendation_settings ADD COLUMN channel varchar(255);

ALTER TABLE public.recommendation_settings ADD COLUMN "name" varchar(255);

ALTER TABLE public.recommended_list ADD COLUMN total_pages_with_recommended_questions int4;

ALTER TABLE public.recommended_list ADD COLUMN minimum_date_for_recommended_questions timestamp;