'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.alumnoService
 * @description
 * # alumnoService
 * Service in the clientAppApp.
 */
angular.module('clientAppApp')
  .service('alumnoService', function ($http) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var server = 'http://192.168.1.11:8080/'; //http://192.168.0.101:8080/EscuelaSantaLucia/rest/sAlumno/listAll
    var query = 'EscuelaSantaLucia/rest/sAlumno/listAll';

    this.alumnoGetAll = function () {
    	return $http.get(server + query);
    }

  });
