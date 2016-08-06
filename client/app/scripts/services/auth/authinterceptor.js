'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.auth/AuthInterceptor
 * @description
 * # auth/AuthInterceptor
 * Factory in the clientAppApp.
 */
 angular.module('clientAppApp')
 .factory('AuthInterceptor', ['$q', '$rootScope', 'AUTH_EVENTS', function ($q, $rootScope, AUTH_EVENTS) {
  return {
    response: function (response) {
      if (_.includes(response.config.url, 'rest')) {
        console.log('server: ' + response.config.headers['auth0']);
        console.log('local: ' + window.sessionStorage.getItem('LOCAL_TOKEN_KEY'));
        if (window.sessionStorage.getItem('LOCAL_TOKEN_KEY') !== response.config.headers['auth0']) {
          console.log('server: ' + response.config.headers['auth0']);
          console.log('local: ' + window.sessionStorage.getItem('LOCAL_TOKEN_KEY'));
          window.sessionStorage.setItem('LOCAL_TOKEN_KEY', response.config.headers['auth0']);
        };
        
      };

      return $q.resolve(response);
    },
    responseError: function (response) {
      if (_.includes(response.config.url, 'rest')) {
        console.log('error server token: ' + response.config.headers['auth0']);
        console.log('error local token: ' + window.sessionStorage.getItem('LOCAL_TOKEN_KEY'));
      }
      $rootScope.$broadcast({
        401: AUTH_EVENTS.notAuthenticated,
        403: AUTH_EVENTS.notAuthorized
      }[response.status], response);
      return $q.reject(response);
    }
  };
}])

.config(function ($httpProvider) {
  $httpProvider.interceptors.push('AuthInterceptor');
});
