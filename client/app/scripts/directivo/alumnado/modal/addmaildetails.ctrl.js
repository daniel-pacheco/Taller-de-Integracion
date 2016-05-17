angular.module('clientAppApp')
.controller('mailAvanzadoModalController', [
  '$scope', '$element', 'title', 'listaMails', 'close', 'ObjectsFactory',
  function($scope, $element, title, listaMails, close, ObjectsFactory) {//acá se inyecta las variables necesarias y luego la función close

    $scope.copiaListaMails = angular.copy (listaMails);
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
      close ($scope.copiaListaMails , 500);
    else
      close (listaMails , 500);
  };

  $scope.cancel = function() {
   
    close('cancel', 500); // close, but give 500ms for bootstrap to animate
  };

  $scope.nuevoMail = ObjectsFactory.newMail();
  $scope.addMail = function(){
    $scope.copiaListaMails.push($scope.nuevoMail);
    $scope.nuevoMail = ObjectsFactory.newMail();
    $scope.form.$setUntouched();
  }

  $scope.edit = function(mail){
    $scope.copiaMail = angular.copy (mail);
  }
  $scope.saveEditMail = function (position){
    console.log(position);
    $scope.copiaListaMails[position].direccionMail = $scope.copiaMail.direccionMail;
    $scope.copiaListaMails[position].tipoMail = $scope.copiaMail.tipoMail;
  }
  $scope.deleteMail = function (mail){
    $scope.copiaListaMails.splice($scope.copiaListaMails.indexOf(mail),1);
  }

}]);