'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.alumnoService
 * @description
 * # alumnoService
 * Service in the clientAppApp.
 */
angular.module('clientAppApp')
  .service('alumnoService', function ($http, SERVER) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var server = SERVER.address;/*'http://localhost:8080/'; 'http://192.168.1.8:8080/';//EscuelaSantaLucia/rest/sAlumno/listAll //http://localhost:8080/EscuelaSantaLucia/rest/sAlumno/listAll'*/
    var sAlumno = SERVER.sAlumno;

    var expectParam = 'alu/';
    var expectDni = 'alu/getByDni/';
    var expectDniMin = 'getByDniMin/';
    var listAll = 'listAll';
    var listAllMin = 'listAllMinAct';
    var myData = 'DatosPersonales';


    this.getById = function (id) {
        return $http.get(server + sAlumno + expectParam + id);
    };

    this.getByDni = function (dni) {
        return $http.get(server + sAlumno + expectDni + dni);
    };

    this.getByDniMin = function (dni) {
        return $http.get(server + sAlumno + expectDniMin + dni);
    };

    this.getAll = function () {
    	return $http.get(server + sAlumno + listAll);
    };

    this.getAllMin = function () {
        return $http.get(server + sAlumno + listAllMin);
    };

	this.putNew = function(alumno){	  
	  return $http.put(server + sAlumno + expectParam, alumno);
	};

    this.getMyData = function () {
        return $http.get(server + sAlumno + myData);
    };

	this.alumnoDel = function(alumnoId){	  
	  return $http.delete(server + sAlumno + expectParam + alumnoId);
	};

  });
