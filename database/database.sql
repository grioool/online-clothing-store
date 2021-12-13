create table user_role
(
    id        serial
        constraint user_role_pk
            primary key,
    role_name varchar(10) not null
);

alter table user_role
    owner to postgres;

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

create unique index user_role_role_name_uindex
    on user_role (role_name);

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

create table order_status
(
    id          serial
        constraint order_status_pk
            primary key,
    status_name varchar(10) not null
);

alter table order_status
    owner to postgres;

create unique index order_status_status_uindex
    on order_status (status_name);

create table payment_method
(
    id          serial
        constraint payment_method_pk
            primary key,
    method_name varchar(10) not null
);

alter table payment_method
    owner to postgres;

create unique index payment_method_method_name_uindex
    on payment_method (method_name);

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

create table town
(
    id        serial
        constraint town_pk
            primary key,
    town_name varchar(25) not null
);

alter table town
    owner to postgres;

create table "order"
(
    id                  serial
        constraint order_pk
            primary key,
    payment_method      integer   not null
        constraint order_payment_method_fk
            references payment_method
            on update cascade,
    status_id           integer   not null
        constraint order_status_id_fk
            references order_status
            on update cascade,
    delivery_date       timestamp not null,
    order_date          timestamp not null,
    delivery_country_id integer   not null
        constraint order_delivery_country_id_fk
            references country
            on update cascade,
    user_id             integer   not null
        constraint order_user_id_fk
            references "user"
            on update cascade on delete cascade,
    delivery_town_id    integer   not null
        constraint order_delivery_town_id_fk
            references town
            on update cascade
);

alter table "order"
    owner to postgres;

create unique index town_town_name_uindex
    on town (town_name);

create table brand
(
    id         serial
        constraint brand_pk
            primary key,
    brand_name varchar(25) not null
);

alter table brand
    owner to postgres;

create table product
(
    id                  serial
        constraint product_pk
            primary key,
    product_name        varchar(25)      not null,
    price               double precision not null,
    brand_id            integer          not null
        constraint product_brand_fk
            references brand
            on update cascade,
    category_id         integer          not null
        constraint product_category_fk
            references product_category
            on update cascade on delete cascade,
    name_of_image       varchar(255)     not null,
    product_description varchar(255)     not null,
    article             integer          not null
);

alter table product
    owner to postgres;

create unique index product_product_name_uindex
    on product (product_name);

create table product_in_order
(
    order_id   integer not null
        constraint product_in_order_order_id_fk
            references "order"
            on update cascade on delete cascade,
    product_id integer not null
        constraint product_in_order_product_id_fk
            references product
            on update cascade,
    amount     integer not null
);

alter table product_in_order
    owner to postgres;

create unique index brand_brand_name_uindex
    on brand (brand_name);

