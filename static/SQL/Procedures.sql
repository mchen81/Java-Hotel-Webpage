create definer = jerry@`%` procedure mydb.addReview(IN I_REVIEW_ID varchar(255), IN I_HOTEL_ID int, IN I_RATING int, IN I_TITLE varchar(255), IN I_REVIEW_TEXT blob, IN I_USER_ID int, IN I_SUBMIT_TIME timestamp)
begin
    INSERT INTO mydb.REVIEW(REVIEW_ID, HOTEL_ID, RATING, TITLE, REVIEW_TEXT, USER_ID, SUBMIT_TIME)
    VALUES (I_REVIEW_ID, I_HOTEL_ID, I_RATING, I_TITLE, I_REVIEW_TEXT, I_USER_ID, I_SUBMIT_TIME);
end;

create definer = jerry@`%` procedure mydb.addUser(IN I_NAME varchar(255), IN I_HASH_PASSWORD varchar(255), IN I_SALT varchar(255), IN I_EMAIL varchar(255))
begin
    INSERT INTO mydb.USER(USER_NAME, HASH_PASS, SALT, EMAIL)
    VALUES (I_NAME, I_HASH_PASSWORD, I_SALT, I_EMAIL);
end;

create definer = jerry@`%` procedure mydb.addUserSaveHotel(IN I_USER_ID int, IN I_HOTEL_ID int)
begin
    INSERT INTO USER_SAVED_HOTEL
    VALUES (I_USER_ID, I_HOTEL_ID);
end;

create definer = jerry@`%` procedure mydb.clearUserSavedHotelById(IN I_USER_ID int)
begin
    delete from USER_SAVED_HOTEL where USER_ID = I_USER_ID;
end;

create definer = jerry@`%` procedure mydb.deleteReview(IN I_REVIEW_ID varchar(255))
begin
    delete from mydb.REVIEW where REVIEW_ID = I_REVIEW_ID;
end;

create definer = jerry@`%` procedure mydb.getAllHotels()
begin
    select * from HOTEL;
end;

create definer = jerry@`%` procedure mydb.getAllReviews()
begin
    select * from mydb.REVIEW;
end;

create definer = jerry@`%` procedure mydb.getAllUserSavedHotel()
begin
    select * from USER_SAVED_HOTEL;
end;

create definer = jerry@`%` procedure mydb.getHotelById(IN I_HOTEL_ID int)
begin
    select *
    from mydb.HOTEL
    where HOTEL_ID = I_HOTEL_ID;
end;

create definer = jerry@`%` procedure mydb.getReviewById(IN I_REVIEW_ID int)
begin
    select * from mydb.REVIEW where REVIEW_ID = I_REVIEW_ID;
end;

create definer = jerry@`%` procedure mydb.getUserById(IN I_USER_ID int)
begin
    select *
    from mydb.USER u
    where I_USER_ID = u.ID;
end;

create definer = jerry@`%` procedure mydb.getUserByName(IN I_USER_NAME varchar(255))
begin
    select * from mydb.USER
    where USER_NAME = I_USER_NAME;
end;

create definer = jerry@`%` procedure mydb.getUserSavedHotelById(IN I_USER_ID int)
begin
    SELECT HOTEL_ID
    from USER_SAVED_HOTEL
    WHERE USER_ID = I_USER_ID;
end;

create definer = jerry@`%` procedure mydb.removeUserSavedHotel(IN i_userId int, IN i_hotelId int)
begin
    delete from USER_SAVED_HOTEL
        where USER_ID = i_userId and HOTEL_ID = i_hotelId;
end;

create definer = jerry@`%` procedure mydb.updateHotel(IN hoteid int, IN lat double, IN log double)
begin
    update HOTEL
        set LATITUDE = lat, LONGITUDE = log
    where HOTEL_ID = hoteid;
end;

create definer = jerry@`%` procedure mydb.updateLastLoginTime(IN userId int)
begin
    update mydb.USER
    SET LAST_LOGIN_TIME = current_timestamp
    where ID = userId;
end;

create definer = jerry@`%` procedure mydb.updateReview(IN I_REVIEW_ID varchar(255), IN I_RANTING float, IN I_TITLE varchar(255), IN I_REVIEW_TEXT blob, IN I_SUBMIT_TIME timestamp)
begin
    UPDATE mydb.REVIEW
    SET RATING     = I_RANTING,
        TITLE       = I_TITLE,
        REVIEW_TEXT = I_REVIEW_TEXT,
        SUBMIT_TIME = I_SUBMIT_TIME
    WHERE REVIEW_ID = I_REVIEW_ID;
end;

create definer = jerry@`%` procedure mydb.updateUser(IN I_ID int, IN I_HASH_PASS varchar(255), IN I_EMAIL varchar(255))
begin
    update mydb.USER
        SET HASH_PASS = I_HASH_PASS,
            EMAIL = I_EMAIL
    WHERE ID = I_ID;
end;

