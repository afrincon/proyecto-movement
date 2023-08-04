CREATE TABLE IF NOT EXISTS suscriptor(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    identificacion VARCHAR(15) UNIQUE,
    direccion VARCHAR(50),
    telefono VARCHAR(15),
    estado BOOLEAN DEFAULT true
    );

CREATE TABLE IF NOT EXISTS datosSuscriptor(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    fechaNacimiento DATE NOT NULL,
    sexo CHAR(1) NOT NULL,
    peso NUMERIC(3,2) NOT NULL,
    altura INTEGER NOT NULL,
    CONSTRAINT fk_datos_suscriptor
    FOREIGN KEY(idSuscriptor)
    REFERENCES suscriptor(id)
    );

CREATE TABLE IF NOT EXISTS suscripcion(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    fechaInicio DATE NOT NULL,
    fechaFinalizacion DATE NOT NULL,
    estado BOOLEAN DEFAULT true,
    PRIMARY KEY(id),
    CONSTRAINT fk_suscripcion
    FOREIGN KEY(idSuscriptor)
    REFERENCES suscriptor(id)
    );

CREATE TABLE IF NOT EXISTS pagos(
    id SERIAL PRIMARY KEY,
    idSuscriptor SERIAL NOT NULL,
    valorPago INT NOT NULL CHECK (valorPago >= 0),
    fechaPago DATE NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_pago
    FOREIGN KEY(idSuscriptor)
    REFERENCES suscriptor(id)
    );