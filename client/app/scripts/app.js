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
  'ngMessages',
  'ngResource',
  'ui.router',
  'ngSanitize',
  'ngTouch',
  'mgcrea.ngStrap',
  'ngAnimate',
  'ngFileUpload',
  'ngImgCrop',
  'angularModalService',
  'angularMoment',
  'angularSpinners'

  ])

 .config(function($stateProvider, $urlRouterProvider) {
    // if none of the above states are matched, use this as the fallback
    // $urlRouterProvider.otherwise('/login');
    
    // $urlRouterProvider.otherwise(function () {
    //   loginService.logout();
    //   $state.go('login');
    // });

 $urlRouterProvider.when('/', '/login');
 $urlRouterProvider.when('', '/login');

 $urlRouterProvider.otherwise(function ($injector, $location, $state) {

    event.preventDefault();
    $injector.invoke(function($rootScope, AUTH_EVENTS, $state) {
      $state.go($state.current, {}, {reload: true});
      $rootScope.$broadcast(AUTH_EVENTS.notFound);
    });   
  });
})

  // allow DI for use in controllers, unit tests
  .constant('_', window._)
  // use in views, ng-repeat="x in _.range(3)"


  .run(function ($rootScope, $state, loginService, AUTH_EVENTS) {

    $rootScope._ = window._;

    $rootScope.$on('$stateChangeStart', function (event, next, nextParams, fromState) {

      if ('data' in next && 'authorizedRoles' in next.data) { // agregar call q le pegue al back
        var authorizedRoles = next.data.authorizedRoles;
        if (!loginService.isAuthorized(authorizedRoles)) {
          event.preventDefault();
          $state.go($state.current, {}, {reload: true});
          $rootScope.$broadcast(AUTH_EVENTS.notAuthorized);
        };
      };

      if (!loginService.isAuthenticated()) {
        if (next.name !== 'login') {
          event.preventDefault();
          $state.go('login');
          $rootScope.$broadcast(AUTH_EVENTS.notAthenticated);
        };
      };
    });

    $rootScope.$on(AUTH_EVENTS.notAthenticated, function (){//event, toState, toParams, fromState, fromParams) {
          event.preventDefault();
          loginService.logout();
          $state.go('login');
    });


});
