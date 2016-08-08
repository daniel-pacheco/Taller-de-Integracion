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
 	.state('alumno', {
 		abstract: true,
 		url: '/alumno',
 		templateUrl: 'scripts/alumno/alumnonavbar.html',
 		controller: 'AlumnoNavBarCtrl',
 		data: {
 			pageTitle: 'Alumno',
 			authorizedRoles: [
 			USER_ROLES.admin,
 			USER_ROLES.alumno]
 		}
 	});
 })
 .controller('AlumnoNavBarCtrl', ['$scope', 'MENU_ALUMNO', 'MANUAL', 'ModalService',function ($scope, MENU_ALUMNO, MANUAL, ModalService) {

 	$scope.menuAlumno = MENU_ALUMNO;
 	$scope.manual = MANUAL.ALUMNO 


 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 	};

 	$scope.setActive($scope.menuAlumno.operaciones);

 	$scope.popover = {
 		"title": "Despedirse"   
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