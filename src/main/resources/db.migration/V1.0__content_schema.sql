create extension if not exists "uuid-ossp";

create or replace function generate_code(prefix varchar, length integer)
    returns varchar
    language plpgsql
as
$$
declare
    is_unique boolean := false;
    content   varchar := '';
    output    varchar := '';
begin
    while not is_unique
        loop
            select string_agg(substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', ceil(random() * 36)::integer, 1), '')
            into content
            from generate_series(1, length - 3);
            output := prefix || substring(date_part('year', current_date)::text, 3) || content;
            is_unique := not exists(select 1 from tbl_user where code = output);
        end loop;
    return output;
end;
$$;

create table tbl_dictionary_group (
    dictionary_group_id   bigint primary key generated always as identity,
    dictionary_group_code varchar(8)   not null unique,
    dictionary_group_name varchar(256) not null,
    created_date          timestamp    not null default current_timestamp,
    updated_date          timestamp    not null default current_timestamp,
    created_by            varchar(16)  not null default 'anonymousUser',
    updated_by            varchar(16)  not null default 'anonymousUser',
    is_deleted            boolean      not null default false
);

create table tbl_dictionary (
    dictionary_id         bigint primary key generated always as identity,
    dictionary_code       varchar(8)   not null unique,
    dictionary_name       varchar(256) not null,
    dictionary_group_code varchar(8)   not null,
    dictionary_group_id   bigint       not null,
    sort_order            int          not null,
    value                 varchar(2000),
    is_default            boolean,
    description           text,
    created_date          timestamp    not null default current_timestamp,
    updated_date          timestamp    not null default current_timestamp,
    created_by            varchar(16)  not null default 'anonymousUser',
    updated_by            varchar(16)  not null default 'anonymousUser',
    is_deleted            boolean      not null default false
);

create table tbl_credential (
    credential_id bigint primary key generated always as identity,
    user_id       bigint      not null,
    user_code     varchar(10) not null,
    user_name     varchar(16) not null,
    password      varchar(256),
    roles         varchar(500),
    total_failed  smallint    not null default 0,
    created_date  timestamp   not null default current_timestamp,
    updated_date  timestamp   not null default current_timestamp,
    created_by    varchar(16) not null default 'anonymousUser',
    updated_by    varchar(16) not null default 'anonymousUser',
    is_deleted    boolean     not null default false
);

create table tbl_user (
    user_id      bigint primary key generated always as identity,
    code         varchar(10)      not null unique default generate_code('U', 10),
    first_name   varchar(256)     not null,
    last_name    varchar(256)     not null,
    full_name    varchar(512)     not null,
    birthday     date,
    gender       bigint,
    address      varchar(2000),
    avatar       varchar(256),
    money        double precision not null        default 0,
    belong_group boolean          not null        default false,
    is_lock      boolean          not null        default false,
    created_date timestamp        not null        default current_timestamp,
    updated_date timestamp        not null        default current_timestamp,
    created_by   varchar(16)      not null        default 'anonymousUser',
    updated_by   varchar(16)      not null        default 'anonymousUser',
    is_deleted   boolean          not null        default false
);
create unique index tbl_user_code_uindex on tbl_user (code);

create table tbl_login_history (
    login_history_id bigint primary key generated always as identity,
    user_id          bigint      not null,
    user_code        varchar(10) not null,
    is_success       boolean     not null,
    security_code    varchar(8),
    is_renew_token   boolean     not null,
    started_at       timestamp   not null,
    end_at           timestamp,
    expired_at       timestamp   not null,
    session_id       varchar(64) not null,
    is_activated     boolean     not null,
    created_date     timestamp   not null default current_timestamp,
    updated_date     timestamp   not null default current_timestamp,
    created_by       varchar(16) not null default 'anonymousUser',
    updated_by       varchar(16) not null default 'anonymousUser',
    is_deleted       boolean     not null default false
);
create index tbl_login_history_01 on tbl_login_history (user_id);
create index tbl_login_history_02 on tbl_login_history (security_code);

create table tbl_lock_history (
    lock_history_id     bigint primary key generated always as identity,
    user_id             bigint      not null,
    user_code           varchar(10) not null,
    is_lock             boolean     not null,
    lock_time           timestamp   not null,
    unlock_code         varchar(8),
    unlock_code_expired timestamp,
    unlock_time         timestamp,
    created_date        timestamp   not null default current_timestamp,
    updated_date        timestamp   not null default current_timestamp,
    created_by          varchar(16) not null default 'anonymousUser',
    updated_by          varchar(16) not null default 'anonymousUser',
    is_deleted          boolean     not null default false
);
create index tbl_lock_history_idx_01 on tbl_lock_history (user_id);

create table tbl_contact (
    contact_id    bigint primary key generated always as identity,
    user_id       bigint       not null,
    user_code     varchar(10)  not null,
    type          smallint     not null,
    contact       varchar(128) not null,
    provider      varchar(500),
    provider_id   varchar(500),
    is_primary    boolean      not null default false,
    is_verified   boolean,
    verified_date timestamp,
    created_date  timestamp    not null default current_timestamp,
    updated_date  timestamp    not null default current_timestamp,
    created_by    varchar(16)  not null default 'anonymousUser',
    updated_by    varchar(16)  not null default 'anonymousUser',
    is_deleted    boolean      not null default false
);

create table tbl_group (
    group_id     bigint primary key generated always as identity,
    code         varchar(10)  not null unique default generate_code('G', 10),
    group_name   varchar(256) not null,
    other_name   varchar(256),
    banner       varchar(256),
    avatar       varchar(256),
    description  varchar(2000),
    admin_id     bigint       not null,
    admin_code   varchar(10)  not null,
    created_date timestamp    not null        default current_timestamp,
    updated_date timestamp    not null        default current_timestamp,
    created_by   varchar(16)  not null        default 'anonymousUser',
    updated_by   varchar(16)  not null        default 'anonymousUser',
    is_deleted   boolean      not null        default false
);
create unique index tbl_group_code_uindex on tbl_group (code);

create table tbl_tier (
    tier_id          bigint primary key generated always as identity,
    code             varchar(12)  not null unique default generate_code('T', 12),
    tier_name        varchar(256) not null,
    price            bigint       not null,
    duration         bigint       not null,
    start_date       timestamp    not null,
    end_date         timestamp,
    is_sort_priority boolean      not null        default true,
    priority         int          not null        default 0,
    description      varchar(2000),
    group_id         bigint       not null,
    group_code       varchar(10)  not null,
    created_date     timestamp    not null        default current_timestamp,
    updated_date     timestamp    not null        default current_timestamp,
    created_by       varchar(16)  not null        default 'anonymousUser',
    updated_by       varchar(16)  not null        default 'anonymousUser',
    is_deleted       boolean      not null        default false
);
create unique index tbl_tier_code_uindex on tbl_tier (code);

create table tbl_subscribe (
    subscribe_id bigint primary key generated always as identity,
    group_id     bigint      not null,
    group_code   varchar(10) not null,
    is_subscribe boolean              default true,
    tier_id      bigint      not null,
    tier_code    varchar(12) not null,
    user_id      bigint      not null,
    user_code    varchar(10) not null,
    created_date timestamp   not null default current_timestamp,
    updated_date timestamp   not null default current_timestamp,
    created_by   varchar(16) not null default 'anonymousUser',
    updated_by   varchar(16) not null default 'anonymousUser',
    is_deleted   boolean     not null default false
);
create index tbl_subscribe_idx_01 on tbl_subscribe (group_id);
create index tbl_subscribe_idx_02 on tbl_subscribe (tier_id);
create index tbl_subscribe_idx_03 on tbl_subscribe (user_id, is_subscribe);

create table tbl_upgrade (
    upgrade_id   bigint primary key generated always as identity,
    group_id     bigint      not null,
    group_code   varchar(10) not null,
    tier_id      bigint      not null,
    tier_code    varchar(12) not null,
    user_id      bigint      not null,
    user_code    varchar(10) not null,
    start_date   timestamp   not null,
    end_date     timestamp   not null,
    transaction  varchar(64) not null,
    created_date timestamp   not null default current_timestamp,
    updated_date timestamp   not null default current_timestamp,
    created_by   varchar(16) not null default 'anonymousUser',
    updated_by   varchar(16) not null default 'anonymousUser',
    is_deleted   boolean     not null default false
);

create table tbl_album (
    album_id         bigint primary key generated always as identity,
    name             varchar(500) not null,
    description      text,
    tags             text,
    total_read       bigint       not null default 0,
    total_comment    bigint       not null default 0,
    total_post       bigint       not null default 0,
    total_follow     bigint       not null default 0,
    total_like       bigint       not null default 0,
    total_donate     bigint       not null default 0,
    last_upload_chap timestamp,
    author           varchar(500),
    cover_image      varchar(500),
    status           bigint,
    price            bigint,
    tier_id          bigint,
    tier_code        varchar(12),
    owner_id         bigint,
    owner_code       varchar(10),
    group_id         bigint,
    group_code       varchar(10),
    created_date     timestamp    not null default current_timestamp,
    updated_date     timestamp    not null default current_timestamp,
    created_by       varchar(16)  not null default 'anonymousUser',
    updated_by       varchar(16)  not null default 'anonymousUser',
    is_deleted       boolean      not null default false
);

create table tbl_post (
    post_id       bigint primary key generated always as identity,
    name          varchar(500) not null,
    album_id      bigint,
    user_code     varchar(10)  not null,
    content       text         not null,
    tags          text,
    total_read    bigint       not null default 0,
    total_comment bigint       not null default 0,
    total_donate  bigint       not null default 0,
    total_like    bigint       not null default 0,
    group_id      bigint,
    group_code    varchar(10),
    is_comment    boolean      not null default true,
    is_lock       boolean      not null default false,
    unlock_fee    bigint,
    lock_time     timestamp,
    image_linking boolean      not null default true,
    tier_id       bigint,
    public_date   timestamp    not null,
    created_date  timestamp    not null default current_timestamp,
    updated_date  timestamp    not null default current_timestamp,
    created_by    varchar(16)  not null default 'anonymousUser',
    updated_by    varchar(16)  not null default 'anonymousUser',
    is_deleted    boolean      not null default false
);
create index tbl_post_idx_01 on tbl_post (group_id);
create index tbl_post_idx_02 on tbl_post (tier_id);
create index tbl_post_idx_03 on tbl_post (public_date desc);
create index tbl_post_idx_04 on tbl_post (album_id);

create table tbl_comment (
    comment_id        bigint primary key generated always as identity,
    parent_comment_id bigint,
    message           text        not null,
    post_id           bigint      not null,
    user_id           bigint      not null,
    created_date      timestamp   not null default current_timestamp,
    updated_date      timestamp   not null default current_timestamp,
    created_by        varchar(16) not null default 'anonymousUser',
    updated_by        varchar(16) not null default 'anonymousUser',
    is_deleted        boolean     not null default false
);
create index tbl_comment_idx_01 on tbl_comment (post_id);
create index tbl_comment_idx_02 on tbl_comment (user_id);

create table tbl_like (
    like_id      bigint primary key generated always as identity,
    user_id      bigint      not null,
    post_id      bigint      not null,
    is_like      boolean     not null,
    like_time    timestamp,
    unlike_time  timestamp,
    created_date timestamp   not null default current_timestamp,
    updated_date timestamp   not null default current_timestamp,
    created_by   varchar(16) not null default 'anonymousUser',
    updated_by   varchar(16) not null default 'anonymousUser',
    is_deleted   boolean     not null default false
);
create index tbl_like_idx_01 on tbl_like (user_id);
create index tbl_like_idx_02 on tbl_like (post_id);

create table tbl_follow (
    follow_id    bigint primary key generated always as identity,
    user_id      bigint      not null,
    album_id     bigint      not null,
    is_follow    boolean     not null,
    created_date timestamp   not null default current_timestamp,
    updated_date timestamp   not null default current_timestamp,
    created_by   varchar(16) not null default 'anonymousUser',
    updated_by   varchar(16) not null default 'anonymousUser',
    is_deleted   boolean     not null default false
);

create table tbl_read (
    read_id      bigint primary key generated always as identity,
    user_id      bigint      not null,
    album_id     bigint,
    post_id      bigint      not null,
    is_read      boolean     not null default true,
    read_time    timestamp,
    created_date timestamp   not null default current_timestamp,
    updated_date timestamp   not null default current_timestamp,
    created_by   varchar(16) not null default 'anonymousUser',
    updated_by   varchar(16) not null default 'anonymousUser',
    is_deleted   boolean     not null default false
);

create table tbl_order (
    order_id     bigint primary key generated always as identity,
    user_id      bigint      not null,
    album_id     bigint      not null,
    cost         bigint      not null,
    created_date timestamp   not null default current_timestamp,
    updated_date timestamp   not null default current_timestamp,
    created_by   varchar(16) not null default 'anonymousUser',
    updated_by   varchar(16) not null default 'anonymousUser',
    is_deleted   boolean     not null default false
);

create table tbl_donate (
    donate_id    bigint primary key generated always as identity,
    user_id      bigint      not null,
    user_code    varchar(10) not null,
    album_id     bigint,
    post_id      bigint,
    group_id     bigint      not null,
    group_code   varchar(10) not null,
    cost         bigint      not null,
    transaction  varchar(64) not null,
    created_date timestamp   not null default current_timestamp,
    updated_date timestamp   not null default current_timestamp,
    created_by   varchar(16) not null default 'anonymousUser',
    updated_by   varchar(16) not null default 'anonymousUser',
    is_deleted   boolean     not null default false
);
create index tbl_donate_idx_01 on tbl_donate (group_id);
create index tbl_donate_idx_02 on tbl_donate (user_id);
create index tbl_donate_idx_03 on tbl_donate (post_id);

create table tbl_unlock_post (
    unlock_post_id bigint primary key generated always as identity,
    user_id        bigint      not null,
    user_code      varchar(10) not null,
    post_id        bigint      not null,
    group_id       bigint      not null,
    group_code     varchar(10) not null,
    cost           bigint      not null,
    unlock_time    timestamp   not null,
    transaction    varchar(64) not null,
    created_date   timestamp   not null default current_timestamp,
    updated_date   timestamp   not null default current_timestamp,
    created_by     varchar(16) not null default 'anonymousUser',
    updated_by     varchar(16) not null default 'anonymousUser',
    is_deleted     boolean     not null default false
);
create index tbl_unlock_post_idx_01 on tbl_unlock_post (group_id);
create index tbl_unlock_post_idx_02 on tbl_unlock_post (post_id);
create index tbl_unlock_post_idx_03 on tbl_unlock_post (user_id);

create table tbl_transaction (
    transaction_id    uuid primary key          default uuid_generate_v4(),
    session           varchar(64)      not null,
    user_id           bigint           not null,
    user_code         varchar(10)      not null,
    type              int              not null,
    cost              double precision not null,
    payment_method_id bigint,
    status            smallint         not null,
    created_date      timestamp        not null default current_timestamp,
    updated_date      timestamp        not null default current_timestamp,
    created_by        varchar(16)      not null default 'anonymousUser',
    updated_by        varchar(16)      not null default 'anonymousUser',
    is_deleted        boolean          not null default false
);

create table tbl_payment_method (
    payment_method_id bigint primary key generated always as identity,
    user_id           bigint       not null,
    type              bigint       not null,
    bank              varchar(500) not null,
    account           varchar(128) not null,
    verify_code       varchar(16)  not null,
    created_date      timestamp    not null default current_timestamp,
    updated_date      timestamp    not null default current_timestamp,
    created_by        varchar(16)  not null default 'anonymousUser',
    updated_by        varchar(16)  not null default 'anonymousUser',
    is_deleted        boolean      not null default false
);

create table tbl_email_unsent (
    email_unsent_id bigint primary key generated always as identity,
    email_from      varchar(128),
    email_to        text,
    email_cc        text,
    email_bcc       text,
    priority        smallint,
    created_date    timestamp,
    send_time       timestamp,
    attachment_path text,
    subject         varchar(400),
    count_error     smallint,
    content         text,
    uuid            varchar(20)
);

create table tbl_email_send (
    email_send_id   bigint primary key generated always as identity,
    email_id        bigint,
    email_from      varchar(128),
    email_to        varchar(128),
    email_cc        text,
    email_bcc       text,
    priority        smallint,
    created_date    timestamp,
    send_time       timestamp,
    attachment_path text,
    subject         varchar(400),
    count_error     smallint,
    content         text,
    uuid            varchar(20)
);

create table tbl_email_send_failed (
    email_send_failed_id bigint primary key generated always as identity,
    email_id             bigint,
    email_from           varchar(128),
    email_to             varchar(128),
    email_cc             text,
    email_bcc            text,
    priority             smallint,
    created_date         timestamp,
    send_time            timestamp,
    attachment_path      text,
    subject              varchar(400),
    count_error          smallint,
    content              text,
    uuid                 varchar(20)
);

create table tbl_email_template (
    email_template_id bigint primary key generated always as identity,
    subject           varchar(200),
    description       varchar(1000),
    type              smallint,
    template_name     varchar(200),
    code              varchar(8),
    content           text,
    created_date      timestamp   not null default current_timestamp,
    updated_date      timestamp   not null default current_timestamp,
    created_by        varchar(16) not null default 'anonymousUser',
    updated_by        varchar(16) not null default 'anonymousUser',
    is_deleted        boolean     not null default false
);

create table tbl_event_failed (
    event_failed_id bigint primary key generated always as identity,
    event_id        varchar(128),
    event_name      varchar(500),
    function_handle varchar(500),
    class_handle    varchar(500),
    is_sync         boolean,
    total_retry     smallint,
    event_data      text,
    created_date    timestamp   not null default current_timestamp,
    updated_date    timestamp   not null default current_timestamp,
    created_by      varchar(16) not null default 'anonymousUser',
    updated_by      varchar(16) not null default 'anonymousUser',
    is_deleted      boolean     not null default false
);

create table qrtz_blob_triggers (
    trigger_name  character varying(80)                                             not null,
    trigger_group character varying(80)                                             not null,
    blob_data     text,
    sched_name    character varying(120) default 'TestScheduler'::character varying not null
);

create table qrtz_calendars (
    calendar_name character varying(80)                                             not null,
    calendar      text                                                              not null,
    sched_name    character varying(120) default 'TestScheduler'::character varying not null
);

create table qrtz_cron_triggers (
    trigger_name    character varying(80)                                             not null,
    trigger_group   character varying(80)                                             not null,
    cron_expression character varying(80)                                             not null,
    time_zone_id    character varying(80),
    sched_name      character varying(120) default 'TestScheduler'::character varying not null
);

create table qrtz_fired_triggers (
    entry_id          character varying(95)                                             not null,
    trigger_name      character varying(80)                                             not null,
    trigger_group     character varying(80)                                             not null,
    instance_name     character varying(80)                                             not null,
    fired_time        bigint                                                            not null,
    priority          integer                                                           not null,
    state             character varying(16)                                             not null,
    job_name          character varying(80),
    job_group         character varying(80),
    is_nonconcurrent  boolean,
    is_update_data    boolean,
    sched_name        character varying(120) default 'TestScheduler'::character varying not null,
    sched_time        bigint                                                            not null,
    requests_recovery boolean
);

create table qrtz_job_details (
    job_name          character varying(128)                                            not null,
    job_group         character varying(80)                                             not null,
    description       character varying(120),
    job_class_name    character varying(200)                                            not null,
    is_durable        boolean,
    is_nonconcurrent  boolean,
    is_update_data    boolean,
    sched_name        character varying(120) default 'TestScheduler'::character varying not null,
    requests_recovery boolean,
    job_data          bytea
);

create table qrtz_locks (
    lock_name  character varying(40)                                             not null,
    sched_name character varying(120) default 'TestScheduler'::character varying not null
);

insert into qrtz_locks
values ('trigger_access');
insert into qrtz_locks
values ('job_access');
insert into qrtz_locks
values ('calendar_access');
insert into qrtz_locks
values ('state_access');
insert into qrtz_locks
values ('misfire_access');

create table qrtz_paused_trigger_grps (
    trigger_group character varying(80)                                             not null,
    sched_name    character varying(120) default 'TestScheduler'::character varying not null
);

create table qrtz_scheduler_state (
    instance_name     character varying(200)                                            not null,
    last_checkin_time bigint,
    checkin_interval  bigint,
    sched_name        character varying(120) default 'TestScheduler'::character varying not null
);

create table qrtz_simple_triggers (
    trigger_name    character varying(80)                                             not null,
    trigger_group   character varying(80)                                             not null,
    repeat_count    bigint                                                            not null,
    repeat_interval bigint                                                            not null,
    times_triggered bigint                                                            not null,
    sched_name      character varying(120) default 'TestScheduler'::character varying not null
);

create table qrtz_simprop_triggers (
    sched_name    character varying(120) not null,
    trigger_name  character varying(200) not null,
    trigger_group character varying(200) not null,
    str_prop_1    character varying(512),
    str_prop_2    character varying(512),
    str_prop_3    character varying(512),
    int_prop_1    integer,
    int_prop_2    integer,
    long_prop_1   bigint,
    long_prop_2   bigint,
    dec_prop_1    numeric(13, 4),
    dec_prop_2    numeric(13, 4),
    bool_prop_1   boolean,
    bool_prop_2   boolean
);

create table qrtz_triggers (
    trigger_name   character varying(80)                                             not null,
    trigger_group  character varying(80)                                             not null,
    job_name       character varying(80)                                             not null,
    job_group      character varying(80)                                             not null,
    description    character varying(120),
    next_fire_time bigint,
    prev_fire_time bigint,
    priority       integer,
    trigger_state  character varying(16)                                             not null,
    trigger_type   character varying(8)                                              not null,
    start_time     bigint                                                            not null,
    end_time       bigint,
    calendar_name  character varying(80),
    misfire_instr  smallint,
    job_data       bytea,
    sched_name     character varying(120) default 'TestScheduler'::character varying not null
);


alter table only qrtz_blob_triggers
    add constraint qrtz_blob_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

alter table only qrtz_calendars
    add constraint qrtz_calendars_pkey primary key (sched_name, calendar_name);

alter table only qrtz_cron_triggers
    add constraint qrtz_cron_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

alter table only qrtz_fired_triggers
    add constraint qrtz_fired_triggers_pkey primary key (sched_name, entry_id);

alter table only qrtz_job_details
    add constraint qrtz_job_details_pkey primary key (sched_name, job_name, job_group);

alter table only qrtz_locks
    add constraint qrtz_locks_pkey primary key (sched_name, lock_name);

alter table only qrtz_paused_trigger_grps
    add constraint qrtz_paused_trigger_grps_pkey primary key (sched_name, trigger_group);

alter table only qrtz_scheduler_state
    add constraint qrtz_scheduler_state_pkey primary key (sched_name, instance_name);

alter table only qrtz_simple_triggers
    add constraint qrtz_simple_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

alter table only qrtz_simprop_triggers
    add constraint qrtz_simprop_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

alter table only qrtz_triggers
    add constraint qrtz_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

create index fki_qrtz_simple_triggers_job_details_name_group on qrtz_triggers using btree (job_name, job_group);

create index fki_qrtz_simple_triggers_qrtz_triggers on qrtz_simple_triggers using btree (trigger_name, trigger_group);

create index idx_qrtz_ft_j_g on qrtz_fired_triggers using btree (sched_name, job_name, job_group);

create index idx_qrtz_ft_jg on qrtz_fired_triggers using btree (sched_name, job_group);

create index idx_qrtz_ft_t_g on qrtz_fired_triggers using btree (sched_name, trigger_name, trigger_group);

create index idx_qrtz_ft_tg on qrtz_fired_triggers using btree (sched_name, trigger_group);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers using btree (sched_name, instance_name);

create index idx_qrtz_j_grp on qrtz_job_details using btree (sched_name, job_group);

create index idx_qrtz_t_c on qrtz_triggers using btree (sched_name, calendar_name);

create index idx_qrtz_t_g on qrtz_triggers using btree (sched_name, trigger_group);

create index idx_qrtz_t_j on qrtz_triggers using btree (sched_name, job_name, job_group);

create index idx_qrtz_t_jg on qrtz_triggers using btree (sched_name, job_group);

create index idx_qrtz_t_n_g_state on qrtz_triggers using btree (sched_name, trigger_group, trigger_state);

create index idx_qrtz_t_n_state on qrtz_triggers using btree (sched_name, trigger_name, trigger_group, trigger_state);

create index idx_qrtz_t_next_fire_time on qrtz_triggers using btree (sched_name, next_fire_time);

create index idx_qrtz_t_nft_misfire on qrtz_triggers using btree (sched_name, misfire_instr, next_fire_time);

create index idx_qrtz_t_nft_st on qrtz_triggers using btree (sched_name, trigger_state, next_fire_time);

create index idx_qrtz_t_nft_st_misfire on qrtz_triggers using btree (sched_name, misfire_instr, next_fire_time, trigger_state);

create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers using btree (sched_name, misfire_instr, next_fire_time,
                                                                         trigger_group, trigger_state);

create index idx_qrtz_t_state on qrtz_triggers using btree (sched_name, trigger_state);

alter table only qrtz_blob_triggers
    add constraint qrtz_blob_triggers_sched_name_fkey foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers (sched_name, trigger_name, trigger_group);

alter table only qrtz_cron_triggers
    add constraint qrtz_cron_triggers_sched_name_fkey foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers (sched_name, trigger_name, trigger_group);

alter table only qrtz_simple_triggers
    add constraint qrtz_simple_triggers_sched_name_fkey foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers (sched_name, trigger_name, trigger_group);

alter table only qrtz_simprop_triggers
    add constraint qrtz_simprop_triggers_sched_name_fkey foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers (sched_name, trigger_name, trigger_group);

alter table only qrtz_triggers
    add constraint qrtz_triggers_sched_name_fkey foreign key (sched_name, job_name, job_group) references qrtz_job_details (sched_name, job_name, job_group);