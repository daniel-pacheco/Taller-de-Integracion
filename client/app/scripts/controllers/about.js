'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
  .controller('AboutCtrl', function ($scope, Upload, $timeout) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  

$scope.upload = function (dataUrl) {
        Upload.upload({
            url: 'https://angular-file-upload-cors-srv.appspot.com/upload',
            data: {
                file: Upload.dataUrltoBlob(dataUrl)
            },
        }).then(function (response) {
            $timeout(function () {
                $scope.result = response.data;
            });
        }, function (response) {
            if (response.status > 0) $scope.errorMsg = response.status 
                + ': ' + response.data;
        }, function (evt) {
            $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
        });
    }


  });
