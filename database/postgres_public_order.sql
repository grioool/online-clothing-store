create table "order"
(
    id                  serial
        constraint order_pk
            primary key,
    payment_method      integer       not null
        constraint order_payment_method_fk
            references payment_method
            on update cascade,
    status_id           integer       not null
        constraint order_status_id_fk
            references order_status
            on update cascade,
    products            numeric(6, 2) not null,
    delivery_date       timestamp     not null,
    order_date          timestamp     not null,
    delivery_country_id integer       not null
        constraint order_delivery_country_id_fk
            references country
            on update cascade,
    user_id             integer       not null
        constraint order_user_id_fk
            references "user"
            on update cascade on delete cascade,
    delivery_town_id    integer       not null
        constraint order_delivery_town_id_fk
            references town
            on update cascade
);

alter table "order"
    owner to postgres;

