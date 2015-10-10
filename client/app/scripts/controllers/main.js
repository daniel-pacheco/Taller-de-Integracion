'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
  .controller('MainCtrl', function ($scope, $aside) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    
    $scope.tooltip = {
      "title": "Hello Tooltip<br />This is a multiline message!",
      "checked": false
    }

    $scope.aside = {
  "title": "Title huuu",
  "content": "Hello Aside<br />This is a multiline message!"
  };

    $scope.HolaMundo ='Hola mundo';
  });
