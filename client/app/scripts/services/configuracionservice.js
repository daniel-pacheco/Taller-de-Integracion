'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.configuracionService
 * @description
 * # configuracionService
 * Service in the clientAppApp.
 */
 angular.module('clientAppApp')
 .service('configuracionService', ['$http', 'SERVER', function ($http, SERVER) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var server = SERVER.address;
    var sConfiguracion = SERVER.sConfiguracion;

    var putAllConfig = 'parametro/addAll';

    var getParametroConfig = 'parametro/';
    var parametroConfig = 'parametro/';
    var getAllParametroConfig = 'parametroListAll/';

    this.configPutAll = function (arrayConfigObj) {
        return $http.put(server + sConfiguracion + putAllConfig, arrayConfigObj);
    };

    this.configPut = function (configObj) {
        return $http.put(server + sConfiguracion + parametroConfig, configObj);
    }; 

    this.getParametroConfigAll = function () {
        return $http.get(server + sConfiguracion + getAllParametroConfig);
    };

    this.getParametroConfigById = function (idConfig) {
        return $http.get(server + sConfiguracion + getParametroConfig + idConfig);
    };

}]);