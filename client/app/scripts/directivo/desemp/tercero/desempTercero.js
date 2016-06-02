angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.desemp.tercero', {
 		url: '/tercero',
 		templateUrl: 'scripts/directivo/desemp/tercero/desempTercero.html',
 		controller: 'DesempTerceroCtrl',
 		data: {
 			pageTitle: 'Tercero'
 		}
 	});
 })
 .controller('DesempTerceroCtrl', function ($scope, CURSOS) {
  
  $scope.titulo = "Tercero Controler";

})