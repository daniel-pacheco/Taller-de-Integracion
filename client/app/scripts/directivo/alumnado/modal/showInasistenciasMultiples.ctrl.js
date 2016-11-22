angular.module('clientAppApp')
.config(function($datepickerProvider) {
  angular.extend($datepickerProvider.defaults, {
    dateFormat: 'dd/MM/yyyy',
    startWeek: 1
  });
})
.controller('showInasistenciasMultiplesModalController', [
  '$element', '$scope', 'alumnoData', 'close', 'count', 'INASISTENCIAS', 'ObjectsFactory', 'spinnerService', 'title',
  function($element, $scope, alumnoData, close, count, INASISTENCIAS, ObjectsFactory, spinnerService, title) {//acá se inyecta las variables necesarias y luego la función close


//-- [libInasistencias]
$scope.cancel = function() {
  close('cancel', 500);
};

$scope.close = function(guardar) {
  var result = {};
  result.seleccionados = $scope.listAlumnosInasistencia.length;
  result.listAluIna = $scope.listAlumnosInasistencia;
  result.count = count;
  if (guardar){
    result.guardo = true;
    spinnerService.show('searchSpinner');
    result.nuevaInasistencia = $scope.nuevaInasistencia;
    close (result, 500);
  }
  else{
    close (result , 500);
  }
  $scope.listAlumnosInasistencia = [];
  $scope.alumnoData = [];
};

//-- [libInasistencias] variables
$scope.alumnoData = alumnoData;
$scope. listAlumnosInasistencia = angular.copy(alumnoData);
$scope.title = title;

$scope.nuevaInasistencia = {};
$scope.date = new Date();
$scope.nuevaInasistencia.fecha = $scope.date;
$scope.inasistenciasOptions = INASISTENCIAS;
//-- [libInasistencias] Form Management
var count = count;

$scope.seleccionarCheckbox = function(alumno) {//al presionar un td de la lista de alumnos pone checkbox en true y muestra boton colocar inasistencias

  if (alumno.selected) {
    count--;
    alumno.selected = false;
    $scope.listAlumnosInasistencia.splice($scope.listAlumnosInasistencia.indexOf(alumno),1);
    //console.log($scope.listAlumnosInasistencia);
  }
  else  {
    count++;
    alumno.selected = true;
    $scope.listAlumnosInasistencia.push(alumno);
    //console.log($scope.listAlumnosInasistencia);

  }
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
}, tooltipFilterPersonalizado: {
  'title' : 'Filtro personalizado'
}, tooltipFilterPorTrim: {
  'title' : 'Filtro por trimestre'
}
};


}]);