CREATE TABLE IF NOT EXISTS od_order (
	id                          serial4            primary key,
	user_id                     int4               not null,
	items                       jsonb              not null,
	price                       int4               not null,
	created_at                  timestamp(0)       not null
);