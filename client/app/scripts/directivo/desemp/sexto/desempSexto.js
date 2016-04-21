angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.desemp.sexto', {
 		url: '/sexto',
 		templateUrl: 'scripts/directivo/desemp/sexto/desempSexto.html',
 		controller: 'DesempSextoCtrl',
 		data: {
 			pageTitle: 'Sexto'
 		}
 	});
 })
 .controller('DesempSextoCtrl', function ($scope, CURSOS) {
  
  $scope.titulo = "Sexto Controler";

})