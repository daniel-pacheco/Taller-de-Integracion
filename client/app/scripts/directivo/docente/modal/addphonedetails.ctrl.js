angular.module('clientAppApp')
.controller('telefonoAvanzadoModalController', [
  '$scope', '$element', 'title', 'listaTelefonos', 'close', 'ObjectsFactory',
  function($scope, $element, title, listaTelefonos, close, ObjectsFactory) {//acá se inyecta las variables necesarias y luego la función close

    $scope.copiaListaTelefonos = angular.copy (listaTelefonos);
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
      close ($scope.copiaListaTelefonos , 500);
    else
      close (listaTelefonos , 500);
  };

  $scope.cancel = function() {
    close('cancel', 500); // close, but give 500ms for bootstrap to animate
  };

   $scope.nuevoTelefono = ObjectsFactory.newTelefono();
  $scope.addTelefono = function(){
    $scope.copiaListaTelefonos.push($scope.nuevoTelefono);
    $scope.nuevoTelefono = ObjectsFactory.newTelefono();
    $scope.form.$setUntouched();
  }
  $scope.edit = function(telefono){
      $scope.copiaTelefono = angular.copy (telefono);
  }
$scope.saveEditTelefono = function (position){
  $scope.copiaListaTelefonos[position].caracteristica = $scope.copiaTelefono.caracteristica;
  $scope.copiaListaTelefonos[position].nroTelefono = $scope.copiaTelefono.nroTelefono;
  $scope.copiaListaTelefonos[position].tipoTelefono = $scope.copiaTelefono.tipoTelefono;
}
$scope.deleteTelefono = function (telefono){
    $scope.copiaListaTelefonos.splice($scope.copiaListaTelefonos.indexOf(telefono),1);
}

}]);