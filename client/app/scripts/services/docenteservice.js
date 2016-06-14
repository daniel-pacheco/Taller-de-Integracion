'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.docenteService
 * @description
 * # docenteService
 * Service in the clientAppApp.
 */
 angular.module('clientAppApp')
 .service('docenteService', function ($http, SERVER) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var server = SERVER.address;/*'http://localhost:8080/'; 'http://192.168.1.8:8080/';//EscuelaSantaLucia/rest/sDocente/listAll //http://localhost:8080/EscuelaSantaLucia/rest/sDocente/listAll'*/
    var sDocente = SERVER.sDocente;

    var expectParam = 'doc/';
    var getByDni = 'getByDni/';
    var getByDniMin = 'getByDniMin/';
    var listAll = 'listAll';
    var listAllMin = 'listAllMin';
    var myData = 'DatosPersonales';
    var deleteByDni = 'deleteByDni/'

    this.getById = function (id) {
    	return $http.get(server + sDocente + expectParam + id);
    };

    this.getByDni = function (dni) {
    	return $http.get(server + sDocente + expectParam + getByDni + dni);
    };

    this.getByDniMin = function (dni) {
    	return $http.get(server + sDocente + expectParam + getByDniMin + dni);
    };

    this.getAll = function () {
    	return $http.get(server + sDocente + listAll);
    };

    this.getAllMin = function () {
    	return $http.get(server + sDocente + listAllMin);
    };

    this.getMyData = function () {
        return $http.get(server + sAlumno + myData);
    };

    this.putNew = function(docente){	  
    	return $http.put(server + sDocente + expectParam, docente);
    };

    this.delByDni = function(dni){      
      return $http.delete(server + sDocente + expectParam + deleteByDni + dni);
    };

});    