'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DocenteCtrl
 * @description
 * # DocenteCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')

 .config(function($stateProvider) {
 	$stateProvider
 	.state('docente', {
 		url: '/docente',
 		templateUrl: 'views/docente.html',
 		controller: 'DocenteCtrl',
 		data: {
 			pageTitle: 'Docente'
 		}
 	});
 })
  .controller('DocenteCtrl', function ($scope) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    $scope.tooltip = {
    tooltipProfile : {
      'title' : 'Perfil'
    },
    tooltipDelete : {
      'title' : 'Eliminar'
    },
   };

 $scope.dropDownAreas = ['Cs. Sociales', 'Cs. Naturales', 'Cs. Exactas','Artes'];


$scope.listado = true;
    $scope.seleccionar = function(id){
    	$scope.listado = false;
    	$scope.nuevoPerfil = false;

    	switch (id) {
    		case 'listado':
    		$scope.listado = true;
    		break;
    		case 'nuevoPerfil':
    		$scope.nuevoPerfil = true;
    		break;
    	}
    };

    $scope.docentes = [{name:'John', area:'Cs. Sociales', cuil:'252525', materia:'Historia'},
{name:'Mary', area:'Cs. Naturales', cuil:'434343', materia:'Biologia' },
{name:'Mike', area:'Cs. Sociales', cuil:'111111', materia:'Geografia'},
{name:'Adam', area:'Artes', cuil:'7777777', materia:'Plastica'},
{name:'Julie', area:'Cs. Sociales', cuil:'000000', materia:'peperoni'},
{name:'Juliette', area:'Cs. Exactas', cuil:'929225', materia:'Fisica'}];
  });
