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
  .controller('LoginCtrl', function ($scope, $aside, $http, loginService, USER_ROLES) {
    $scope.roleOptions = USER_ROLES;
    
    $scope.loguearse =  loginService.login;

    $scope.HolaMundo ='Hola mundo';
  });
