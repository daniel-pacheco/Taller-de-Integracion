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
        templateUrl: 'scripts/directivo/materias/materias.html',
        controller: 'MateriasCtrl',
        data: {
          pageTitle: 'Materias'
        }
    });
})
 .controller('MateriasCtrl', function ($scope, ModalService, areasData, $timeout, $alert, materiasData, ObjectsFactory, docenteData) {
  $scope.listado = true;
    $scope.subtitle = "Listado";
    $scope.listaMaterias = materiasData;

  $scope.seleccionar = function(id) {
    switch (id){
    case 'listado':
    $scope.showNuevaMateria = false;
    $scope.subtitle = "Listado";
    $scope.listado = true;
    break;
    case 'nuevaMateria':
    $scope.listado = false;
    $scope.subtitle = "Nueva materia";
    $scope.showNuevaMateria = true;
    $scope.nuevaMateria = new ObjectsFactory.newMateria();
    $scope.listaDocentes = docenteData;//Esta lista de docentes deberia tener solo el docente y el ID
    break;

  }
  };

  $scope.tooltip = {
    tooltipEdit : {
      'title' : 'Editar'
    }, tooltipDelete : {
      'title' : 'Eliminar'
    }
  };
  $scope.dropDownOptions = ['1', '2', '3', '4', '5', '6', '7', '8'];
  $scope.dropDownValue = '';

  
$scope.activeMenuIzqAlu = 1;
$scope.setActiveAlu = function(menuItemAlu) {
  $scope.activeMenuIzqAlu = menuItemAlu;
};


//-- Modals
  $scope.listaAreas = areasData;

$scope.addArea = function() {
  ModalService.showModal({
    templateUrl: 'scripts/directivo/materias/modal/addareadetails.tpl.html',
    controller: 'addAreaDetailsModalController',
    inputs: {
      title: "Administrar Areas",
      listaAreas: $scope.listaAreas,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      $scope.listaAreas = result;
    });
     /* modal.close.then(function(result) {        
        console.log('el resultado es: ' + result); //$scope.algo.nroDocumento = result;
    });*/
});
};

$scope.confirmModal = function(mesagge , materia) {
  ModalService.showModal({
    templateUrl: 'scripts/utils/confirm/modalConfirm.tpl.html',
    controller: 'modalConfirmController',
    inputs: {
      mensaje: mesagge,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      if (result) {
        $scope.listaMaterias.splice($scope.listaMaterias.indexOf(materia),1);//esto tiene que ser una llamada al service que elimine la materia
        //$scope.todoOk = true;
        }
    });
});
};

//-- Llamadas al servicio
$scope.deleteMateria = function (materia) {
  $scope.confirmModal("¿Desea eliminar "+materia.nombre+" de "+materia.ano+" "+materia.division+"?" , materia);
  //Hay que actualizar de nuevo la lista de docentes
};

$scope.agregarMateria = function () {
  //agregar el alumno
  //actualizar la lista
  $scope.listaMaterias.push ($scope.nuevaMateria);
  $scope.formMat.$setUntouched();
  $scope.nuevaMateria = new ObjectsFactory.newMateria();
};

//Test
$scope.friends = [{nombre:'Educación Fisica', docenteTitular:'María Laura', anioPertenece: '4º', area: 'cs sociales'},
{nombre:'Matemática', docenteTitular:'Marta Blanco', anioPertenece: '4', area: 'cs sociales'},
{nombre:'Lengua', docenteTitular:'Lorena Gomez', anioPertenece: '4', area: 'cs naturales'},
{nombre:'Fisica', docenteTitular:'Alicia Modenutti', anioPertenece: '5º', area: 'cs sociales'},
{nombre:'Geografía', docenteTitular:'Mariela Rickert', anioPertenece: '5º', area: 'cs naturales'},
{nombre:'Historia', docenteTitular:'Gloria Herrlein', anioPertenece: '4º', area: 'cs exsactas'}];

$scope.listaAnios = [
  {
    "idAnio": 0,
    "nombre": "4to"
  },
    {
    "idAnio": 1,
    "nombre": "5to"
  },
    {
    "idAnio": 2,
    "nombre": "6to"
  },
    {
    "idAnio": 3,
    "nombre": "1ro"
  },
    {
    "idAnio": 4,
    "nombre": "3roto"
  },
];
});

