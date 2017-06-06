insert into permission values ('1', 'INSERT_PERMISSION');
insert into permission values ('2', 'UPDATE_PERMISSION');
insert into permission values ('3', 'DELETE_PERMISSION');
insert into permission values ('4', 'FIND_ALL_PERMISSION');

insert into permission values ('5', 'INSERT_ROLE');
insert into permission values ('6', 'UPDATE_ROLE');
insert into permission values ('7', 'DELETE_ROLE');
insert into permission values ('8', 'FIND_ALL_ROLE');

insert into permission values ('9', 'INSERT_USER');
insert into permission values ('10', 'UPDATE_USER');
insert into permission values ('11', 'DELETE_USER');
insert into permission values ('12', 'FIND_ALL_USER');
insert into permission values ('13', 'PASSWORD_CHANGE_USER');

insert into permission values ('14', 'INSERT_STATE');
insert into permission values ('15', 'UPDATE_STATE');
insert into permission values ('16', 'DELETE_STATE');
insert into permission values ('17', 'FIND_ALL_STATE');
insert into permission values ('18', 'FILTER_STATE');

insert into permission values ('19', 'INSERT_CITY');
insert into permission values ('20', 'UPDATE_CITY');
insert into permission values ('21', 'DELETE_CITY');
insert into permission values ('22', 'FIND_ALL_CITY');
insert into permission values ('23', 'FILTER_CITY');
insert into permission values ('24', 'FIND_CITY_BY_STATE');

insert into permission values ('25', 'INSERT_ACTIVITY');
insert into permission values ('26', 'UPDATE_ACTIVITY');
insert into permission values ('27', 'DELETE_ACTIVITY');
insert into permission values ('28', 'FIND_ALL_ACTIVITY');
insert into permission values ('29', 'FILTER_ACTIVITY');
insert into permission values ('30', 'FIND_ONE_ACTIVITY');

insert into permission values ('31', 'INSERT_VALUTE');
insert into permission values ('32', 'UPDATE_VALUTE');
insert into permission values ('33', 'DELETE_VALUTE');
insert into permission values ('34', 'FIND_ALL_VALUTE');
insert into permission values ('35', 'FILTER_VALUTE');
insert into permission values ('36', 'FIND_ONE_VALUTE');
insert into permission values ('37', 'FIND_VALUTE_BY_CODE');

insert into permission values ('38', 'FIND_ALL_EMPLOYEE');
insert into permission values ('39', 'GET_ALL_LEGAL');

insert into permission values ('40', 'INSERT_MESSAGE');
insert into permission values ('41', 'UPDATE_MESSAGE');
insert into permission values ('42', 'DELETE_MESSAGE');
insert into permission values ('43', 'FIND_ALL_MESSAGE');
insert into permission values ('44', 'FIND_MESSAGE_BY_CODE');
insert into permission values ('45', 'FIND_ONE_MESSAGE');

insert into permission values ('46', 'GENERATE_CERTIFICATE_REQUEST');
insert into permission values ('47', 'GET_CERTIFICATE_REQUESTS');
insert into permission values ('48', 'MAKE_CERTIFICATE');
insert into permission values ('49', 'FIND_CERTIFICATE_BY_ALIAS');
insert into permission values ('50', 'REVOKE_CERTIFICATE');
insert into permission values ('51', 'PROCESS_REVOKE_REQUEST');
insert into permission values ('52', 'GET_REVOKE_REQUEST');
insert into permission values ('53', 'SAVE_REVOKE_REQUEST');

insert into permission values ('54', 'OCSP_RESPONSE');

insert into permission values ('55', 'GENERATE_CERTIFICATE');


insert into permission values ('56', 'INSERT_ACCOUNT');
insert into permission values ('57', 'DEACTIVATE_ACCOUNT');
insert into permission values ('58', 'FIND_ALL_ACCOUNT');
insert into permission values ('59', 'FIND_ONE_ACCOUNT');
insert into permission values ('60', 'FIND_ACCOUNT_BY_OWNER');
insert into permission values ('61', 'FILTER_ACCOUNT');

