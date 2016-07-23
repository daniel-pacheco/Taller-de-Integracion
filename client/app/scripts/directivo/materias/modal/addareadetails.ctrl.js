angular.module('clientAppApp')
.controller('addAreaDetailsModalController', [
  '$scope', '$element', 'title', 'close', 'ObjectsFactory', 'listaAreas', 'academicoService', 'ModalService',
  function($scope, $element, title, close, ObjectsFactory, listaAreas, academicoService, ModalService) {//acá se inyecta las variables necesarias y luego la función close

    $scope.listaAreas = listaAreas;
    $scope.nuevaArea = '';
    $scope.selectedArea = '';

    $scope.copiaListaAreas = angular.copy ($scope.listaAreas);

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

  $scope.close = function(modif) {
   if (modif)
     close ($scope.copiaListaAreas , 500);
   else
     close (listaAreas , 500);
 };

 $scope.edit = function (area){
  $scope.copiaArea = angular.copy (area);
};

$scope.deleteArea = function (area) {
  $scope.copiaListaAreas.splice($scope.copiaListaAreas.indexOf(area),1);
};

$scope.saveEditarea = function (position) {
  $scope.copiaListaAreas[position].nombre = $scope.copiaArea.nombre;
};

$scope.nuevaArea = ObjectsFactory.newArea();

$scope.addArea = function (){
  $scope.copiaListaAreas.push($scope.nuevaArea);
  $scope.nuevaArea = ObjectsFactory.newArea();
  $scope.form.$setUntouched();
};

function searchArea() {
  // spinnerService.show('searchMateriaSpinner');
  academicoService.areaGetAll()
  .then(
    function(response){
      console.log(response.data);
      $scope.listaAreas = response.data;
    },
    function(response){
      // showServerError('Se ha producido un error al intentar contactar al servidor: ' + response.statusText);
    })
  .finally(function(){
    // spinnerService.hide('searchMateriaSpinner');
  });
};
// $scope.$on('$viewContentLoaded', function(){
  // searchArea();
// });

function addArea(area) {
  academicoService.areaPutNew(area)
  .then(
    function(response){
      console.log(response);
    },
    function(response){
      console.log(response);
    })
  .finally(function(){

  });
};

}]);