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
    var putAnio = "anio";

    var areaGetAll = 'area/listAll';
    
    var matGetAllMin = 'mat/listAllMin';
    var putMateria = "mat";

    this.anioGetAllMin = function () {
        return $http.get(server + sAcademico + anioGetAllMin);
    };

    this.anioPutNew = function (anio) {
        return $http.put(server + sAcademico + putAnio, anio);
    };

    this.areaGetAll = function () {
        return $http.get(server + sAcademico + areaGetAll);
    };

    this.matGetAllMin = function () {
        return $http.get(server + sAcademico + matGetAllMin);
    };

    this.materiaPutNew = function (mat) {
        return $http.put(server + sAcademico + putMateria, mat);
    };
});