'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:ClientappappCtrl
 * @description
 * # ClientappappCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')

 .controller('ClientappappCtrl', function ($scope, $state, loginService, modalService, AUTH_EVENTS, MENU_DIRECTIVO) {
  
  $scope.credentials = {};

  //$scope.username = loginService.username();

  $scope.unauthorized = {};
  $scope.unauthorized.controller =  'ClientappappCtrl';  
  $scope.unauthorized.title = 'No Autorizado';
  $scope.unauthorized.content = 'No tiene permitido acceder a este recurso.';
  $scope.unauthorized.templateUrl = 'views/templates/message.tpl.html';
	//$scope.unauthorized.text = 'perooo, vo so loco vite?';
	$scope.unauthorized.show = true;

	$scope.unauthenticated = {
   controller : 'ClientappappCtrl',  
   title : 'Usuario no logueado',
   content : 'Debe loguearse para acceder a este recurso.',
   templateUrl : 'views/templates/message.tpl.html',
	//$scope.unauthorized.text = 'perooo, vo so loco vite?';
	show : true
};

$scope.$on(AUTH_EVENTS.notAuthorized, function(event) {
  //modalService.setModal($scope.unauthorized);
  alert("unathorized");
});

$scope.$on(AUTH_EVENTS.notAuthenticated, function(event) {
  //loginService.logout();
  //$state.go('login');
  this.logOut();
  //modalService.setModal($scope.unauthenticated);
  alert('unauthenticated');
});

$scope.setCurrentUsernameAndRole = function() {
  $scope.credentials.userName = loginService.userName();
  $scope.credentials.userRole = loginService.userRole();
};

$scope.logOut = function () {
  loginService.logout();
  this.setCurrentUsernameAndRole();
  $state.go('login');
};

    // aca se van a cargar todas las cosas que le pidamos al backend como para arrancar la app


    //Cambia el estilo a active al seleccionar un item del menu
    $scope.menuDirectivo = MENU_DIRECTIVO;
    $scope.setActive = function(menuItem) {
    	$scope.activeMenu = menuItem
    }


});
