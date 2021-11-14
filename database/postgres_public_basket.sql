create table basket
(
    user_id    integer not null
        constraint basket_user_id_fk
            references "user",
    id         serial
        constraint basket_pk
            primary key,
    product_id integer not null
        constraint basket_product_id_fk
            references product,
    amount     integer not null
);

alter table basket
    owner to postgres;

