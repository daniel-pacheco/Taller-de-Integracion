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
 .controller('DocenteNavBarCtrl', ['$scope', 'MENU_DOCENTE', 'MANUAL', 'ModalService',function ($scope, MENU_DOCENTE, MANUAL, ModalService) {

 	$scope.menuDocente = MENU_DOCENTE;
 	$scope.manual = MANUAL.DOCENTE;
 	
 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

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
 