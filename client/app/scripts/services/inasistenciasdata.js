'use strict';

angular.module('clientAppApp')
.constant('boletinInasistenciasData', 

 
{ 
  "nombre": "Juan Ignacio",
  "apellido": "Pelena Gomez",
  "nroDocumento": 123456789,
  "anio": "4º año",
  "curso": "U",
  "cicloLectivo": 2016,
  "listaInasistencias" :[
  {
    "idInasistencia" : 0,
    "cantidad" : 1/4,
    "fecha" : "15/08/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "concepto": "Ed física",
    "justificada" : "J",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 1,
    "cantidad" : 1/2,
    "fecha" : "15/10/2015"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "I",
    "concepto": "Ed física",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 2,
    "cantidad" : 1,
    "fecha" : "16/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "J",
    "concepto": "Clases",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 3,
    "cantidad" : 1/2,
    "fecha" : "15/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "J",
    "concepto": "Ed física",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 4,
    "cantidad" : 1,
    "fecha" : "05/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "concepto": "Clases",
    "justificada" : "J",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 5,
    "cantidad" : 1/4,
    "fecha" : "15/02/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "I",
    "concepto": "Se retira",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 6,
    "cantidad" : 1/2,
    "fecha" : "15/10/2016"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "J",
    "concepto": "Ed física",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : null,
    "cantidad" : 1/2,
    "fecha" : "14/10/2016"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "I",
    "concepto": "Ed física",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : null,
    "cantidad" : 1/2,
    "fecha" : "04/10/2014"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "J",
    "concepto": "Ed física",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : null,
    "cantidad" : 1/2,
    "fecha" : "15/10/2015"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "J",
    "concepto": "Llegada tarde",
    "totalAcum" : 0,
  }
  ]
}
)

