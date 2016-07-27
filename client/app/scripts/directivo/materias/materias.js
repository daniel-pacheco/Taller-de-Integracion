'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:MateriasCtrl
 * @description
 * # MateriasCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider) {
  $stateProvider
  .state('directivo.materias', {
    url: '/materias',
    templateUrl: 'scripts/directivo/materias/materias.html',
    controller: 'MateriasCtrl',
    data: {
      pageTitle: 'Materias'
    }
  });
})
  .controller('MateriasCtrl', ['$scope', 'academicoService', 'docenteService', 'exportTableService', 'ModalService', 'ObjectsFactory', 'spinnerService' ,function ($scope, academicoService, docenteService, exportTableService, ModalService, ObjectsFactory, spinnerService) {

//-- [Materias]
//-- [Materias] variables
//-- [Materias] Form Management

$scope.tooltip = {
  tooltipEdit : {
    'title' : 'Editar'
  }, tooltipDelete : {
    'title' : 'Eliminar'
  },
  tooltipExport: {
    'title': 'Exportar para impresión'
  }
};
$scope.dropDownOptions = [];// ['1', '2', '3', '4', '5', '6', '7', '8'];
$scope.dropDownValue = '';

var setActiveAlu = function(menuItemAlu) {
  $scope.activeMenuIzqAlu = menuItemAlu;
};

$scope.seleccionar = function(id) {
  switch (id){
    case 'listado':
    $scope.showNuevaMateria = false;
    $scope.subtitle = "Listado";
    $scope.listado = true;
    setActiveAlu(1);
    searchAnio();
    searchDocente();//Esta lista de docentes deberia tener solo el docente y el ID
    searchArea();
    listAllMaterias();
    break;
    case 'nuevaMateria':
    $scope.formMat.$setUntouched();
    $scope.listado = false;
    $scope.subtitle = "Nueva materia";
    $scope.showNuevaMateria = true;
    $scope.nuevaMateria = new ObjectsFactory.newMateria();
    searchDocente();//Esta lista de docentes deberia tener solo el docente y el ID
    searchArea();
    searchAnio();
    setActiveAlu(2);
    break;
    case 'editar':
    $scope.listado = false;
    $scope.showNuevaMateria = true;
    searchArea();
    searchAnio();
    setActiveAlu(2);
    break;

  }
};
$scope.$on('$viewContentLoaded', function(){
  $scope.seleccionar('listado');
});

//-- [Materias] filters
//-- [Materias] modals

$scope.confirmModal = function(mesagge, funcion, parametro) { //este confirm recibe una funcion y un parametro para que despues de confirmar se pueda llamar a la funcion que se necesite
  ModalService.showModal({
    templateUrl: 'scripts/utils/confirm/modalConfirm.tpl.html',
    controller: 'modalConfirmController',
    inputs: {
      mensaje: mesagge,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      funcion(parametro);
    });
  });
};

//-- [Materias] utils (spinners, mensajes impresion etc)

//-- Export Table
$scope.exportAction = function(id){ 
  exportTableService.exportAction(id);
};

$scope.showMessage = function(mesagge, title, isGood) { //isGood recibe true si salio bien o false si salio mal
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
  // console.log(response);
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
  // console.log(response);
  var msg = message;

  if ( response && response.data) {
    msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};

//-- [Materias] service calls

$scope.addArea = function() {
  ModalService.showModal({
    templateUrl: 'scripts/directivo/materias/modal/addareadetails.tpl.html',
    controller: 'addAreaDetailsModalController',
    inputs: {
      title: "Administrar Areas",
      listaAreas: $scope.listaAreas,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      $scope.listaAreas = result;
      searchArea();
    });
  });
};

//-- Llamadas al servicio
$scope.deleteMateria = function (materia) {
  $scope.confirmModal("¿Desea eliminar " + materia.nombre + " de " + materia.anio + "? (Esta operación es irreversible!)", $scope.eliminarMateria, materia);
  //Hay que actualizar de nuevo la lista de docentes
};

//esto tiene que ser una llamada al service que elimine la materia
$scope.eliminarMateria = function(materia){

  if (materia.docenteTitular && materia.docenteSuplente) {
    $scope.showMessage('No es posible eliminar esta materia; tiene docentes asociados. ', 'ERROR', false);
  } else {

    spinnerService.show('searchMateriaSpinner');
    academicoService.matDelete(materia.idMateria)
    .then(function(response){
      showServerSuccess('La materia ha sido eliminada con éxito ', response);
      listAllMaterias();
    },
    function(response){
      showServerError(response);
    })
    .finally(function(){
      spinnerService.hide('searchMateriaSpinner');
    });
  };
};



//-- [Materias/Listado] 
//-- [Materias/Listado] variables
$scope.listaMaterias = [];
//-- [Materias/Listado] Form Management

$scope.editMateria = function(materia){
  getMateriaById(materia);
};

var editMateria = function(materia, materiaFull) {
  $scope.seleccionar('editar');
  // var materiaFull = materia;

  $scope.nuevaMateria.nombreMateria = materiaFull.nombre;
  $scope.nuevaMateria.descripcion = materiaFull.descripcion;
  $scope.nuevaMateria.idMateria = materiaFull.idMateria;
  $scope.nuevaMateria.idAnio = _.find($scope.listaAnios, ['nombre', materia.anio]).idAnio;
  $scope.nuevaMateria.area = _.find($scope.listaAreas, ['nombre', materia.area]).idArea;
  $scope.nuevaMateria.idDocenteTitular = materia.docenteTitular? materiaFull.docenteTitular.idUsuario: null;
  $scope.nuevaMateria.idDocenteSuplente = materia.docenteSuplente? materiaFull.docenteSuplente.idUsuario: null;

};

//-- [Materias/Listado] filters
//-- [Materias/Listado] modals
//-- [Materias/Listado] spinners
//-- [Materias/Listado] service calls
function listAllMaterias() {
  spinnerService.show('searchMateriaSpinner');
  academicoService.matGetAllMin()
  .then(function(response){
    $scope.listaMaterias = response.data;
  },
  function(response){
    showServerError(response);
  })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner');
  });
};
$scope.$on('$viewContentLoaded', function(){
  listAllMaterias(); //cambiar esto cuando se pueda elegir el año
});


