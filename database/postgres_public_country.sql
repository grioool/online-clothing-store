create table country
(
    id           serial
        constraint country_pk
            primary key,
    country_name varchar(25) not null
);

alter table country
    owner to postgres;

create unique index country_country_name_uindex
    on country (country_name);

