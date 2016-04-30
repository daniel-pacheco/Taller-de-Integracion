'use strict';

angular.module('clientAppApp')
.factory('ObjectsFactory', function () {

  var newAlumno = function(){
    var alumno = {
      "matricula": 0,
      "idUsuario": 0,
      "nroDocumento": 0,
      "tipoDocumento": "DU/DNI",
      "nombre": "",
      "apellido": "",
      "listaTelefonos": [],
      "listaMails": [],
      "domicilio": {},
      "sexo": "",
      "nombreUsuario": "",
      "fechaNacimiento": ""
    };
    return alumno;
  };

  var newAlumnoLight = function(){ //no creo q lo usemos para un post pero queda de referencia
    var alumno = {
      "nroDocumento": 0,
      "nombre": "",
      "apellido": "",
      "curso" : "",
      "division" : "" 
    };
    return alumno;
  };

  var newTelefono = function(){
    var telefono = {
      "idTelefono": 0,
      "caracteristica": 0,
      "nroTelefono": 0,
      "tipoTelefono": ""
    };
    return telefono;
  };

  var newMail = function(){
    var mail = {
      "idMail": 0,
      "direccionMail": "",
      "tipoMail": ""
    };
    return mail;
  };

  var newDomicilio = function(){
    var domicilio = {
      "idDomicilio": 0,
      "calle": "",
      "numero": 0,
      "piso": 0,
      "localidad": "",
      "dpto": "-",
      "departamento": "Paraná",
      "provincia": "Entre Ríos",
      "codigoPostal": 3100,
      "barrio": ""
    };
    return domicilio;
  };

  var newDocente = function(){
    var docente = {
      "idUsuario"     : 0,
      "nroDocumento"  : 0,
      "tipoDocumento" : "DU/DNI",
      "nombre"        : "",
      "apellido"      : "",
      "cuil"          : 0,
      "rol"           : "",
      "listaTelefonos" :[],
      "listaMails"    :[],
      "domicilio"         :{},
      "listaTitulos": [],
      "sexo"            : "",
      "nombreUsuario"   : "",
      "fechaNacimiento" : "",
      "activo"          : false
    };
    return doncente;
  };

  var newDocenteLight = function(){ //no creo que se use pero queda de referencia
    var docente = {
      "nroDocumento" : 0,
      "nombre" : "",
      "apellido" : "",
      "año" : [],
      "area": [],
      "materia" : []
    };
    return doncente;
  };

  var newTitulo = function(){
    var titulo = {
      "idTitulo"         : 0,
      "nombreTitulo"     : "",
      "descripcionTitulo": ""
    };
    return titulo;
  };

  var newEstadisticaAnioCurso = function(){
    var estadisticaAnioCurso = {
      "cicloLectivo": 0,
      "anio": '',
      "division": 'U',
      "cantAlumnos": 0,
      "materias":[],
      "trimestres":[],
      "desempenioAnual":{}
    };
    return estadisticaAnioCurso;
  };

  var newEstadisticaMateria = function(){
    var estadisticaMateria = {
      "nombre": '',
      "aprobados": 0,
      "desaprobados": 0
    };
    return estadisticaMateria;
  };

  var newEstadisticaTrimestre = function(){
    var estadisticaTrimestre = {
      "aprobados": 0,
      "debenUna": 0,
      "debenDosOMas": 0
    };
    return estadisticaTrimestre;
  };

  return {
    newAlumno: newAlumno,
    newTelefono: newTelefono,
    newMail: newMail,
    newDomicilio: newDomicilio,
    newDocente: newDocente,
    newEstadisticaAnioCurso: newEstadisticaAnioCurso,
    newEstadisticaMateria: newEstadisticaMateria,
    newEstadisticaTrimestre: newEstadisticaTrimestre
  }
//};
})