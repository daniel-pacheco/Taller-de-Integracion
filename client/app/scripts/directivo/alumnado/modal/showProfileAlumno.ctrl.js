angular.module('clientAppApp')
.controller('showProfileAlumnoModalController', [
  '$scope', '$element', 'title', 'alumno','close',
  function($scope, $element, title, alumno, close) {//acá se inyecta las variables necesarias y luego la función close

    $scope.tooltip = {
      tooltipDetailsDom : {
        'title' : 'Domicilio detallado'
      }
    };

    $scope.title = title;
    $scope.alumno = {}; 
  //$scope.dni = docente.nroDocumento;
  $scope.alumno = alumno;
  $scope.alumno.sexo = ($scope.alumno.sexo === 'M') ? 'Masculino' : 'Femenino';
  
  $scope.close = function() {
    close($scope.alumno, 500); 
  };

  $scope.cancel = function() {
    close('cancel', 500); 
  };

}]);