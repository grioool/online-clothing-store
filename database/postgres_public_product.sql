create table product
(
    id                  serial
        constraint product_pk
            primary key,
    product_name        varchar(25)   not null,
    price               numeric(5, 2) not null,
    brand_id            integer       not null
        constraint product_brand_fk
            references brand
            on update cascade,
    category_id         integer       not null
        constraint product_category_fk
            references product_category
            on update cascade on delete cascade,
    name_of_image       varchar(255)  not null,
    product_description varchar(255)  not null,
    article             integer       not null
);

alter table product
    owner to postgres;

create unique index product_product_name_uindex
    on product (product_name);

