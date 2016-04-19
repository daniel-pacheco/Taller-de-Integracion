angular.module('clientAppApp')
.controller('showProfileAlumnoModalController', [
  '$scope', '$element', 'title', 'alumno','close',
  function($scope, $element, title, alumno, close) {//acá se inyecta las variables necesarias y luego la función close

  $scope.title = title;
  $scope.alumno = {}; 
  //$scope.dni = docente.nroDocumento;
  $scope.alumno = alumno;
  
  //  This close function doesn't need to use jQuery or bootstrap, because
  //  the button has the 'data-dismiss' attribute.
  $scope.close = function() {
    close(alumno.nroDocumento, 500); // close, sends the first parameter but give 500ms for bootstrap to animate
  };

  //  This cancel function must use the bootstrap, 'modal' function because
  //  the doesn't have the 'data-dismiss' attribute.
  $scope.cancel = function() {

    //  Manually hide the modal. if no data-dismiss="modal"
    //$element.modal('hide');
    
    //  Now call close, returning control to the caller.
    close('cancel', 500); // close, but give 500ms for bootstrap to animate
  };

}]);