create table product_in_order
(
    order_id   integer not null
        constraint product_in_order_order_id_fk
            references "order"
            on update cascade,
    product_id integer not null
        constraint product_in_order_product_id_fk
            references product
            on update cascade,
    amount     integer not null
);

alter table product_in_order
    owner to postgres;

