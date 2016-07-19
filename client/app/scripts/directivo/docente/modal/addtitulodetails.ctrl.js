angular.module('clientAppApp')
.controller('tituloAvanzadoModalController', [
  '$scope', '$element', 'title', 'listaTitulos', 'close', 'ObjectsFactory',
  function($scope, $element, title, listaTitulos, close, ObjectsFactory) {//acá se inyecta las variables necesarias y luego la función close

    $scope.copiaListaTitulos = angular.copy (listaTitulos);
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
      close ($scope.copiaListaTitulos , 500);
    else
      close (listaTitulos , 500);
  };

  $scope.cancel = function() {
    close('cancel', 500); // close, but give 500ms for bootstrap to animate
  };

   $scope.nuevoTitulo = ObjectsFactory.newTitulo();
  $scope.addTitulo = function(){
    $scope.copiaListaTitulos.push($scope.nuevoTitulo);
    $scope.nuevoTitulo = ObjectsFactory.newTitulo();
    $scope.form.$setUntouched();
  }
  $scope.edit = function(titulo){
      $scope.copiaTitulo = angular.copy (titulo);
  }
$scope.saveEditTitulo = function (position){
  $scope.copiaListaTitulos[position].nombre = $scope.copiaTitulo.caracteristica;
  $scope.copiaListaTitulos[position].descripcionTitulo = $scope.copiaTitulo.descripcionTitulo + ' ' + $scope.copiaTitulo.anio;
  $scope.copiaListaTitulos[position].anio = $scope.copiaTitulo.anio;


}
$scope.deleteTitulo = function (telefono){
    $scope.copiaListaTitulos.splice($scope.copiaListaTitulos.indexOf(telefono),1);
}

}]);