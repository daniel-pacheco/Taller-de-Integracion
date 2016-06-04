'use strict';
/* JSON PARA REALIZAR PRUEBAS DE SERVICIOS */


/* ##### DOCENTE (4) ##### */

/* PRIMERO */
angular.module('clientAppApp')
  .constant('mesasData', 

  	[
  	{
    "idMesa" : 1,
    "fechaHoraInicio" : "2016-06-01T14:15:00.000Z",
    "fechaHoraFin" : "2016-06-01T18:00:00.000Z",
    "plazoInscripcion" : 2,
    "materia" : {
    	"nombre": "Matemática",
    	"anio": 5,
    	"division": "U",
    },
    "integrantesTribunal" : ["Marta Blanco", "Martín Calli", "Marcela none"],
    "inscripto": true
},
  	{
    "idMesa" : 1,
    "fechaHoraInicio" : "2016-06-15T15:00:00.000Z",
    "fechaHoraFin" : "2016-06-15T20:30:00.000Z",
    "plazoInscripcion" : 2,
    "materia" : {
    	"nombre": "Química",
    	"anio": 3,
    	"division": "U",
    },
    "integrantesTribunal" : ["Alicia Tonutti", "Ana Luna", "None None"],
    "inscripto": false
},
  	{
    "idMesa" : 1,
    "fechaHoraInicio" : "2016-06-01T14:00:00.000Z",
    "fechaHoraFin" : "2016-06-01T22:00:00.000Z",
    "plazoInscripcion" : 2,
    "materia" : {
    	"nombre": "Matemática",
    	"anio": 6,
    	"division": "U",
    },
    "integrantesTribunal" : ["Marta Blanco", "Martín Calli", "Marcela none"],
    "inscripto": false
},

 ]
 );