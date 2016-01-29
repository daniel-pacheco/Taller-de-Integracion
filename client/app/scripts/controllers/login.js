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
        url: '/',
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        data: {
          pageTitle: 'Login'            
        }
    });
})
  .controller('LoginCtrl', function ($scope, $state, $http, loginService, USER_ROLES) {
    $scope.roleOptions = USER_ROLES;
    
    $scope.loguearse =  loginService.login;

    $scope.HolaMundo ='Hola mundo';

    $scope.data = {};
 
  $scope.login = function(data) {
    loginService.login(data.username, data.password, data.role).then(function(authenticated) {
      $state.go('alumnado', {}, {reload: true});
      $scope.setCurrentUsername(data.username);
    }, function(err) {
      alert('Login failed! Please check your credentials!');
    });
  };

  });
