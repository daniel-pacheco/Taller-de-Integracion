'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:MateriasCtrl
 * @description
 * # MateriasCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider) {
    $stateProvider
    .state('directivo.materias', {
        url: '/materias',
        templateUrl: 'views/materias.html',
        controller: 'MateriasCtrl',
        data: {
          pageTitle: 'Materias'
        }
    });
})
 .controller('MateriasCtrl', function ($scope) {
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

  
$scope.activeMenuIzqAlu = 1;
$scope.setActiveAlu = function(menuItemAlu) {
  $scope.activeMenuIzqAlu = menuItemAlu;
};

//Test
$scope.friends = [{nombre:'Educación Fisica', docenteTitular:'María Laura', anioPertenece: '4º', area: 'cs sociales'},
{nombre:'Matemática', docenteTitular:'Marta Blanco', anioPertenece: '4', area: 'cs sociales'},
{nombre:'Lengua', docenteTitular:'Lorena Gomez', anioPertenece: '4', area: 'cs naturales'},
{nombre:'Fisica', docenteTitular:'Alicia Modenutti', anioPertenece: '5º', area: 'cs sociales'},
{nombre:'Geografía', docenteTitular:'Mariela Rickert', anioPertenece: '5º', area: 'cs naturales'},
{nombre:'Historia', docenteTitular:'Gloria Herrlein', anioPertenece: '4º', area: 'cs exsactas'}];

});

