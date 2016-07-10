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
 .controller('MateriasCtrl', function ($scope, ModalService, areasData, $timeout, $alert, materiasData, ObjectsFactory, docenteData, academicoService, docenteService, $modal, spinnerService, exportTableService) {

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
$scope.dropDownOptions = ['1', '2', '3', '4', '5', '6', '7', '8'];
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
    break;
    case 'nuevaMateria':
    $scope.listado = false;
    $scope.subtitle = "Nueva materia";
    $scope.showNuevaMateria = true;
    $scope.nuevaMateria = new ObjectsFactory.newMateria();
    searchDocente();//Esta lista de docentes deberia tener solo el docente y el ID
    searchArea();
    searchAnio();
    setActiveAlu(2);
    break;

  }
};
$scope.seleccionar('listado');

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

  if ( response && response.data) {
    msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};

//-- [Materias] service calls

  // $scope.listado = true;
  // $scope.subtitle = "Listado";
  // $scope.listaMaterias = materiasData;







  // $scope.activeMenuIzqAlu = 1;
  


//-- Modals
// $scope.listaAreas = [{idArea: 1, nombre: "Sistemas"},
// {idArea: 2, nombre: "Computación"}];//areasData;

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
    });
  });
};





//-- Llamadas al servicio
$scope.deleteMateria = function (materia) {
  $scope.confirmModal("¿Desea eliminar "+materia.nombre+" de "+materia.ano+" "+materia.division+"?", $scope.eliminarMateria, materia);
  //Hay que actualizar de nuevo la lista de docentes
};

//esto tiene que ser una llamada al service que elimine la materia
$scope.eliminarMateria = function(materia){
  $scope.listaMaterias.splice($scope.listaMaterias.indexOf(materia),1);
  $scope.showMessage("todo ok", "Aviso", false);
  //$modal({title: 'hola', content: 'Todo bien', show: true});
  $scope.showMessage("todo ok", "Aviso", true);
};



//-- [Materias/Listado] 
//-- [Materias/Listado] variables
$scope.listaMaterias = [];
//-- [Materias/Listado] Form Management
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
    console.log(response);
  })
  .finally(function(){
    spinnerService.hide('searchMateriaSpinner');
  });
};
$scope.$on('$viewContentLoaded', function(){
  listAllMaterias(); //cambiar esto cuando se pueda elegir el año
});

//Test
$scope.friends = [{nombre:'Educación Fisica', docenteTitular:'María Laura', anioPertenece: '4º', area: 'cs sociales'},
{nombre:'Matemática', docenteTitular:'Marta Blanco', anioPertenece: '4', area: 'cs sociales'},
{nombre:'Lengua', docenteTitular:'Lorena Gomez', anioPertenece: '4', area: 'cs naturales'},
{nombre:'Fisica', docenteTitular:'Alicia Modenutti', anioPertenece: '5º', area: 'cs sociales'},
{nombre:'Geografía', docenteTitular:'Mariela Rickert', anioPertenece: '5º', area: 'cs naturales'},
{nombre:'Historia', docenteTitular:'Gloria Herrlein', anioPertenece: '4º', area: 'cs exsactas'}];

// $scope.listaAnios = [
// {
//   "idAnio": 0,
//   "nombre": "4to"
// },
// {
//   "idAnio": 1,
//   "nombre": "5to"
// },
// {
//   "idAnio": 2,
//   "nombre": "6to"
// },
// {
//   "idAnio": 3,
//   "nombre": "1ro"
// },
// {
//   "idAnio": 4,
//   "nombre": "3roto"
// },
// ];


//-- [Materias/Nueva] 
//-- [Materias/Nueva] variables

$scope.nuevaMateria = ObjectsFactory.newMateria();

//-- [Materias/Nueva] Form Management
//-- [Materias/Nueva] filters
//-- [Materias/Nueva] modals
//-- [Materias/Nueva] spinners
//-- [Materias/Nueva] service calls

$scope.agregarMateria = function (mat) {
  //agregar el alumno
  //actualizar la lista
  // $scope.listaMaterias.push ($scope.nuevaMateria);
  // $scope.formMat.$setUntouched();
  

  spinnerService.show('searchMateriaSpinner');
  academicoService.materiaPutNew(mat)
  .then(
    function(response){
      showServerSuccess('La materia se ha dado de alta con éxito', response);
      $scope.formMat.$setUntouched();
      $scope.nuevaMateria = ObjectsFactory.newMateria();
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

});

