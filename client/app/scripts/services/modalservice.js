'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.modalService
 * @description
 * # modalService
 * Service in the clientAppApp.
 */
 angular.module('clientAppApp')
 .service('modalService', function ($modal) {

  var myModal = {};
  var myVar = {};

  this.set = function(myParameter){
    myVar = myParameter;
  }

  this.get = function(){
    return myVar;
  }

this.getRandomArbitrary = function(min, max) {
    return Math.random() * (max - min) + min;
}

this.getRandomInt = function(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

this.makeId = function(longitud)
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    for( var i=0; i < longitud; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}
 
this.aleatorio = function(){
  for (var i = 0; i < 10; i++) {
    console.log(getRandomInt(15, 30));
  };
  for (var i = 0; i < 10; i++) {
    console.log(getRandomArbitrary(1, 10));
  };
  for (var i = 0; i < 10; i++) {
    console.log(makeId(10));
  };
}

  /*
  
  $scope.modalx = {};
  $scope.modalx.controller =  'DirectivoCtrl';  
  $scope.modalx.title = 'title local directivo';
  $scope.modalx.content = 'content local directivo';
  $scope.modalx.templateUrl = 'views/templates/message.tpl.html';
  $scope.modalx.text = 'perooo, vo so loco vite?';
  $scope.modalx.show = false;

  */

  this.setModal = function(modalOptions){
    myModal = $modal(modalOptions);
  }

//  var myModal = $modal({controller: this.MyModalController, templateUrl: 'views/templates/messageregistrationdetails.tpl.html', show: false, animation: 'am-fade-and-slide-top'});
this.showModal = function() {
  myModal.$promise.then(myModal.show);
};

this.hideModal = function() {
  myModal.$promise.then(myModal.hide);
};

});
