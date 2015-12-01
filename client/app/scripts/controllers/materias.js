'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:MateriasCtrl
 * @description
 * # MateriasCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
  .controller('MateriasCtrl', function ($scope) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

$scope.listado1 = true;

$scope.seleccionar() = function(id) {
		if (id === 'listado') {
		$scope.listado1 = true;
	}else if (id === 'nuevaMateria'){
		$scope.listado1 = false;
	};
};
  });

