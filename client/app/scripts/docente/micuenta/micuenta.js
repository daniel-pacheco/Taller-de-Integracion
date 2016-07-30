
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

.controller('DocenteMiCuentaCtrl', function ($scope, $state) {
	
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


	$scope.activeMenuIzqAlu = 1;
	$scope.setActiveAlu = function(menuItemAlu) {
		$scope.activeMenuIzqAlu = menuItemAlu;
	};

//-- variables
$scope.mails = {};
$scope.mails.mailNuevo = '';
$scope.mails.mailNuevoConf = '';

$scope.passwords = {};
$scope.passwords.passNuevo = '';
$scope.passwords.passNuevoConf = '';

})
