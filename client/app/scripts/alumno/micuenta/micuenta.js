'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AlumnoMiCuentaCtrl
 * @description
 * # AlumnoMiCuentaCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
.config(function($stateProvider, USER_ROLES) {
	$stateProvider
	.state('alumno.micuenta', {
		url: '/micuenta',
		templateUrl: 'scripts/alumno/micuenta/micuenta.html',
		controller: 'AlumnoMiCuentaCtrl',
		data: {
			pageTitle: 'Mi Cuenta',
			authorizedRoles: [
 			USER_ROLES.admin,
 			USER_ROLES.alumno]
		}
	});
})

.controller('AlumnoMiCuentaCtrl', ['$state', '$scope', 'alumnoService', 'ModalService', 'spinnerService', function ($state, $scope, alumnoService, ModalService, spinnerService) {
	
//-- [Mi cuenta]
//-- [Mi cuenta] variables
$scope.titulo = 'Mi Cuenta';
$scope.alumno = {};

$scope.mails = {};
$scope.mails.mailNuevo = '';
$scope.mails.mailNuevoConf = '';

$scope.passwords = {};
$scope.passwords.passNuevo = '';
$scope.passwords.passNuevoConf = '';
//-- [Mi cuenta] Form Management

$scope.seleccionar = function (id){

	$scope.actualizarMisDatos = false;
	$scope.cambiarPass = false;
	$scope.misDatos = false;

	switch (id){
		case 'misDatos':
		$scope.subtitle = "Mis Datos";
		$scope.misDatos = true;
		setActiveAlu(1);
		$scope.initCall();
		break;
		case 'actualizarMisDatos':
		$scope.subtitle = "Actualizar Mis Datos";
		$scope.nuevoAlumno = null;
		$scope.actualizarMisDatos = true;
		setActiveAlu(2);
		break;
		case 'cambiarPass':
		$scope.subtitle = "Cambiar Clave";
		$scope.cambiarPass = true;
		setActiveAlu(3);
		break;
	}
}

$scope.$on('$viewContentLoaded', function(){
	$scope.seleccionar('misDatos');
});

function setActiveAlu (menuItemAlu) {
	$scope.activeMenuIzqAlu = menuItemAlu;
};
//-- [Mi cuenta] filters
//-- [Mi cuenta] modals

$scope.confirmModal = function(mesagge, funcion, parametro) { //este confirm recibe una funcion y un parametro para que despues de confirmar se pueda llamar a la funcion que se necesite
	ModalService.showModal({
		templateUrl: 'scripts/utils/confirm/modalConfirm.tpl.html',
		controller: 'modalConfirmController',
		inputs: {
			mensaje: mesagge,
		}
	}).then(function(modal) {
		modal.element.modal();
		modal.close.then(function(result){
			funcion(parametro);
		});
	});
};

//-- [Mi cuenta] utils

$scope.showMessage = function(mesagge, title, isGood) { //todo ok recibe true si salio bien o false si salio mal
	ModalService.showModal({
		templateUrl: 'scripts/utils/showMessage/modalMessage.tpl.html',
		controller: 'modalMessageController',
		inputs: {
			mensaje: mesagge,
			title: title,
			isGood: isGood
		}
	}).then(function(modal) {
		modal.element.modal();
	});
};

function showServerError (response){
  //console.log(response);
  var msg = '';

  if (response.statusText) {
  	msg = response.statusText;
  };

  if (response.data) {
  	msg += ' - ' + response.data.mensaje + ' ' + response.data.severidad;
  };      
  $scope.showMessage(msg, 'Error al contactar al servidor' , false);
};

function showServerSuccess (message, response){
  //console.log(response);
  var msg = message;

  if (response.data) {
  	msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};
//-- [Mi cuenta] service calls


$scope.initCall = function() {
	spinnerService.show('searchAlumnoSpinner');
	alumnoService.getMyData()
	.then(
		function(response) {
			// console.log(response);
			$scope.alumno = response.data;
			$scope.alumno.sexo = ($scope.alumno.sexo === 'M') ? 'Masculino' : 'Femenino';
		},
		function(response){
			showServerError(response);
		})
	.finally(function(){
		spinnerService.hide('searchAlumnoSpinner');
	})
}; 

//-- variables


//-- [Mi cuenta/sub-seccion]
//-- [Mi cuenta/sub-seccion] variables
//-- [Mi cuenta/sub-seccion] Form Management
//-- [Mi cuenta/sub-seccion] filters
//-- [Mi cuenta/sub-seccion] modals
//-- [Mi cuenta/sub-seccion] utils
//-- [Mi cuenta/sub-seccion] service calls

}])
