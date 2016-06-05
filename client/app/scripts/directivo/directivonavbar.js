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
 	.state('directivo', {
 		url: '/directivo',
 		templateUrl: 'scripts/directivo/directivonavbar.html',
 		controller: 'DirectivoNavBarCtrl',
 		data: {
 			pageTitle: 'Directivo'
 		}
 	});
 })
 .controller('DirectivoNavBarCtrl', function ($scope, MENU_DIRECTIVO) {

 	$scope.menuDirectivo = MENU_DIRECTIVO;
 	
 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

 	$scope.popover = {
 		"title": "Despedirse",   
 	};

 });
 