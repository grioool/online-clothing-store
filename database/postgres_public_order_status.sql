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

