

INSERT INTO FEI_USER (username, name, nif, password, fcm_token, phone, address, email, salt) VALUES ('rneves', 'Ronilton Neves', 123456789, 'X', '', 987654321, 'Por ai', 'rneves@mail.com', '278EA9956522EAF46070E4DD59DBF163D70CFC307B01DC42AAB5EAE9A3484E41');
UPDATE FEI_USER SET password = 'CA2BBBD7A2BE6B31A60E928B6B015D00120401D7D4EAAAB897902DBF074BC8A2';

insert into QUEUE_TYPE (id, description) VALUES (1, 'General Purpose'), 
(2, 'Specific'), (3, 'Priority');

insert into SERVICE (id, name, description) VALUES 
(1, 'Service A', 'Service A Description'), 
(2, 'Service B', 'Service B Description'),
(3, 'Service C', 'Service C Description');


insert into SERVICE_POSTOFFICE (id, latitude, longitude, description, serviceid, address) VALUES 
(1, -3.0, 9.2, 'Post Office 1 of Service A', 1, 'Rua lá à frente'), 
(2, -1.0, 9.2, 'Post Office 2 of Service A', 1, 'Do outro lado da rua'),
(3, -1.0, 4.2, 'Post Office 1 of Service B', 2, 'Rua da esquerda'),
(4, -2.0, 4.2, 'Post Office 1 of Service C', 3, 'Rua da direita');

insert into QUEUE (id, activeservers, letter, name, description, type, maxavailable, servicepostofficeid, tolerance) 
VALUES (1, 1, 'A', 'Queue A','Description Queue A', 1, 100, 1, '0'),
(2, 2, 'B', 'Queue B','Description Queue B', 2, 100, 1, '0'),
(3, 1, 'C', 'Queue C','Description Queue C', 3, 100, 1, '0'),
(4, 1, 'A', 'Queue A','Description Queue A', 1, 100, 2, '1'),
(5, 1, 'B', 'Queue B','Description Queue B', 1, 100, 2, '1'),
(6, 1, 'A', 'Queue A','Description Queue A', 1, 100, 3, '0'),
(7, 1, 'B', 'Queue B','Description Queue B', 2, 100, 3, '0'),
(8, 1, 'A', 'Queue A','Description Queue A', 2, 70, 4, '0'),
(9, 1, 'B', 'Queue B','Description Queue B', 2, 50, 4, '0');

insert into QUEUE_STATE (number, letter, queueId) VALUES 
(1, 'A', 1),
(1, 'B', 2),
(1, 'C', 3),
(1, 'A', 4),
(1, 'B', 5),
(1, 'A', 6),
(1, 'B', 7),
(1, 'A', 8),
(1, 'B', 9);

insert into QUEUE_ATTENDED (number, letter, queueId) VALUES 
(1, 'A', 1),
(1, 'B', 2),
(1, 'C', 3),
(1, 'A', 4),
(1, 'B', 5),
(1, 'A', 6),
(1, 'B', 7),
(1, 'A', 8),
(1, 'B', 9);

INSERT INTO FORECAST (queueId) VALUES (1);


--TEMP
insert into ticket_user (queueid, number, userid) values (1, 1, 1);
insert into ticket_user (queueid, number, userid) values (4, 3, 1), (4, 4, 1);


INSERT INTO FORECAST (queueId) VALUES (2),(3),(4),(5),(6),(7),(8),(9),(99),(12),(11) ;



call SET_USER_TICKET_ATTENDED(1, 1);

insert into ROLES (role) VALUES ('ADMIN'), ('SERVER'), ('USER');


insert into USER_ROLE (userId, role) VALUES ('1', 'ADMIN');



INSERT INTO FEI_USER (username, name, nif, password, fcm_token, phone, address, email, salt) VALUES ('rneves', 'The Server', 122456789, 'CA2BBBD7A2BE6B31A60E928B6B015D00120401D7D4EAAAB897902DBF074BC8A2', '', 977654321, 'Por ai', 'server@mail.com', '278EA9956522EAF46070E4DD59DBF163D70CFC307B01DC42AAB5EAE9A3484E41');
insert into USER_ROLE (userId, role) VALUES ('2', 'SERVER');

