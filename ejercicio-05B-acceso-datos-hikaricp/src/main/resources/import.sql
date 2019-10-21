CREATE TABLE provincias (
	id INT NOT NULL, 
 	nombre VARCHAR(200) NOT NULL,
 	PRIMARY KEY (id)
);

CREATE TABLE localidades (
 	id INT NOT NULL, 
	nombre VARCHAR(200) NOT NULL,
 	provincia_id INT NOT NULL,
 	PRIMARY KEY (id)
);

INSERT INTO PROVINCIAS (id, nombre) values (1, 'SANTA FE');
INSERT INTO PROVINCIAS (id, nombre) values (2, 'CORDOBA');
INSERT INTO PROVINCIAS (id, nombre) values (3, 'CORRIENTES');
INSERT INTO PROVINCIAS (id, nombre) values (4, 'ENTRE RIOS');

INSERT INTO LOCALIDADES (id, nombre, provincia_id) values (1, 'ROSARIO', 1);
INSERT INTO LOCALIDADES (id, nombre, provincia_id) values (2, 'SAN LORENZO', 1);
INSERT INTO LOCALIDADES (id, nombre, provincia_id) values (3, 'FUNES', 1);
INSERT INTO LOCALIDADES (id, nombre, provincia_id) values (4, 'CORDOBA', 2);
INSERT INTO LOCALIDADES (id, nombre, provincia_id) values (5, 'RIO CUARTO', 2);
INSERT INTO LOCALIDADES (id, nombre, provincia_id) values (6, 'VICTORIA', 4);
