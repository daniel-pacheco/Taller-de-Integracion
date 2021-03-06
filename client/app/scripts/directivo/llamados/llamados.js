'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:LlamadosCtrl
 * @description
 * # LlamadosCtrl
 * Controller of the clientAppApp
 */
 
 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES, $selectProvider) {
  $stateProvider
  .state('directivo.llamados', {
    url: '/llamados',
    templateUrl: 'scripts/directivo/llamados/llamados.html',
    controller: 'LlamadosCtrl',
    data: {
      pageTitle: 'Materias',
      authorizedRoles: [
      USER_ROLES.admin,
      USER_ROLES.directivo]
    }
  });

  angular.extend($selectProvider.defaults, {
    animation: 'am-flip-x',
    sort: false,
    placeholder: 'Seleccione tribunal de docentes'
  });
})

 .controller('LlamadosCtrl', ['$scope', '$select', 'academicoService', 'docenteService', 'exportTableService', 'ModalService', 'ObjectsFactory', 'spinnerService', function ($scope, $select, academicoService, docenteService, exportTableService, ModalService, ObjectsFactory, spinnerService) {

//-- [Llamado]
//-- [Llamado] variables
//-- [Llamado] Form Management
var setActiveLlamado = function(menuItemLlamado) {
  $scope.activeMenuIzqLlamado = menuItemLlamado;
};

$scope.seleccionar = function (id) {

  if ($scope.showEditLlamadoMenuIzq){
    $scope.clearFormLlamado();
  };
  if ($scope.showEditMesaMenuIzq){
    $scope.clearFormMesa(); 
  };

  $scope.nuevaMesa = false;
  $scope.listado = false;
  $scope.inscripcion = false;
  $scope.gestionarLlamados = false;
  $scope.calificar = false;
  $scope.nuevoLlamado = false;
  $scope.showEditLlamadoMenuIzq = false;
  $scope.showEditMesaMenuIzq = false;

  switch (id){
    case 'nuevaMesa':
    $scope.nuevaMesa = true;
    $scope.subtitle = 'Nueva mesa';
    setActiveLlamado(1);
    $scope.clearFormMesa();
    break;
    case 'listado':
    $scope.listado = true;
    $scope.subtitle = 'Listado';
    setActiveLlamado(2); 
    getLlamados();
    break;
    case 'inscripcion':
    $scope.inscripcion = true;
    $scope.subtitle = 'Inscripción';
    setActiveLlamado(3);
    break;
    case 'gestionarLlamados':
    $scope.gestionarLlamados = true;
    $scope.subtitle = 'Gestionar llamados';
    setActiveLlamado(4);
    break;
    case 'calificar':
    $scope.calificar = true;
    $scope.subtitle = 'Calificar';
    setActiveLlamado(5);
    break;
    case 'nuevoLlamado':
    $scope.nuevoLlamado = true;
    $scope.subtitle = 'Nuevo llamado';
    setActiveLlamado(6);
    break;
    case 'editarLlamado':
    $scope.nuevoLlamado = true;
    $scope.subtitle = 'Editar llamado';
    $scope.showEditLlamadoMenuIzq = true;
    setActiveLlamado(7);
    break;
    case 'editarMesa':
    $scope.nuevaMesa = true;
    $scope.subtitle = 'Editar mesa';
    $scope.showEditMesaMenuIzq = true;
    setActiveLlamado(8);
    break;
  }
};

$scope.$on('$viewContentLoaded', function(){
  //$scope.clearFormMesa();
  $scope.seleccionar("listado");  
});

$scope.tooltip = {
  tooltipEdit : {
    'title' : 'Editar'
  }, tooltipDelete : {
    'title' : 'Eliminar'
  }, tooltipSaveEdit : {
    'title' : 'Guardar edición'
  }, tooltipInscripto : {
    'title' : 'Ya se encuentra inscripto'
  }, tooltipNoInscripto: {
    'title': 'No se encuentra inscripto'
  }
};


//-- [Llamado] filters
//-- [Llamado] modals
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
//-- [Llamado] utils
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
  //console.log(response);
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
  //console.log(response);
  var msg = message;

  if (response.data) {
    msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};


//Order list Llamado
$scope.predicate = 'fechaInicio';
$scope.reverse = false;
$scope.orderLlamado = function(predicate) {
  $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
  $scope.predicate = predicate;
};

//-- [Llamado] service calls


//-- [Llamado/Nueva mesa]
//-- [Llamado/Nueva mesa] variables

//-- [Llamado/Nueva mesa] Form Management
//-- [Llamado/Nueva mesa] filters
//-- [Llamado/Nueva mesa] modals
//-- [Llamado/Nueva mesa] utils
function initMesa(mesaMin){
  var mesa = ObjectsFactory.newMesa();
  mesa.idMesa = mesaMin.idMesa;
  mesa.idLlamado = mesaMin.periodo.idLlamado;
  mesa.fechaHoraInicio = setFechaHora(mesaMin.horaInicio);
  mesa.fechaHoraFin = setFechaHora(mesaMin.horaFin);
  mesa.idMateria = mesaMin.selectedMateria;
  mesa.tribunalDoc1 = mesaMin.docentes[0];
  mesa.tribunalDoc2 = mesaMin.docentes[1];
  mesa.tribunalDoc3 = mesaMin.docentes[2];
  return mesa;
};

function setFechaHora(hora){
  var date = new Date($scope.nuevaMesaObj.fechaMesa);
  date.setHours(hora.getHours());
  date.setMinutes(hora.getMinutes());
  return date;
};

$scope.clearFormMesa = function(){
  $scope.formMesa.$setUntouched();
  getLlamados();
  getAnios();
  getMaterias();
  getDocentes();
  $scope.nuevaMesaObj = ObjectsFactory.newMesa();
  $scope.nuevaMesaObj.docentes = [];
};
//-- [Llamado/Nueva mesa] service calls
function getAnios() {
  spinnerService.show('searchLlamadoSpinner');
  academicoService.anioGetAllMin()
  .then(
    function(response){
      $scope.anios = response.data;
    },
    function(response){
      showServerError (response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner')
  });
};

function getMaterias () {
  spinnerService.show('searchLlamadoSpinner');
  academicoService.matGetAllMin()
  .then(
    function(response){
      $scope.materias = response.data;
    },
    function(response){
      showServerError (response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner')
  });
};

function getDocentes() {
  var promise = {};

  spinnerService.show('searchLlamadoSpinner');
  promise = docenteService.getAllMin()  
  .then(function(response){
    $scope.listaDocentes = response.data;
  },
  function(response){
   showServerError('Se ha producido un error al intentar contactar al servidor: ' + response.statusText);
 })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  });  
};

$scope.newMesa = function(mesaMin) {

  var mesa = initMesa(mesaMin);
  spinnerService.show('searchLlamadoSpinner');

  academicoService.mesaPutNew(mesa)
  .then(
    function(response){
      $scope.clearFormMesa();
      if ($scope.showEditMesaMenuIzq){
        $scope.seleccionar('listado'); 
        showServerSuccess('La mesa se ha modificado con éxito',response.data);
      } else showServerSuccess('La mesa se ha dado de alta de alta con éxito',response.data);
      
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  });

};


//-- [Llamado/nuevo Llamado]
//-- [Llamado/nuevo Llamado] variables
//-- [Llamado/nuevo Llamado] Form Management
//-- [Llamado/nuevo Llamado] filters
//-- [Llamado/nuevo Llamado] modals
//-- [Llamado/nuevo Llamado] utils
function initLlamado(llamadoMin){
  var llamado = ObjectsFactory.newLlamado();
  llamado.descripcion = llamadoMin.descripcion;
  llamado.fechaInicio = llamadoMin.fechaInicioLlamado;
  llamado.fechaFin = llamadoMin.fechaFinLlamado;
  llamado.idLlamado = llamadoMin.idLlamado;
  llamado.listaMesas = llamadoMin.listaMesas;
  // console.log(llamado);
  return llamado;
};
//-- [Llamado/nuevo Llamado] service calls
$scope.newLlamado = function(llamadoMin) {
  var llamado = initLlamado(llamadoMin);
  spinnerService.show('searchLlamadoSpinner');
  academicoService.llamadoPutNew(llamado)
  .then(
    function(response){
      showServerSuccess('El llamado se ha dado de alta de alta con éxito ',response.data);
      if ($scope.showEditLlamadoMenuIzq) {//si estaba editando vuelve a listado
        $scope.seleccionar('listado');
      }
      $scope.clearFormLlamado();
      getLlamados();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  });
};

$scope.clearFormLlamado = function(){
  $scope.formLlamado.$setUntouched();
  $scope.nuevoLlamadoObj = ObjectsFactory.newLlamado();
}

//-- [Llamado/Listado]
//-- [Llamado/Listado] variables
//-- [Llamado/Listado] Form Management

$scope.editLlamado = function(llamado){
  $scope.nuevoLlamadoObj = ObjectsFactory.newLlamado();
  $scope.nuevoLlamadoObj.idLlamado = llamado.idLlamado;
  $scope.nuevoLlamadoObj.descripcion = llamado.descripcion;
  $scope.nuevoLlamadoObj.fechaFinLlamado = new Date(llamado.fechaFin);
  $scope.nuevoLlamadoObj.fechaInicioLlamado = new Date(llamado.fechaInicio);
  $scope.nuevoLlamadoObj.listaMesas = llamado.listaMesas;
  $scope.seleccionar('editarLlamado');
};



$scope.editMesa = function(mesa, idLlamado){

  $scope.nuevaMesaObj = ObjectsFactory.newMesa();
  $scope.nuevaMesaObj.periodo = _.find($scope.llamados, ['idLlamado', idLlamado]);
  $scope.nuevaMesaObj.idMesa = mesa.idMesa;
  $scope.nuevaMesaObj.selectedAnio = mesa.anio;
  $scope.nuevaMesaObj.fechaMesa = new Date(mesa.fechaHoraInicio);
  $scope.nuevaMesaObj.horaInicio = new Date(mesa.fechaHoraInicio);
  $scope.nuevaMesaObj.horaFin = new Date(mesa.fechaHoraFin);
  $scope.nuevaMesaObj.selectedMateria = mesa.idMateria;
  $scope.nuevaMesaObj.docentes = [];
  (mesa.idTribunal1 != undefined) ? searchDocenteById(mesa.idTribunal1) : null;
  (mesa.idTribunal2 != undefined) ? searchDocenteById(mesa.idTribunal2) : null;
  (mesa.idTribunal3 != undefined) ? searchDocenteById(mesa.idTribunal3): null;
  $scope.seleccionar('editarMesa');
};
//-- [Llamado/Listado] filters
//-- [Llamado/Listado] modals
//-- [Llamado/Listado] utils
function searchDocenteById(idDocente){
  $scope.nuevaMesaObj.docentes.push((_.find($scope.listaDocentes, ['idUsuario', idDocente])).nroDocumento);
};

$scope.confirmDeleteLlamado = function(llamado) {
  var fechaLlamado = new Date(llamado.fechaInicio);
  $scope.confirmModal("¿Desea eliminar el llamado de " + llamado.descripcion + " del " + fechaLlamado.getDate() + " al " + llamado.fechaFin.getDate() + " del mes " + (llamado.fechaInicio.getMonth()+1) + " del " + llamado.fechaInicio.getFullYear() +"?", deleteLlamado, llamado);
}

$scope.confirmDeleteMesa = function(mesa) {
  var fechaMesa = new Date(mesa.fechaHoraInicio)
  fechaMesa = fechaMesa.getDate() + "/" + (fechaMesa.getMonth()+1) + "/" + fechaMesa.getFullYear();
  $scope.confirmModal("¿Desea eliminar la mesa de " + mesa.Materia + " de " + mesa.anio + " del día " + fechaMesa + "?", deleteMesa, mesa.idMesa);
}

function asignarAnioMesa(arrayListaMateriasMin, listaMesas){
  angular.forEach(listaMesas, function(item){
    item.anio = _.find(arrayListaMateriasMin, ['idMateria', item.idMateria]).anio;
  });
};
//-- [Llamado/Listado] service calls
function getLlamados() {
  spinnerService.show('searchLlamadoSpinner');
  academicoService.llamadoGetAllMin()
  .then(
    function(response){
      $scope.llamados = response.data;
      angular.forEach($scope.llamados, function (item) {
        item.fechaInicio = new Date(item.fechaInicio);
        item.fechaFin = new Date(item.fechaFin);
        getMateriasMin(item.listaMesas);//es para asignar el anio a cada mesa
      });
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  })
};

function deleteLlamado (llamado) {
  if (llamado.listaMesas.length !== 0) {
    $scope.showMessage('El llamado no debe contener mesas para poder ser eliminado.', 'ERROR!', false);
  } else{
    spinnerService.show('searchLlamadoSpinner');
    academicoService.llamadoDelete(llamado.idLlamado)
    .then(
      function(response){
        showServerSuccess('El llamado ha sido eliminado con éxito', response);
        getLlamados();
      },
      function(response){
        showServerError(response);
      })
    .finally(function(){
      spinnerService.hide('searchLlamadoSpinner')
    });
  };
};

function deleteMesa(idMesa) {
  spinnerService.show('searchLlamadoSpinner');
  academicoService.mesaDelete(idMesa)
  .then(
    function(response){
      showServerSuccess('La mesa ha sido eliminado con éxito', response);
      getLlamados();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner')
  });
};

function getMateriasMin (listaMesas){
  spinnerService.show('searchLlamadoSpinner');
  academicoService.matGetAllMin()
  .then(
    function(response){
      asignarAnioMesa(response.data, listaMesas);
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  })
};
//-- [Llamado/inscripcion]
//-- [Llamado/inscripcion] variables
//-- [Llamado/inscripcion] Form Management
$scope.listMesas = false;
//-- [Llamado/inscripcion] filters
//-- [Llamado/inscripcion] modals
//-- [Llamado/inscripcion] utils
$scope.confirmInscribir = function(idMesa, idAlumno, dni, alumno, materia, fecha){
  var params = {
    'idMesa': idMesa,
    'idAlumno': idAlumno,
    'dni': dni
  };

  $scope.confirmModal("Desea inscribir a "+ alumno + " a la mesa de " + materia + " el día " + fecha, $scope.inscribir, params);
};

$scope.confirmDesinscribir = function(idMesa, idAlumno, dni, alumno, materia, fecha){
  var params = {
    'idMesa': idMesa,
    'idAlumno': idAlumno,
    'dni': dni
  };

  $scope.confirmModal("Desea desinscribir a "+ alumno + " de la mesa de " + materia + " el día " + fecha, $scope.desinscribir, params);
};
//-- [Llamado/inscripcion] service calls
$scope.getMesas = function(dni){
  spinnerService.show('searchLlamadoSpinner');
  academicoService.mesasGet(dni)
  .then(
    function(response){
      $scope.listaMesasDisponibles = response.data;
      $scope.listMesas = true;
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  })
};

$scope.inscribir = function(params){
  spinnerService.show('searchLlamadoSpinner');
  academicoService.mesasInscribir(params['idMesa'], params['idAlumno'])
  .then(
    function(response){
      showServerSuccess('La inscripción finalizó con éxito ',response.data);
      $scope.getMesas(params['dni']);
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  })
};
$scope.desinscribir = function(params){
  spinnerService.show('searchLlamadoSpinner');
  academicoService.mesasDesinscribir(params['idMesa'], params['idAlumno'])
  .then(
    function(response){
      showServerSuccess('La desinscripción finalizó con éxito ',response.data);
      $scope.getMesas(params['dni']);
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchLlamadoSpinner');
  })
};
//-- [Llamado/sub-seccion]
//-- [Llamado/sub-seccion] variables
//-- [Llamado/sub-seccion] Form Management
//-- [Llamado/sub-seccion] filters
//-- [Llamado/sub-seccion] modals
//-- [Llamado/sub-seccion] utils
//-- [Llamado/sub-seccion] service calls
}]);

