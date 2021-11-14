create table product_category
(
    id                serial
        constraint product_category_pk
            primary key,
    category_name     varchar(50)  not null,
    filename_of_image varchar(255) not null
);

alter table product_category
    owner to postgres;

create unique index product_category_category_name_uindex
    on product_category (category_name);

