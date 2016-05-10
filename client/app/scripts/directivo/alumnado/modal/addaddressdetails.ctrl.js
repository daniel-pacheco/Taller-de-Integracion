angular.module('clientAppApp')
.controller('addAddressDetailsModalController', [
  '$scope', '$element', 'title', 'close', 'domicilioAvanzado',
  function($scope, $element, title, close, domicilioAvanzado) {//acá se inyecta las variables necesarias y luego la función close

    $scope.title = title;
    $scope.alumno = {}; 
    $scope.domicilioCopia = angular.copy(domicilioAvanzado);

    $scope.close = function(modif) {
      if (modif){
        close ($scope.domicilioCopia , 500);}
        else{ 
          close (domicilioAvanzado , 500);}

        };

        $scope.cancel = function() {

    close('cancel', 500); // close, but give 500ms for bootstrap to animate
  };

}]);