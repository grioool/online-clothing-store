create table brand
(
    id         serial
        constraint brand_pk
            primary key,
    brand_name varchar(25) not null
);

alter table brand
    owner to postgres;

create unique index brand_brand_name_uindex
    on brand (brand_name);

