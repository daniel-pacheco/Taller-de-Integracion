angular.module('clientAppApp')
.controller('adminEspecialidadModalController', [
  '$element', '$scope', 'academicoService', 'close', 'ModalService', 'ObjectsFactory', 'spinnerService', 'title',
  function($element, $scope, academicoService, close, ModalService, ObjectsFactory, spinnerService, title) {

    $scope.close = function(modif) {
     if (modif)
       close ($scope.especialidades, 500);
     else
       close (500);
   };

//-- [Especialidad]
//-- [Especialidad] variables
$scope.title = title;

//-- [Especialidad] Form Management
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
//-- [Especialidad] filters
//-- [Especialidad] modals
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

//-- [Especialidad] utils
//-- [Especialidad] service calls
$scope.getEspecialidades = function(){
  // $scope.alumnos = []; //vacia la lista para que se vuelva a llenar
  spinnerService.show('searchEspecialidadesSpinner');
  academicoService.especialidadGetAll()
  .then(function(response){
    $scope.especialidades = response.data;
  },
  function(response){
    showServerError(response);
  })
  .finally(function(){
    spinnerService.hide('searchEspecialidadesSpinner')
  });
};

//-- [Especialidad/agregar]
//-- [Especialidad/agregar] variables
//-- [Especialidad/agregar] Form Management

//-- [Especialidad/agregar] filters
//-- [Especialidad/agregar] modals
//-- [Especialidad/agregar] utils
function clearFormEspecialidad(){
  $scope.form.$setUntouched();
  $scope.especialidadObj = ObjectsFactory.newEspecialidad();
  $scope.getEspecialidades();
};
//-- [Especialidad/agregar] service calls
function initEspecialidad(espMin){
  var especialidad = ObjectsFactory.newEspecialidad();
  especialidad.idEspecialidad = espMin.idEspecialidad;
  especialidad.nombre = espMin.nombre;
  especialidad.nombreCorto = espMin.nombreCorto;
  return especialidad;
};

$scope.newEspecialidad = function(espMin) {

  spinnerService.show('searchEspecialidadesSpinner');
  academicoService.especialidadPutNew(initEspecialidad(espMin))
  .then(
    function(response){
      clearFormEspecialidad();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchEspecialidadesSpinner');
  });
};

//-- [Especialidad/editar]
//-- [Especialidad/editar] variables
//-- [Especialidad/editar] Form Management
$scope.edit = function (especialidad){
  $scope.copiaEspecialidad = angular.copy(especialidad);
};
//-- [Especialidad/editar] filters
//-- [Especialidad/editar] modals
//-- [Especialidad/editar] utils
//-- [Especialidad/editar] service calls

//-- [Especialidad/eliminar]
//-- [Especialidad/eliminar] variables
//-- [Especialidad/eliminar] Form Management
//-- [Especialidad/eliminar] filters
//-- [Especialidad/eliminar] modals
//-- [Especialidad/eliminar] utils
getAnios();
var arrayEspAnios = [];
function setArrayEspAnios(){
  angular.forEach($scope.anios, function (item) {
    arrayEspAnios.push(item.especialidad)
  });
}

$scope.confirmEliminar = function(especialidad){
  var params = {
    'idEspecialidad': especialidad.idEspecialidad,
    'nombre': especialidad.nombre
  };

  $scope.confirmModal("¿Desea eliminar la especialidad " + especialidad.nombre + "?", $scope.deleteEspecialidad, params);
};
//-- [Especialidad/eliminar] service calls
$scope.deleteEspecialidad = function (especialidad) {
  setArrayEspAnios();
  if (_.includes(arrayEspAnios, especialidad.nombre)){
    $scope.showMessage('La especialidad no debe estar siendo utilizada en ningún año para poder ser eliminada.', 'ERROR!', false);
  }else{
    spinnerService.show('searchEspecialidadesSpinner');
    academicoService.especialidadDelete(especialidad.idEspecialidad)
    .then(
      function(response){
        $scope.getEspecialidades();
      },
      function(response){
        showServerError(response);
      })
    .finally(function(){
      spinnerService.hide('searchEspecialidadesSpinner')
    });
  };

};


function getAnios() {
  spinnerService.show('searchEspecialidadesSpinner');
  academicoService.anioGetAllMin()
  .then(
    function(response){
      $scope.anios = response.data;
    },
    function(response){
      showServerError (response);
    })
  .finally(function(){
    spinnerService.hide('searchEspecialidadesSpinner')
  });
};
$scope.getEspecialidades();

}]);