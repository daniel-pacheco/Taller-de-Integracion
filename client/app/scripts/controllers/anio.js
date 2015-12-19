'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AnioCtrl
 * @description
 * # AnioCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .controller('AnioCtrl', function ($scope) {
 	this.awesomeThings = [
 	'HTML5 Boilerplate',
 	'AngularJS',
 	'Karma'
 	];

 	$scope.listado1 = true;

 	$scope.seleccionar = function(id) {
 		if (id === 'listado') {
 			$scope.listado1 = true;
 		}else if (id === 'nuevoAnio'){
 			$scope.listado1 = false;
 		};
 	};
 });
