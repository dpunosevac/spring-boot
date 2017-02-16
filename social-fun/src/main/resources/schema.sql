-- Drop tables if exist in right order --
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS "user";

-- Create tables in right order --

CREATE TABLE "user"
(
  id serial PRIMARY KEY NOT NULL,
  email character varying(50) NOT NULL,
  username character varying(50) NOT NULL,
  password character varying(50) NOT NULL,
  first_name character varying(50) NOT NULL,
  last_name character varying(50) NOT NULL,
  birthdate date NOT NULL
);

CREATE TABLE role
(
  id serial PRIMARY KEY NOT NULL,
  name character varying(30) NOT NULL
);

CREATE TABLE user_role
(
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id),
  CONSTRAINT user_role_role_id_fkey FOREIGN KEY (role_id)
      REFERENCES role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_role_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES "user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE post
(
  id serial PRIMARY KEY NOT NULL,
  content character varying(140) NOT NULL,
  date timestamp without time zone NOT NULL,
  user_id bigint NOT NULL,
  CONSTRAINT post_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES "user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE comment
(
  id serial PRIMARY KEY NOT NULL,
  content character varying(140) NOT NULL,
  date timestamp without time zone NOT NULL,
  user_id bigint NOT NULL,
  post_id bigint NOT NULL,
  CONSTRAINT comment_post_id_fkey FOREIGN KEY (post_id)
      REFERENCES post (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT comment_user_id_fkey FOREIGN KEY (user_id)
      REFERENCES "user" (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER SEQUENCE user_id_seq RESTART WITH 1;
ALTER SEQUENCE post_id_seq RESTART WITH 1;
ALTER SEQUENCE comment_id_seq RESTART WITH 1;