angular.module('clientAppApp')
.controller('addAreaDetailsModalController', [
  '$scope', '$element', 'title', 'close', 'ObjectsFactory', 'listaAreas', 'academicoService', 'ModalService', 'spinnerService',
  function($scope, $element, title, close, ObjectsFactory, listaAreas, academicoService, ModalService, spinnerService) {//acá se inyecta las variables necesarias y luego la función close


    $scope.close = function(modif) {
     if (modif)
       close ($scope.copiaListaAreas , 500);
     else
       close (listaAreas , 500);
   };

//-- [AreaDetails]
//-- [AreaDetails] variables

// $scope.listaAreas = listaAreas;
$scope.nuevaArea = '';
$scope.selectedArea = '';

// $scope.copiaListaAreas = angular.copy ($scope.listaAreas);

//-- [AreaDetails] Form Management

$scope.title = title;

$scope.tooltip = {
  tooltipEdit : {
    'title' : 'Editar'
  }, tooltipDelete : {
    'title' : 'Eliminar'
  }, tooltipSaveEdit : {
    'title' : 'Guardar edición'
  }, tooltipCancelEdit : {
    'title' : 'Cancelar edición'
  }
};

//-- [AreaDetails] filters
//-- [AreaDetails] modals
//-- [AreaDetails] utils (spinners, mensajes impresion etc)

$scope.confirmModal = function(mesagge, funcion, parametro) { //este confirm recibe una funcion y un parametro para que despues de confirmar se pueda llamar a la funcion que se necesite
  ModalService.showModal({
    templateUrl: 'scripts/utils/confirm/modalConfirm.tpl.html',
    controller: 'modalConfirmController',
    inputs: {
      mensaje: mesagge,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      funcion(parametro);
    });
  });
};
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

$scope.confirmModal = function(mesagge, funcion, parametro) { //este confirm recibe una funcion y un parametro para que despues de confirmar se pueda llamar a la funcion que se necesite
  ModalService.showModal({
    templateUrl: 'scripts/utils/confirm/modalConfirm.tpl.html',
    controller: 'modalConfirmController',
    inputs: {
      mensaje: mesagge,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      funcion(parametro);
    });
  });
};

//-- [AreaDetails] service calls

function searchArea() {
  spinnerService.show('searchAreasSpinner');
  academicoService.areaGetAll()
  .then(
    function(response){
      $scope.copiaListaAreas = response.data;
    },
    function(response){
      showServerError('Se ha producido un error al intentar contactar al servidor: ' + response.statusText);
    })
  .finally(function(){
    spinnerService.hide('searchAreasSpinner');
  });
};

$scope.edit = function (area){
  $scope.copiaArea = angular.copy (area);
};

$scope.deleteArea = function (area) {
  // $scope.copiaListaAreas.splice($scope.copiaListaAreas.indexOf(area),1);
  $scope.confirmModal("¿Desea eliminar el área " + area.nombre + "? (Esta operación es irreversible!)", deleteArea, area.idArea);
};

$scope.saveEditarea = function (position) {
  addArea($scope.copiaArea);
};

$scope.nuevaArea = ObjectsFactory.newArea();

$scope.addArea = function (){
  // $scope.copiaListaAreas.push($scope.nuevaArea);
  addArea($scope.nuevaArea);  
};

function addArea(area) {
  spinnerService.show('searchAreasSpinner');
  academicoService.areaPutNew(area)
  .then(
    function(response){
      showServerSuccess('El area ha sido cargada con éxito: ',response);
      searchArea();
      $scope.nuevaArea = ObjectsFactory.newArea();
      $scope.form.$setUntouched();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchAreasSpinner');
  });
};

function deleteArea(idArea) {
  spinnerService.show('searchAreasSpinner');
  academicoService.areaDelete(idArea)
  .then(function(response){
    showServerSuccess('El área ha sido eliminada con éxito: ', response);
    searchArea();
  },
  function(response){
    showServerError(response);
  })
  .finally(function(){
    spinnerService.hide('searchAreasSpinner');
  });
};

// $scope.$on('$viewContentLoaded', function(){
  searchArea();
// });

//-------------------------------------------------------

}]);