angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.desemp.primero', {
 		url: '/primero',
 		templateUrl: 'scripts/directivo/desemp/primero/desempPrimero.html',
 		controller: 'DesempPrimeroCtrl',
 		data: {
 			pageTitle: 'Primero'
 		}
 	});
 })
 .controller('DesempPrimeroCtrl', function ($scope, CURSOS) {
  
  $scope.titulo = "Primero Controler";

})