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
 .controller('ConfigCtrl', [ '$scope', 'ModalService', 'ObjectsFactory', 'spinnerService', function ($scope, ModalService, ObjectsFactory, spinnerService) { 	

//-- [Configuracion]
//-- [Configuracion] variables
//-- [Configuracion] Form Management

$scope.configTitle = 'Configuracion Título';

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

 }]);