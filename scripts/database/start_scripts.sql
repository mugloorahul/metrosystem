DROP TABLE metro_station;
DROP TABLE route;
DROP TABLE metro_train;
DROP TABLE stations_routes;
DROP TABLE bank_account;
DROP TABLE metro_user;
DROP TABLE metro_card;
DROP TABLE payment_method;
DROP TABLE credit_card_payment;
DROP TABLE debit_card_payment;
DROP TABLE net_banking_payment;
DROP TABLE train_journey_monitor;
DROP TABLE train_journey;
DROP TABLE user_journey;

CREATE TABLE metro_station(station_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name VARCHAR(50) NOT NULL,latitude VARCHAR(30) NOT NULL,longitude VARCHAR(30) NOT NULL);

CREATE TABLE route(route_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name VARCHAR(50) NOT NULL);

CREATE TABLE metro_train(train_number INT PRIMARY KEY ,name VARCHAR(100) NOT NULL,route_id INT, CONSTRAINT fk_route FOREIGN KEY(route_id) REFERENCES route(route_id));

CREATE TABLE stations_routes(id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),station_id INT NOT NULL,route_id INT NOT NULL,SEQUENCE INT NOT NULL, CONSTRAINT fk_station FOREIGN KEY(station_id) REFERENCES metro_station(station_id), CONSTRAINT fk_route_1 FOREIGN KEY(route_id) REFERENCES route(route_id),CONSTRAINT ck_stations_routes_seq CHECK (SEQUENCE > 0));

CREATE TABLE bank_account(bank_account_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),account_number varchar(20) not null,balance decimal(12,2) NOT NULL WITH DEFAULT 0,user_id INT NOT NULL);

CREATE TABLE metro_user(user_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(100) NOT NULL);

ALTER TABLE bank_account ADD CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES metro_user(user_id);

CREATE TABLE metro_card(card_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),balance decimal(12,2) NOT NULL WITH DEFAULT 0,
user_id INT NOT NULL,deleted VARCHAR(1) DEFAULT 'N' not null,card_number VARCHAR(30) not null,
CONSTRAINT fk_user_1 FOREIGN KEY(user_id) REFERENCES metro_user(user_id));

CREATE TABLE payment_method(id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),deleted VARCHAR(1) DEFAULT 'N' not null,pay_method_id varchar(30) NOT NULL,payment_type varchar(4) NOT NULL,bank_account_id INT NOT NULL,CONSTRAINT fk_bank_account_1 FOREIGN KEY(bank_account_id) REFERENCES bank_account(bank_account_id), CONSTRAINT uk_pay_method_id UNIQUE(pay_method_id));

CREATE TABLE credit_card_payment(pay_method_id INT PRIMARY KEY,expiry_month VARCHAR(2) NOT NULL,expiry_year VARCHAR(4) NOT NULL,cvv_number int NOT NULL,credit_limit decimal(8,2) NOT NULL,CONSTRAINT fk_payment_method FOREIGN KEY(pay_method_id) REFERENCES payment_method(id));

CREATE TABLE debit_card_payment(pay_method_id INT PRIMARY KEY,expiry_month VARCHAR(2) NOT NULL,expiry_year VARCHAR(4) NOT NULL,cvv_number int NOT NULL,CONSTRAINT fk_payment_method1 FOREIGN KEY(pay_method_id) REFERENCES payment_method(id));

CREATE TABLE net_banking_payment(pay_method_id INT PRIMARY KEY,password VARCHAR(30) NOT NULL,CONSTRAINT fk_payment_method2 FOREIGN KEY(pay_method_id) REFERENCES payment_method(id));

CREATE TABLE train_journey(journey_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),train_number INT NOT NULL,scheduled_start_time TIMESTAMP NOT NULL,actual_start_time TIMESTAMP,end_time TIMESTAMP,CONSTRAINT fk_train FOREIGN KEY(train_number) REFERENCES metro_train(train_number));

CREATE TABLE user_journey(journey_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),user_id INT NOT NULL,train_journey_id INT, scheduled_start_time TIMESTAMP NOT NULL,actual_start_time TIMESTAMP,end_time TIMESTAMP,source_station_id INT NOT NULL,dest_station_id INT NOT NULL,swipe_in_time TIMESTAMP, swipe_out_time TIMESTAMP);

ALTER TABLE user_journey ADD CONSTRAINT fk_train_journey FOREIGN KEY(train_journey_id) REFERENCES train_journey(journey_id);

ALTER TABLE user_journey ADD CONSTRAINT fk_user_2 FOREIGN KEY(user_id) REFERENCES metro_user(user_id);

ALTER TABLE user_journey ADD CONSTRAINT fk_station_1 FOREIGN KEY(source_station_id) REFERENCES metro_station(station_id);

ALTER TABLE user_journey ADD CONSTRAINT fk_station_2 FOREIGN KEY(dest_station_id) REFERENCES metro_station(station_id);

CREATE TABLE train_journey_monitor(monitor_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),train_journey_id INT NOT NULL,station_id INT NOT NULL, current_station_flag VARCHAR(1) DEFAULT 'N',scheduled_arrival_time TIMESTAMP,scheduled_departure_time TIMESTAMP,actual_arrival_time TIMESTAMP,actual_departure_time TIMESTAMP);

ALTER TABLE train_journey_monitor ADD CONSTRAINT fk_train_journey_1 FOREIGN KEY(train_journey_id) REFERENCES train_journey(journey_id);

ALTER TABLE train_journey_monitor ADD CONSTRAINT fk_station_3 FOREIGN KEY(station_id) REFERENCES metro_station(station_id);

ALTER TABLE train_journey_monitor ADD CONSTRAINT ck_monitor_station_flag CHECK (current_station_flag IN ('Y','N'));

ALTER TABLE route ADD CONSTRAINT uk_route_name UNIQUE (name);

ALTER TABLE metro_station ADD CONSTRAINT uk_station_name UNIQUE (name);

ALTER TABLE stations_routes ADD CONSTRAINT uk_station_route_name UNIQUE (station_id,route_id);

ALTER TABLE metro_train ADD CONSTRAINT uk_train_name UNIQUE(name);

ALTER TABLE metro_user ADD unique_id VARCHAR(30) not null DEFAULT '';

ALTER TABLE metro_user ADD CONSTRAINT uk_user_identifier UNIQUE (unique_id);

ALTER TABLE metro_card ADD CONSTRAINT uk_card_number UNIQUE(card_number);

ALTER TABLE bank_account ADD CONSTRAINT uk_account_number UNIQUE(account_number);

ALTER TABLE bank_account ADD COLUMN deleted VARCHAR(1) not null DEFAULT 'N';

COMMIT;
