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


  $scope.tooltip = {
    tooltipEdit : {
      'title' : 'Editar'
    }, tooltipRemove : {
      'title' : 'Eliminar'
    }
  };
  $scope.dropDownOptions = ['4', '5º', '3º'];
  $scope.dropDownValue = '';

//Test
$scope.friends = [{nombre:'Educación Fisica', docenteTitular:'María Laura', anioPertenece: '4º', area: 'cs sociales'},
{nombre:'Matemática', docenteTitular:'Marta Blanco', anioPertenece: '4', area: 'cs sociales'},
{nombre:'Lengua', docenteTitular:'Lorena Gomez', anioPertenece: '4', area: 'cs naturales'},
{nombre:'Fisica', docenteTitular:'Alicia Modenutti', anioPertenece: '5º', area: 'cs sociales'},
{nombre:'Geografía', docenteTitular:'Mariela Rickert', anioPertenece: '5º', area: 'cs naturales'},
{nombre:'Historia', docenteTitular:'Gloria Herrlein', anioPertenece: '4º', area: 'cs exsactas'}];

});

