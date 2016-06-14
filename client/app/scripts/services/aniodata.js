'use strict';
/* JSON PARA REALIZAR PRUEBAS DE SERVICIOS */


/* ##### DOCENTE (4) ##### */

/* PRIMERO */
angular.module('clientAppApp')
.constant('aniosData', 

	[
	{
  "idAnio": 1,
  "nombre": '5°',
  "descripcion": '5to año de Licenciatura en Sistemas de Información',
  "listaCursos": [
    {
      "idCurso": 2,
      "curso": "A",
      "turno": "Mañana",
      "cicloLectivo": 2016,
      "cantAlumnos": 28
    },
    {
      "idCurso": 3,
      "curso": "A",
      "turno": "Nocturno",
      "cicloLectivo": 2016,
      "cantAlumnos": 29
    }
  ],
  "activo": true
},
	{
  "idAnio": 2,
  "nombre": '1°',
  "descripcion": '1ro año de Licenciatura en Sistemas de Información',
  "listaCursos": [
    {
      "idCurso": 2,
      "curso": "A",
      "turno": "Mañana",
      "cicloLectivo": 2016,
      "cantAlumnos": 22
    },
    {
      "idCurso": 3,
      "curso": "A",
      "turno": "Nocturno",
      "cicloLectivo": 2016,
      "cantAlumnos": 24
    }
  ],
  "activo": true
},
	{
  "idAnio": 3,
  "nombre": '3°',
  "descripcion": '3er año de Licenciatura en Sistemas de Información',
  "listaCursos": [
    {
      "idCurso": 4,
      "curso": "B",
      "turno": "Tarde",
      "cicloLectivo": 2016,
      "cantAlumnos": 30
    },
    {
      "idCurso": 2,
      "curso": "B",
      "turno": "Mañana",
      "cicloLectivo": 2016,
      "cantAlumnos": 15
    }
  ],
  "activo": true
},
	{
  "idAnio": 4,
  "nombre": '4°',
  "descripcion": '4to año de Licenciatura en Sistemas de Información',
  "listaCursos": [
    {
      "idCurso": 2,
      "curso": "A",
      "turno": "Mañana",
      "cicloLectivo": 2016,
      "cantAlumnos": 19
    }
  ],
  "activo": true
},
	{
  "idAnio": 5,
  "nombre": '2°',
  "descripcion": '2do año de Licenciatura en Sistemas de Información',
  "listaCursos": [
    {
      "idCurso": 0,
      "curso": "A",
      "turno": "Mañana",
      "cicloLectivo": 2016,
      "cantAlumnos": 20
    },
    {
      "idCurso": 2,
      "curso": "A",
      "turno": "Tarde",
      "cicloLectivo": 2016,
      "cantAlumnos": 23
    }
  ],
  "activo": true
},

	]
	);