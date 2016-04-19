angular.module('clientAppApp')
.controller('showInasistenciasModalController', [
  '$scope', '$element', 'title', 'libInasistencias','close',
  function($scope, $element, title, libInasistencias, close) {//acá se inyecta las variables necesarias y luego la función close

    $scope.title = title;
    $scope.libInasistencias = {}; 
  //$scope.dni = docente.nroDocumento;
  $scope.libInasistencias = libInasistencias;
  
  //  This close function doesn't need to use jQuery or bootstrap, because
  //  the button has the 'data-dismiss' attribute.
  $scope.close = function() {
    /*close(alumno.nroDocumento, 500);*/ // close, sends the first parameter but give 500ms for bootstrap to animate
  };

  //  This cancel function must use the bootstrap, 'modal' function because
  //  the doesn't have the 'data-dismiss' attribute.
  $scope.cancel = function() {

    //  Manually hide the modal. if no data-dismiss="modal"
    //$element.modal('hide');
    
    //  Now call close, returning control to the caller.
    close('cancel', 500); // close, but give 500ms for bootstrap to animate
  };
  $scope.tooltip = {
   tooltipEdit : {
    'title' : 'Editar'
  }, tooltipDelete : {
    'title' : 'Eliminar'
  }
};
$scope.libInasistencias.listaInasistencias.fecha = new Date();
$scope.addInasistencia = function(){//Esto agrega una fila dinamicamente en el 
  libInasistencias.listaInasistencias.push({
    'fecha':$scope.libInasistencias.listaInasistencias.fecha, 
    'faltoA': $scope.libInasistencias.listaInasistencias.faltoA, 
    'cantidad':$scope.libInasistencias.listaInasistencias.cantidad, 
    'justificada':$scope.libInasistencias.listaInasistencias.justificada.toUpperCase() });
  $scope.libInasistencias.listaInasistencias.fecha="";
  $scope.libInasistencias.listaInasistencias.faltoA="";
  $scope.libInasistencias.listaInasistencias.cantidad="";
  $scope.libInasistencias.listaInasistencias.justificada="";

};  
}]);