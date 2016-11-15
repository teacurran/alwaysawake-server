-- // create accounts
-- Migration SQL that makes the change goes here.

create table accounts (
    `id` bigint not null auto_increment,
    `admin` bit not null,
    `avatar` varchar(255),
    `background` varchar(255),
    `bio` longtext,
    `date_created` datetime,
    `date_login` datetime,
    `date_modified` datetime,
    `disabled` bit not null,
    `disabled_reason_id` integer,
    `email` varchar(255),
    `followers_count` integer,
    `following_count` integer,
    `full_name` varchar(200),
    `invites` integer not null,
    `location` varchar(255),
    `login_failure_count` integer,
    `password` varchar(200),
    `password_salt` varchar(255),
    `status_id` integer not null,
    `timezone_id` varchar(255),
    `username` varchar(255),
    `username_normalized` varchar(255),
    `website` varchar(255),
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table account_password_resets (
    `uuid` varchar(45) not null,
    `date_created` datetime,
    `account_id` bigint,
    primary key (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table account_settings (
    `id` bigint not null auto_increment,
    `date_set` datetime,
    `key` varchar(255),
    `value` varchar(255),
    `account_id` bigint,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table api_applications (
    `uuid` varchar(45) not null,
    `approved` bit,
    `callback_uri` varchar(255),
    `date_approved` datetime,
    `date_created` datetime,
    `date_modified` datetime,
    `name` varchar(255),
    `secret` varchar(255),
    `account_id` bigint,
    primary key (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table authorizations (
    `uuid` varchar(45) not null,
    `date_accessed` datetime,
    `date_created` datetime,
    `refresh_token` varchar(255),
    `request_code` varchar(255),
    `token` varchar(255),
    `account_id` bigint,
    `api_applicaiton_uuid` varchar(45),
    primary key (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table invites (
    `id` bigint not null auto_increment,
    `date_claimed` datetime,
    `date_created` datetime,
    `value` varchar(255),
    `from_account_id` bigint,
    `used_by_account_id` bigint,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table login_audits (
    `uuid` varchar(45) not null,
    `date_created` datetime,
    `ip_address` varchar(255),
    `is_successful` bit,
    `username` varchar(255),
    `account_id` bigint,
    primary key (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table restricted_usernames (
    `id` bigint not null auto_increment,
    `date_created` datetime,
    `date_modified` datetime,
    `reason` varchar(255),
    `username` varchar(255),
    `username_normalized` varchar(255),
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table accounts
    add constraint uk_k8h1bgqoplx0rkngj01pm1rgp unique (`username`);

alter table accounts
    add constraint uk_peech9eg3heoyy8s56a976vt8 unique (`username_normalized`);

alter table authorizations
    add constraint ix_authorization_request_code unique (`request_code`);

alter table authorizations
    add constraint ix_authorizations_refresh_token unique (`refresh_token`);

alter table authorizations
    add constraint ix_authorizations_token unique (`token`);

alter table account_password_resets
    add constraint `fk_804q1sh94wc28k9s0pqnha3me`
    foreign key (`account_id`)
    references accounts (`id`);

alter table account_settings
    add constraint `fk_1upy1fjq982sea1dvim286nwx`
    foreign key (`account_id`)
    references accounts (`id`);

alter table api_applications
    add constraint `fk_f3wt308fxvonxn03ke2bcpsn1`
    foreign key (`account_id`)
    references accounts (`id`);

alter table authorizations
    add constraint `fk_qmvyqnu7fl8o18rwk74i1fh5g`
    foreign key (`account_id`)
    references accounts (`id`);

alter table authorizations
    add constraint `fk_3m99jgesqpox26p6v5ss7qi9n`
    foreign key (`api_applicaiton_uuid`)
    references api_applications (`uuid`);

alter table invites
    add constraint `fk_liu2h3awjjm9r8r17vokw1bc1`
    foreign key (`from_account_id`)
    references accounts (`id`);

alter table invites
    add constraint `fk_gt6pws86r2vuloa4p1g9twrc6`
    foreign key (`used_by_account_id`)
    references accounts (`id`);

alter table login_audits
    add constraint `fk_3xoxgfn9j1iddf67nfu8j46s3`
    foreign key (`account_id`)
    references accounts (`id`);

-- //@UNDO
-- SQL to undo the change goes here.

alter table accounts
    drop index uk_k8h1bgqoplx0rkngj01pm1rgp;

alter table accounts
    drop index uk_peech9eg3heoyy8s56a976vt8;

alter table authorizations
    drop index ix_authorization_request_code;

alter table authorizations
    drop index ix_authorizations_refresh_token;

alter table authorizations
    drop index ix_authorizations_token;

drop table restricted_usernames;
drop table login_audits;
drop table invites;
drop table authorizations;
drop table api_applications;
drop table account_settings;
drop table account_password_resets;
drop table accounts;
