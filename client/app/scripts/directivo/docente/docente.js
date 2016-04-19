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
 	.state('directivo.docente', {
 		url: '/docente',
 		templateUrl: 'scripts/directivo/docente/docente.html',
 		controller: 'DocenteCtrl',
 		data: {
 			pageTitle: 'Docente'
 		}
 	});
 })
 .controller('DocenteCtrl', function ($scope, docenteData, $timeout, ModalService, SERVER, ObjectsFactory) {

  $scope.tooltip = {
    tooltipProfile : {
      'title' : 'Perfil'
    },
    tooltipDelete : {
      'title' : 'Eliminar'
    },    
    tooltipEdit : {
      'title' : 'Editar'
    },
  };


$scope.subtitle = "Nuevo Docente"
  $scope.dropDownAreas = ['Cs. Sociales', 'Cs. Naturales', 'Cs. Exactas','Artes'];


  $scope.listado = true;
  $scope.seleccionar = function(id){
   $scope.listado = false;
   $scope.nuevoPerfil = false;

   switch (id) {
    case 'listado':
    $scope.listado = true;
    this.showData();
    $scope.docenteEdit = null;
    break;
    case 'nuevoPerfil':
    $scope.nuevoPerfil = true;
      $scope.subtitle = "Nuevo Docente"
          $scope.docenteEdit = null;
    break;
  }
};
$scope.docenteFilter = function (docente) {//la clave de este comparador es q transofrma todo a string y va comparando las posiciones, no tiene en cuenta los espacios
  return (/*angular.lowercase(docente.materia).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||*/
    angular.lowercase(docente.nombre).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||
    angular.lowercase(docente.apellido).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||
    angular.lowercase(docente.cuil.toString()).indexOf(angular.lowercase($scope.filterByName) || '') !== -1
    );
};
$scope.showData = function() {
  $scope.docentes = docenteData;
}
$scope.showData();
$scope.activeMenuIzqDoc = 1;
$scope.setActiveDoc = function(menuItemDoc) {
  $scope.activeMenuIzqDoc = menuItemDoc;
};


//Modal
 $scope.showModal = function(docente) {

    ModalService.showModal({
      templateUrl: 'scripts/directivo/docente/modal/showProfileDocente.tpl.html',
      controller: 'showProfileDocenteModalController',
      inputs: {
        title: "Perfil",
        docente: docente
      }
    }).then(function(modal) {
      modal.element.modal();
      modal.close.then(function(result) {        
        console.log('el resultado es: ' + result); //$scope.algo.nroDocumento = result;
      });
    });

  }; 


$scope.docenteEdit = null;
$scope.editProfile = function(docente) {
  $scope.listado = false;
  $scope.subtitle = "Editar Docente"
  $scope.nuevoPerfil = true;
  $scope.docenteEdit = docente;
}
    /*$scope.docentes = [{name:'John', surname:'lenono', area:'Cs. Sociales', cuil:'252525', materia:'Historia'},
{name:'Mary', surname:'yein', area:'Cs. Naturales', cuil:'434343', materia:'Biologia' },
{name:'Mike', surname:'chumajer', area:'Cs. Sociales', cuil:'111111', materia:'Geografia'},
{name:'Adam', surname:'sami', area:'Artes', cuil:'7777777', materia:'Plastica'},
{name:'Julie', surname:'rose', area:'Cs. Sociales', cuil:'000000', materia:'peperoni'},
{name:'Juliette', surname:'romeo', area:'Cs. Exactas', cuil:'929225', materia:'Fisica'}];*/
});
