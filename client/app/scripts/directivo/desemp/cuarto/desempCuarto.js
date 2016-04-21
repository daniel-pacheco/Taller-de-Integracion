angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.desemp.cuarto', {
 		url: '/cuarto',
 		templateUrl: 'scripts/directivo/desemp/cuarto/desempCuarto.html',
 		controller: 'DesempCuartoCtrl',
 		data: {
 			pageTitle: 'Cuarto'
 		}
 	});
 })
 .controller('DesempCuartoCtrl', function ($scope, CURSOS) {
  
  $scope.titulo = "Cuarto Controler";

})