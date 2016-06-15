'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DocenteNavBarCtrl
 * @description
 * # DocenteNavBarCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('docente', {
 		url: '/docente',
 		templateUrl: 'scripts/docente/docentenavbar.html',
 		controller: 'DocenteNavBarCtrl',
 		data: {
 			pageTitle: 'Docente'
 		}
 	});
 })
 .controller('DocenteNavBarCtrl', function ($scope, MENU_DOCENTE) {

 	$scope.menuDocente = MENU_DOCENTE;
 	
 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

 	$scope.popover = {
 		"title": "Despedirse",   
 	};

 });
 