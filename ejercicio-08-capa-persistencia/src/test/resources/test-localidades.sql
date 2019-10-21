CREATE TABLE provincias (
	id INT NOT NULL,
	nombre VARCHAR(200) NOT NULL,
	PRIMARY KEY(id)
);

INSERT INTO provincias VALUES (1, 'SANTA FE');
INSERT INTO provincias VALUES (2, 'CORDOBA');

CREATE TABLE localidades (
	id INT NOT NULL,
	nombre VARCHAR(200) NOT NULL,
	provincia_id INT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (provincia_id) REFERENCES provincias(id)
);

INSERT INTO localidades VALUES (1, 'SANTA FE', 1);
INSERT INTO localidades VALUES (2, 'ROSARIO', 1);
INSERT INTO localidades VALUES (3, 'CORDOBA', 2);
