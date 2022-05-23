# EJERCICIO FINAL CLOUD

Este ejercicio servirá para aplicar los conocimientos adquiridos en la formación para realizar una aplicación que simule una agencia de autobuses

- Descripcion
- Tecnologias utilizadas
- Instalación
- Funcionamiento

## Descripción

- Esta aplicación constará de dos back, backEmpresa y backWeb.
- Permite la reserva de tickets de autobús.
- BackWeb : es el servidor que recibe las peticiones procedentes del front(en este caso será Postman).
- BackEmpresa : es el servidor instalado en la empresa. Será el que se comunique con el servidor backweb pero también aceptará llamadas externas.


## Tecnologías

Estas son las tecnologías usadas para la realización de esta aplicación

- [Spring Boot](https://spring.io/projects/spring-boot) - Implementar la aplicación
- [Kafka](https://kafka.apache.org/) - Permite realizar la comunicación asíncrona entre las dos aplicaciones
- [Docker](https://www.docker.com/) - Exportar la aplicacion y gestionar todos los servicios


## Instalación

Lo primero que debemos hacer para correr la aplicación es descargar el proyecto y una vez descargado, ejecutar el archivo docker-compose.yml para generar los siguientes contenedores(desde la raíz del proyecto).
```
docker-compose up -d
```


## Funcionamiento

Una vez descargado, procederemos a probar la aplicación con Postman con una colección de endpoints que se encuentran en el fuente de la aplicación.

### BackEmpresa
* Reserva
  - http://localhost:8090/v0-empresa/reserva : lista con todas las reservas realizadas

* Cliente
  - http://localhost:8080/v0-empresa/client/{email} : cliente filtrado por email
  - http://localhost:8080/v0-empresa/mail/client : lista con los clientes

* Correo
  - http://localhost:8080/v0-empresa/mail : lista con todos los correos
  - http://localhost:8080/v0-empresa/mail/MAIL0002 : email filtrado por id

* Trip
  - (POST) http://localhost:8080/v0-empresa/trip : lista con todos los trips
  - (GET) http://localhost:8080/v0-empresa/trip/TRIP0001 : trip filtrado por id
  - (UPDATE) http://localhost:8080/v0-empresa/trip: modifica un trip
  - (DELETE) http://localhost:8080/v0-empresa/trip : elimina el trip
  
