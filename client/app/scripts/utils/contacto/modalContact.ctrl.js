angular.module('clientAppApp')
.controller('modalContactoController', [
  '$scope', '$element','close',
  function($scope, $element, close) {//acá se inyecta las variables necesarias y luego la función close
  
  $scope.close = function(confirm) {
    close(confirm, 500); 
  };

  $scope.cancel = function() {
    close('cancel', 500); 
  };

}]);