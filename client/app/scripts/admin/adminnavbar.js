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
 .controller('AdminNavBarCtrl', ['$scope', 'MENU_ADMIN', 'MANUAL', 'ModalService', function ($scope, MENU_ADMIN, MANUAL, ModalService) {

 	$scope.menuAdmin = MENU_ADMIN;
 	$scope.manual = MANUAL.ADMINISTRADOR;

 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

 	$scope.setActive (['.configuracion', 'Configuracion']);

 	$scope.popover = {
 		"title": "Despedirse",   
 	};
 	$scope.popoverHelp = {
 		"title": "Ayuda",   
 	}; 

 	$scope.acercaDeModal = function() {
 		ModalService.showModal({
 			templateUrl: 'scripts/utils/contacto/modalContact.tpl.html',
 			controller: 'modalContactoController',
 			inputs: {
 				
 			}
 		}).then(function(modal) {
 			modal.element.modal();
 			modal.close.then(function(result){
 				
 			});
 		});
 	};

 }]);
 