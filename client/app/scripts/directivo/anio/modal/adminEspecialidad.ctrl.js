angular.module('clientAppApp')
.controller('adminEspecialidadModalController', [
  '$scope', '$element', 'title', 'close', 'ObjectsFactory', 'listaEspecialidades',
  function($scope, $element, title, close, ObjectsFactory, listaEspecialidades) {

    $scope.listaEspecialidades = listaEspecialidades;
    $scope.nuevaEspecialidad = '';
    $scope.selectedEspecialidad = '';

    $scope.copiaListaEspecialidades = angular.copy (listaEspecialidades);

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
     close ($scope.copiaListaEspecialidades , 500);
   else
     close (listaEspecialidades , 500);
 };

 $scope.edit = function (especialidad){
  $scope.copiaEspecialidad = angular.copy(especialidad);
};

$scope.deleteEspecialidad = function (especialidad) {
  $scope.copiaListaEspecialidades.splice($scope.copiaListaEspecialidades.indexOf(especialidad),1);
};

$scope.saveEditEspecialidad = function (position) {
  $scope.copiaListaEspecialidades[position].nombre = $scope.copiaEspecialidad.nombre;
  $scope.copiaListaEspecialidades[position].nombreCorto = $scope.copiaEspecialidad.nombreCorto;
};

$scope.nuevaEspecialidad = ObjectsFactory.newEspecialidad();

$scope.addEspecialidad = function (){
  $scope.copiaListaEspecialidades.push($scope.nuevaEspecialidad);
  $scope.nuevaEspecialidad = ObjectsFactory.newEspecialidad();
  $scope.form.$setUntouched();
};

}]);