INSERT INTO FEI_USER (username, name, nif, password, fcm_token, phone, address, email, salt) VALUES ('tuser', 'The User', 122356789, 'CA2BBBD7A2BE6B31A60E928B6B015D00120401D7D4EAAAB897902DBF074BC8A2', '', 975654321, 'Por ai', 'user@mail.com', '278EA9956522EAF46070E4DD59DBF163D70CFC307B01DC42AAB5EAE9A3484E41');
insert into USER_ROLE (userId, role) VALUES ('3', 'USER')

INSERT INTO FEI_USER (username, name, nif, password, fcm_token, phone, address, email, salt) VALUES ('serviceadmin', 'Server Admin User', 122356789, 'CA2BBBD7A2BE6B31A60E928B6B015D00120401D7D4EAAAB897902DBF074BC8A2', '', 975654321, 'Por ai', 'serviceadmin@mail.com', '278EA9956522EAF46070E4DD59DBF163D70CFC307B01DC42AAB5EAE9A3484E41');
insert into USER_ROLE (userId, role) VALUES ('4', 'SERVICE_ADMIN')

insert into user_service(userid, serviceid) values (8, 1);

insert into user_post_office(userid, servicepostofficeid) values (2, 1);



SELECT setval('service_id_seq', (SELECT max(id) FROM service));
SELECT nextval('service_id_seq');

SELECT setval('fei_user_id_seq', (SELECT max(id) FROM fei_user));
SELECT nextval('fei_user_id_seq');

SELECT setval('forecast_id_seq', (SELECT max(id) FROM forecast));
SELECT nextval('forecast_id_seq');

SELECT setval('queue_id_seq', (SELECT max(id) FROM queue));
SELECT nextval('queue_id_seq');

SELECT setval('service_postoffice_id_seq', (SELECT max(id) FROM service_postoffice));
SELECT nextval('service_postoffice_id_seq');


SELECT setval('location_id_seq', (SELECT max(id) FROM location));
SELECT nextval('location_id_seq');

alter table QUEUE alter column tolerance TYPE BOOLEAN USING case when tolerance=1::bit then true else false end;

alter table SESSION alter column token DROP NOT NULL ;

insert into location(latitude, longitude, userid)
values(0.0,0.0, 1), (0.0,0.0, 2),(0.0,0.0, 3);

alter table FEI_USER alter column fcm_token TYPE VARCHAR(200);

alter table FEI_USER add column notifications BOOLEAN NOT NULL DEFAULT TRUE;



/************************************************ Access Control ************************************************/
--TODO put up for deploy

INSERT INTO PATH_CONTROL_ACCESS(method, regex, roles) VALUES
--HOMEPAGE
('GET', '(/?)+(/|\s|\?\w*|.{0})', 'NONE'),
--SERVICE
('GET', '(/?)+(service)+(/|\s|\?\w*|.{0})', 'ADMIN USER'),
('GET', '(/?)+(service)+(/\d+)+(/|\s|\?\w*|.{0})', 'ADMIN USER SERVER SERVICE_ADMIN'),
('GET', '(/?)+(service)+(/\d+)+(/postoffices)+(/|\s|\?\w*|.{0})', 'ADMIN USER SERVICE_ADMIN'),
('GET', '(/?)+(service)+(/postoffices)+(/\d+)+(/|\s|\?\w*|.{0})', 'ADMIN USER SERVER SERVICE_ADMIN'),
('GET', '(/?)+(service)+(/postoffice)+(/\d+)+(/queues)+(/|\s|\?\w*|.{0})', 'ADMIN USER SERVER SERVICE_ADMIN'),
('GET', '(/?)+(service)+(/postoffice)+(/queue)+(/\d+)+(/|\s|\?\w*|.{0})', 'ADMIN USER SERVER SERVICE_ADMIN'),

