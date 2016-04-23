angular.module('clientAppApp')
.controller('showInasistenciasModalController', [
  '$scope', '$element', 'title', 'libInasistencias', 'close', 
  function($scope, $element, title, libInasistencias, close) {//acá se inyecta las variables necesarias y luego la función close

    $scope.title = title;
    $scope.libInasistencias = {}; 
  //$scope.dni = docente.nroDocumento;
  var copiaLibInasistencias = angular.copy (libInasistencias)
$scope.libInasistencias = libInasistencias;//creo la copia para poder editarla tranquilo
    angular.forEach($scope.libInasistencias.listaInasistencias, function (item) {//funcion que crea el campo fechasort con la forma YYYYmmdd para poder ordenarlo en el boletin de inassitencias
      var year = item.fecha.substr(6,4);
      var month = item.fecha.substr(3,2);
      var day = item.fecha.substr(0,2);
    item.fechaSort = year+month+day;//22/02/1995
    item.show=false;
  });

  //  This close function doesn't need to use jQuery or bootstrap, because
  //  the button has the 'data-dismiss' attribute.
  $scope.close = function(modif) {
    /*close(alumno.nroDocumento, 500);*/ // close, sends the first parameter but give 500ms for bootstrap to animate
    if (modif){
      close ($scope.libInasistencias , 500);}
      else{
        close (copiaLibInasistencias , 500);}
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
  }, tooltipSaveEdit : {
    'title' : 'Guardar edición'
  }, tooltipCancelEdit : {
    'title' : 'Cancelar edición'
  }

};
var today = new Date();
var dd = today.getDate(); 

if (dd < 10)//para poder ordenar con el fechasort necesitamos tener el dia y el mes de dos caracteres siempre
  {dd = "0"+dd}

var mm = today.getMonth()+1; 
if (mm < 10)
  {mm = "0"+mm}

var yyyy = today.getFullYear(); 

$scope.currentDate = dd+"/"+mm+"/"+yyyy;
$scope.addInasistencia = function(){//Esto agrega una fila dinamicamente en el boletin de inasistencias
  libInasistencias.listaInasistencias.push({
    'fecha':$scope.libInasistencias.listaInasistencias.fecha, 
    'faltoA': $scope.libInasistencias.listaInasistencias.faltoA, 
    'cantidad':$scope.libInasistencias.listaInasistencias.cantidad, 
    'justificada':$scope.libInasistencias.listaInasistencias.justificada.toUpperCase() });
  $scope.libInasistencias.listaInasistencias.faltoA="";
  $scope.libInasistencias.listaInasistencias.cantidad="";
  $scope.libInasistencias.listaInasistencias.justificada="";
};

$scope.saveEditInasistencia = function(position) {
  $scope.libInasistencias.listaInasistencias[position].fecha = $scope.copiaInasistencia.fecha;
  $scope.libInasistencias.listaInasistencias[position].faltoA = $scope.copiaInasistencia.faltoA;
  $scope.libInasistencias.listaInasistencias[position].cantidad = $scope.copiaInasistencia.cantidad;
  $scope.libInasistencias.listaInasistencias[position].justificada = $scope.copiaInasistencia.justificada;
};

$scope.deleteInasistencia = function(inasistencia) {
  $scope.libInasistencias.listaInasistencias.splice($scope.libInasistencias.listaInasistencias.indexOf(inasistencia),1);
};

$scope.edit = function(inasistencia) {
  $scope.copiaInasistencia = angular.copy (inasistencia);
}


  // Service usage

}]);