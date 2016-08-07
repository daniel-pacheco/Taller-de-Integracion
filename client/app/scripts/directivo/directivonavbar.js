'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoCtrl
 * @description
 * # DirectivoCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES) {
 	$stateProvider
 	.state('directivo', {
 		abstract: true,
 		url: '/directivo',
 		templateUrl: 'scripts/directivo/directivonavbar.html',
 		controller: 'DirectivoNavBarCtrl',
 		data: {
 			pageTitle: 'Directivo',
 			authorizedRoles: [
 			USER_ROLES.admin,
 			USER_ROLES.directivo]
 		}
 	});
 })
 .controller('DirectivoNavBarCtrl', ['$scope', 'MENU_DIRECTIVO', 'MANUAL', function ($scope, MENU_DIRECTIVO, MANUAL) {

 	$scope.menuDirectivo = MENU_DIRECTIVO;
 	$scope.manual = MANUAL.DIRECTIVO;
 	
 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

 	$scope.popover = {
 		"title": "Despedirse",   
 	};
	$scope.popoverHelp = {
 		"title": "Ayuda",   
 	}; 

 	//tooltips
 	$scope.tooltip = {
 		tooltipAuthors : {
 			'title' : 'ericpennachini <br> daniel-pacheco <br>  MartinHerrlein <br>  mauricioarielramirez <br> <b>soporte.sgsa@gmail.com</b>'
 		}
 	};

 }]);
 