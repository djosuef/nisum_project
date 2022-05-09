-- MYSQL
--INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ENABLED, LASTPASSWORDRESETDATE) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 'admin@admin.com', 1, STR_TO_DATE('01-01-2016', '%d-%m-%Y'));
--INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ENABLED, LASTPASSWORDRESETDATE) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'enabled@user.com', 1, STR_TO_DATE('01-01-2016','%d-%m-%Y'));
--INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ENABLED, LASTPASSWORDRESETDATE) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', 0, STR_TO_DATE('01-01-2016','%d-%m-%Y'));


-- PARSEDATETIME('01-01-2016', 'dd-MM-yyyy')

-- H2
INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ISACTIVE, LASTPASSWORDRESETDATE, TOKEN, CREATED, MODIFIED, LAST_LOGIN) VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 'admin', 'admin@admin.com', 1, PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'), 'asasasass', PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'), PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'), PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'));
INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ISACTIVE, LASTPASSWORDRESETDATE, TOKEN, CREATED, MODIFIED, LAST_LOGIN) VALUES (2, 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'enabled@user.com', 1, PARSEDATETIME('01-01-2016','dd-MM-yyyy'), 'asasasass', PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'), PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'), PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'));
INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ISACTIVE, LASTPASSWORDRESETDATE, TOKEN, CREATED, MODIFIED, LAST_LOGIN) VALUES (3, 'disabled', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', 0, PARSEDATETIME('01-01-2016','dd-MM-yyyy'), 'sdasdasafas', PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'), PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'), PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'));

--CREATE TABLE PHONE ( ID VARCHAR(10) NOT NULL,
--TICKETPRICE NUMERIC(8,2) NOT NULL,
--TOURISTINFO_ID INT FOREIGN KEY REFERENCES TOURISTINFO
--)

INSERT INTO PHONE (ID, NUMBER, CITYCODE, COUNTRYCODE, USER_ID) VALUES (1, '123456', '1', '12', 1);
INSERT INTO PHONE (ID, NUMBER, CITYCODE, COUNTRYCODE,USER_ID) VALUES (2, '654321', '2', '14', 2);
INSERT INTO PHONE (ID, NUMBER, CITYCODE, COUNTRYCODE,USER_ID) VALUES (3, '98765122', '4', '24', 2);

INSERT INTO AUTHORITY (ID, NAME) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (ID, NAME) VALUES (2, 'ROLE_ADMIN');

--INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 2);
--INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (2, 1);
--INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (1, 1);
--INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (3, 1);

--INSERT INTO USER_PHONE (USER_ID, PHONE_ID) VALUES (1, 1);
--INSERT INTO USER_PHONE (USER_ID, PHONE_ID) VALUES (1, 2);
