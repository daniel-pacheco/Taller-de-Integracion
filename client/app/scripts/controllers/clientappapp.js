'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:ClientappappCtrl
 * @description
 * # ClientappappCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')

 .controller('ClientappappCtrl', ['$popover', '$scope', '$state', 'loginService', 'ModalService', 'AUTH_EVENTS', function ($popover, $scope, $state, loginService, ModalService, AUTH_EVENTS) {


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

  $scope.credentials = {};

//   //$scope.username = loginService.username();

//   $scope.unauthorized = {};
//   $scope.unauthorized.controller =  'ClientappappCtrl';  
//   $scope.unauthorized.title = 'No Autorizado';
//   $scope.unauthorized.content = 'No tiene permitido acceder a este recurso.';
//   $scope.unauthorized.templateUrl = 'views/templates/message.tpl.html';
// 	//$scope.unauthorized.text = 'perooo, vo so loco vite?';
// 	$scope.unauthorized.show = true;

// 	$scope.unauthenticated = {
//    controller : 'ClientappappCtrl',  
//    title : 'Usuario no logueado',
//    content : 'Debe loguearse para acceder a este recurso.',
//    templateUrl : 'views/templates/message.tpl.html',
// 	//$scope.unauthorized.text = 'perooo, vo so loco vite?';
// 	show : true
// };

$scope.$on(AUTH_EVENTS.notAuthorized, function(event) {
  showMessage('No tiene las credenciales necesarias para acceder. ', 'ERROR!', false);
});

$scope.$on(AUTH_EVENTS.notAuthenticated, function(event) {
  $scope.logOut();
  showMessage('Debe loguearse para acceder. ', 'ERROR!', false);
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
  loginService.logout();
  this.setCurrentUsernameAndRole();
  $state.go('login');
};

    // aca se van a cargar todas las cosas que le pidamos al backend como para arrancar la app

}]);
