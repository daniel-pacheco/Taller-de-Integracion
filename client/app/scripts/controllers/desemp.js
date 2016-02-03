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

    $scope.years = ['2010', '2011', '2012', '2013', '2014'];

    $scope.materias = ['Matem', 'Historia', 'Lengua', 'Geografia'];



    $scope.cursos = CURSOS;

    
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
  $scope.activeMenu = "";
  $scope.setActiveMenu = function(menuItem) {
   $scope.activeMenu = menuItem
 };
 $scope.activeYear = function(yearSelect) {
  $scope.year = yearSelect
};
$scope.activeMateria = function(materiaSelect) {
  $scope.materia = materiaSelect
};

  //---test list alumnos notas
$scope.alumnos = [{nro:'1', name:'John', surName:'Lennon', DNI:'464564666654',},
          {nro:'1', name:'Sebastian', surName:'Lennon', DNI:'555555555'},
          {nro:'2', name:'David', surName:'Lennon', DNI:'35464231'},
          {nro:'3', name:'John', surName:'Lennon', DNI:'555555555'},
          {nro:'4', name:'Veronica', surName:'Lennon', DNI:'154684351'},
          {nro:'5', name:'John', surName:'Lennon', DNI:'555555555'},
          {nro:'6', name:'Catalina', surName:'Lennon', DNI:'555555555'},
          {nro:'7', name:'John', surName:'Lennon', DNI:'555555555'}];

});
