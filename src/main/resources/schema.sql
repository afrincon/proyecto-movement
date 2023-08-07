CREATE TABLE IF NOT EXISTS suscriptor(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    identificacion VARCHAR(15) UNIQUE,
    sexo VARCHAR(1),
    direccion VARCHAR(50),
    telefono VARCHAR(15),
    estado BOOLEAN DEFAULT true,
    created timestamp default now() not null,
    updated timestamp default now() not null
);

CREATE TABLE IF NOT EXISTS pago(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    fechaPago VARCHAR(10) NOT NULL,
    valorPago INT NOT NULL CHECK (valorPago >= 0),
    created timestamp default now() not null,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS datos_suscriptor(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    fechaNacimiento VARCHAR(10) NOT NULL,
    peso NUMERIC NOT NULL,
    altura INTEGER NOT NULL,
    created timestamp default now() not null
);

CREATE TABLE IF NOT EXISTS suscripcion(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    fechaInicio VARCHAR(10) NOT NULL,
    fechaFinalizacion VARCHAR(10) NOT NULL,
    estado BOOLEAN DEFAULT true,
    created timestamp default now() not null,
    updated timestamp default now() not null,
    PRIMARY KEY(id)
);

