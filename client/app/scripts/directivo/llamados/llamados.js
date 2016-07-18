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

 .controller('LlamadosCtrl', function ($scope, ModalService, $timeout, ObjectsFactory, spinnerService, exportTableService, academicoService, docenteService, $select) {

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
  $scope.nuevoLlamado = false;

  switch (id){
    case 'nuevaMesa':
    $scope.nuevaMesa = true;
    $scope.subtitle = 'Nueva mesa';
    setActiveLlamado(1);
    getLlamados();
    getAnios();
    getMaterias();
    getDocentes();
    break;
    case 'listado':
    $scope.listado = true;
    $scope.subtitle = 'Listado';
    setActiveLlamado(2);  
    //getLlamados();
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
    case 'nuevoLlamado':
    $scope.nuevoLlamado = true;
    $scope.subtitle = 'Nuevo llamado';
    setActiveLlamado(6);
  }
};

$scope.seleccionar("listado");

//-- Order list
$scope.predicate = 'fechaInicio';
$scope.reverse = false;
$scope.orderLlamado = function(predicate) {
  $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
  $scope.predicate = predicate;
};
//-- [Llamado] filters
//-- [Llamado] modals
//-- [Llamado] utils
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
  //console.log(response);
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
  //console.log(response);
  var msg = message;

  if (response.data) {
    msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};

$scope.$on('$viewContentLoaded', function(){
  getLlamados();//Here your view content is fully loaded !!
});
//-- [Llamado] service calls


//-- [Llamado/Nueva mesa]
//-- [Llamado/Nueva mesa] variables

//-- [Llamado/Nueva mesa] Form Management
//-- [Llamado/Nueva mesa] filters
//-- [Llamado/Nueva mesa] modals
//-- [Llamado/Nueva mesa] utils
function initMesa(mesaMin){
  var mesa = ObjectsFactory.newMesa();
  mesa.idLlamado = mesaMin.periodo.idLlamado;
  mesa.fechaHoraInicio = setFechaHora(mesaMin.horaInicio);//new Date(mesaMin.horaInicio);
  mesa.fechaHoraFin = setFechaHora(mesaMin.horaFin);//new Date(mesaMin.horaFin);
  mesa.idMateria = mesaMin.selectedMateria;
  mesa.tribunalDoc1 = mesaMin.docentes[0];
  mesa.tribunalDoc2 = mesaMin.docentes[1];
  mesa.tribunalDoc3 = mesaMin.docentes[2];
  return mesa;
};

function setFechaHora(hora){
  var date = new Date($scope.nuevaMesaObj.fechaMesa);
  date.setHours(hora.getHours());
  date.setMinutes(hora.getMinutes());
  return date;
};

$scope.clearFormMesa = function(){
  $scope.formMesa.$setUntouched();
  $scope.nuevaMesaObj = ObjectsFactory.newMesa();
};
//-- [Llamado/Nueva mesa] service calls
function getAnios() {
  spinnerService.show('searchLlamadoSpinner');
  academicoService.anioGetAllMin()
  .then(
    function(response){
      $scope.anios = response.data;
    },
    function(response){
      showServerError (response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner')
  });
};

function getMaterias() {
  spinnerService.show('searchLlamadoSpinner');
  academicoService.matGetAllMin()
  .then(
    function(response){
      $scope.materias = response.data;
    },
    function(response){
      showServerError (response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner')
  });
};

function getDocentes() {
  spinnerService.show('searchLlamadoSpinner');
  docenteService.getAllMin()  
  .then(function(response){
    $scope.listaDocentes = response.data;
  },
  function(response){
   showServerError('Se ha producido un error al intentar contactar al servidor: ' + response.statusText);
 })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  });  
};

$scope.newMesa = function(mesaMin) {
  var mesa = initMesa(mesaMin);
  spinnerService.show('searchLlamadoSpinner');
  academicoService.mesaPutNew(mesa)
  .then(
    function(response){
      showServerSuccess('La mesa se ha dado de alta de alta con éxito',response.data);
      $scope.clearFormMesa();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  });

};


//-- [Llamado/nuevo Llamado]
//-- [Llamado/nuevo Llamado] variables
//-- [Llamado/nuevo Llamado] Form Management
//-- [Llamado/nuevo Llamado] filters
//-- [Llamado/nuevo Llamado] modals
//-- [Llamado/nuevo Llamado] utils
function initLlamado(llamadoMin){
  var llamado = ObjectsFactory.newLlamado();
  llamado.descripcion = llamadoMin.descripcion;
  llamado.fechaInicio = llamadoMin.fechaInicioLlamado;
  llamado.fechaFin = llamadoMin.fechaFinLlamado;
  return llamado;
};
//-- [Llamado/nuevo Llamado] service calls
$scope.newLlamado = function(llamadoMin) {
  var llamado = initLlamado(llamadoMin);
  spinnerService.show('searchLlamadoSpinner');
  academicoService.llamadoPutNew(llamado)
  .then(
    function(response){
      showServerSuccess('El llamado se ha dado de alta de alta con éxito. Id:',response.data);
      $scope.clearFormLlamado();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  });
};

$scope.clearFormLlamado = function(){
  $scope.formLlamado.$setUntouched();
  $scope.nuevoLlamadoObj = ObjectsFactory.newLlamado();
}

//-- [Llamado/Listado]
//-- [Llamado/Listado] variables
//-- [Llamado/Listado] Form Management
//-- [Llamado/Listado] filters
//-- [Llamado/Listado] modals
//-- [Llamado/Listado] utils
//-- [Llamado/Listado] service calls
function getLlamados() {
  spinnerService.show('searchLlamadoSpinner');
  academicoService.llamadoGetAll()
  .then(
    function(response){
      $scope.llamados = response.data;
      angular.forEach($scope.llamados, function (item) {
        item.fechaInicio = new Date(item.fechaInicio);
        item.fechaFin = new Date(item.fechaFin);
      });
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  })

};

//-- [Llamado/sub-seccion]
//-- [Llamado/sub-seccion] variables
//-- [Llamado/sub-seccion] Form Management
//-- [Llamado/sub-seccion] filters
//-- [Llamado/sub-seccion] modals
//-- [Llamado/sub-seccion] utils
//-- [Llamado/sub-seccion] service calls
});