function getMateriaById(materia) {
  spinnerService.show('searchMateriaSpinner');
  academicoService.matGetById(materia.idMateria)
  .then(function(response){
    editMateria(materia, response.data);
  },
  function(response){
    showServerError(response);
  })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner');
  });
};


//-- [Materias/Nueva] 
//-- [Materias/Nueva] variables

$scope.nuevaMateria = ObjectsFactory.newMateria();

//-- [Materias/Nueva] Form Management
//-- [Materias/Nueva] filters
//-- Order List
$scope.predicate = 'nombre';
$scope.reverse = true;
$scope.orderAnio = function(predicate) {
  $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
  $scope.predicate = predicate;
};
//-- [Materias/Nueva] modals
//-- [Materias/Nueva] spinners
//-- [Materias/Nueva] service calls

$scope.agregarMateria = function (mat) {
  mat.area = _.find($scope.listaAreas, ['idArea', mat.area]); // workaround para q retorne el objeto area y no un string area

  spinnerService.show('searchMateriaSpinner');
  academicoService.materiaPutNew(mat)
  .then(
    function(response){
      showServerSuccess('La materia se ha dado de alta con éxito', response);
      $scope.formMat.$setUntouched();
      $scope.nuevaMateria = ObjectsFactory.newMateria();
      listAllMaterias();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner');
  });
};

function searchDocente() {

  spinnerService.show('searchMateriaSpinner');
  docenteService.getAllMin()  
  .then(function(response){
    $scope.listaDocentes = response.data;
  },
  function(response){
   showServerError('Se ha producido un error al intentar contactar al servidor: ' + response.statusText);
 })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner');
  });  
};

function searchAnio() {
  spinnerService.show('searchMateriaSpinner');
  academicoService.anioGetAllMin()
  .then(function(response){
    $scope.listaAnios = response.data;
  },
  function(response){
    showServerError (response);
  })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner')
  });
};

function searchArea() {
  spinnerService.show('searchMateriaSpinner');
  academicoService.areaGetAll()
  .then(
    function(response){
      $scope.listaAreas = response.data;
    },
    function(response){
      showServerError('Se ha producido un error al intentar contactar al servidor: ' + response.statusText);
    })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner');
  });
};

function addArea(area) {
  academicoService.areaPutNew(area)
  .then(
    function(response){

    },
    function(response){

    })
  .finally(function(){

  });
};

}]);

