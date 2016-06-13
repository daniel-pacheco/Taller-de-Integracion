angular.module('clientAppApp')
.controller('showInasistenciasModalController', [
  '$scope', '$element', 'title', 'libInasistencias', 'close', 'ObjectsFactory', 
  function($scope, $element, title, libInasistencias, close, ObjectsFactory) {//acá se inyecta las variables necesarias y luego la función close

  $scope.title = title;
  $scope.libInasistencias = {}; 
  $scope.nuevaInasistencia = {};
  var copiaLibInasistencias = angular.copy (libInasistencias);
  $scope.copiaLibInasistencias = copiaLibInasistencias;//creo la copia para poder editarla tranquilo
    angular.forEach($scope.copiaLibInasistencias.listaInasistencias, function (item) {
      item.fecha = new Date(item.fecha);
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

/*var today = new Date();
var dd = today.getDate(); 

if (dd < 10)//para poder ordenar con el fechasort necesitamos tener el dia y el mes de dos caracteres siempre
  {dd = "0"+dd}

var mm = today.getMonth()+1; 
if (mm < 10)
  {mm = "0"+mm}

var yyyy = today.getFullYear(); 

currentDay = dd+"/"+mm+"/"+yyyy;
*/
$scope.date = new Date();

$scope.nuevaInasistencia = new ObjectsFactory.newInasistencia();
$scope.nuevaInasistencia.fecha = $scope.date;

$scope.addInasistencia = function(){
  copiaLibInasistencias.listaInasistencias.push($scope.nuevaInasistencia);
  $scope.nuevaInasistencia = new ObjectsFactory.newInasistencia();
  $scope.form.$setUntouched();
  $scope.nuevaInasistencia.fecha = $scope.date;
  $scope.calcTotalInasistencias();
};

$scope.saveEditInasistencia = function(position) {
  $scope.copiaLibInasistencias.listaInasistencias[position].fecha = $scope.copiaInasistencia.fecha;
  $scope.copiaLibInasistencias.listaInasistencias[position].concepto = $scope.copiaInasistencia.concepto;
  $scope.copiaLibInasistencias.listaInasistencias[position].cantidad = $scope.copiaInasistencia.cantidad;
  $scope.copiaLibInasistencias.listaInasistencias[position].justificada = $scope.copiaInasistencia.justificada;
  $scope.calcTotalInasistencias();
};

$scope.deleteInasistencia = function(inasistencia) {
  $scope.copiaLibInasistencias.listaInasistencias.splice($scope.copiaLibInasistencias.listaInasistencias.indexOf(inasistencia),1);
  $scope.calcTotalInasistencias();
};

$scope.edit = function(inasistencia) {
  $scope.copiaInasistencia = angular.copy (inasistencia);
  $scope.copiaInasistencia.fecha = new Date(inasistencia.fecha);
  $scope.calcTotalInasistencias();
};

$scope.totalIn = [];

$scope.calcTotalInasistencias = function(){
  
  $scope.totalIn.length = 0;

  $scope.copiaLibInasistencias.listaInasistencias.forEach(function(value, index, array){
    var rdo = 0;

    if (index > 0) {
      rdo = $scope.totalIn[index - 1];
    };
    

    $scope.totalIn.push(value.cantidad + rdo);
  });
};
$scope.calcTotalInasistencias();
  // Service usage

}]);