'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.auth/AuthInterceptor
 * @description
 * # auth/AuthInterceptor
 * Factory in the clientAppApp.
 */
angular.module('clientAppApp')
  .factory('AuthInterceptor', function ($rootScope, $q, AUTH_EVENTS) {
  return {
    responseError: function (response) {
      $rootScope.$broadcast({
        401: AUTH_EVENTS.notAuthenticated,
        403: AUTH_EVENTS.notAuthorized
      }[response.status], response);
      return $q.reject(response);
    }
  };
})
 
.config(function ($httpProvider) {
  $httpProvider.interceptors.push('AuthInterceptor');
});
