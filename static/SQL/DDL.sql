create table mydb.USER
(
    ID              int auto_increment
        primary key,
    USER_NAME       varchar(255) not null,
    HASH_PASS       varchar(255) not null,
    SALT            varchar(255) not null,
    EMAIL           varchar(255) not null,
    LAST_LOGIN_TIME timestamp    null,
    constraint USER_USER_NAME_uindex
        unique (USER_NAME)
);


create table mydb.HOTEL
(
    HOTEL_ID   int           not null
        primary key,
    HOTEL_NAME varchar(255)  not null,
    COUNTRY    varchar(255)  not null,
    STATE      varchar(255)  not null,
    CITY       varchar(255)  not null,
    ADDRESS    varchar(255)  not null,
    LATITUDE   double(10, 6) not null,
    LONGITUDE  double(10, 6) not null,
    constraint HOTEL_HOTEL_NAME_uindex
        unique (HOTEL_NAME)
);

create table mydb.REVIEW
(
    REVIEW_ID   varchar(255) not null
        primary key,
    HOTEL_ID    int          not null,
    RATING      float(2, 1)  not null,
    TITLE       varchar(255) null,
    REVIEW_TEXT blob         null,
    USER_ID     int          not null,
    SUBMIT_TIME datetime     not null,
    constraint Review_HOTEL_HOTEL_ID_fk
        foreign key (HOTEL_ID) references mydb.HOTEL (HOTEL_ID),
    constraint Review_USER_ID_fk
        foreign key (USER_ID) references mydb.USER (ID)
);

create table mydb.USER_SAVED_HOTEL
(
    USER_ID  int not null,
    HOTEL_ID int not null,
    constraint UNIQUE_INDEX
        unique (USER_ID, HOTEL_ID),
    constraint USER_SAVED_HOTEL_HOTEL_HOTEL_ID_fk
        foreign key (HOTEL_ID) references mydb.HOTEL (HOTEL_ID),
    constraint USER_SAVED_HOTEL_USER_ID_fk
        foreign key (USER_ID) references mydb.USER (ID)
);
