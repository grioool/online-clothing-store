create table town
(
    id        serial
        constraint town_pk
            primary key,
    town_name varchar(25) not null
);

alter table town
    owner to postgres;

create unique index town_town_name_uindex
    on town (town_name);

