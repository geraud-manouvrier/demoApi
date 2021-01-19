-- DROP DATABASE demoapidb;

CREATE DATABASE demoapidb
    WITH 
    OWNER = "demoApiUserDb"
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_Chile.1252'
    LC_CTYPE = 'Spanish_Chile.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- DROP TABLE public.person;

CREATE TABLE public.person
(
    rutint integer NOT NULL,
    rutdv "char",
    nameperson character varying(100) COLLATE pg_catalog."default",
    lastname character varying(100) COLLATE pg_catalog."default",
    age integer,
    course character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT person_pkey PRIMARY KEY (rutint)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.person
    OWNER to postgres;
