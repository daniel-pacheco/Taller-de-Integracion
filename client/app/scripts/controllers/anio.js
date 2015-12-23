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

 	$scope.listadoAnio = true;
 	$scope.administrarAnio = false;
 	$scope.nuevoAnio = false;

 	$scope.seleccionar = function(id) {
 		if (id === 'listadoAnio') {
 			$scope.administrarAnio = false;
 			$scope.nuevoAnio = false;
 			$scope.listadoAnio = true;
 			
 		}else if (id === 'nuevoAnio'){
 			$scope.listadoAnio = false;
 			$scope.administrarAnio = false;
 			$scope.nuevoAnio = true;
 		} else if (id === "administrarAnio"){
 		 	$scope.listadoAnio = false;
 		 	$scope.nuevoAnio = false;
 		 	$scope.administrarAnio = true;
 		 };
 	};
 });
