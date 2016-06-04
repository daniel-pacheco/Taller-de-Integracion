'use strict';

angular.module('clientAppApp')
.factory('ObjectsFactory', function () {

  var newAlumno = function(){
    var alumno = {
      "matricula": 0,
      "idUsuario": null,
      "nroDocumento": 0,
      "tipoDocumento": "DNI",
      "nombre": "",
      "apellido": "",
      "listaTelefonos": [],
      "listaMails": [],
      "domicilio": this.newDomicilio(),
      "sexo": "",
      "nombreUsuario": "",
      "fechaNacimiento": "",
      "activo": true
    };
    return alumno;
  };

  var newAlumnoLight = function(){ //no creo q lo usemos para un post pero queda de referencia
    var alumno = {
      "nroDocumento": 0,
      "nombre": "",
      "apellido": "",
      "anio" : "",
      "curso" : "" 
    };
    return alumno;
  };

  var newTelefono = function(){
    var telefono = {
      "idTelefono": null,
      "caracteristica": null,
      "nroTelefono": null,
      "tipoTelefono": ""
    };
    return telefono;
  };

  var newMail = function(){
    var mail = {
      "idMail": null,
      "direccionMail": "",
      "tipoMail": ""
    };
    return mail;
  };

  var newDomicilio = function(){
    var domicilio = {
      "idDomicilio": null,
      "calle": "",
      "numero": 0,
      "piso": 0,
      "localidad": "Paraná",
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
      "idUsuario"     : null,
      "nroDocumento"  : 0,
      "tipoDocumento" : "DNI",
      "nombre"        : "",
      "apellido"      : "",
      "cuil"          : 0,
      "rol"           : "",
      "listaTelefonos" :[],
      "listaMails"    :[],
      "domicilio"     :this.newDomicilio(),
      "listaTitulos": [],
      "sexo"            : "",
      "nombreUsuario"   : "",
      "fechaNacimiento" : "",
      "activo"          : false
    };
    return docente;
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
      "idTitulo" : null,
      "nombreTitulo" : "",
      "tipo" : "",
      "anio" : '0'
    };
    return titulo;
  };

  var newMateria = function(){
    var materia = {
      "idMateria": null,
      "nombreMateria": '',
      "descripcion": '',
      "idDocenteTitular": null,
      "idDocenteSuplente": null,
      "area": {
        "idArea": null, 
        "nombre": ''
      },
      "activo": true,
      "idAnio": null
    };
    return materia;
  };

//Necesitamos materia ligth para listar materias

var newArea = function(){
  var area = {
    "idArea": null,
    "nombre": ''
  };
  return area;
};

var newAnio = function(){
  var anio = {
    "idAanio": null,
    "nombre": '',
    "descripcion": '',
    "listaCursos": [],
    "listaMaterias": [],
    "activo": true
  };
  return anio;
};

var newCurso = function(){
  var curso = {
    "idCurso": null,
    "division": '',
    "turno": '',
    "cicloLectivo": 0,
    "listaAlumnos": []
  };
  return curso;
};

var newInasistencia = function(){
  var inasistencia = {
    "idInasistencia" : null,
    "cantidad" : 0.0,
    "fecha" : "mmm dd, aaaa hh:mm:ss AM/PM",
    "concepto" : "",
    "justificada" : true,
    "totalAcum" : 0.0
  };
  return inasistencia;
};

var newMesa = function(){
  var mesa= {
    "idMesa" : null,
    "fechaHoraInicio" : "mmm dd, aaaa hh:mm:ss AM/PM",
    "fechaHoraFin" : "mmm dd, aaaa hh:mm:ss AM/PM",
    "plazoInscripcion" : 2,
    "materia" : null,
    "integrantesTribunal" : []
  };
  return mesa;
};

  //-- Estadisticas

  var newEstadisticaAnioCurso = function(){
    var estadisticaAnioCurso = {
      "cicloLectivo": 0,
      "anio": '',
      "curso": 'U',
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

  var newEstadisticaPorMateria = function(){
    var estadisticaPorMateria = {
      "cicloLectivo": 0,
      "nombre": '',
      "anio": '',
      "curso": 'U',
      "cantAlumnos": 0,
      "notas": [], //length === cantAlumnos
      "historico": []
    };
    return estadisticaPorMateria;
  };

  var newHistoricoPorMateria = function(){
    var historicoPorMateria = {
      "cicloLectivo": 0,
      "cantAlumnos": 0,
      "aprobados": 0,
      "desaprobados": 0
    };
    return historicoPorMateria;
  };

  return {
    newAlumno: newAlumno,
    newAlumnoLight: newAlumnoLight,
    newTelefono: newTelefono,
    newMail: newMail,
    newDomicilio: newDomicilio,
    newDocente: newDocente,
    newDocenteLight: newDocenteLight,
    newTitulo: newTitulo,
    newMateria: newMateria,
    newArea: newArea,
    newAnio: newAnio,
    newCurso: newCurso,
    newInasistencia: newInasistencia,
    newMesa: newMesa,

    //-- Estadisticas

    newEstadisticaAnioCurso: newEstadisticaAnioCurso,
    newEstadisticaMateria: newEstadisticaMateria,
    newEstadisticaTrimestre: newEstadisticaTrimestre,
    newEstadisticaPorMateria: newEstadisticaPorMateria,
    newHistoricoPorMateria, newHistoricoPorMateria
  }
})