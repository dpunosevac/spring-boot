INSERT INTO "user"
VALUES(1, 'dusan.punosevac@gmail.com', 'dpunosevac', 'password', 'Dusan', 'Punosevac', '29/08/1993');

INSERT INTO "user"
VALUES(2, 'ljubisa.punosevac@gmail.com', 'ljpunosevac', 'password', 'Ljubisa', 'Punosevac', '26/02/1978');

INSERT INTO "user"
VALUES(3, 'zoran.punosevac@gmail.com', 'zpunosevac', 'password', 'Zoran', 'Punosevac', '29/03/1961');

INSERT INTO post
VALUES(1, 'Test', now(), 1);

INSERT INTO post
VALUES(2, 'Test1', now(), 2);

INSERT INTO comment
VALUES(2, 'Proba', now(), 2, 1);

INSERT INTO comment
VALUES(1, 'Proba1', now(), 3, 2);