angular.module('clientAppApp')
.controller('showProfileAlumnoModalController', [
  '$element', '$scope', 'alumno','close', 'ModalService', 'title',
  function($element, $scope, alumno, close, ModalService, title) {//acá se inyecta las variables necesarias y luego la función close

    $scope.tooltip = {
      tooltipDetailsDom : {
        'title' : 'Domicilio detallado'
      }
    };

    $scope.confirm = confirm;

    $scope.confirmModal = function(mesagge, funcion, parametro) { //este confirm recibe una funcion y un parametro para que despues de confirmar se pueda llamar a la funcion que se necesite
      ModalService.showModal({
        templateUrl: 'scripts/utils/confirm/modalConfirm.tpl.html',
        controller: 'modalConfirmController',
        inputs: {
          mensaje: mesagge,
        }
      }).then(function(modal) {
        modal.element.modal();
        modal.close.then(function(result){
          funcion(parametro);
        });
      });
    };

    $scope.confirmReestablecerPass = function(){
      $scope.confirmModal('¿Desea reestablecer la contraseña de '+ $scope.alumno.nombre +' '+ $scope.alumno.apellido+'?', reestablecerPass, $scope.alumno.idAlumno);
    }

    function reestablecerPass(){

    }

    $scope.title = title;
    $scope.alumno = {}; 
  //$scope.dni = docente.nroDocumento;
  $scope.alumno = alumno;
  $scope.alumno.sexo = ($scope.alumno.sexo === 'M') ? 'Masculino' : 'Femenino';

  $scope.close = function() {
    close($scope.alumno, 500); 
  };

  $scope.cancel = function() {
    close('cancel', 500); 
  };

}]);