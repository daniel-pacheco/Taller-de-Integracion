'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.academicoService
 * @description
 * # academicoService
 * Service in the clientAppApp.
 */
 angular.module('clientAppApp')
 .service('academicoService', function ($http, SERVER) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var server = SERVER.address;
    var sAcademico = SERVER.sAcademico;

    var anioGetAllMin = 'anio/listAllMin';
    var deleteAnio = 'anio/';
    var putAnio = 'anio';

    var areaGetAll = 'area/listAll';
    var putArea = 'area';

    var putCurso = 'cur/';
    var curso = 'cur';
    var cursoVincularAlu = 'cur/vin';
    var cursoDesvincularAlu = 'cur/desvin';
    
    var matGetAllMin = 'mat/listAllMin';
    var materia = 'mat/';
    var materiaDesvincularDoc = 'mat/desvinDoc';

    var putLlamado = "llm/";
    var llamadosGetAll = "listAll";
    var deleteLlamado = "llm/"; 

    var putMesa = "mesa/";
    var getMesas = "inscripcion/";
    var inscripMesa = "inscripcion";
    var desinscripMesa = "desinscripcion";

    var especialidadesGetAll = "especialidadListAll/";
    var putEspecialidad = "especialidad/";

    this.anioDelete = function (idAnio) {
        return $http.delete(server + sAcademico + deleteAnio + idAnio);
    };

    this.anioGetAllMin = function () {
        return $http.get(server + sAcademico + anioGetAllMin);
    };

    this.anioPutNew = function (anioObj) {
        return $http.put(server + sAcademico + putAnio, anioObj);
    };

    this.areaGetAll = function () {
        return $http.get(server + sAcademico + areaGetAll);
    };

    this.areaPutNew = function (areaObj) {
        return $http.put(server + sAcademico + putArea, areaObj);
    };

    this.cursoDelete = function (idCurso) {
        return $http.delete(server + sAcademico + putCurso + idCurso);
    };

    this.cursoPutNew = function (cursoObj, idAnio) {
        return $http.put(server + sAcademico + putCurso + idAnio, cursoObj);
    };

    this.cursoVin = function (idAlumno, idCurso) {
        return $http.post(server + sAcademico + cursoVincularAlu, {values:[idAlumno, idCurso]});
    };

    this.cursoDesvin = function (idAlumno, idCurso) {
        return $http.post(server + sAcademico + cursoDesvincularAlu, {values:[idAlumno, idCurso]});
    };

    this.especialidadGetAll = function () {
        return $http.get(server + sAcademico + especialidadesGetAll);
    };

     this.especialidadPutNew = function (especialidadObj) {
        return $http.put(server + sAcademico + putEspecialidad, especialidadObj);
    };

    this.especialidadDelete = function (idEspecialidad) {
        return $http.delete(server + sAcademico + putEspecialidad + idEspecialidad);
    };

    this.llamadoPutNew = function (llamadoObj) {
        return $http.put(server + sAcademico + putLlamado, llamadoObj);
    };

    this.llamadoGetAll = function () {
        return $http.get(server + sAcademico + putLlamado + llamadosGetAll);
    };

    this.llamadoDelete = function (idLlamado) {
        return $http.delete(server + sAcademico + deleteLlamado + idLlamado);
    };

    this.matDelete = function (id) {
        return $http.delete(server + sAcademico + materia + id);
    };

    this.matGetAllMin = function () {
        return $http.get(server + sAcademico + matGetAllMin);
    };

    this.matGetById = function (id) {
        return $http.get(server + sAcademico + materia + id);
    };

    this.materiaPutNew = function (matObj) {
        return $http.put(server + sAcademico + materia, matObj);
    };
    
    this.matDesvin = function (params) {
        return $http.post(server + sAcademico + materiaDesvincularDoc, {values:[params[0], params[1], params[2]]}); //{values:[idTitular, idSuplente, idMaateria}
    };

    this.mesaPutNew = function (mesaObj){
        return $http.put(server + sAcademico + putMesa, mesaObj);
    };

    this.mesasGet = function (dni){
        return $http.get(server + sAcademico + getMesas + dni);
    };

    this.mesasInscribir = function (idMesa, idAlumno){
        return $http.post(server + sAcademico + inscripMesa, [idMesa, idAlumno]);
    };

    this.mesasDesinscribir = function (idMesa, idAlumno){
        return $http.post(server + sAcademico + desinscripMesa, [idMesa, idAlumno]);
    };

});