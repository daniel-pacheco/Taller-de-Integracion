'use strict';

/**
 * @ngdoc overview
 * @name clientAppApp
 * @description
 * # clientAppApp
 *
 * Main module of the application.
 */
angular
  .module('clientAppApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ui.router',
    'ngSanitize',
    'ngTouch',
    'mgcrea.ngStrap',
    'ngAnimate',
    'ngFileUpload',
    'ngImgCrop'
  ])
  
  .config(function($stateProvider, $urlRouterProvider) {
    // if none of the above states are matched, use this as the fallback
    $urlRouterProvider.otherwise('/');
});/*
  .config(function ($routeProvider) {
    $routeProvider      
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about'
      })
      .when('/directivo', {
        templateUrl: 'views/directivo.html',
        controller: 'DirectivoCtrl',
        controllerAs: 'directivo'
      })
      .when('/materias', {
        templateUrl: 'views/materias.html',
        controller: 'MateriasCtrl',
        controllerAs: 'materias'
      })      
      .otherwise({
        redirectTo: '/'
      });
  });
*/