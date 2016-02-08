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
    alert(server);
    var getByIdQuery = 'EscuelaSantaLucia/rest/sAlumno/alu/';
    var getQuery = 'EscuelaSantaLucia/rest/sAlumno/listAll';
    var postQuery = 'EscuelaSantaLucia/rest/sAlumno/alu/';
    var delQuery = 'EscuelaSantaLucia/rest/sAlumno/alu/';

    this.alumnoGetById = function (id) {
        var token = loginService.authToken();
        var rol = loginService.userRole();

        return $http.get(server + getByIdQuery + id, {headers:{"auth0": token, "rol": rol, "Access-Control-Allow-Origin":"*"}});
    }

    this.alumnoGetAll = function () {
    	return $http.get(server + getQuery);
    }

	this.alumnoPut = function(alumno){	  
	  return $http.put(server + postQuery, alumno);
	}

	this.alumnoDel = function(alumnoId){	  
	  return $http.delete(server + postQuery + alumnoId);
	}

  });
