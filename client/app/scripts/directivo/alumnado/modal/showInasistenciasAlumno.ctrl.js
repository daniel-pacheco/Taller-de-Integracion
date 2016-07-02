angular.module('clientAppApp')
.config(function($datepickerProvider) {
  angular.extend($datepickerProvider.defaults, {
    dateFormat: 'dd/MM/yyyy',
    startWeek: 1
  });
})
.controller('showInasistenciasModalController', [
  '$scope', '$element', 'title', 'libInasistencias', 'close', 'ObjectsFactory', 'INASISTENCIAS', 'spinnerService', 'untilDate', 'fromDate',
  function($scope, $element, title, libInasistencias, close, ObjectsFactory, INASISTENCIAS, spinnerService, untilDate, fromDate) {//acá se inyecta las variables necesarias y luego la función close

    $scope.title = title;
    $scope.libInasistencias = {}; 
    $scope.nuevaInasistencia = {};
    var copiaLibInasistencias = angular.copy(libInasistencias);

    copiaLibInasistencias.listaInasistencias = _.sortBy(copiaLibInasistencias.listaInasistencias, function(value) {return value.fecha; });
    
  $scope.copiaLibInasistencias = copiaLibInasistencias;//creo la copia para poder editarla tranquilo
  angular.forEach($scope.copiaLibInasistencias.listaInasistencias, function (item) {
    item.fecha = new Date(item.fecha);
  });
  
  $scope.inasistenciasOptions = INASISTENCIAS;
  $scope.filterByRangeDate = [];


  $scope.fromDate = fromDate;
  $scope.untilDate = untilDate;
  $scope.dateFilter = function (date){
    if (($scope.fromDate === '') && ($scope.untilDate === '')){
      return date;
    }else if ((date.fecha > $scope.fromDate) && (date.fecha < $scope.untilDate)){
      return date;
    };
    $scope.calcTotalInasistencias($scope.filterByRangeDate);
  };

  $scope.limpiarFiltro = function () {
    $scope.fromDate = '';
    $scope.untilDate = '';
    $scope.calcTotalInasistencias($scope.copiaLibInasistencias.listaInasistencias);
  }

  $scope.close = function(modif) {
    var result = {};
    result.fromDate = $scope.fromDate;
    result.untilDate = $scope.untilDate;
    if (modif){
      result.lib = $scope.copiaLibInasistencias;
      result.modif = true;
      spinnerService.show('searchSpinner');
      close (result, 500);}
      else{
        result.lib = $scope.libInasistencias;
        result.modif = false;
        close (result , 500);}
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
      }, tooltipJustif : {
        'title' : 'Justificada'
      }, tooltipInjustif : {
        'title' : 'Injustificada'
      }, tooltipClearFilter : {
        'title' : 'Limpiar el filtro'
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
  $scope.calcTotalInasistencias($scope.copiaLibInasistencias.listaInasistencias);
};

$scope.saveEditInasistencia = function(position) {
  $scope.copiaLibInasistencias.listaInasistencias[position].fecha = $scope.copiaInasistencia.fecha;
  $scope.copiaLibInasistencias.listaInasistencias[position].concepto = $scope.copiaInasistencia.concepto;
  $scope.copiaLibInasistencias.listaInasistencias[position].cantidad = $scope.copiaInasistencia.cantidad;
  $scope.copiaLibInasistencias.listaInasistencias[position].justificada = $scope.copiaInasistencia.justificada;
  $scope.calcTotalInasistencias($scope.copiaLibInasistencias.listaInasistencias);
};

$scope.deleteInasistencia = function(inasistencia) {
  $scope.copiaLibInasistencias.listaInasistencias.splice($scope.copiaLibInasistencias.listaInasistencias.indexOf(inasistencia),1);
  $scope.calcTotalInasistencias($scope.copiaLibInasistencias.listaInasistencias);
};

$scope.edit = function(inasistencia) {
  $scope.copiaInasistencia = angular.copy (inasistencia);
  $scope.copiaInasistencia.fecha = new Date(inasistencia.fecha);
  $scope.calcTotalInasistencias($scope.copiaLibInasistencias.listaInasistencias);
};

$scope.totalIn = [];
$scope.totalInJ = [];
$scope.totalInI = [];


$scope.calcTotalInasistencias = function(arreglo){

  $scope.totalIn.length = 0;
  $scope.totalInJ.length = 0;
  $scope.totalInI.length = 0;
  $scope.totalInJ[0] = 0;
  $scope.totalInI[0] = 0;
  
  arreglo.forEach(function(value, index, array){
    var rdo = 0;

    if (index > 0) {
      rdo = $scope.totalIn[index - 1];
    };

    $scope.totalIn.push((parseFloat(value.cantidad) + parseFloat(rdo)).toFixed(2));

    if(value.justificada){
        $scope.totalInJ.push((parseFloat(value.cantidad) + parseFloat(_.last($scope.totalInJ))).toFixed(2));
      }else {
        $scope.totalInI.push((parseFloat(value.cantidad) + parseFloat(_.last($scope.totalInI))).toFixed(2));
      }
    });
};
$scope.calcTotalInasistencias($scope.copiaLibInasistencias.listaInasistencias);
  // Service usage

}]);