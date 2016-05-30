'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DocenteCtrl
 * @description
 * # DocenteCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')

 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.docente', {
 		url: '/docente',
 		templateUrl: 'scripts/directivo/docente/docente.html',
 		controller: 'DocenteCtrl',
 		data: {
 			pageTitle: 'Docente'
 		}
 	});
 })
 .controller('DocenteCtrl', function ($scope, docenteData, $timeout, ModalService, SERVER, ObjectsFactory, $alert) {

  $scope.tooltip = {
    tooltipProfile : {
      'title' : 'Perfil'
    },
    tooltipDelete : {
      'title' : 'Eliminar'
    },    
    tooltipEdit : {
      'title' : 'Editar'
    },
  };


$scope.subtitle = "Nuevo Docente"
  $scope.dropDownAreas = ['Cs. Sociales', 'Cs. Naturales', 'Cs. Exactas','Artes'];

  $scope.nuevoDocente = ObjectsFactory.newDocente();

  $scope.clearFormDoc = function(){
    $scope.formDoc.$setUntouched();
    $scope.nuevoDocente = ObjectsFactory.newDocente();

  }



//-- Modal
 $scope.showModalProfile = function(docente) {

    ModalService.showModal({
      templateUrl: 'scripts/directivo/docente/modal/showProfileDocente.tpl.html',
      controller: 'showProfileDocenteModalController',
      inputs: {
        title: "Perfil",
        docente: docente
      }
    }).then(function(modal) {
      modal.element.modal();
      modal.close.then(function(result) {        
        $scope.editProfile(result);
      });
    });

  }; 

$scope.domicilioAvanzado = function() {
  //$scope.nuevoDocente.domicilio = ObjectsFactory.newDomicilio();
  ModalService.showModal({
    templateUrl: 'scripts/directivo/docente/modal/addaddressdetails.tpl.html',
    controller: 'addAddressDetailsModalController',
    inputs: {
      title: "Nuevo Domicilio",
      domicilioAvanzado: $scope.nuevoDocente.domicilio,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      $scope.nuevoDocente.domicilio = result;
    });
  });
};

$scope.telefonoAvanzado = function(){//esta deberia ser una funcion que pida la libreta de inasistencias del alumno que recibe
  ModalService.showModal({
    templateUrl: 'scripts/directivo/docente/modal/addphonedetails.tpl.html',
    controller: 'telefonoAvanzadoModalController',
    inputs: {
      title: "Teléfono",
      listaTelefonos: $scope.nuevoDocente.listaTelefonos,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      $scope.nuevoDocente.listaTelefonos = result;
      if ($scope.nuevoDocente.listaTelefonos.length > 0){//Esto es para listar los telefonos en una lista en el form principal 
        $scope.mostrarListaTelefonos = true;
      }else{ 
        $scope.mostrarListaTelefonos = false;
      }
    });
  });
};

$scope.mailAvanzado = function(){
  ModalService.showModal({
    templateUrl: 'scripts/directivo/docente/modal/addmaildetails.tpl.html',
    controller: 'mailAvanzadoModalController',
    inputs: {
      title: "Mails",
      listaMails: $scope.nuevoDocente.listaMails,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      $scope.nuevoDocente.listaMails = result;
      if ($scope.nuevoDocente.listaMails.length > 0){//Esto es para listar los telefonos en una lista en el form principal 
        $scope.mostrarListaMails = true;
      }else{ 
        $scope.mostrarListaMails = false;
      }
    });
  });
};

$scope.tituloAvanzado = function(){
  ModalService.showModal({
    templateUrl: 'scripts/directivo/docente/modal/addtitulodetails.tpl.html',
    controller: 'tituloAvanzadoModalController',
    inputs: {
      title: "Títulos",
      listaTitulos: $scope.nuevoDocente.listaTitulos,
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      $scope.nuevoDocente.listaTitulos = result;
      if ($scope.nuevoDocente.listaTitulos.length > 0){//Esto es para listar los telefonos en una lista en el form principal 
        $scope.mostrarListaTitulos = true;
      }else{ 
        $scope.mostrarListaTitulos = false;
      }
    });
  });
};

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

//-- fin Modal

