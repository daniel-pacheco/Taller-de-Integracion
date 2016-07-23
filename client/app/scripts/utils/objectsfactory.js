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
      "domicilio": {},
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
      "caracteristica": 343,
      "nroTelefono": null,
      "tipoTelefono": "Otro"
    };
    return telefono;
  };

  var newMail = function(){
    var mail = {
      "idMail": null,
      "direccionMail": "",
      "tipoMail": "Otro"
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
      "rolDirectivo": false,
      "rolDocente": true,
      "listaTelefonos" :[],
      "listaMails"    :[],
      "domicilio"     :{},
      "listaTitulos": [],
      "sexo"            : "",
      "nombreUsuario"   : "",
      "fechaNacimiento" : "",
      "activo"          : true
    };
    return docente;
  };

  var newDocenteLight = function(){ //no creo que se use pero queda de referencia
    var docente = {
      "nroDocumento" : 0,
      "nombre" : "",
      "apellido" : "",
      "listaAnios" : [],
      "listaMaterias" : []
    };
    return docente;
  };

  var newTitulo = function(){
    var titulo = {
      "idTitulo" : null,
      "nombreTitulo" : "",
      "descripcionTitulo" : "", //refactorizar titulo, no concuerda con los campos de la BD
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
      "area": {},
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
    "idAnio": null,
    "nombre": '',
    "descripcion": '',
    "especialidad": {},
    "orden": 0, 
    // "listaCursos": [],
    // "listaMaterias": [],
    "cicloLectivo": 0,
    "activo": true
  };
  return anio;
};

var newEspecialidad = function(){
  var especialidad = {
    "idEspecialidad" : null,
    "nombre" : '',
    "nombreCorto" : ''
  };
  return especialidad;
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

var newAnioMin = function(){
  var anioMin = {
    "idAanio": null,
    "nombre": '',
    "descripcion": '',
    "cicloLectivo": 0,
    "listaCursos": [],
  };
  return anioMin;
};

var newCursoMin = function(){
  var cursoMin = {
    "idCurso": null,
    "division": '',
    "turno": '',
    "cantAlu": 0
  };
  return cursoMin;
};

var newInasistencia = function(){
  var inasistencia = {
    "idInasistencia" : null,
    "cantidad" : null,
    "fecha" : "",
    "justificada" : "",
    "concepto" : ""
  };
  return inasistencia;
};

var newBoletinInasistencia = function(){
  var boletinInasistencia = {
    "idBoletinInasistencias": null,
    "nombre": "",
    "apellido": "",
    "nroDocumento": 0,
    "cicloLectivo": null,
    "listaInasistencias": []
  };
  return boletinInasistencia;
};

var newPlanillaTrimDTO = function(){
  var planillaTrimDTO = {
    "trimestre": null,
    "anio": "",
    "curso": "",
    "cicloLectivo": 0
  };
  return planillaTrimDTO;
};

var newPlanillaTrimUpdateDTO = function(){
  var planillaTrimUpdateDTO = {
    "trimestre": null,
    "anio": "",
    "curso": "",
    "cicloLectivo": 0,
    "planilla": []
  };
  return planillaTrimUpdateDTO;
};

var newMesa = function(){
  var mesa= {
    "idLlamado" : null,
    "fechaHoraInicio" : "mmm dd, aaaa hh:mm:ss AM/PM",
    "fechaHoraFin" : "mmm dd, aaaa hh:mm:ss AM/PM",
    "idMateria" : null,
    "tribunalDoc1" : "",
    "tribunalDoc2" : "",
    "tribunalDoc3" : ""
  };
  return mesa;
};

var newLlamado = function(){
  var llamado = {
    "idLlamado": null,
    "descripcion": "",
    "listaMesas": null,
    "fechaInicio": "mmm dd, aaaa hh:mm:ss AM/PM",
    "fechaFin": "mmm dd, aaaa hh:mm:ss AM/PM"
  };
  return llamado;
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
    newAnioMin: newAnioMin,
    newCursoMin: newCursoMin,
    newInasistencia: newInasistencia,
    newBoletinInasistencia: newBoletinInasistencia,
    newPlanillaTrimDTO: newPlanillaTrimDTO,
    newPlanillaTrimUpdateDTO: newPlanillaTrimUpdateDTO,
    newMesa: newMesa,
    newLlamado: newLlamado,
    newEspecialidad: newEspecialidad,

    //-- Estadisticas

    newEstadisticaAnioCurso: newEstadisticaAnioCurso,
    newEstadisticaMateria: newEstadisticaMateria,
    newEstadisticaTrimestre: newEstadisticaTrimestre,
    newEstadisticaPorMateria: newEstadisticaPorMateria,
    newHistoricoPorMateria: newHistoricoPorMateria
  };
});
