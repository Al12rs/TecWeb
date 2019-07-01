
    alter table many2many_bees_A_____many2many_bees_B 
        drop 
        foreign key FK2BB37AA05911C27A;

    alter table many2many_bees_A_____many2many_bees_B 
        drop 
        foreign key FK2BB37AA0591FD9FC;

    alter table many2one_bees_B 
        drop 
        foreign key FKF6E4C9A0D700F5F8;

    alter table many2one_one2many_bidirectional_list_bees_B 
        drop 
        foreign key FKCEC3B911DB46554B;

    alter table many2one_one2many_bidirectional_set_bees_B 
        drop 
        foreign key FKF5C220158D9584B;

    alter table one2many_list_bees_B 
        drop 
        foreign key FKD64E2B213D69B04A;

    alter table one2many_list_string_strings 
        drop 
        foreign key FKAADB9EC1F3FE084C;

    alter table one2many_set_bees_B 
        drop 
        foreign key FK59192C05B96F216C;

    alter table one2many_set_string_strings 
        drop 
        foreign key FK3F997BA5846BB8EE;

    drop table if exists many2many_bees_A;

    drop table if exists many2many_bees_A_____many2many_bees_B;

    drop table if exists many2many_bees_B;

    drop table if exists many2one_bees_A;

    drop table if exists many2one_bees_B;

    drop table if exists many2one_one2many_bidirectional_list_bees_A;

    drop table if exists many2one_one2many_bidirectional_list_bees_B;

    drop table if exists many2one_one2many_bidirectional_set_bees_A;

    drop table if exists many2one_one2many_bidirectional_set_bees_B;

    drop table if exists one2many_list_bees_A;

    drop table if exists one2many_list_bees_B;

    drop table if exists one2many_list_string_A;

    drop table if exists one2many_list_string_strings;

    drop table if exists one2many_set_bees_A;

    drop table if exists one2many_set_bees_B;

    drop table if exists one2many_set_string_A;

    drop table if exists one2many_set_string_strings;

    drop table if exists one2one_A;

    drop table if exists one2one_B;

    drop table if exists primarykey_A;

    drop table if exists primarykey_B;





    create table many2many_bees_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table many2many_bees_A_____many2many_bees_B (
        a_ids integer not null,
        b_ids integer not null,
        primary key (b_ids, a_ids)
    );

    create table many2many_bees_B (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table many2one_bees_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table many2one_bees_B (
        id integer not null,
        value varchar(255) not null unique,
        a_id integer,
        primary key (id)
    );

    create table many2one_one2many_bidirectional_list_bees_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table many2one_one2many_bidirectional_list_bees_B (
        id integer not null,
        value varchar(255) not null unique,
        a_id integer,
        indice integer,
        primary key (id)
    );

    create table many2one_one2many_bidirectional_set_bees_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table many2one_one2many_bidirectional_set_bees_B (
        id integer not null,
        value varchar(255) not null unique,
        a_id integer,
        primary key (id)
    );

    create table one2many_list_bees_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table one2many_list_bees_B (
        id integer not null,
        value varchar(255) not null unique,
        a_id integer,
        indice integer,
        primary key (id)
    );

    create table one2many_list_string_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table one2many_list_string_strings (
        id integer not null,
        value varchar(255),
        indice integer not null,
        primary key (id, indice)
    );

    create table one2many_set_bees_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table one2many_set_bees_B (
        id integer not null,
        value varchar(255) not null unique,
        a_id integer,
        primary key (id)
    );

    create table one2many_set_string_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table one2many_set_string_strings (
        id integer not null,
        value varchar(255)
    );

    create table one2one_A (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table one2one_B (
        id integer not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table primarykey_A (
        value1 varchar(255) not null,
        value2 varchar(255) not null,
        primary key (value1, value2)
    );

    create table primarykey_B (
        id bigint not null,
        value1 varchar(255) not null,
        value2 varchar(255) not null,
        primary key (id),
        unique (value1, value2)
    );

    alter table many2many_bees_A_____many2many_bees_B 
        add index FK2BB37AA05911C27A (a_ids), 
        add constraint FK2BB37AA05911C27A 
        foreign key (a_ids) 
        references many2many_bees_A (id);

    alter table many2many_bees_A_____many2many_bees_B 
        add index FK2BB37AA0591FD9FC (b_ids), 
        add constraint FK2BB37AA0591FD9FC 
        foreign key (b_ids) 
        references many2many_bees_B (id);

    alter table many2one_bees_B 
        add index FKF6E4C9A0D700F5F8 (a_id), 
        add constraint FKF6E4C9A0D700F5F8 
        foreign key (a_id) 
        references many2one_bees_A (id);

    alter table many2one_one2many_bidirectional_list_bees_B 
        add index FKCEC3B911DB46554B (a_id), 
        add constraint FKCEC3B911DB46554B 
        foreign key (a_id) 
        references many2one_one2many_bidirectional_list_bees_A (id);

    alter table many2one_one2many_bidirectional_set_bees_B 
        add index FKF5C220158D9584B (a_id), 
        add constraint FKF5C220158D9584B 
        foreign key (a_id) 
        references many2one_one2many_bidirectional_set_bees_A (id);

    alter table one2many_list_bees_B 
        add index FKD64E2B213D69B04A (a_id), 
        add constraint FKD64E2B213D69B04A 
        foreign key (a_id) 
        references one2many_list_bees_A (id);

    alter table one2many_list_string_strings 
        add index FKAADB9EC1F3FE084C (id), 
        add constraint FKAADB9EC1F3FE084C 
        foreign key (id) 
        references one2many_list_string_A (id);

    alter table one2many_set_bees_B 
        add index FK59192C05B96F216C (a_id), 
        add constraint FK59192C05B96F216C 
        foreign key (a_id) 
        references one2many_set_bees_A (id);

    alter table one2many_set_string_strings 
        add index FK3F997BA5846BB8EE (id), 
        add constraint FK3F997BA5846BB8EE 
        foreign key (id) 
        references one2many_set_string_A (id);
