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
 .controller('DirectivoTestCtrl', function ($scope, Upload, $timeout, ModalService, spinnerService) {
    this.awesomeThings = [
    'HTML5 Boilerplate',
    'AngularJS',
    'Karma'
    ];

//     $scope.spinnerService = spinnerService;

//     var myEl = angular.element('#myNav');//angular.element( document.querySelector( '#myNav' ) );
//     console.log(myEl);

//     $scope.openNav = function () {
//         // $scope.spinnerService.show('spinner6');
//     var elementbyid = document.getElementById("#myNav");//.style.width = "100%";

//     console.log(elementbyid);

//     myEl.webkitIsFullScreen = true;
// };

// $scope.closeNav = function () {
//     // myEl.width = "0%";
//     $('#myNav').width = "100%";
//     // document.getElementById("#myNav").style.width = "0%";
//     // $scope.spinnerService.hide('spinner6');
// };

$scope.login = function () {
    spinnerService.show('html5spinner');
    $timeout(function () {
        spinnerService.hide('html5spinner');
        $scope.loggedIn = true;
    }, 9999999999);
};


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
            title: "Confirmar contraseña",
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
