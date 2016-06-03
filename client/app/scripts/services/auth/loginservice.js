'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.loginService
 * @description
 * # loginService
 * Service in the clientAppApp.
 */
 angular.module('clientAppApp')
 .service('loginService', function ($q, $http, USER_ROLES, SERVER) {
  var isAuthenticated = false;
  var userName = '';
  var userRole = '';
  var authToken;
  
  function loadUserCredentials() {
    var token = window.sessionStorage.getItem('LOCAL_TOKEN_KEY');
    if (token) {
      var name = window.sessionStorage.getItem('USER_NAME');
      var rol = window.sessionStorage.getItem('USER_ROLE');
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
    userName = name;
    isAuthenticated = true;
    authToken = token;
    userRole = role; 
    // Set the token as header for your requests!
    $http.defaults.headers.common['auth0'] = token;//auth0 le dicen los pibes
    $http.defaults.headers.common['rol'] = role;
  }
  
  function destroyUserCredentials() {
    authToken = undefined;
    userName = '';
    userRole = '';
    isAuthenticated = false;
    $http.defaults.headers.common['auth0'] = undefined;
    $http.defaults.headers.common['rol'] = undefined;
    window.sessionStorage.removeItem('LOCAL_TOKEN_KEY');
    window.sessionStorage.removeItem('USER_NAME');
    window.sessionStorage.removeItem('USER_ROLE');
  }

  var login = function(name, pw, role) {
      return $http.post(SERVER.address + SERVER.login, [name, pw, role])
      .success(function(data,status,headers,config){
        console.log(headers('auth0'));
        storeUserCredentials(name, headers('auth0'), role);
      })
      .error(function(data, status, headers, config) {
        storeUserCredentials("", "", "ADMINISTRADOR");
        alert(status);
      })
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
    userName: function() {return window.sessionStorage.getItem('USER_NAME');},
    userRole: function() {return window.sessionStorage.getItem('USER_ROLE');},
    authToken: function() {return window.sessionStorage.getItem('LOCAL_TOKEN_KEY');}
  };
});
