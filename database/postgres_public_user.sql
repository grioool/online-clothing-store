create table "user"
(
    id           serial
        constraint user_pk
            primary key,
    username     varchar(25)  not null,
    password     varchar(255) not null,
    first_name   varchar(20)  not null,
    last_name    varchar(40),
    email        varchar(255) not null,
    is_blocked   boolean      not null,
    phone_number varchar(18)  not null,
    role_id      integer      not null
        constraint user_fk
            references user_role
            on update cascade
);

alter table "user"
    owner to postgres;

create unique index user_email_uindex
    on "user" (email);

create unique index user_username_uindex
    on "user" (username);

INSERT INTO public."user" (id, username, password, first_name, last_name, email, is_blocked, phone_number, role_id) VALUES (3, 'griooool', '888', 'ol', 'gr', 'ooo', false, '39393', 1);
INSERT INTO public."user" (id, username, password, first_name, last_name, email, is_blocked, phone_number, role_id) VALUES (5, 'griol', '888', 'ol', 'gr', 'otto', false, '39393', 1);