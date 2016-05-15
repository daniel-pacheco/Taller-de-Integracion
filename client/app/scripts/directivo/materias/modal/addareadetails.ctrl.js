angular.module('clientAppApp')
.controller('addAreaDetailsModalController', [
  '$scope', '$element', 'title', 'close', 'ObjectsFactory', 'listaAreas',
  function($scope, $element, title, close, ObjectsFactory, listaAreas) {//acá se inyecta las variables necesarias y luego la función close

    $scope.listaAreas = listaAreas;
    $scope.nuevaArea = '';
    $scope.selectedArea = '';

    $scope.copiaListaAreas = angular.copy (listaAreas);

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
  
}]);