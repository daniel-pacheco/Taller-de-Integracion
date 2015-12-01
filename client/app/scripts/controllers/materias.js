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

$scope.friends = [{name:'John', phone:'555-1276', anio: '4º'},
                         {name:'Mary', phone:'800-BIG-MARY', anio: '4'},
                         {name:'Mike', phone:'555-4321', anio: '4'},
                         {name:'Adam', phone:'555-5678', anio: '5º'},
                         {name:'Julie', phone:'555-8765', anio: '5º'},
                         {name:'Juliette', phone:'555-5678', anio: '4º'}];

});

