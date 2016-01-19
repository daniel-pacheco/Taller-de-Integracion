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
