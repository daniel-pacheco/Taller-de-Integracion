'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AnioCtrl
 * @description
 * # AnioCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES, $selectProvider) {
  $stateProvider
  .state('directivo.anio', {
    url: '/anio',
    templateUrl: 'scripts/directivo/anio/anio.html',
    controller: 'AnioCtrl',
    data: {
     pageTitle: 'Anio',
     authorizedRoles: [USER_ROLES.admin]           
   }
 });

})

 .controller('AnioCtrl', function ($scope, cursosData, ModalService, ObjectsFactory, aniosData, academicoService, spinnerService, alumnoService, $timeout) {

//-- [Anio] 
//-- [Anio] variables

$scope.anios = {};//aniosData;

//-- [Anio] Form Management

$scope.tooltip = {
  tooltipEdit : {
    'title' : 'Editar'
  }, tooltipDelete : {
    'title' : 'Eliminar'
  }, tooltipSaveEdit : {
    'title' : 'Guardar edición'
  }, tooltipCancelEdit : {
    'title' : 'Cancelar edición'
  },
  tooltipDesasignar: {
    'title': 'Remover alumno de este curso'
  },
  tooltipAsignar: {
    'title': 'Agregar alumno a este curso'
  }
};

var setActiveAnio = function(menuItemAnio) {
  $scope.activeMenuIzqAnio = menuItemAnio;
};

$scope.seleccionar = function(id) {//Hacer una funcion que ponga en true el nombre de variable que le llegue y el resto en false (toogle de tres variables)
  $scope.listadoAnio = false;
  $scope.nuevoAnio = false;
  $scope.nuevoCurso = false;
  $scope.administrarAnio = false;  
  $scope.editanio = false;
  $scope.showEditAnioMenuIzq = false;

  switch (id) {
    case 'listadoAnio':
    $scope.listadoAnio = true;
    $scope.subtitle = "Listado";
    setActiveAnio(1);
    // getAnios();
    break;
    case 'nuevoAnio':
    $scope.nuevoAnio = true;
    $scope.subtitle = "Nuevo Año";
    setActiveAnio(2);
    $scope.clearFormAnio();
    break;
    case 'nuevoCurso':
    $scope.nuevoCurso = true;
    $scope.subtitle = "Nuevo Curso";
    setActiveAnio(3);
    clearFormCurso();
    break;
    case 'administrarAnio':
    $scope.administrarAnio = true;
    $scope.subtitle = "Administrar";
    setActiveAnio(4);
    break;
    case 'editAnio':
    $scope.nuevoAnio = true;    
    $scope.subtitle = "Editar año";
    $scope.showEditAnioMenuIzq = true;
    setActiveAnio(5);    
  }
};
$scope.seleccionar('listadoAnio');

$scope.editAnio = function(anio){
 $scope.nuevoAnioObj = angular.copy(anio);
 $scope.seleccionar('editAnio');
};

$scope.getAnios = function(){
  getAnios();
};

//-- [Anio] filters

//-- Order List
$scope.predicate = 'anio';
$scope.reverse = true;
$scope.orderAnio = function(predicate) {
  $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
  $scope.predicate = predicate;
};

//-- [Anio] modals
//-- [Anio] utils

$scope.showMessage = function(mesagge, title, isGood) { //todo ok recibe true si salio bien o false si salio mal
  ModalService.showModal({
    templateUrl: 'scripts/utils/showMessage/modalMessage.tpl.html',
    controller: 'modalMessageController',
    inputs: {
      mensaje: mesagge,
      title: title,
      isGood: isGood
    }
  }).then(function(modal) {
    modal.element.modal();
  });
};

function showServerError (response){
  console.log(response);
  var msg = '';

  if (response.statusText) {
    msg = response.statusText;
  };

  if (response.data) {
    msg += ' - ' + response.data.mensaje + ' ' + response.data.severidad;
  };      
  $scope.showMessage(msg, 'Error al contactar al servidor' , false);
};

function showServerSuccess (message, response){
  console.log(response);
  var msg = message;

  if (response.data) {
    msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};

//-- [Anio] service calls

$scope.$on('$viewContentLoaded', function(){
  getAnios();//Here your view content is fully loaded !!
});

function getAnios() {
  spinnerService.show('searchAniosSpinner');
  academicoService.anioGetAllMin()
  .then(
    function(response){
      $scope.anios = response.data;
    },
    function(response){
      showServerError (response);
    })
  .finally(function(){
    spinnerService.hide('searchAniosSpinner')
  });
};

$scope.deleteAnio = function(anio){

  if (anio.listaCursos.length !== 0) {
    $scope.showMessage('El año no debe contener cursos para poder ser eliminado.', 'ERROR!', false);
  } else{
    spinnerService.show('searchAniosSpinner');
    academicoService.anioDelete(anio.idAnio)
    .then(
      function(response){
        showServerSuccess('El año ha sido eliminado con éxito', response);
        $scope.clearFormAnio();
      },
      function(response){
        showServerError(response);
      })
    .finally(function(){
      spinnerService.hide('searchAniosSpinner')
    });
  };
};

//-------------------------------------------

//-- [Anio/NuevoAnio]
//-- [Anio/NuevoAnio] variables
//-- [Anio/NuevoAnio] Form Management

$scope.clearFormAnio = function() {

  $scope.formAnio.$setUntouched();
  $scope.nuevoAnioObj = ObjectsFactory.newAnio();
  // $scope.nuevoAnio.listaCursos = [];
  $scope.anios = {};
  getAnios();
};

//-- [Anio/NuevoAnio] filters
//-- [Anio/NuevoAnio] modals
//-- [Anio/NuevoAnio] utils

function initAnio(anioMin) {
  var anio = ObjectsFactory.newAnio();
  anio.nombre = anioMin.nombre;
  anio.descripcion = anioMin.descripcion;
  anio.idAnio = anioMin.idAnio;
  return anio;
};

//-- [Anio/NuevoAnio] service calls

$scope.newAnio = function(anioMin) {
  if (!anioMin.cicloAcademico) {
    var anio = initAnio(anioMin); //eliminar cuando se mande el cilo acadéico en anio light
  };

  spinnerService.show('searchAniosSpinner');
  academicoService.anioPutNew(anio)
  .then(
    function(response){
      showServerSuccess('El año se ha dado de alta con éxito n° de Id: ', response);
      $scope.clearFormAnio();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchAniosSpinner');
  });
};

//-----------------------------------

//-- [Anio/NuevoCurso]
//-- [Anio/NuevoCurso] variables

$scope.addCursoObj = ObjectsFactory.newCurso();

//-- [Anio/NuevoCurso] Form Management

$scope.editCurso = function (curso){
  $scope.copiaCurso = angular.copy (curso);
};

$scope.deleteCurso = function (curso) {
  if (curso.cantAlu !== 0) {
    $scope.showMessage('El curso no debe contener alumnos para poder ser eliminado.', 'ERROR!', false);
  } else{
    spinnerService.show('searchAniosSpinner');
    academicoService.cursoDelete(curso.idCurso)
    .then(
      function(response){
        showServerSuccess('El curso se ha eliminado con éxito', response);
        clearFormCurso();
      },
      function(reponse){
        showServerError(response);
      })
    .finally(function(){
      spinnerService.hide('searchAniosSpinner');
    });
  };
};

$scope.saveEditCurso = function (copiaCurso, idAnio) {
  $scope.addCurso(copiaCurso, idAnio);
};

function clearFormCurso(){
  getAnios();
  $scope.addCursoObj = ObjectsFactory.newCurso();
  $scope.form.$setUntouched(); //hay que aclarar que form limpiamos?
};

//-- [Anio/NuevoCurso] filters
//-- [Anio/NuevoCurso] modals
//-- [Anio/NuevoCurso] utils
//-- [Anio/NuevoCurso] service calls

$scope.addCurso = function (addCursoObj, idAnio){
  spinnerService.show('searchAniosSpinner');
  academicoService.cursoPutNew(addCursoObj, idAnio)
  .then(
    function(response){
      showServerSuccess('El curso se ha dado de alta con éxito', response.data);
      clearFormCurso();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchAniosSpinner');
  });
};

//------------------------------------

//-- [Anio/Administrar]
//-- [Anio/Administrar] variables

$scope.cursoCero = {
  anio: '-',
  curso: 0
};


//-- [Anio/Administrar] Form Management

$scope.listAnioIsEnabled = false;

$scope.resetAdministrarTable = function(){
  $scope.listAnioIsEnabled = false;
  $scope.alumnos = [];  
};

//-- [Anio/Administrar] filters

$scope.anioCursoFilter = function (alumno) {//la clave de este comparador es q transofrma todo a string y va comparando las posiciones, no tiene en cuenta los espacios

  if (alumno) {
    return (
      _.includes(alumno.anio, $scope.dropDownSelectedAnio.nombre) &&
      _.includes(alumno.curso, $scope.dropDownSelectedCurso.division)
      );
  };
};

$scope.cursoCeroFilter = function (alumno) {//la clave de este comparador es q transofrma todo a string y va comparando las posiciones, no tiene en cuenta los espacios

  if (alumno) {
    return (
      _.includes(alumno.anio, $scope.cursoCero.anio) &&
      _.includes(alumno.curso, $scope.cursoCero.curso)
      );
  };
};

//-- [Anio/Administrar] modals
//-- [Anio/Administrar] utils
//-- [Anio/Administrar] service calls

$scope.getAlumnos = function(){
  // $scope.alumnos = []; //vacia la lista para que se vuelva a llenar
  spinnerService.show('searchAniosSpinner');
  alumnoService.getAllMin()
  .then(function(response){
    $scope.alumnos = response.data;
    $scope.listAnioIsEnabled = true;
  },
  function(response){
    showServerError(response);
    $scope.listAnioIsEnabled = false;
  })
  .finally(function(){
    spinnerService.hide('searchAniosSpinner')
  });
};

$scope.vincularAlumno = function(idAlu, idCur){
  spinnerService.show('administrarAniosSpinner');
  academicoService.cursoVin(idAlu, idCur)
  .then(
    function(response){
      showServerSuccess('El alumno ha sido vinculado al curso con éxito.', response);
      $scope.getAlumnos();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('administrarAniosSpinner');
  });
};

$scope.desvincularAlumno = function(idAlu, idCur){
  spinnerService.show('administrarAniosSpinner');
  academicoService.cursoDesvin(idAlu, idCur)
  .then(
    function(response){
      showServerSuccess('El alumno ha sido desvinculado del curso con éxito.', response);
      $scope.getAlumnos();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('administrarAniosSpinner');
  });
};

//------------------------------------



});

