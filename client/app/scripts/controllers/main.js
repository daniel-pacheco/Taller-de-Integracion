'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
  .controller('MainCtrl', function ($scope, $aside, $http) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    
    $scope.data = {};

    $http.get('http://192.168.1.6:8080/EscuelaSantaLucia/rest/sAlumno/listAll').success(function(response){

      $scope.data = response;

    });


    $scope.tooltip = {
      "title": "Hello Tooltip <br/>This is a multiline message!",
      "checked": false
    }

    $scope.aside = {
  "title": "Title huuu",
  "content": "Hello Aside <br/>This is a multiline message!"
  };

  $scope.modal = {
    "title": "Title",
    "content": "ventanita modal!"
  };

    $scope.HolaMundo ='Hola mundo';
  });
