angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.desemp.quinto', {
 		url: '/quinto',
 		templateUrl: 'scripts/directivo/desemp/quinto/desempQuinto.html',
 		controller: 'DesempQuintoCtrl',
 		data: {
 			pageTitle: 'Quinto'
 		}
 	});
 })
 .controller('DesempQuintoCtrl', function ($scope, CURSOS) {
  
  $scope.titulo = "Quinto Controler";

})