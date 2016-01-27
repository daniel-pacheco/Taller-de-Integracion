'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:ClientappappCtrl
 * @description
 * # ClientappappCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .controller('ClientappappCtrl', function ($scope,MENU_DIRECTIVO) {
 	this.awesomeThings = [
 	'HTML5 Boilerplate',
 	'AngularJS',
 	'Karma'
 	];

    // aca se van a cargar todas las cosas que le pidamos al backend como para arrancar la app


    //Cambia el estilo a active al seleccionar un item del menu
    $scope.menuDirectivo = MENU_DIRECTIVO;
    $scope.setActive = function(menuItem) {
    	$scope.activeMenu = menuItem
    }


});