insert into permission values ('62', 'FIND_ALL_DEACTIVATION');
insert into permission values ('63', 'FILTER_DEACTIVATION');
insert into permission values ('64', 'FILTER_DEACTIVATION_BY_ACCOUNT');


insert into permission values ('65', 'INSERT_PHYSICAL');
insert into permission values ('66', 'UPDATE_PHYSICAL');
insert into permission values ('67', 'DELETE_PHYSICAL');
insert into permission values ('68', 'FIND_ALL_PHYSICAL');
insert into permission values ('69', 'FIND_ALL_PHYSICAL_BY_CITY');
insert into permission values ('70', 'FILTER_PHYSICAL');

insert into permission values ('71', 'INSERT_LEGAL');
insert into permission values ('72', 'UPDATE_LEGAL');
insert into permission values ('73', 'DELETE_LEGAL');
insert into permission values ('74', 'FIND_ALL_LEGAL');
insert into permission values ('75', 'FIND_ALL_LEGAL_BY_CITY');
insert into permission values ('76', 'FILTER_LEGAL');

insert into permission values ('77', 'FIND_ALL_DAILY_STATE');
insert into permission values ('78', 'FILTER_DAILY_STATE');
insert into permission values ('79', 'FIND_DAILY_STATE_BY_ACCOUNT');

insert into permission values ('80', 'FIND_ALL_ANALYTICS');
insert into permission values ('81', 'FILTER_ANALYTICS');
insert into permission values ('82', 'FIND_ANALYTICS_BY_DAILY_STATE');

insert into permission values ('83', 'IMPORT_FROM_XML');

insert into permission values ('84', 'LOGOFF');


insert into role values (1, 'ADMINISTRATOR_BANK');
insert into role values (2, 'MANAGER');
insert into role values (3, 'COUNTER_OFFICER');
insert into role values (4, 'LEGAL');
insert into role values (5, 'ADMINISTRATOR_CENTRAL');

insert into role_permissions values (1, 1);
insert into role_permissions values (1, 2);
insert into role_permissions values (1, 3);
insert into role_permissions values (1, 4);
insert into role_permissions values (1, 5);
insert into role_permissions values (1, 6);
insert into role_permissions values (1, 7);
insert into role_permissions values (1, 8);
insert into role_permissions values (1, 9);
insert into role_permissions values (1, 10);
insert into role_permissions values (1, 11);
insert into role_permissions values (1, 12);
insert into role_permissions values (1, 13);
insert into role_permissions values (1, 14);
insert into role_permissions values (1, 15);
insert into role_permissions values (1, 16);
insert into role_permissions values (1, 17);
insert into role_permissions values (1, 18);
insert into role_permissions values (1, 19);
insert into role_permissions values (1, 20);
insert into role_permissions values (1, 21);
insert into role_permissions values (1, 22);
insert into role_permissions values (1, 23);
insert into role_permissions values (1, 24);
insert into role_permissions values (1, 25);
insert into role_permissions values (1, 26);
insert into role_permissions values (1, 27);
insert into role_permissions values (1, 28);
insert into role_permissions values (1, 29);
insert into role_permissions values (1, 30);
insert into role_permissions values (1, 31);
insert into role_permissions values (1, 32);
insert into role_permissions values (1, 33);
insert into role_permissions values (1, 34);
insert into role_permissions values (1, 35);
insert into role_permissions values (1, 36);
insert into role_permissions values (1, 37);
insert into role_permissions values (1, 38);
insert into role_permissions values (1, 39);
insert into role_permissions values (1, 40);
insert into role_permissions values (1, 41);
insert into role_permissions values (1, 42);
insert into role_permissions values (1, 43);
insert into role_permissions values (1, 44);
insert into role_permissions values (1, 45);
insert into role_permissions values (1, 46);
insert into role_permissions values (1, 47);
insert into role_permissions values (1, 48);
insert into role_permissions values (1, 49);
insert into role_permissions values (1, 50);
insert into role_permissions values (1, 51);
insert into role_permissions values (1, 52);
insert into role_permissions values (1, 53);
insert into role_permissions values (1, 54);

