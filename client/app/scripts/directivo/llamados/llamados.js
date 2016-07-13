'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:LlamadosCtrl
 * @description
 * # LlamadosCtrl
 * Controller of the clientAppApp
 */
 
 angular.module('clientAppApp')
 .config(function($stateProvider, $selectProvider) {
  $stateProvider
  .state('directivo.llamados', {
    url: '/llamados',
    templateUrl: 'scripts/directivo/llamados/llamados.html',
    controller: 'LlamadosCtrl',
    data: {
      pageTitle: 'Materias'
    }
  });

  angular.extend($selectProvider.defaults, {
    animation: 'am-flip-x',
    sort: false,
    placeholder: 'Seleccione tribunal de docentes'
  });

})




 .controller('LlamadosCtrl', function ($scope, ModalService, $timeout, ObjectsFactory, $modal, spinnerService, exportTableService, academicoService, docenteService, $select) {

//-- [Llamado]
//-- [Llamado] variables
//-- [Llamado] Form Management
var setActiveLlamado = function(menuItemLlamado) {
  $scope.activeMenuIzqLlamado = menuItemLlamado;
};

$scope.seleccionar = function (id) {
  $scope.nuevaMesa = false;
  $scope.listado = false;
  $scope.inscripcion = false;
  $scope.gestionarLlamados = false;
  $scope.calificar = false;
  switch (id){
    case 'nuevaMesa':
    $scope.nuevaMesa = true;
    $scope.subtitle = 'Nueva mesa';
    setActiveLlamado(1);
    getAnios();
    getMaterias();
    getDocentes();
    break;
    case 'listado':
    $scope.listado = true;
    $scope.subtitle = 'Listado';
    setActiveLlamado(2);  
    break;
    case 'inscripcion':
    $scope.inscripcion = true;
    $scope.subtitle = 'Inscripción';
    setActiveLlamado(3);
    break;
    case 'gestionarLlamados':
    $scope.gestionarLlamados = true;
    $scope.subtitle = 'Gestionar llamados';
    setActiveLlamado(4);
    break;
    case 'calificar':
    $scope.calificar = true;
    $scope.subtitle = 'Calificar';
    setActiveLlamado(5);
    break;

  }
};



//-- [Llamado] filters
//-- [Llamado] modals
//-- [Llamado] utils
//-- [Llamado] service calls
function getAnios() {
  spinnerService.show('searchMateriaSpinner');
  academicoService.anioGetAllMin()
  .then(
    function(response){
      $scope.anios = response.data;
    },
    function(response){
      showServerError (response);
    })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner')
  });
};

function getMaterias() {
  spinnerService.show('searchMateriaSpinner');
  academicoService.matGetAllMin()
  .then(
    function(response){
      $scope.materias = response.data;
    },
    function(response){
      showServerError (response);
    })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner')
  });
};

function getDocentes() {
  spinnerService.show('searchMateriaSpinner');
  docenteService.getAllMin()  
  .then(function(response){
    $scope.listaDocentes = response.data;
  },
  function(response){
   showServerError('Se ha producido un error al intentar contactar al servidor: ' + response.statusText);
 })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner');
  });  
};

//-- [Llamado/Nueva mesa]
//-- [Llamado/Nueva mesa] variables

//-- [Llamado/Nueva mesa] Form Management
$scope.setFechaMesa = function () {
  if ($scope.nuevaMesaObj.horaFin === undefined) {
    $scope.nuevaMesaObj.horaFin = _.clone($scope.nuevaMesaObj.horaInicio);
  }
};
//-- [Llamado/Nueva mesa] filters
//-- [Llamado/Nueva mesa] modals
//-- [Llamado/Nueva mesa] utils
function initMesa(mesaMin){
  var mesa = ObjectsFactory.newMesa();
  mesa.idLlamado = mesaMin.periodo;
  mesa.fechaHoraInicio = mesaMin.horaInicio;
  mesa.fechaHoraFin = mesaMin.horaFin;
  mesa.idMateria = 8;//mesaMin.idMateria;
  mesa.tribunalDoc1 = mesaMin.docentes[0];
  mesa.tribunalDoc2 = mesaMin.docentes[1];
  mesa.tribunalDoc3 = mesaMin.docentes[2];
  return mesa;
};
//-- [Llamado/Nueva mesa] service calls
$scope.newMesa = function(mesaMin) {
  var mesa = initMesa(mesaMin);
  spinnerService.show('searchMateriaSpinner');
  console.log(mesa);
  spinnerService.hide('searchMateriaSpinner');


};


//-- [Llamado/sub-seccion]
//-- [Llamado/sub-seccion] variables
//-- [Llamado/sub-seccion] Form Management
//-- [Llamado/sub-seccion] filters
//-- [Llamado/sub-seccion] modals
//-- [Llamado/sub-seccion] utils
//-- [Llamado/sub-seccion] service calls
});

