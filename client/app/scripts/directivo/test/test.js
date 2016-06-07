'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider) {
    $stateProvider
    .state('directivo.test', {
        url: '/test',
        templateUrl: 'scripts/directivo/test/test.html',
        controller: 'DirectivoTestCtrl',
        data: {
          pageTitle: 'About'
      }
  });
})
 .controller('DirectivoTestCtrl', function ($scope, Upload, $timeout, ModalService) {
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

$scope.requiredPass = function() {
    ModalService.showModal({
        templateUrl: 'scripts/utils/requiredPassword/modalRequiredPassword.tpl.html',
        controller: 'RequiredPasswordModalController',
        inputs: {
            title: "Ingrese su contraseña",
        }
    }).then(function(modal) {
        modal.element.modal();
        modal.close.then(function(result){
            alert(result);
        });
    });
};


$scope.date = new Date();
});
