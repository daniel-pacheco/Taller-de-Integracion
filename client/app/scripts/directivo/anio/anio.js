'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AnioCtrl
 * @description
 * # AnioCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES, $selectProvider) {
  $stateProvider
  .state('directivo.anio', {
    url: '/anio',
    templateUrl: 'scripts/directivo/anio/anio.html',
    controller: 'AnioCtrl',
    data: {
     pageTitle: 'Anio',
     authorizedRoles: [USER_ROLES.admin]           
   }
 });

  angular.extend($selectProvider.defaults, {
    animation: 'am-flip-x',
    sort: false,
    placeholder: 'Seleccione divisiones'
  });
})

 .controller('AnioCtrl', function ($scope, cursosData, ModalService, ObjectsFactory, aniosData) {
 	
 	$scope.tooltip = {
    tooltipEdit : {
      'title' : 'Editar'
    }
  };

  $scope.anios = aniosData;

//-- Filters
$scope.listadoAnio = true;
$scope.subtitle = "Listado";

$scope.seleccionar = function(id) {//Hacer una funcion que ponga en true el nombre de variable que le llegue y el resto en false (toogle de tres variables)
 $scope.administrarAnio = false;
 $scope.nuevoAnio = false;
 $scope.listadoAnio = false;
 $scope.editanio = false;
 $scope.showEditAnioMenuIzq = false;

 switch (id) {
  case 'listadoAnio':
  $scope.listadoAnio = true;
  $scope.subtitle = "Listado";
  break;
  case 'nuevoAnio':
  $scope.nuevoAnio = true;
  $scope.subtitle = "Nuevo Año";
  break;
  case 'administrarAnio':
  $scope.administrarAnio = true;
  $scope.subtitle = "Administrar";
}
};


//-- Multiselect


//-- Order List
$scope.predicate = 'anio';
$scope.reverse = true;
$scope.orderAnio = function(predicate) {
  $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
  $scope.predicate = predicate;
};


$scope.clearFormAnio = function() {
  $scope.formAnio.$setUntouched();
  $scope.newAnio = ObjectsFactory.newAnio();
  $scope.newAnio.listaCursos = [];
};

$scope.cursos = cursosData;

$scope.acept = function () {
  if (!$scope.listAnioIsEnabled) {
    $scope.listAnioIsEnabled = true;
  };
};

$scope.activeMenuIzqAnio = 2;
$scope.setActiveAnio = function(menuItemAnio) {
  $scope.activeMenuIzqAnio = menuItemAnio;
};



//-- Modals 
$scope.addCurso = function() {
  ModalService.showModal({
    templateUrl: 'scripts/directivo/anio/modal/addCurso.tpl.html',
    controller: 'addCursoModalController',
    inputs: {
      title: "Administrar Divisiones",
      listaCursos: $scope.cursos,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      $scope.cursos = result;
    });
});
};

//-- Llamadas al service
$scope.saveAnio = function(curso) {
};

$scope.editAnio = function(anio){
 $scope.listadoAnio = false;
 $scope.nuevoAnio = true;
  $scope.setActiveAnio(4);
  $scope.subtitle = "Editar año";
  $scope.showEditAnioMenuIzq = true;
  $scope.newAnio = angular.copy(anio);


  angular.forEach($scope.newAnio.listaCursos, function (item, index) {
      $scope.newAnio.listaCursos[index] = item.idCurso;
  });

};
 	//Test
 /*	$scope.anios = [{anio:'7º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '21'},
 	{anio:'1º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '32'},
 	{anio:'2º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '28'},
 	{anio:'3º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '22'},
 	{anio:'4º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '45'},
 	{anio:'5º', division:'U', turno: 'Tarde', cantidadDeAlumnos: '32'}];*/

  //---test list alumnos notas
  $scope.test = [{nro:'1', name:'John', surName:'Lennon', DNI:'555555555'},
  {nro:'1', name:'John', surName:'Lennon', DNI:'555555555'},
  {nro:'2', name:'John', surName:'Lennon', DNI:'555555555'},
  {nro:'3', name:'John', surName:'Lennon', DNI:'555555555'},
  {nro:'4', name:'John', surName:'Lennon', DNI:'555555555'},
  {nro:'5', name:'John', surName:'Lennon', DNI:'555555555'},
  {nro:'6', name:'John', surName:'Lennon', DNI:'555555555'},
  {nro:'7', name:'John', surName:'Lennon', DNI:'555555555'}];

});
