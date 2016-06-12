
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

.controller('DirectivoMiCuentaCtrl', function ($scope) {

	$scope.titulo = 'Mi Cuenta';
	// $scope.alumno = alumnoData[0];
	/*$scope.call = function(){
		alumnoService.alumnoGetById(7).then(function(response){$scope.alumno = response.data});
		console.log($scope.alumno);
	};*/

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
			//$scope.nuevoAlumno = null;
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
