'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.loginService
 * @description
 * # loginService
 * Service in the clientAppApp.
 */
 angular.module('clientAppApp')
 .service('loginService', ['$q', '$http', 'USER_ROLES', 'SERVER', function ($q, $http, USER_ROLES, SERVER) {
  var isAuthenticated = false;
  var userName = '';
  var userRole = '';
  var authToken;

  var server = SERVER.address;
  var sLogin = SERVER.login;
  var recoverPassword = 'recuperarClave/';
  var validateCred = 'validar/';
  var cambiarClave = 'cambiarClave/';
  
  function loadUserCredentials() {
    var token = window.sessionStorage.getItem('LOCAL_TOKEN_KEY');
    if (token) {
      var name = window.sessionStorage.getItem('USER_NAME');
      var rol = window.sessionStorage.getItem('USER_ROLE');
      useCredentials(name, token, rol);
    };
  };
  loadUserCredentials(); // caga las credenciales al reload browser
  
  function storeUserCredentials(name, token, role) {
    window.sessionStorage.setItem('USER_NAME', name);
    window.sessionStorage.setItem('LOCAL_TOKEN_KEY', token);
    window.sessionStorage.setItem('USER_ROLE', role);    
    
    // console.log(
    //   window.sessionStorage.getItem('USER_NAME') + ' ' +
    //   window.sessionStorage.getItem('LOCAL_TOKEN_KEY') + ' ' +
    //   window.sessionStorage.getItem('USER_ROLE')
    //   );

useCredentials(name, token, role);
};

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
    var deferred = $q.defer(); // retorna una promesa que se resolvera cuando responda el server

    $http.post(server + sLogin + 'login', [name, pw, role])
    .success(function(data, status, headers, config){
      // console.log(headers('auth0'));
      // console.log(status);
      storeUserCredentials(name, headers('auth0'), role);
      deferred.resolve('Success!');
    })
    .error(function(data, status, headers, config) {
      storeUserCredentials("", "", "ADMINISTRADOR");
      // console.log(status);
      deferred.reject('Error!');
    })
    .finally(function(){
    });
    return deferred.promise;
  };   

  var logout = function() {
    destroyUserCredentials();
  };

  var isAuthorized = function(authorizedRoles) {
    if (!angular.isArray(authorizedRoles)) {
      authorizedRoles = [authorizedRoles];
    }
    //return (isAuthenticated && authorizedRoles.indexOf(role) !== -1);
    return (getIsAutenticated() && _.includes(authorizedRoles, getUserRole()));
  };

  var validateCredentials = function() {
    var credentialsAreValid = false;

    $http.post(server + sLogin + validateCred)
    .success(function(data, status, headers, config){
      credentialsAreValid = true;
      if (getAuthToken() !== headers('auth0')) {
        storeUserCredentials(getUserName(), headers('auth0'), getUserRole());
      };
    })
    .error(function(data, status, headers, config){
      credentialsAreValid = false;
    })
    .finally(function(){
      return credentialsAreValid;
    });

  };  
  
  var changePassword = function(recoverData) {

    var credenciales = {
      dniUsuario: 0,
      rol: '',
      email: ''
    };

    credenciales.dniUsuario = recoverData.recoverUserName;
    credenciales.rol = recoverData.recoverRole;
    credenciales.email = recoverData.recoverMail;

    return $http.post(server + sLogin + recoverPassword, credenciales);
  };

  var cambiarPass = function(cambiarData) {
    return $http.post(server + sLogin + cambiarClave, cambiarData);
  };

  function getUserName() {
    return window.sessionStorage.getItem('USER_NAME');
  };

  function getUserRole() {
    return window.sessionStorage.getItem('USER_ROLE');
  };
  
  function getAuthToken() {
    return window.sessionStorage.getItem('LOCAL_TOKEN_KEY');
  };

  function getIsAutenticated() {
    return isAuthenticated;
  };

  return {
    cambiarPass : cambiarPass,
    changePassword: changePassword,
    login: login,
    logout: logout,
    isAuthorized: isAuthorized,
    isAuthenticated: getIsAutenticated,
    userName: getUserName,
    userRole: getUserRole,
    authToken: getAuthToken,
    validateCredentials: validateCredentials
  };

}]);
