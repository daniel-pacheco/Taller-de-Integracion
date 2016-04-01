'use strict';

 angular.module('clientAppApp')
 .constant('boletinInasistenciasData', 

   
   { 
    "nombre": "Juan Ignacio",
    "apellido": "Pelena Gomez",
    "nroDocumento": 123456789,
    "cursaActualmente": "4º U",
    "anioAcademico": 2016,
    "listaInasistencias" :[
    {
      "idInasistencia" : 0,
      "cantidad" : 1/4,
      "fecha" : "15/08/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "faltoA": "Ed. física",
      "justificada" : "J",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : 1,
      "cantidad" : 1/2,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "justificada" : "I",
      "faltoA": "Ed. física",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : 2,
      "cantidad" : 1,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "justificada" : "J",
      "faltoA": "Clases",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : 3,
      "cantidad" : 1/2,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "justificada" : "J",
      "faltoA": "Ed. física",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : 4,
      "cantidad" : 1,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "faltoA": "Clases",
      "justificada" : "J",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : 5,
      "cantidad" : 1/4,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "justificada" : "I",
      "faltoA": "Se retira",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : 6,
      "cantidad" : 1/2,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "justificada" : "J",
      "faltoA": "Ed. física",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : null,
      "cantidad" : 1/2,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "justificada" : "I",
      "faltoA": "Ed. física",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : null,
      "cantidad" : 1/2,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "justificada" : "J",
      "faltoA": "Ed. física",
      "totalAcum" : 0,
    },
    {
      "idInasistencia" : null,
      "cantidad" : 1/2,
      "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
      "justificada" : "J",
      "faltoA": "Llegada tarde",
      "totalAcum" : 0,
    }
    ]
  }
  )

