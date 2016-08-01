'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider) {
  $stateProvider
  .state('login', {
    url: '/login',
    templateUrl: 'scripts/login/login.html',
    controller: 'LoginCtrl',
    data: {
      pageTitle: 'Login'            
    }
  });
})
 .controller('LoginCtrl', ['$scope', '$state', 'loginService', 'ModalService', 'spinnerService', 'USER_ROLES', 'LANDING_ROUTES', function ($scope, $state, loginService, ModalService, spinnerService, USER_ROLES, LANDING_ROUTES) {
  //-- [Seccion]
  //-- [Seccion] variables

  $scope.roleOptions = USER_ROLES;
  $scope.data = {};
  $scope.recoverData = {};

  //-- [Seccion] Form Management

  $scope.enablePage = function(page) {
    $scope.enableLoginForm = page == 'login'? true: false;
    $scope.loginForm.$setUntouched();
    $scope.recoverForm.$setUntouched();
  };
  $scope.$on('$viewContentLoaded', function(){
    $scope.enablePage('login');
  });

  //-- [Seccion] filters
  //-- [Seccion] modals
  //-- [Seccion] utils (spinners, mensajes impresion etc)

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

    if ( response && response.data) {
      msg += ' ' + response.data;
    };      
    $scope.showMessage(msg, 'Operación exitosa' , true);
  };

  //-- [Seccion] service calls
  $scope.login = function(data) {
    spinnerService.show('loginSpinner');
    loginService.login(data.username, data.password, data.role)
    .then(
      function(response) {
        $state.go(LANDING_ROUTES[data.role], {}, {reload: true});
        $scope.setCurrentUsernameAndRole();
      }, function(response) {
      // alert('Login failed! Please check your credentials!');
      $scope.showMessage('El proceso de login ha fallado. ', 'ERROR', false);
        // $state.go(LANDING_ROUTES[data.role], {}, {reload: true});//esto es para que redirija igual, se tiene q sacar cuando la auth esté activa
      })
    .finally(function(){
      spinnerService.hide('loginSpinner');
    });
    
  };

  $scope.changePassword = function(data) {
    spinnerService.show('loginSpinner');
    loginService.changePassword(data)
    .then(function(response){
        showServerSuccess('Una nueva contraseña se ha enviado exitosamente a su correo.', response);
      },
      function(response){
        showServerError(response);
      })
    .finally(function(){
      spinnerService.hide('loginSpinner');
    });
  };
}]);
