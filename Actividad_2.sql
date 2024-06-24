Create table Usuario(
UUID_usuario VARCHAR2(50),
Nombre VARCHAR2(50),
Correo VARCHAR2(50),
Contrasena VARCHAR2(50)
);

Select * from Usuario;

CREATE TABLE Tickets(
UUID_ticket VARCHAR2(50),
numTicket INT,
titulo VARCHAR2(50),
descripcion VARCHAR2(250),
autor VARCHAR2(50),
emailContact VARCHAR2(50),
Estado VARCHAR2(15)
); 

select * from tickets