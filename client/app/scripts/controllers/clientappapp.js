'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:ClientappappCtrl
 * @description
 * # ClientappappCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')

 .controller('ClientappappCtrl', ['$popover', '$scope', '$state', '$timeout', 'loginService', 'ModalService', 'AUTH_EVENTS', function ($popover, $scope, $state, $timeout, loginService, ModalService, AUTH_EVENTS) {


  var showMessage = function(mesagge, title, isGood) { //todo ok recibe true si salio bien o false si salio mal

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

  var showErrorMessage = function(mesagge, title, isGood) { //todo ok recibe true si salio bien o false si salio mal
    
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
      // modal.close.then(function(){console.log('close' + new Date())});
      // modal.closed
      // .then(
      //   function(){console.log('closed' + new Date());$scope.logOut();}
      //   );
    });    
  };


  $scope.credentials = {};

  $scope.$on(AUTH_EVENTS.notAuthorized, function(event) {
    // console.log('notAuthorized' + new Date());
    showMessage('No tiene las credenciales necesarias para acceder. ', 'ERROR!', false);
  });

  $scope.$on(AUTH_EVENTS.notAuthenticated, function(event) {
    showErrorMessage('Debe loguearse para acceder. ', 'ERROR!', false);
    $scope.logOut();
  });

  $scope.$on(AUTH_EVENTS.notFound, function(event) {
  // $scope.logOut();
  showMessage('URL no válida. ', 'ERROR!', false);
});

  $scope.setCurrentUsernameAndRole = function() {
    $scope.credentials.userName = loginService.userName();
    $scope.credentials.userRole = loginService.userRole();
  };
  $scope.setCurrentUsernameAndRole();

  $scope.logOut = function () {
    console.log('logOut' + new Date());
    loginService.logout();
    this.setCurrentUsernameAndRole();
    $state.go('login');
  };

    // aca se van a cargar todas las cosas que le pidamos al backend como para arrancar la app

  }]);
