'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AdminCtrl
 * @description
 * # AdminCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES) {
 	$stateProvider
 	.state('administrador', {
 		abstract: true,
 		url: '/admin',
 		templateUrl: 'scripts/admin/adminnavbar.html',
 		controller: 'AdminNavBarCtrl',
 		data: {
 			pageTitle: 'Admin',
 			authorizedRoles: [
 			USER_ROLES.admin]
 		}
 	});
 })
 .controller('AdminNavBarCtrl', ['$scope', 'MENU_ADMIN', 'MANUAL', function ($scope, MENU_ADMIN, MANUAL) {

 	$scope.menuAdmin = MENU_ADMIN;
 	$scope.manual = MANUAL.ADMINISTRADOR;
 	
 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

 	$scope.setActive (['.configuracion', 'Configuracion']);

 	$scope.popover = {
 		"title": "Despedirse",   
 	};

 }]);
 