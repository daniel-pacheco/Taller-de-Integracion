angular.module('clientAppApp')
.controller('modalConfirmController', [
  '$scope', '$element','mensaje','close',
  function($scope, $element, mensaje, close) {//acá se inyecta las variables necesarias y luego la función close

  $scope.mensaje = mensaje; 
  
  $scope.close = function(confirm) {
    close(confirm, 500); 
  };

  $scope.cancel = function() {
    close('cancel', 500); 
  };

}]);