insert into role_permissions values (1, 74);
insert into role_permissions values (1, 84);


insert into role_permissions values (2, 13);
insert into role_permissions values (2, 22);
insert into role_permissions values (2, 28);
insert into role_permissions values (2, 35);

insert into role_permissions values (2, 56);
insert into role_permissions values (2, 57);
insert into role_permissions values (2, 58);
insert into role_permissions values (2, 59);
insert into role_permissions values (2, 60);
insert into role_permissions values (2, 61);
insert into role_permissions values (2, 62);
insert into role_permissions values (2, 63);
insert into role_permissions values (2, 64);

insert into role_permissions values (2, 71);
insert into role_permissions values (2, 72);
insert into role_permissions values (2, 73);
insert into role_permissions values (2, 74);
insert into role_permissions values (2, 75);
insert into role_permissions values (2, 76);

insert into role_permissions values (2, 77);
insert into role_permissions values (2, 78);
insert into role_permissions values (2, 79);
insert into role_permissions values (2, 80);
insert into role_permissions values (2, 81);
insert into role_permissions values (2, 82);
insert into role_permissions values (2, 83);
insert into role_permissions values (2, 84);


insert into role_permissions values (3, 13);
insert into role_permissions values (3, 22);
insert into role_permissions values (3, 28);
insert into role_permissions values (3, 35);

insert into role_permissions values (3, 56);
insert into role_permissions values (3, 57);
insert into role_permissions values (3, 58);
insert into role_permissions values (3, 59);
insert into role_permissions values (3, 60);
insert into role_permissions values (3, 61);
insert into role_permissions values (3, 62);
insert into role_permissions values (3, 63);
insert into role_permissions values (3, 64);
insert into role_permissions values (3, 65);
insert into role_permissions values (3, 66);
insert into role_permissions values (3, 67);
insert into role_permissions values (3, 68);
insert into role_permissions values (3, 69);
insert into role_permissions values (3, 70);

insert into role_permissions values (3, 77);
insert into role_permissions values (3, 78);
insert into role_permissions values (3, 79);
insert into role_permissions values (3, 80);
insert into role_permissions values (3, 81);
insert into role_permissions values (3, 82);
insert into role_permissions values (3, 83);
insert into role_permissions values (3, 84);

insert into role_permissions values (4, 55);
insert into role_permissions values (4, 84);



insert into bank values (1, '123', '12345678', 'Prva banka', '12345678');
insert into bank values (2, '234', '21345678',  'Druga banka', '21345678');
insert into bank values (3, '345', '14345678' , 'Treca banka', '14345678');
insert into bank values (4, '456', '16345678' , 'Cetvrta banka', '16345678');
insert into bank values (5, '567', '12342678', 'Peta banka', '12342678');

insert into drzava values (1, 'Srbija', 'SRB');
insert into drzava values (2,'Makedonija', 'MKD' );
insert into drzava values (3, 'Crna Gora', 'MTN');
insert into drzava values (4, 'Bosna i Hercegovina', 'BIH');
insert into drzava values (5, 'Hrvatska', 'CRO');


insert into naseljeno_mesto values (1, 'Novi Sad', 'NS', '21000', 1);
insert into naseljeno_mesto values (2, 'Beograd', 'BG', '11000', 1);
insert into naseljeno_mesto values (3, 'Kragujevac', 'KG', '14300', 1);
insert into naseljeno_mesto values (4, 'Skoplje', 'SK', '98000', 2);
insert into naseljeno_mesto values (5, 'Prnjavor', 'PR', '43000', 4);

