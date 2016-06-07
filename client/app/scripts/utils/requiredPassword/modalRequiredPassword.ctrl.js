angular.module('clientAppApp')
.controller('RequiredPasswordModalController', [
	'$scope', '$element', 'title','close',
  function($scope, $element, title, close) {//acá se inyecta las variables necesarias y luego la función close

  	$scope.title = title;

  	$scope.close = function(password) {
  		close(password, 500); 
  	};

  	$scope.cancel = function() {
  		close('cancel', 500); 
  	};

  }]);