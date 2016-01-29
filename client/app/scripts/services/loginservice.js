'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.loginService
 * @description
 * # loginService
 * Service in the clientAppApp.
 */
angular.module('clientAppApp')
  .service('loginService', function ($q, $http, USER_ROLES) {
  var LOCAL_TOKEN_KEY = 'yourTokenKey';
  var USER_ROLE = '';
  var USER_NAME = '';
  var isAuthenticated = false;
  var username = '';
  var userRole = '';
  var authToken;
 
  function loadUserCredentials() {
    var token = window.localStorage.getItem('LOCAL_TOKEN_KEY');
    if (token) {
      var name = window.localStorage.getItem('USER_NAME');
      var rol = window.localStorage.getItem('USER_ROLE');
      useCredentials(name, token, rol);
    }
  }
 
  function storeUserCredentials(name, token, role) {
    window.sessionStorage.setItem('USER_NAME', name);
    window.sessionStorage.setItem('LOCAL_TOKEN_KEY', token);
    window.sessionStorage.setItem('USER_ROLE', role);    
    
    console.log(
        window.sessionStorage.getItem('USER_NAME') + ' ' +
        window.sessionStorage.getItem('LOCAL_TOKEN_KEY') + ' ' +
        window.sessionStorage.getItem('USER_ROLE')
      );

    useCredentials(name, token, role);
  }
 
  function useCredentials(name, token, role) {
    username = name;
    isAuthenticated = true;
    authToken = token;
    userRole = role; 
    // Set the token as header for your requests!
    $http.defaults.headers.common['auth0'] = token;//auth0 le dicen los pibes
  }
 
  function destroyUserCredentials() {
    authToken = undefined;
    username = '';
    isAuthenticated = false;
    $http.defaults.headers.common['auth0'] = undefined;
    window.localStorage.removeItem('LOCAL_TOKEN_KEY');
  }
 
  var login = function(name, pw, role) {
    return $q(function(resolve, reject) {
      if ((name == 'admin' && pw == '1') || (name == 'user' && pw == '1')) {
        // Make a request and receive your auth token from your server
        storeUserCredentials(name, 'yourServerToken', role);
        resolve('Login success.');
      } else {
        reject('Login Failed.');
      }
    });
  };
 
  var logout = function() {
    destroyUserCredentials();
  };
 
  var isAuthorized = function(authorizedRoles) {
    if (!angular.isArray(authorizedRoles)) {
      authorizedRoles = [authorizedRoles];
    }
    //return (isAuthenticated && authorizedRoles.indexOf(role) !== -1);
    return (isAuthenticated && authorizedRoles == userRole);
  };
 
  loadUserCredentials();
 
  return {
    login: login,
    logout: logout,
    isAuthorized: isAuthorized,
    isAuthenticated: function() {return isAuthenticated;},
    username: function() {return username;},
    userRole: function() {return userRole;}
  };
});
