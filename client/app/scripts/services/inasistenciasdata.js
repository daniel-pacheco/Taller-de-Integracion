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
    "fecha" : "May 15, 2016 03:45:12 am",/*"mmm dd, aaaa hh:mm:ss AM/PM"*/
    "concepto": "Ed física",
    "justificada" : "J",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 1,
    "cantidad" : 1/2,
    "fecha" : "May 15, 2016 03:45:12 am"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "I",
    "concepto": "Ed física",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 2,
    "cantidad" : 1,
    "fecha" : "May 15, 2016 03:45:12 am"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "J",
    "concepto": "Clases",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 3,
    "cantidad" : 1/2,
    "fecha" : "May 15, 2016 03:45:12 am"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "J",
    "concepto": "Ed física",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 4,
    "cantidad" : 1,
    "fecha" : "May 15, 2016 03:45:12 am"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "concepto": "Clases",
    "justificada" : "J",
    "totalAcum" : 0,
  },
  {
    "idInasistencia" : 5,
    "cantidad" : 1/4,
    "fecha" : "May 15, 2016 03:45:12 am"/*"mmm dd, aaaa hh:mm:ss AM/PM"*/,
    "justificada" : "I",
    "concepto": "Se retira",
    "totalAcum" : 0,
  },
  
  ]
}
)