('POST', '(/?)+(service)+(/|\s|\?\w*|.{0})', 'ADMIN'),
('POST', '(/?)+(service)+(/postoffice)(/|\s|\?\w*|.{0})', 'ADMIN SERVICE_ADMIN'),
('POST', '(/?)+(service)+(/postoffice)+(/queue)+(/|\s|\?\w*|.{0})', 'ADMIN SERVICE_ADMIN'),

('PUT', '(/?)+(service)+(/\d+)+(/update)+(/|\s|\?\w*|.{0})', 'ADMIN SERVICE_ADMIN'),
('PUT', '(/?)+(service)+(/postoffice)+(/\d+)+(/update)+(/|\s|\?\w*|.{0})', 'ADMIN SERVICE_ADMIN'),
('PUT', '(/?)+(service)+(/postoffice)+(/queue)+(/\d+)+(/update)+(/|\s|\?\w*|.{0})', 'ADMIN SERVICE_ADMIN'),

('PUT', '(/?)+(service)+(/\d+)+(/delete)+(/|\s|\\?\w*|.{0})', 'ADMIN'),
('PUT', '(/?)+(service)+(/postoffice)+(/\d+)+(/delete)+(/|\s|\?\w*|.{0})', 'ADMIN SERVICE_ADMIN'),
('PUT', '(/?)+(service)+(/postoffice)+(/queue)+(/\d+)+(/delete)+(/|\s|\?\w*|.{0})', 'ADMIN SERVICE_ADMIN'),

-- TICKET
('GET', '(/?)+(ticket)+(/\d+)+(/|\s|\?\w*|.{0})', 'ADMIN USER'),
('GET', '(/?)+(ticket)+(/postoffice)+(/\d+)+(/|\s|\?\w*|.{0})', 'ADMIN USER'),
('GET', '(/?)+(ticket)+(/postoffice)+(/\d+)+(/states)+(/|\s|\?\w*|.{0})', 'ADMIN USER SERVER SERVICE_ADMIN'),
('GET', '(/?)+(ticket)+(/postoffice)+(/\d+)+(/battended)+(/|\s|\?\w*|.{0})', 'ADMIN USER SERVER SERVICE_ADMIN'),

('POST', '(/?)+(ticket)+(/\d+)+(/take)+(/|\s|\?\w*|.{0})', 'USER'),
('POST', '(/?)+(ticket)+(/subscribe)+(/|\s|\?\w*|.{0})', 'USER'),
('POST', '(/?)+(ticket)+(/postoffice)+(/subscribe)+(/|\s|\?\w*|.{0})', 'USER'),
('POST', '(/?)+(ticket)+(/unsubscribe)+(/|\s|\?\w*|.{0})', 'USER'),
('POST', '(/?)+(ticket)+(/postoffice)+(/unsubscribe)+(/|\s|\?\w*|.{0})', 'USER'),

('PUT', '(/?)+(ticket)+(/\d+)+(/|\s|\?\w*|.{0})', 'ADMIN SERVICE_ADMIN SERVER'),

-- USER

('GET', '(/?)+(isValid)+(/|\s|\?\w*|.{0})', 'NONE'),
('GET', '(/?)+(account)+(/|\s|\?\w*|.{0})', 'USER'),
('GET', '(/?)+(roles)+(/|\s|\?\w*|.{0})', 'NONE'),

('POST', '(/?)+(login)+(/|\s|\?\w*|.{0})', 'NONE'),
('POST', '(/?)+(logout)+(/|\s|\?\w*|.{0})', 'NONE'),
('POST', '(/?)+(register)+(/|\s|\?\w*|.{0})', 'NONE'),

('PUT', '(/?)+(fcmtoken)+(/|\s|\?\w*|.{0})', 'USER'),
('PUT', '(/?)+(location)+(/|\s|\?\w*|.{0})', 'USER'),
('PUT', '(/?)+(serving)+(/|\s|\?\w*|.{0})', 'SERVER'),
('PUT', '(/?)+(account)+(/|\s|\?\w*|.{0})', 'USER');



