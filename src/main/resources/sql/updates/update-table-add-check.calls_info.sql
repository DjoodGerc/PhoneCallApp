alter table calls_info
    alter column called_phone_number type bigint using called_phone_number::bigint;
alter table calls_info
    alter column caller_phone_number type bigint using caller_phone_number::bigint;

alter table calls_info
    add is_spam boolean;

alter table calls_info
    add is_roaming boolean;

alter table calls_info
    add roaming_country varchar(30);

alter table calls_info
    add is_fraud boolean;

CREATE INDEX if not exists index1 ON calls_info (caller_phone_number);
CREATE INDEX if not exists index2 ON calls_info (called_phone_number);
CREATE INDEX if not exists index3 ON calls_info (call_date);