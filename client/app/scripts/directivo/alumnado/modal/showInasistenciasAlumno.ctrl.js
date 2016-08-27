angular.module('clientAppApp')
.config(function($datepickerProvider) {
  angular.extend($datepickerProvider.defaults, {
    dateFormat: 'dd/MM/yyyy',
    startWeek: 1
  });
})
.controller('showInasistenciasModalController', [
  '$element', '$scope', 'close', 'datePrimerTrimestre', 'dateSegundoTrimestre', 'dateTercerTrimestre', 'libInasistencias', 'ObjectsFactory', 'spinnerService', 'title', 'INASISTENCIAS',
  function($element, $scope, close, datePrimerTrimestre, dateSegundoTrimestre, dateTercerTrimestre, libInasistencias, ObjectsFactory, spinnerService, title, INASISTENCIAS) {//acá se inyecta las variables necesarias y luego la función close


//-- [libInasistencias]
$scope.cancel = function() {
  close('cancel', 500);
};

$scope.close = function(modif) {
  var result = {};
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

//-- [libInasistencias] variables
$scope.totalIn = [];
$scope.totalInJ = [];
$scope.totalInI = [];
$scope.datePrimerTrimestre = datePrimerTrimestre;
$scope.dateSegundoTrimestre = dateSegundoTrimestre;
$scope.dateTercerTrimestre = dateTercerTrimestre;

$scope.button = {radio: 'ninguno'};
$scope.filtroPerzonalizado = true;
$scope.fromDate = '';
$scope.untilDate = '';
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
//-- [libInasistencias] Form Management

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
}, tooltipFilterPersonalizado: {
  'title' : 'Filtro personalizado'
}, tooltipFilterPorTrim: {
  'title' : 'Filtro por trimestre'
}
};

$scope.selectTrimestre = function (trimestre){
  $scope.calcTotalInasistencias($scope.filterByRangeDate);
  switch(trimestre){
    case 'primerTrim':
    $scope.fromDate = new Date(datePrimerTrimestre[0].valor);
    $scope.untilDate = new Date(datePrimerTrimestre[1].valor);
    break;
    case 'segundoTrim':
    $scope.fromDate = new Date(dateSegundoTrimestre[0].valor);
    $scope.untilDate = new Date(dateSegundoTrimestre[1].valor);
    break;
    case 'tercerTrim':
    $scope.fromDate = new Date(dateTercerTrimestre[0].valor);
    $scope.untilDate = new Date(dateTercerTrimestre[1].valor);
    break;
    case 'ninguno':
    $scope.fromDate = '';
    $scope.untilDate = '';
    break;
  }
}
//-- [libInasistencias] filters
//-- [libInasistencias] modals
//-- [libInasistencias] utils
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
  $scope.filtroPerzonalizado = !$scope.filtroPerzonalizado;
}
//-- [libInasistencias] service calls

//-- [libInasistencias/Listado]
//-- [libInasistencias/Listado] variables
//-- [libInasistencias/Listado] Form Management
//-- [libInasistencias/Listado] filters
//-- [libInasistencias/Listado] modals
//-- [libInasistencias/Listado] utils
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
//-- [libInasistencias/Listado] service calls


//-- [libInasistencias/Nueva]
//-- [libInasistencias/Nueva] variables
$scope.date = new Date();

$scope.nuevaInasistencia = new ObjectsFactory.newInasistencia();
$scope.nuevaInasistencia.fecha = $scope.date;
//-- [libInasistencias/Nueva] Form Management
//-- [libInasistencias/Nueva] filters
//-- [libInasistencias/Nueva] modals
//-- [libInasistencias/Nueva] utils

$scope.addInasistencia = function(){
  copiaLibInasistencias.listaInasistencias.push($scope.nuevaInasistencia);
  $scope.nuevaInasistencia = new ObjectsFactory.newInasistencia();
  $scope.form.$setUntouched();
  $scope.nuevaInasistencia.fecha = $scope.date;
  $scope.calcTotalInasistencias($scope.copiaLibInasistencias.listaInasistencias);
};
//-- [libInasistencias/Nueva] service calls
}]);