insert into subject values ('1');
insert into subject values ('2');
insert into subject values ('3');
insert into subject values ('4');
insert into subject values ('5');
insert into subject values ('6');
insert into subject values ('7');

insert into subject values ('8');
insert into subject values ('9');
insert into subject values ('10');
insert into subject values ('11');

insert into subject values ('12');
insert into subject values ('13');
insert into subject values ('14');

insert into subject values ('15');



insert into employee values ('Prvizaposleni', 'Prvizaposleni', '1', '1');
insert into employee values ('Drugizaposleni', 'Drugizaposleni', '2', '1');
insert into employee values ('Trecizaposleni', 'Trecizaposleni', '3', '1');
insert into employee values ('Cetvrtizaposleni', 'Cetvrtizaposleni', '4', '1');
insert into employee values ('Petizaposleni', 'Petizaposleni', '5', '1');
insert into employee values ('Sestizaposleni', 'Sestizaposleni', '6', '1');
insert into employee values ('Sedmizaposleni', 'Sedmizaposleni', '7', '1');

insert into employee values ('Osmizaposleni', 'Osmizaposleni', '8', '1');
insert into employee values ('Devetizaposleni', 'Devetizaposleni', '9', '1');
insert into employee values ('Desetizaposleni', 'Desetizaposleni', '10', '1');
insert into employee values ('Jedanaestizaposleni', 'Jedanaestizaposleni', '11', '1');

insert into employee values ('Mojzaposleni', 'Mojzaposleni', '15', '1');

insert into user values ('1', FALSE, '2017-06-04 00:05:00', 'neki1@kkk.kkk', 'username1', '', 'username1', '1', '1', '1');
insert into user values ('2', TRUE, '2013-08-30 19:06:00', 'neki2@kkk.kkk', 'username2', '', 'username2', '1', '1', '2');
insert into user values ('3', TRUE, '2013-08-30 19:07:00', 'neki3@kkk.kkk', 'username3', '', 'username3', '1', '2', '3');
insert into user values ('4', TRUE, '2013-08-30 19:08:00', 'neki4@kkk.kkk', 'username4', '', 'username4', '1', '2', '4');
insert into user values ('5', TRUE, '2013-08-30 19:09:00', 'neki5@kkk.kkk', 'username5', '', 'username5', '1', '2', '5');
insert into user values ('6', TRUE, '2013-08-30 19:10:00', 'neki6@kkk.kkk', 'username6', '', 'username6', '1', '3', '6');
insert into user values ('7', TRUE, '2013-08-30 19:11:00', 'neki7@kkk.kkk', 'username7', '', 'username7', '1', '3', '7');


insert into klijent values ('adresa', 'mail1@kkk.kkk', TRUE, 'Ime1', '4578445121255', 'Prezime1', '123456788', '12', '1', '1');
insert into klijent values ('adresa', 'mail2@kkk.kkk', TRUE, 'Ime2', '4578545121255', 'Prezime2', '123454788', '13', '1', '1');
insert into klijent values ('adresa', 'mail3@kkk.kkk', TRUE, 'Ime3', '4518545121255', 'Prezime3', '123452788', '14', '1', '1');

insert into racun values ('1', '123-1726533787929-93', '2017-04-30 19:05:00', '2013-02-15 19:05:00', '0', FALSE, '1', '12');
insert into racun values ('2', '123-9338670954849-69', NULL, '2013-01-15 19:05:00', '2000', TRUE, '1', '12');
insert into racun values ('3', '123-8880775775674-13', null, '2013-05-10 19:05:00', '12000', TRUE, '1', '13');
insert into racun values ('4', '123-7184821064803-82', null, '2013-05-07 19:05:00', '12000', TRUE, '1', '13');

insert into zatvaranje_racuna values ('1', '2017-04-30 19:05:00', '123-7184821064803-82', '1');

insert into activity values('1', 'IOP', 'Ipoppop');
