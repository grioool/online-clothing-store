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

