Bienvenido a la Aplicación de Gestión de Proyectoss

Antes de probarla debes tener en cuenta varios factores.

Para poder ejecutar esta app debes realizar varios pasos:

1. Crea una base de datos en MySQL con el nombre que prefieras (te recomiendo llamarla 'gestion_proyectos_springboot' para no tener que modificarla después).

2. Configura el archivo application.properties modificando las siguientes partes (te muestro un ejemplo):

#Configuraciones de la base de datos

spring.datasource.url=jdbc:mysql://localhost:3306/gestion_proyectos_springboot --> Introduce tu puerto y el nombre de la bbdd que creaste

spring.datasource.username=root --> Introduce el username para acceder a tu bbdd

spring.datasource.password= --> Introduce la contraseña para acceder a tu bbdd

3. Debes ejecutar en MySql el siguiente script:
CREATE DATABASE gestion_proyectos_springboot;

CREATE TABLE departamento (
  ID int(11) NOT NULL AUTO_INCREMENT,
  Nombre varchar(100) NOT NULL,
  Ubicacion varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE empleado (
  ID int(11) NOT NULL AUTO_INCREMENT,
  Nombre varchar(100) NOT NULL,
  Apellido varchar(100) NOT NULL,
  Salario decimal(10,2) NOT NULL,
  ID_DEPARTAMENTO int(11) DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY ID_DEPARTAMENTO (ID_DEPARTAMENTO),
  FOREIGN KEY (ID_DEPARTAMENTO) REFERENCES departamento (ID)
);

CREATE TABLE proyecto (
  ID int(11) NOT NULL AUTO_INCREMENT,
  Nombre varchar(255) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE proyecto_empleado (
  ID_PROYECTO int(11) NOT NULL,
  ID_EMPLEADO int(11) NOT NULL,
  PRIMARY KEY (ID_PROYECTO,ID_EMPLEADO),
  KEY ID_EMPLEADO (ID_EMPLEADO),
  FOREIGN KEY (ID_PROYECTO) REFERENCES proyecto (ID),
  FOREIGN KEY (ID_EMPLEADO) REFERENCES empleado (ID)
);

Luego ejecuta la base de datos.

¡Disfruta de la Experiencia!
