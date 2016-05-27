angular.module('clientAppApp')
.config(function($stateProvider) {
	$stateProvider
	.state('alumno.operaciones', {
		url: '/operaciones',
		templateUrl: 'scripts/alumno/operaciones/operaciones.html',
		controller: 'OperacionesCtrl',
		data: {
			pageTitle: 'Operaciones'
		}
	});
})

.controller('OperacionesCtrl', function ($scope) {

	$scope.titulo = 'Operaciones';
});