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
	directivoService.getMyData().then(function(response) {
		// console.log(response);
		$scope.directivo = response.data;
	},
	function(response){
		showServerError('Ha ocurrido un error al contactar al servidor ' + response.statusText);
	});
};
	$scope.initCall(); //carga los datos desde la BD

//-- [Mi cuenta/cambiar contrasenia]
//-- [Mi cuenta/cambiar contrasenia] variables
    var cambiarPassData = [];
//-- [Mi cuenta/cambiar contrasenia] Form Management
//-- [Mi cuenta/cambiar contrasenia] filters
//-- [Mi cuenta/cambiar contrasenia] modals
//-- [Mi cuenta/cambiar contrasenia] utils
//-- [Mi cuenta/cambiar contrasenia] service calls
$scope.changePass = function () {

	cambiarPassData[0] = $scope.directivo.nroDocumento;
	cambiarPassData[1] = $scope.passwords.passActual;
	cambiarPassData[2] = $scope.passwords.passNuevo;

	spinnerService.show('miCuentaDirectivoSpinner');
	loginService.cambiarPass(cambiarPassData)
	.then(function(response){
		showServerSuccess('Contraseña cambiada con éxito',response);
		$scope.formModifPass.$setUntouched();
		$scope.passwords = {};
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('miCuentaDirectivoSpinner');
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
