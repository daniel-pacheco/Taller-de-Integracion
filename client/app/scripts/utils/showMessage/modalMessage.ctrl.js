angular.module('clientAppApp')
.controller('modalMessageController', [
  '$scope', '$element','mensaje', 'title', 'isGood', 'close',
  function($scope, $element, mensaje, title, isGood, close) {//acá se inyecta las variables necesarias y luego la función close

  $scope.mensaje = mensaje; 
  $scope.titulo = title;
  $scope.todoOk = isGood;

  
  $scope.close = function(confirm) {
    close(); 
  };

  $scope.cancel = function() {
    close('cancel', 500); 
  };

}]);