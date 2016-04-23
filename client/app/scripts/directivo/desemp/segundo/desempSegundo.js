angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.desemp.segundo', {
 		url: '/segundo',
 		templateUrl: 'scripts/directivo/desemp/segundo/desempSegundo.html',
 		controller: 'DesempSegundoCtrl',
 		data: {
 			pageTitle: 'Segundo'
 		}
 	});
 })
 .controller('DesempSegundoCtrl', function ($scope, CURSOS) {
  
  $scope.titulo = "Segundo Controler";

})