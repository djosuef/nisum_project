# RestFull Api with Spring Boot, JWT, H2.

Api Restfull para creación de usuarios y authenticcación con JWT.

## Comenzando 🚀

git clone https://github.com/djosuef/nisum_project.git

### Consideraciones iniciales

El servicio queda desplegado en http://localhost:8082
Se levanta una instancia de H2, que es accecible en http://192.168.10.2:8082/h2-console/
Se incluyen datos iniciales de acuerdo a las instrucciones en el fichero **\src\main\resources\import.sql**

### Uso de la Api 📋

## Autenticacion de Usuario

Se debe generar un token utilizando uno de los usurios incluidos en el arranque.
Usuarios: 
 1.- admin, password: admin
 2.- user, password: password

`POST /auth`

{"username":"admin", "password":"password"}

### Response

{
    "token": "token de autenticación"
}

**En todas los otros request se debe enviar en el header la variable **Authorization** con el token generado anteriormente.**
Al no disponer de un token valido se optendrá la siguiente respuesta:

{
    "message": "Error. Unauthorized"
}


## Crear nuevo usurio con teléfonos asociados

`POST /*`

{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "contrycode": "57"
    }
  ]
 }

### Response
HTTP/1.1 200 OK

{
    "id": 4,
    "created": "Mon May 09 13:42:22 COT 2022",
    "modified": "",
    "last_login": "Mon May 09 13:42:22 COT 2022",
    "token": "token",
    "isactive": true
}

## Consultar usuario autenticado del token

`GET /user`

HTTP/1.1 200 OK

{
    "name": "admin",
    "email": "admin@admin.com",
    "password": "$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi"
}

## Consultar un usurio específico

`GET /user/{id}`

http://localhost:8082/user/2

### Response

{
    "id": 2,
    "created": "2016-01-01 00:00:00.0",
    "modified": "",
    "last_login": "2016-01-01 00:00:00.0",
    "token": "asasasass",
    "isactive": true,
    "phones": [
        {
            "id": 2,
            "number": "654321",
            "citycode": "2",
            "countrycode": "14",
            "description": null
        },
        {
            "id": 3,
            "number": "98765122",
            "citycode": "4",
            "countrycode": "24",
            "description": null
        }
    ]
}

## Modificacion de usurio

`PUT /user/{id}`

### Request example

http://localhost:8082/user/1

{
"username": "admin",
"email": "juan@rodriguezo.rg",
"enabled":1,
"password": "bbbbdd3"
}

### Response Example

{
    "id": 1,
    "created": "2016-01-01 00:00:00.0",
    "modified": "Mon May 09 16:24:17 COT 2022",
    "last_login": "2016-01-01 00:00:00.0",
    "token": "asasasass",
    "isactive": true
}

## Eliminar usuario (La eliminacion de los Phones asociados se da en cascada)

`DEL /user/{id}`

http://localhost:8082/user/1

### Response

{
    "message": "Fue eliminado el usurio: admin"
}

## Listar Teléfonos

`GET /phone`

## Buscar teléfono por ID 

`GET /phone/{id}`

