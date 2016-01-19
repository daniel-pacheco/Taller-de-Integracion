'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.loginService
 * @description
 * # loginService
 * Service in the clientAppApp.
 */
angular.module('clientAppApp')
  .service('loginService', function () {
    // AngularJS will instantiate a singleton by calling "new" on this function
    this.login = function(data){
    	alert('usuario: ' + data.username + ' password: ' + data.password + ' USER_ROLE: ' + data.role);
    }
  });
