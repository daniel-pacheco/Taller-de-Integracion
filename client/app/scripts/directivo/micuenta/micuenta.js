'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoMiCuentaCtrl
 * @description
 * # DirectivoMiCuentaCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
.config(function($stateProvider) {
	$stateProvider
	.state('directivo.micuenta', {
		url: '/micuenta',
		templateUrl: 'scripts/directivo/micuenta/micuenta.html',
		controller: 'DirectivoMiCuentaCtrl',
		data: {
			pageTitle: 'Mi Cuenta'
		}
	});
})

.controller('DirectivoMiCuentaCtrl', ['$scope', 'directivoService', 'ModalService', function ($scope, directivoService, ModalService) {

//-- [Mi cuenta]
//-- [Mi cuenta] variables
	$scope.titulo = 'Mi Cuenta';
	$scope.misDatos = true;
	$scope.subtitle = "Mis Datos";

	$scope.directivo = {};
//-- [Mi cuenta] Form Management
	$scope.seleccionar = function (id){
		switch (id){
			case 'misDatos':
			$scope.actualizarMisDatos = false;
			$scope.subtitle = "Mis datos";
			$scope.cambiarPass = false;
			$scope.misDatos = true;
			break;
			case 'actualizarMisDatos':
			$scope.misDatos = false;
			$scope.subtitle = "Actualizar mis datos";
			//$scope.nuevoAlumno = null;
			$scope.cambiarPass = false;
			$scope.actualizarMisDatos = true;
			break;
			case 'cambiarPass':
			$scope.misDatos = false;
			$scope.actualizarMisDatos = false;
			$scope.subtitle = "Cambiar clave";
			$scope.cambiarPass = true;
			break;
		}
	}

		$scope.activeMenuIzqAlu = 1;
	$scope.setActiveAlu = function(menuItemAlu) {
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
		console.log(response);
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
		console.log(response);
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
			console.log(response);
			$scope.directivo = response.data;
		},
		function(response){
			showServerError('Ha ocurrido un error al contactar al servidor ' + response.statusText);
		});
	};
	$scope.initCall(); //carga los datos desde la BD

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
