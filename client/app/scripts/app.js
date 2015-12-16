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
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'mgcrea.ngStrap',
    'ngAnimate'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
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
      .when('/anio', {
        templateUrl: 'views/anio.html',
        controller: 'AnioCtrl',
        controllerAs: 'anio'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
