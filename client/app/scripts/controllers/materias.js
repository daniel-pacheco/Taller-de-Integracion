'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:MateriasCtrl
 * @description
 * # MateriasCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .controller('MateriasCtrl', function ($scope) {
  this.awesomeThings = [
  'HTML5 Boilerplate',
  'AngularJS',
  'Karma'
  ];

  $scope.listado1 = true;

  $scope.seleccionar = function(id) {
    if (id === 'listado') {
      $scope.listado1 = true;
    }else if (id === 'nuevaMateria'){
      $scope.listado1 = false;
    };
  };

  $scope.dropDownOptions = ['4', '5º', '3º', 'Todos'];

//Test
$scope.friends = [{nombre:'John', docenteTitular:'555-1276', anioPertenece: '4º', area: 'cs sociales'},
                         {nombre:'Mary', docenteTitular:'800-BIG-MARY', anioPertenece: '4', area: 'cs sociales'},
                         {nombre:'Mike', docenteTitular:'555-4321', anioPertenece: '4', area: 'cs naturales'},
                         {nombre:'Adam', docenteTitular:'555-5678', anioPertenece: '5º', area: 'cs sociales'},
                         {nombre:'Julie', docenteTitular:'555-8765', anioPertenece: '5º', area: 'cs naturales'},
                         {nombre:'Juliette', docenteTitular:'555-5678', anioPertenece: '4º', area: 'cs exsactas'}];

});

