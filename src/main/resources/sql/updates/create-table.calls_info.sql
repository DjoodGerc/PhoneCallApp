create table if not exists calls_info
(
    id                  bigserial primary key  not null,
        caller_phone_number varchar(20)           not null,
    called_phone_number varchar(20)           not null,
    call_duration       bigint                  not null,
    call_date           timestamp
);
CREATE INDEX if not exists index1 ON calls_info (caller_phone_number);



