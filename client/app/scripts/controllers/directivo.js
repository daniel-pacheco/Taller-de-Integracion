'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoCtrl
 * @description
 * # DirectivoCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
  .controller('DirectivoCtrl', function ($scope) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
$scope.listado1 = true;

$scope.seleccionar = function (id){

	if (id === 'listado') {
		$scope.listado1 = true;
	}else if (id === 'nuevoPerfil'){
		$scope.listado1 = false;
	};
}

  });
