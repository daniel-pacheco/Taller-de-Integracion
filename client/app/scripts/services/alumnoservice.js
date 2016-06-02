'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.alumnoService
 * @description
 * # alumnoService
 * Service in the clientAppApp.
 */
angular.module('clientAppApp')
  .service('alumnoService', function ($http, SERVER, loginService) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var server = SERVER.address;/*'http://localhost:8080/'; 'http://192.168.1.8:8080/';//EscuelaSantaLucia/rest/sAlumno/listAll //http://localhost:8080/EscuelaSantaLucia/rest/sAlumno/listAll'*/
    console.log('server address: ' + server);
    var sAlumno = SERVER.sAlumno;
    console.log('sAlumno route: ' + sAlumno);

    var expectParam = 'alu/';
    var expectDni = 'alu/getByDni/';
    var listAll = 'listAll';
    var listAllMin = 'listAllMin';

    this.getById = function (id) {
        return $http.get(server + sAlumno + expectParam + id);
    }

    this.getByDni = function (dni) {
        return $http.get(server + sAlumno + expectDni + dni);
    }

    this.getAll = function () {
    	return $http.get(server + sAlumno + listAll);
    }

    this.getAllMin = function () {
        return $http.get(server + sAlumno + listAllMin);
    }

	this.putNew = function(alumno){	  
	  return $http.put(server + sAlumno + expectParam, alumno);
	}

	this.alumnoDel = function(alumnoId){	  
	  return $http.delete(server + postQuery + alumnoId);
	}

  });
