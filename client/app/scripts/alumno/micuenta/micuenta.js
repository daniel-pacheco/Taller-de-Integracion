angular.module('clientAppApp')
.config(function($stateProvider) {
	$stateProvider
	.state('alumno.micuenta', {
		url: '/micuenta',
		templateUrl: 'scripts/alumno/micuenta/micuenta.html',
		controller: 'MiCuentaCtrl',
		data: {
			pageTitle: 'Mi Cuenta'
		}
	});
})

.controller('MiCuentaCtrl', function ($scope, alumnoData) {

	$scope.titulo = 'Mi Cuenta';
	$scope.alumno = alumnoData[0];
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
			$scope.subtitle = "Actualiza Mis Datos";
			$scope.nuevoAlumno = null;
			$scope.cambiarPass = false;
			$scope.actualizarMisDatos = true;
			break;
			case 'cambiarPass':
			$scope.misDatos = false;
			$scope.actualizarMisDatos = false;
			$scope.subtitle = "Cambiar Contrase&acute;a";
			$scope.cambiarPass = true;
			break;


		}

	}


	$scope.activeMenuIzqAlu = 1;
	$scope.setActiveAlu = function(menuItemAlu) {
		$scope.activeMenuIzqAlu = menuItemAlu;
	};



})
