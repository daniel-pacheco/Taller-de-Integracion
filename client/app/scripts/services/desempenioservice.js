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

    var server = SERVER.address;
    var sDesempenio = SERVER.sDesempenio;

    var boletinInsistencia = 'boletinInasist/';
    var updateBoletinInasistencia = 'inasistencia/procesar';

    this.getByDni = function (dni) {
        return $http.get(server + sDesempenio + boletinInsistencia + dni);
    };

    this.putNewBoletinInasist = function(boletin){	  
	  return $http.put(server + sDesempenio + boletinInsistencia, boletin);
	};

	this.update = function(boletin){
		return $http.post(server + sDesempenio + updateBoletinInasistencia, boletin);
	};
});