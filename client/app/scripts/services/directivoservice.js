'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.directivoService
 * @description
 * # directivoService
 * Service in the clientAppApp.
 */
angular.module('clientAppApp')
  .service('directivoService', function ($http, SERVER) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var server = SERVER.address;/*'http://localhost:8080/'; 'http://192.168.1.8:8080/';//EscuelaSantaLucia/rest/sAlumno/listAll //http://localhost:8080/EscuelaSantaLucia/rest/sAlumno/listAll'*/
    var sDirectivo = SERVER.sDirectivo;

    var getByDni = 'getByDni/';
    var expectParam = 'dir/';
    var listAllMin = 'listAllMin';
    var myData = 'DatosPersonales';
    var deleteByDni = 'deleteByDni/';

    this.delByDni = function(dni){      
      return $http.delete(server + sDirectivo + expectParam + deleteByDni + dni);
    };

    this.getAllMin = function () {
        return $http.get(server + sDirectivo + listAllMin);
    };

    this.getByDni = function (dni) {
        return $http.get(server + sDirectivo + expectParam + getByDni + dni);
    };

    this.getMyData = function () {
        return $http.get(server + sDirectivo + myData);
    };

    this.putNew = function(directivo){	  
    	return $http.put(server + sDirectivo + expectParam, directivo);
    };

  });