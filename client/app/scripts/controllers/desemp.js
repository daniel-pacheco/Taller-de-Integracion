'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DesempCtrl
 * @description
 * # DesempCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('desemp', {
 		url: '/desemp',
 		templateUrl: 'views/desemp.html',
 		controller: 'DesempCtrl',
 		data: {
 			pageTitle: 'Desempeño'
 		}
 	});
 })
  .controller('DesempCtrl', function ($scope, CURSOS) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    //filters

    $scope.cursos = CURSOS;
    $scope.listadoTodos = true;

    
 	$scope.seleccionar = function(id) {//Hacer una funcion que ponga en true el nombre de variable que le llegue y el resto en false (toogle de tres variables)
    $scope.listadoTodos = false;
    $scope.listadoPrimero = false;
    $scope.listadoSegundo = false;
    $scope.listadoTercero = false;
    $scope.listadoCuarto = false;
    $scope.listadoQuinto = false;
    $scope.listadoSexto = false;

    switch (id) {
      case $scope.cursos.todos:
        $scope.listadoTodos = true;
        break; 
      case $scope.cursos.primero:
        $scope.listadoPrimero = true;
        break;
      case $scope.cursos.segundo:
        $scope.listadoSegundo = true;
        break; 
      case $scope.cursos.tercero:
        $scope.listadoTercero = true;
        break; 
      case $scope.cursos.cuarto:
        $scope.listadoCuarto = true;
        break; 
      case $scope.cursos.quinto:
        $scope.listadoQuinto = true;
        break;  
      case $scope.cursos.sexto:
        $scope.listadoSexto = true;
        break;  
    };    
 	};

 	$scope.setActive = function(menuItem) {
    	$scope.activeMenu = menuItem
    };

  });
