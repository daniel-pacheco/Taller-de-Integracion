angular.module('clientAppApp')
.controller('addAreaDetailsModalController', [
  '$scope', '$element', 'title', 'close', 'ObjectsFactory',
  function($scope, $element, title, close, ObjectsFactory) {//acá se inyecta las variables necesarias y luego la función close

  $scope.listaAreas = ['asdfasdf', 'asdhfdgjhdfj', 'fdgjdfgjdfj', 'dfjdfjfdj'];
  $scope.nuevaArea = '';
  $scope.selectedArea = '';

    // $scope.copiaListaTelefonos = angular.copy (listaTelefonos);

    // $scope.title = title;
    // if (listaTelefonos.length < 1)
    //   listaTelef = false;
    // else listaTelef = true;

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

  $scope.agregarArea = function(area){
    $scope.listaAreas.unshift(area);
    $scope.nuevaArea = '';
  }
 
  //  This close function doesn't need to use jQuery or bootstrap, because
  //  the button has the 'data-dismiss' attribute.
//   $scope.close = function(modif) {
//     if (modif)
//       close ($scope.copiaListaTelefonos , 500);
//     else
//       close (listaTelefonos , 500);
//   };

//   $scope.cancel = function() {
   
//     close('cancel', 500); // close, but give 500ms for bootstrap to animate
//   };

//    $scope.nuevoTelefono = ObjectsFactory.newTelefono();
//   $scope.addTelefono = function(){
//     $scope.copiaListaTelefonos.push({
//       'caracteristica':$scope.nuevoTelefono.caracteristica, 
//       'nroTelefono': $scope.nuevoTelefono.nroTelefono, 
//       'tipoTelefono':$scope.nuevoTelefono.tipoTelefono, });
//     $scope.nuevoTelefono.caracteristica = "";
//     $scope.nuevoTelefono.nroTelefono = "";
//     $scope.nuevoTelefono.tipoTelefono = "";
//     $scope.form.$setUntouched();
//   }
//   $scope.edit = function(telefono){
//       $scope.copiaTelefono = angular.copy (telefono);
//   }
// $scope.saveEditTelefono = function (position){
//   $scope.copiaListaTelefonos[position].caracteristica = $scope.copiaTelefono.caracteristica;
//   $scope.copiaListaTelefonos[position].nroTelefono = $scope.copiaTelefono.nroTelefono;
//   $scope.copiaListaTelefonos[position].tipoTelefono = $scope.copiaTelefono.tipoTelefono;
// }
// $scope.deleteTelefono = function (telefono){
//     $scope.copiaListaTelefonos.splice($scope.copiaListaTelefonos.indexOf(telefono),1);
// }

}]);