//-- Llamadas al servicio
$scope.deleteDocente = function (docente) {
  $scope.confirmModal("¿Desea eliminar al docente"+docente.nombre+" "+docente.apellido+"?", $scope.eliminarDocente, docente);
  //Hay que actualizar de nuevo la lista de docentes
};
//esto tiene que ser una llamada al service que elimine el docente
$scope.eliminarDocente = function(docente){
  $scope.docentes.splice($scope.docentes.indexOf(docente),1);
  $scope.showAlert();
};
//-- fin Llamadas al servicio

//-- Alert

$scope.showAlert = function() {
  //la idea seria poder mandarle el mensaje a la variable pero por ahora solo tenemos una variable por alert
  eliminarDocenteAlert.show();
};

var eliminarDocenteAlert = $alert({
  title: 'Mensaje:', 
  placement: 'top',
  content: 'Docente eliminado con éxito', 
  type: 'info', 
  keyboard: true, 
  show: false,
  duration: 3,
  container: '#alerta'
});
//-- Fin Alert
//-- filters
$scope.saveDocente = function (){
console.log ($scope.nuevoDocente);
};

$scope.editProfile = function(docente) {
  $scope.listado = false;
  $scope.subtitle = "Editar Docente"
  $scope.nuevoDocente = angular.copy(docente);
  $scope.showEditProfileMenuIzq = true;
  $scope.setActiveDoc(3); //muestra en el menu izq editar perfil
   if ($scope.nuevoDocente.listaMails.length > 0){//Esto es para listar los telefonos en una lista en el form principal 
    $scope.mostrarListaMails = true;
  }else{ 
    $scope.mostrarListaMails = false;
  }
  if ($scope.nuevoDocente.listaTelefonos.length > 0){//Esto es para listar los telefonos en una lista en el form principal 
    $scope.mostrarListaTelefonos = true;
  }else{ 
    $scope.mostrarListaTelefonos = false;
  }
  if ($scope.nuevoDocente.listaTitulos.length > 0){//Esto es para listar los telefonos en una lista en el form principal 
    $scope.mostrarListaTitulos = true;
  }else{ 
    $scope.mostrarListaTitulos = false;
  }
  $scope.nuevoPerfil = true;
}
  $scope.listado = true;
  $scope.seleccionar = function(id){
   $scope.listado = false;
   $scope.nuevoPerfil = false;

   switch (id) {
    case 'listado':
    $scope.listado = true;
    this.showData();
    $scope.nuevoDocente = null;
    break;
    case 'nuevoPerfil':
    $scope.nuevoPerfil = true;
    $scope.subtitle = "Nuevo Docente"
    //$scope.nuevoDocente = ObjectsFactory.newDocente();
    break;
  }
};
$scope.docenteFilter = function (docente) {//la clave de este comparador es q transofrma todo a string y va comparando las posiciones, no tiene en cuenta los espacios
  return (/*angular.lowercase(docente.materia).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||*/
    angular.lowercase(docente.nombre).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||
    angular.lowercase(docente.apellido).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||
    angular.lowercase(docente.cuil.toString()).indexOf(angular.lowercase($scope.filterByName) || '') !== -1
    );
};
$scope.showData = function() {
  $scope.docentes = docenteData;
}
$scope.showData();
$scope.activeMenuIzqDoc = 1;
$scope.setActiveDoc = function(menuItemDoc) {
  $scope.activeMenuIzqDoc = menuItemDoc;
};
    /*$scope.docentes = [{name:'John', surname:'lenono', area:'Cs. Sociales', cuil:'252525', materia:'Historia'},
{name:'Mary', surname:'yein', area:'Cs. Naturales', cuil:'434343', materia:'Biologia' },
{name:'Mike', surname:'chumajer', area:'Cs. Sociales', cuil:'111111', materia:'Geografia'},
{name:'Adam', surname:'sami', area:'Artes', cuil:'7777777', materia:'Plastica'},
{name:'Julie', surname:'rose', area:'Cs. Sociales', cuil:'000000', materia:'peperoni'},
{name:'Juliette', surname:'romeo', area:'Cs. Exactas', cuil:'929225', materia:'Fisica'}];*/
});
