'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DocenteNavBarCtrl
 * @description
 * # DocenteNavBarCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES) {
 	$stateProvider
 	.state('docente', {
 		abstract: true,
 		url: '/docente',
 		templateUrl: 'scripts/docente/docentenavbar.html',
 		controller: 'DocenteNavBarCtrl',
 		data: {
 			pageTitle: 'Docente',
 			authorizedRoles: [
 			USER_ROLES.admin,
 			USER_ROLES.docente]
 		}
 	});
 })
 .controller('DocenteNavBarCtrl', ['$scope', 'MENU_DOCENTE', 'MANUAL', function ($scope, MENU_DOCENTE, MANUAL) {

 	$scope.menuDocente = MENU_DOCENTE;
 	$scope.manual = MANUAL.DOCENTE;
 	
 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

 	$scope.popover = {
 		"title": "Despedirse",   
 	};

 	//tooltips
 	$scope.tooltip = {
 		tooltipAuthors : {
 			'title' : 'ericpennachini <br> daniel-pacheco <br>  MartinHerrlein <br>  mauricioarielramirez'
 		}
 	};

 }]);
 