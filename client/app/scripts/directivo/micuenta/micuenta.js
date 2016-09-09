'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoMiCuentaCtrl
 * @description
 * # DirectivoMiCuentaCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')

 .config(function($stateProvider, USER_ROLES) {
 	$stateProvider
 	.state('directivo.micuenta', {
 		url: '/micuenta',
 		templateUrl: 'scripts/directivo/micuenta/micuenta.html',
 		controller: 'DirectivoMiCuentaCtrl',
 		data: {
 			pageTitle: 'Mi Cuenta',
 			authorizedRoles: [USER_ROLES.directivo]
 		}
 	});
 })

 .controller('DirectivoMiCuentaCtrl', ['$scope', 'directivoService', 'loginService', 'ModalService', 'spinnerService', function ($scope, directivoService, loginService, ModalService, spinnerService) {

//-- [Mi cuenta]
//-- [Mi cuenta] variables
$scope.titulo = 'Mi Cuenta';
$scope.directivo = {};
//-- [Mi cuenta] Form Management
$scope.seleccionar = function (id){
	$scope.actualizarMisDatos = false;
	$scope.cambiarPass = false;
	$scope.misDatos = false;

	switch (id){
		case 'misDatos':
		$scope.subtitle = "Mis datos";
		$scope.misDatos = true;
		setActiveAlu(1);
		break;
		case 'actualizarMisDatos':
		$scope.subtitle = "Actualizar mis datos";
		$scope.actualizarMisDatos = true;
		setActiveAlu(2);
		break;
		case 'cambiarPass':
		$scope.subtitle = "Cambiar clave";
		$scope.cambiarPass = true;
		setActiveAlu(3);
		break;
	}
}

$scope.$on('$viewContentLoaded', function(){
	$scope.seleccionar("misDatos");
	$scope.initCall(); //carga los datos desde la BD
});

function setActiveAlu (menuItemAlu) {
	$scope.activeMenuIzqAlu = menuItemAlu;
};

//-- [Mi cuenta] filters
//-- [Mi cuenta] modals
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
		// console.log(response);
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
		// console.log(response);
		var msg = message;

		if (response.data) {
			msg += ' ' + response.data;
		};      
		$scope.showMessage(msg, 'Operación exitosa' , true);
	};

	
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
//-- [Mi cuenta] service calls

//-- [Mi cuenta/Mis datos]
//-- [Mi cuenta/Mis datos] variables
//-- [Mi cuenta/Mis datos] Form Management
//-- [Mi cuenta/Mis datos] filters
//-- [Mi cuenta/Mis datos] modals
//-- [Mi cuenta/Mis datos] utils
//-- [Mi cuenta/Mis datos] service calls
$scope.initCall = function() {
	spinnerService.show('searchMicuentaSpinner');
	directivoService.getMyData()
	.then(
		function(response) {
			// console.log(response);
			$scope.directivo = response.data;
		},
		function(response){
			showServerError('Ha ocurrido un error al contactar al servidor ' + response.statusText);
		})
	.finally(function(){
		spinnerService.hide('searchMicuentaSpinner');
	});
};
	

//-- [Mi cuenta/cambiar contrasenia]
//-- [Mi cuenta/cambiar contrasenia] variables
var cambiarPassData = [];
//-- [Mi cuenta/cambiar contrasenia] Form Management
$scope.confirmChangePass = function() {
	var params;
	$scope.confirmModal('¿Realmente desea cambiar la contraseña? ', changePass, params);
}
//-- [Mi cuenta/cambiar contrasenia] filters
//-- [Mi cuenta/cambiar contrasenia] modals
//-- [Mi cuenta/cambiar contrasenia] utils
//-- [Mi cuenta/cambiar contrasenia] service calls
function changePass() {

	cambiarPassData[0] = $scope.directivo.nroDocumento;
	cambiarPassData[1] = $scope.passwords.passActual;
	cambiarPassData[2] = $scope.passwords.passNuevo;

	spinnerService.show('searchMicuentaSpinner');
	loginService.cambiarPass(cambiarPassData)
	.then(function(response){
		showServerSuccess('Contraseña cambiada con éxito.',response);
		$scope.formModifPass.$setUntouched();
		$scope.passwords = {};
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchMicuentaSpinner');
	});
};

//-- [Mi cuenta/sub-seccion]
//-- [Mi cuenta/sub-seccion] variables
//-- [Mi cuenta/sub-seccion] Form Management
//-- [Mi cuenta/sub-seccion] filters
//-- [Mi cuenta/sub-seccion] modals
//-- [Mi cuenta/sub-seccion] utils
//-- [Mi cuenta/sub-seccion] service calls



//-- variables
$scope.mails = {};
$scope.mails.mailNuevo = '';
$scope.mails.mailNuevoConf = '';

$scope.passwords = {};
$scope.passwords.passNuevo = '';
$scope.passwords.passNuevoConf = '';

}])
