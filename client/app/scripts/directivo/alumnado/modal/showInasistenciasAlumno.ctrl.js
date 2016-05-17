angular.module('clientAppApp')
.controller('showInasistenciasModalController', [
  '$scope', '$element', 'title', 'libInasistencias', 'close', 'ObjectsFactory', 
  function($scope, $element, title, libInasistencias, close, ObjectsFactory) {//acá se inyecta las variables necesarias y luego la función close

  $scope.title = title;
  $scope.libInasistencias = {}; 
  $scope.nuevaInasistencia = {};
  var copiaLibInasistencias = angular.copy (libInasistencias);
$scope.copiaLibInasistencias = copiaLibInasistencias;//creo la copia para poder editarla tranquilo
    angular.forEach($scope.copiaLibInasistencias.listaInasistencias, function (item) {//funcion que crea el campo fechasort con la forma YYYYmmdd para poder ordenarlo en el boletin de inassitencias
      var year = item.fecha.substr(6,4);
      var month = item.fecha.substr(3,2);
      var day = item.fecha.substr(0,2);
    item.fechaSort = year+month+day;//22/02/1995
    item.show=false;
  });

$scope.close = function(modif) {
    if (modif){
      close ($scope.copiaLibInasistencias , 500);}
      else{
        close (libInasistencias , 500);}
      };

  $scope.cancel = function() {
    close('cancel', 500);
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

currentDay = dd+"/"+mm+"/"+yyyy;

$scope.nuevaInasistencia.fecha = currentDay;

$scope.nuevaInasistencia = new ObjectsFactory.newInasistencia();

$scope.addInasistencia = function(){
  copiaLibInasistencias.listaInasistencias.push($scope.nuevaInasistencia);
  $scope.nuevaInasistencia = new ObjectsFactory.newInasistencia();
  $scope.form.$setUntouched();
  $scope.nuevaInasistencia.fecha = currentDay;
};

$scope.saveEditInasistencia = function(position) {
  $scope.copiaLibInasistencias.listaInasistencias[position].fecha = $scope.copiaInasistencia.fecha;
  $scope.copiaLibInasistencias.listaInasistencias[position].concepto = $scope.copiaInasistencia.concepto;
  $scope.copiaLibInasistencias.listaInasistencias[position].cantidad = $scope.copiaInasistencia.cantidad;
  $scope.copiaLibInasistencias.listaInasistencias[position].justificada = $scope.copiaInasistencia.justificada;
};

$scope.deleteInasistencia = function(inasistencia) {
  $scope.copiaLibInasistencias.listaInasistencias.splice($scope.copiaLibInasistencias.listaInasistencias.indexOf(inasistencia),1);
};

$scope.edit = function(inasistencia) {
  $scope.copiaInasistencia = angular.copy (inasistencia);
}


  // Service usage

}]);