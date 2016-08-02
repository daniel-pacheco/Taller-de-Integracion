'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:ConfigCtrl
 * @description
 * # ConfigCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES) {
 	$stateProvider
 	.state('administrador.configuracion', {
 		url: '/config',
 		templateUrl: 'scripts/admin/configuracion/configuracion.html',
 		controller: 'ConfigCtrl',
 		data: {
 			pageTitle: 'Configuracion',
 			authorizedRoles: [
 			USER_ROLES.admin]
 		}
 	});
 })
 .controller('ConfigCtrl', [ '$scope', 'configuracionService', 'ModalService', 'ObjectsFactory', 'PARAM_CONFIG', 'spinnerService', function ($scope, configuracionService, ModalService, ObjectsFactory, PARAM_CONFIG, spinnerService) { 	

//-- [Configuracion]
//-- [Configuracion] variables
//-- [Configuracion] Form Management
var setActiveConfig = function(menuItemConfig) {
  $scope.activeMenuIzqConfig = menuItemConfig;
};

$scope.seleccionar = function (id) {

  $scope.configGeneral = false;

  switch (id){
    case 'config':
    $scope.configGeneral = true;
    $scope.subtitle = 'Configuración general';
    setActiveConfig(1);
    //$scope.clearFormMesa();
    break;
  }
};

$scope.$on('$viewContentLoaded', function(){
  $scope.seleccionar("config");
  $scope.getAllConfig();
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
//-- [Configuracion] filters
//-- [Configuracion] modals

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

//-- [Configuracion] utils (spinners, mensajes impresion etc)
//-- [Configuracion] service calls
function getConfig(idConfig) {
  spinnerService.show('configSpinner');
  configuracionService.getParametroConfigById(idConfig)
  .then(
    function(response){
      switch (response.data.nombre){
        case 'COMIENZO_ACADEMICO':
        $scope.comienzoAcademicoObj = ObjectsFactory.newParamConfiguracion();
        $scope.comienzoAcademicoObj = response.data;
        $scope.comienzoAcademicoObj.valor = new Date($scope.comienzoAcademicoObj.valor);
        break;
        case 'FIN_ACADEMICO':
        $scope.finAcademicoObj = ObjectsFactory.newParamConfiguracion();
        $scope.finAcademicoObj = response.data;
        $scope.finAcademicoObj.valor = new Date($scope.finAcademicoObj.valor);
        break;
        case 'COMIENZO_TRIM_1':
        $scope.comienzoTrim1Obj = ObjectsFactory.newParamConfiguracion();
        $scope.comienzoTrim1Obj = response.data;
        $scope.comienzoTrim1Obj.valor = new Date($scope.comienzoTrim1Obj.valor);
        break;
        case 'FIN_TRIM_1':
        $scope.finTrim1Obj = ObjectsFactory.newParamConfiguracion();
        $scope.finTrim1Obj = response.data;
        $scope.finTrim1Obj.valor = new Date($scope.finTrim1Obj.valor);
        break;
        case 'COMIENZO_TRIM_2':
        $scope.comienzoTrim2Obj = ObjectsFactory.newParamConfiguracion();
        $scope.comienzoTrim2Obj = response.data;
        $scope.comienzoTrim2Obj.valor = new Date($scope.comienzoTrim2Obj.valor);
        break;
        case 'FIN_TRIM_2':
        $scope.finTrim2Obj = ObjectsFactory.newParamConfiguracion();
        $scope.finTrim2Obj = response.data;
        $scope.finTrim2Obj.valor = new Date($scope.finTrim2Obj.valor);
        break;
        case 'COMIENZO_TRIM_3':
        $scope.comienzoTrim3Obj = ObjectsFactory.newParamConfiguracion();
        $scope.comienzoTrim3Obj = response.data;
        $scope.comienzoTrim3Obj.valor = new Date($scope.comienzoTrim3Obj.valor);
        break;
        case 'FIN_TRIM_3':
        $scope.finTrim3Obj = ObjectsFactory.newParamConfiguracion();
        $scope.finTrim3Obj = response.data;
        $scope.finTrim3Obj.valor = new Date($scope.finTrim3Obj.valor);
        break;
        case 'LIMITE_DIAS_INSCRIP':
        $scope.limiteDiasInscripObj = ObjectsFactory.newParamConfiguracion();
        $scope.limiteDiasInscripObj = response.data;
        $scope.limiteDiasInscripObj.valor = parseInt($scope.limiteDiasInscripObj.valor);
        break;
        case 'VIS_DIAS_LLAMADO':
        $scope.visDiasLlamadoObj = ObjectsFactory.newParamConfiguracion();
        $scope.visDiasLlamadoObj = response.data;
        $scope.visDiasLlamadoObj.valor = parseInt($scope.visDiasLlamadoObj.valor);
        break;
        case 'VIS_LLAMADO':
        $scope.visLlamadoObj = ObjectsFactory.newParamConfiguracion();
        $scope.visLlamadoObj = response.data;
        break;
      }
    },
    function(response){
      showServerError (response);
    })
.finally(function(){
  spinnerService.hide('configSpinner')
});
};

$scope.getAllConfig = function() {
  angular.forEach(PARAM_CONFIG, function (item) {
    getConfig(item[1]);
  });
};

$scope.updateParamConfig = function(paramObj) {
  spinnerService.show('configSpinner');
  configuracionService.configPut(paramObj)
  .then(
    function(response){
      showServerSuccess('El parametro de configuración se guardo con éxito',response.data);
      getConfig(paramObj.idParametroConfiguracion);
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('configSpinner');
  });
};

}]);