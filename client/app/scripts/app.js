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
    'ngImgCrop',
    'ngMockE2E'
  ])
  
  .config(function($stateProvider, $urlRouterProvider) {
    // if none of the above states are matched, use this as the fallback
    $urlRouterProvider.otherwise('/');
<<<<<<< HEAD
})

.run(function ($rootScope, $state, loginService, AUTH_EVENTS) {
  $rootScope.$on('$stateChangeStart', function (event, next, nextParams, fromState) {
 
    if ('data' in next && 'authorizedRoles' in next.data) {
      var authorizedRoles = next.data.authorizedRoles;
      if (!loginService.isAuthorized(authorizedRoles)) {
        event.preventDefault();
        $state.go($state.current, {}, {reload: true});
        $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
      }
    }
 
    if (!loginService.isAuthenticated()) {
      if (next.name !== 'login') {
        event.preventDefault();
        $state.go('login');
      }
    }
=======
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
      .when('/desemp', {
        templateUrl: 'views/desemp.html',
        controller: 'DesempCtrl',
        controllerAs: 'desemp'
      })
      .otherwise({
        redirectTo: '/'
      });
>>>>>>> 557474a1d1203ee5d86c7f188229d2856d5ecff2
  });
})
  
.run(function($httpBackend){
  $httpBackend.whenGET('http://localhost:8100/valid')
        .respond({message: 'This is my valid response!'});
  $httpBackend.whenGET('http://localhost:8100/notauthenticated')
        .respond(401, {message: "Not Authenticated"});
  $httpBackend.whenGET('http://localhost:8100/notauthorized')
        .respond(403, {message: "Not Authorized"});
 
  $httpBackend.whenGET(/views\/\w+.*/).passThrough();
 })