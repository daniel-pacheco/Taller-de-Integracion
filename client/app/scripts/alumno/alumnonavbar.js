'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoCtrl
 * @description
 * # DirectivoCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('alumno', {
 		url: '/alumno',
 		templateUrl: 'scripts/alumno/alumnonavbar.html',
 		controller: 'AlumnoNavBarCtrl',
 		data: {
 			pageTitle: 'Alumno'
 		}
 	});
 })
 .controller('AlumnoNavBarCtrl', function ($scope, MENU_ALUMNO) {

 	$scope.menuAlumno = MENU_ALUMNO;
 	
 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

 	$scope.popover = {
 		"title": "Despedirse"   
 	};

 });