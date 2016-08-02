
angular.module('clientAppApp')
.config(function($stateProvider, USER_ROLES) {
	$stateProvider
	.state('docente.micuenta', {
		url: '/micuenta',
		templateUrl: 'scripts/docente/micuenta/micuenta.html',
		controller: 'DocenteMiCuentaCtrl',
		data: {
			pageTitle: 'Mi Cuenta',
			authorizedRoles: [
 			USER_ROLES.admin,
 			USER_ROLES.docente]
		}
	});
})

.controller('DocenteMiCuentaCtrl', ['$scope', 'docenteService', 'ModalService', 'spinnerService', function ($scope, docenteService, ModalService, spinnerService) {
	
	$scope.titulo = 'Mi Cuenta';
	$scope.docente = {};


	$scope.misDatos = true;

	$scope.subtitle = "Mis Datos";
	$scope.seleccionar = function (id){
		switch (id){
			case 'misDatos':
			$scope.actualizarMisDatos = false;
			$scope.subtitle = "Mis Datos";
			$scope.cambiarPass = false;
			$scope.misDatos = true;
			$scope.initCall();
			break;
			case 'actualizarMisDatos':
			$scope.misDatos = false;
			$scope.subtitle = "Actualizar Mis Datos";
			$scope.nuevoDocente = null;
			$scope.cambiarPass = false;
			$scope.actualizarMisDatos = true;
			break;
			case 'cambiarPass':
			$scope.misDatos = false;
			$scope.actualizarMisDatos = false;
			$scope.subtitle = "Cambiar Clave";
			$scope.cambiarPass = true;
			break;


		}

	}
$scope.$on('$viewContentLoaded', function(){
	$scope.seleccionar('misDatos');
});

	$scope.activeMenuIzqAlu = 1;
	$scope.setActiveAlu = function(menuItemAlu) {
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
	spinnerService.show('searchDocenteSpinner');
	docenteService.getMyData()
	.then(
		function(response) {
			// console.log(response);
			$scope.docente = response.data;
			$scope.docente.sexo = ($scope.docente.sexo === 'M') ? 'Masculino' : 'Femenino';
		},
		function(response){
			showServerError(response);
		})
	.finally(function(){
		spinnerService.hide('searchDocenteSpinner');
	})
}; 

//-- variables


//-- variables
$scope.mails = {};
$scope.mails.mailNuevo = '';
$scope.mails.mailNuevoConf = '';

$scope.passwords = {};
$scope.passwords.passNuevo = '';
$scope.passwords.passNuevoConf = '';

}])
