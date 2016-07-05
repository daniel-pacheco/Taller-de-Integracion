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
    var matGetAllMin = 'mat/listAllMin';

    this.anioGetAllMin = function () {
        return $http.get(server + sAcademico + anioGetAllMin);
    };

    this.anioPutNew = function (anio) {
        return $http.put(server + sAcademico + putAnio, anio);
    };

    this.matGetAllMin = function () {
        return $http.get(server + sAcademico + matGetAllMin);
    };

 //    this.putNewBoletinInasist = function(boletin){	  
	//   return $http.put(server + sDesempenio + boletinInsistencia, boletin);
	// };

	// this.update = function(boletin){
	// 	return $http.post(server + sDesempenio + updateBoletinInasistencia, boletin);
	// };
});