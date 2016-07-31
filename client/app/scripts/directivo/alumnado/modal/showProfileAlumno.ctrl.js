angular.module('clientAppApp')
.controller('showProfileAlumnoModalController', [
  '$element', '$scope', 'alumno','close', 'title',
  function($element, $scope, alumno, close, title) {//acá se inyecta las variables necesarias y luego la función close

$scope.tooltip = {
  tooltipDetailsDom : {
    'title' : 'Domicilio detallado'
  }
};

  $scope.title = title;
  $scope.alumno = {}; 
  //$scope.dni = docente.nroDocumento;
  $scope.alumno = alumno;
  
  $scope.close = function() {
    close($scope.alumno, 500); 
  };

  $scope.cancel = function() {
    close('cancel', 500); 
  };

}]);