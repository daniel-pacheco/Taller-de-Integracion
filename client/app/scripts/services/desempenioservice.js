'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.desempenioService
 * @description
 * # desempenioService
 * Service in the clientAppApp.
 */
angular.module('clientAppApp')
  .service('desempenioService', function ($http, SERVER) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var server = SERVER.address;/*'http://localhost:8080/'; 'http://192.168.1.8:8080/';//EscuelaSantaLucia/rest/sAlumno/listAll //http://localhost:8080/EscuelaSantaLucia/rest/sAlumno/listAll'*/
    var sDesempenio = SERVER.sDesempenio;

    var boletinInsistencia = '/boletinInasist/';

    this.putNewBoletinInasist = function(boletin){	  
	  return $http.put(server + sDesempenio + boletinInsistencia, boletin);
	};
});