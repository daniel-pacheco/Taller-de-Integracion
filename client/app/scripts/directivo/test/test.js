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
 .controller('DirectivoTestCtrl', ['$scope', '$timeout', 'loginService', 'ModalService', 'spinnerService', 'Upload', function ($scope, $timeout, loginService, ModalService, spinnerService, Upload) {
    this.awesomeThings = [
    'HTML5 Boilerplate',
    'AngularJS',
    'Karma'
    ];

// $scope.alumnoData = alumnoData;

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
$scope.toggle = true;


$scope.export_action = 'doc';
$scope.exportAction = function(){ 
      switch($scope.export_action){ 
          // case 'pdf': $scope.$broadcast('export-pdf', {}); // no instalado el plugin
          //             break;  
          case 'excel': $scope.$broadcast('export-excel', {}); 
                      break; 
          case 'doc': $scope.$broadcast('export-doc', {});
                      break; 
          default: console.log('no event caught'); 
       }
}

$scope.validCredentials = false;

$scope.validar = function() {
  $scope.validCredentials = loginService.validateCredentials();

};

$scope.metralleta = function(){
  var aux = [];
  for (var i = 0; i < 100; i++) {
    alumnoService.getAllMin()
    .then(function(response){
      console.log('alumno: '+ i + ' ');
      console.log(response.data);
      aux.push(response.data);
    },
    function(response){
      // alert('Se ha producido un error al intentar cotactar al servidor: ' + response.statusText);
      console.log('error en el alumno: ' + i);
    })
    .finally(function(){});

    academicoService.anioGetAllMin()
    .then(
      function(response){
        console.log('anio: '+ i + ' ');
        console.log(response.data);
        aux.push(response.data);
      },
      function(response){
        console.log('error en el anio: ' + i);
      })
    .finally(function(){
    });
  };
  console.log(aux);
};


}]);

