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
    
    var matGetAllMin = 'mat/listAllMin';
    var putMateria = "mat";

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
        return $http.delete(server + sAcademico + curso + idCurso);
    };

    this.cursoPutNew = function (cursoObj, idAnio) {
        return $http.put(server + sAcademico + putCurso + idAnio, cursoObj);
    };

    this.matGetAllMin = function () {
        return $http.get(server + sAcademico + matGetAllMin);
    };

    this.materiaPutNew = function (matObj) {
        return $http.put(server + sAcademico + putMateria, matObj);
    };
});