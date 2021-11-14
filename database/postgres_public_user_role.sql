create table user_role
(
    id        serial
        constraint user_role_pk
            primary key,
    role_name varchar(10) not null
);

alter table user_role
    owner to postgres;

create unique index user_role_role_name_uindex
    on user_role (role_name);

INSERT INTO public.user_role (id, role_name) VALUES (1, 'USER');