angular.module('clientAppApp')
.controller('addCursoModalController', [
  '$scope', '$element', 'title', 'close', 'ObjectsFactory', 'listaCursos',
  function($scope, $element, title, close, ObjectsFactory, listaCursos) {//acá se inyecta las variables necesarias y luego la función close

    $scope.listaCursos = listaCursos;
    $scope.nuevoCurso = '';
    $scope.selectedCurso = '';

    $scope.copiaListaCursos = angular.copy (listaCursos);

    $scope.title = title;

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

  $scope.close = function(modif) {
   if (modif)
     close ($scope.copiaListaCursos , 500);
   else
     close (listaCursos , 500);
 };

$scope.edit = function (curso){
  $scope.copiaCurso = angular.copy (curso);
};

$scope.deleteCurso = function (curso) {
  $scope.copiaListaCursos.splice($scope.copiaListaCursos.indexOf(curso),1);
};

$scope.saveEditCurso = function (position) {
  $scope.copiaListaCursos[position].curso = $scope.copiaCurso.curso;
  $scope.copiaListaCursos[position].turno = $scope.copiaCurso.turno;
};

$scope.nuevoCurso = ObjectsFactory.newCurso();

$scope.addCurso = function (){
  $scope.copiaListaCursos.push($scope.nuevoCurso);
  $scope.nuevoCurso = ObjectsFactory.newCurso();
  $scope.form.$setUntouched();
};
  
}]);