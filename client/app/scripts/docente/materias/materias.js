'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DocenteMateriasCtrl
 * @description
 * # DocenteMateriasCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider) {
  $stateProvider
  .state('docente.materias', {
    url: '/materias',
    templateUrl: 'scripts/docente/materias/materias.html',
    controller: 'DocenteMateriasCtrl',
    data: {
      pageTitle: 'Materias'
    }
  });
})
 .controller('DocenteMateriasCtrl', function ($scope) {
 	$scope.titulo = "Docente/materia";
 });