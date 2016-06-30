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

 .controller('AnioCtrl', function ($scope, cursosData, ModalService, ObjectsFactory, aniosData, academicoService, spinnerService, $timeout) {

//-- [Anio] 
//-- [Anio] variables

$scope.anios = {};//aniosData;

//-- [Anio] Form Management

$scope.tooltip = {
  tooltipEdit : {
    'title' : 'Editar'
  }
};

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

//-- [Anio] filters

//-- Order List
$scope.predicate = 'anio';
$scope.reverse = true;
$scope.orderAnio = function(predicate) {
  $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
  $scope.predicate = predicate;
};

//-- [Anio] modals
//-- [Anio] utils

$scope.showMessage = function(mesagge, title, isGood) { //todo ok recibe true si salio bien o false si salio mal
  ModalService.showModal({
    templateUrl: 'scripts/utils/showMessage/modalMessage.tpl.html',
    controller: 'modalMessageController',
    inputs: {
      mensaje: mesagge,
      title: title,
      isGood: isGood
    }
  }).then(function(modal) {
    modal.element.modal();
  });
};

function showServerError (response){
  console.log(response);
  var msg = '';

  if (response.statusText) {
    msg = response.statusText;
  };

  if (response.data) {
    msg += ' - ' + response.data.mensaje + ' ' + response.data.severidad;
  };      
  $scope.showMessage(msg, 'Error al contactar al servidor' , false);
};

function showServerSuccess (message, response){
  console.log(response);
  var msg = message;

  if (response.data) {
    msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};

//-- [Anio] service calls

$scope.$on('$viewContentLoaded', function(){
  getAnios();//Here your view content is fully loaded !!
});

function getAnios() {
  spinnerService.show('searchAniosSpinner');
  academicoService.anioGetAllMin()
  .then(function(response){
    $scope.anios = response.data;
  },
  function(response){
    showServerError (response);
  })
  .finally(function(){spinnerService.hide('searchAniosSpinner')});
};




//-------------------------------------------

//-- [Seccion/sub-seccion]
//-- [Seccion/sub-seccion] variables
//-- [Seccion/sub-seccion] Form Management
//-- [Seccion/sub-seccion] filters
//-- [Seccion/sub-seccion] modals
//-- [Seccion/sub-seccion] utils
//-- [Seccion/sub-seccion] service calls


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
$scope.saveAnio = function(anio) {
  alert(anio.nombre+anio.descripcion+anio.listaCursos);
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

