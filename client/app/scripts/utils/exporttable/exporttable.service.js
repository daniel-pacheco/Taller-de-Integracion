'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.exportTableService
 * @description
 * # exportTableService
 * Service in the clientAppApp.
 */
angular.module('clientAppApp')
  .service('exportTableService', ['$rootScope', function ($rootScope) {
    // AngularJS will instantiate a singleton by calling "new" on this function

    var fileType = 'excel';

    this.exportAction = function (id) {
        switch(fileType){ 
          // case 'pdf': $rootScope.$broadcast('export-pdf', {id:id}); 
          //             break;  // no instalado el plugin
          case 'excel': $rootScope.$broadcast('export-excel', {id:id}); 
          break; 
          case 'doc': $rootScope.$broadcast('export-doc', {id:id});
          break; 
          default: console.log('no event caught'); 
      }
    };

    this.setFileType = function (type) {
        fileType = type
    };
}]);