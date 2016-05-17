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
 .controller('LoginCtrl', function ($scope, $state, $http, loginService, USER_ROLES, LANDING_ROUTES) {
  $scope.roleOptions = USER_ROLES;
  $scope.data = {};

  $scope.login = function(data) {
    loginService.login(data.username, data.password, data.role).then(function(authenticated) {
      $state.go(LANDING_ROUTES[data.role], {}, {reload: true});
      $scope.setCurrentUsernameAndRole();
    }, function(err) {
      alert('Login failed! Please check your credentials!');
      $state.go(LANDING_ROUTES[data.role], {}, {reload: true});//esto es para que redirija igual, se tiene q sacar cuando la auth esté activa
    });
  };

});
