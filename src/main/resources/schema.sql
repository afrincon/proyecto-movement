CREATE TABLE IF NOT EXISTS suscriptor(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    identificacion VARCHAR(15) UNIQUE,
    direccion VARCHAR(50),
    telefono VARCHAR(15),
    estado BOOLEAN DEFAULT true
    );

CREATE TABLE IF NOT EXISTS datos_suscriptor(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    fechaNacimiento DATE NOT NULL,
    sexo CHAR(1) NOT NULL,
    peso NUMERIC NOT NULL,
    altura INTEGER NOT NULL
    );

CREATE TABLE IF NOT EXISTS suscripcion(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    fechaInicio DATE NOT NULL,
    fechaFinalizacion DATE NOT NULL,
    estado BOOLEAN DEFAULT true,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS pago(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    valorPago INT NOT NULL CHECK (valorPago >= 0),
    fechaPago DATE NOT NULL,
    PRIMARY KEY(id)
    );