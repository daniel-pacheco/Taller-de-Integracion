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
 // .controller('DocenteCtrl', function ($scope, docenteData, $timeout, ModalService, SERVER, ObjectsFactory, $alert, docenteService, alumnoService, directivoService, modalService, spinnerService, exportTableService, academicoService) {
.controller('DocenteCtrl',['$scope', 'academicoService', 'alumnoService', 'directivoService', 'docenteService', 'exportTableService', 'ModalService', 'ObjectsFactory', 'SERVER', 'spinnerService' , function ($scope, academicoService, alumnoService, directivoService, docenteService, exportTableService, ModalService, ObjectsFactory, SERVER, spinnerService) {
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
    tooltipExport: {
      'title': 'Exportar para impresión'
    },
    tooltipDesasignar: {
      'title': 'Desasignar docente de materia'
    }
  };


//-- [docente]
//-- [docente] variables

$scope.button = {radio: 'doc'};
var service = {};

//-- [docente] Form Management

// $scope.activeMenuIzqDoc = 1;
function setActiveDoc(menuItemDoc) {
  $scope.activeMenuIzqDoc = menuItemDoc;
};

// $scope.listado = true;
$scope.seleccionar = function(id){
  $scope.listado = false;
  $scope.nuevoPerfil = false;
  $scope.showEditProfileMenuIzq = false;

  switch (id) {
    case 'listado':
    $scope.listado = true;
    setActiveDoc(1);
    // $scope.search($scope.button.radio);
    break;
    case 'nuevoPerfil':
    $scope.nuevoPerfil = true;
    $scope.subtitle = 'Alta Personal';
    setActiveDoc(2);
    $scope.clearFormDoc();
    break;
    case 'editDocente':
    $scope.subtitle = "Editar Personal";
    $scope.showEditProfileMenuIzq = true;
    setActiveDoc(3); //muestra en el menu izq editar perfil
    $scope.nuevoPerfil = true;
    break;
  }
};
$scope.seleccionar('listado');

$scope.editProfile = function(docente) {  
  // $scope.nuevoDocente = angular.copy(docente);
  $scope.nuevoDocente = _.clone(docente);
  $scope.nuevoDocente.fechaNacimiento = new Date(docente.fechaNacimiento);
  $scope.seleccionar('editDocente');
  $scope.mostrarListaMails = $scope.nuevoDocente.listaMails.length > 0? true: false;
  $scope.mostrarListaTelefonos = $scope.nuevoDocente.listaTelefonos.length > 0? true: false;
  $scope.mostrarListaTitulos = $scope.nuevoDocente.listaTitulos.length > 0? true: false;
  
};

$scope.clearFormDoc = function(){
  $scope.formDoc.$setUntouched();
  $scope.mostrarListaTelefonos = false;
  $scope.mostrarListaMails = false;
  $scope.mostrarListaTitulos = false;
  $scope.nuevoDocente = ObjectsFactory.newDocente();
  $scope.nuevoDocente.domicilio = ObjectsFactory.newDomicilio();
  $scope.nuevoTelefonoSimple = ObjectsFactory.newTelefono();
  $scope.nuevoMailSimple = ObjectsFactory.newMail();
  $scope.nuevoTituloSimple = ObjectsFactory.newTitulo();
  $scope.cuilHead = '';
  $scope.cuilTail = '';
};



//-- [docente] filters

$scope.docenteFilter = function (docente) {
  return(_.includes(_.lowerCase(docente.apellido), _.lowerCase($scope.filterByName) || '') ||
   _.includes(_.lowerCase(docente.nombre), _.lowerCase($scope.filterByName) || '') ||
   _.includes(_.lowerCase(docente.nroDocumento), _.lowerCase($scope.filterByName) || '')
   );
};

//-- [docente] modals

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

//-- [docente] utils

$scope.showMessage = function(mesagge, title, isGood) { //todo ok recibe true si salio bien o false si salio mal
  ModalService.showModal({
    templateUrl: 'scripts/utils/showMessage/modalMessage.tpl.html',
    controller: 'modalMessageController',
    inputs: {
      mensaje: mesagge,
      title: title,
      isGood: isGood
    }
  }).then(function(modal) {
    modal.element.modal();
  });
};

function showServerError (response){
  console.log(response);
  var msg = '';

  if (response.statusText) {
    msg = response.statusText;
  };

  if (response.data) {
    msg += ' - ' + response.data.mensaje + ' ' + response.data.severidad;
  };      
  $scope.showMessage(msg, 'Error al contactar al servidor' , false);
};

function showServerSuccess (message, response){
  console.log(response);
  var msg = message;

  if ( response && response.data) {
    msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};

//-- Export Table
$scope.exportAction = function(id){ 
  exportTableService.exportAction(id);
}

//-- [docente] service calls

//-------------------------------

// $scope.subtitle = "Nuevo Docente"
$scope.dropDownAreas = ['Cs. Sociales', 'Cs. Naturales', 'Cs. Exactas','Artes'];

//-- [docente/listado]
//-- [docente/listado] variables

$scope.docenteData = [];

$scope.nuevoDocente = ObjectsFactory.newDocente();
$scope.nuevoTelefonoSimple = ObjectsFactory.newTelefono();
$scope.cuilHead = '';
$scope.cuilTail = '';

//-- [docente/listado] Form Management
//-- [docente/listado] filters

//-- Order list
$scope.predicate = 'nombre';
$scope.reverse = true;
$scope.orderDocente = function(predicate) {
  $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
  $scope.predicate = predicate;
};

//-- [docente/listado] modals

$scope.showModalProfile = function(personalDni) {

  var docente = {};

  service.getByDni(personalDni)
  .then(function(response){
    docente = response.data;

    ModalService.showModal({
      templateUrl: 'scripts/directivo/docente/modal/showProfileDocente.tpl.html',
      controller: 'showProfileDocenteModalController',
      inputs: {
        title: "Perfil",
        docente: docente
      }
    }).then(
    function(modal) {
      modal.element.modal();
      modal.close.then(
        function(result) {        
          $scope.editProfile(result);
        });
    });
  },
  function(response){
    showServerError(response);
  });
};

//-- [docente/listado] utils
//-- [docente/listado] service calls


$scope.selectSource = function(origin){
  $scope.button.radio = origin;
  service = origin == 'doc'? docenteService: directivoService;
  $scope.search();
};
$scope.$on('$viewContentLoaded', function(){
  $scope.selectSource('doc');
});

$scope.search = function (param) {

  spinnerService.show('searchDocenteSpinner');
  service.getAllMin()
  .then(function(response){
    $scope.docenteData = response.data;
  },
  function(response){
   showServerError(response);
 })
  .finally(function(){
    spinnerService.hide('searchDocenteSpinner');
  });
};

$scope.comfirmDesvincularDocente = function(idDocente, condicion, idMateria){
  var params = {
    'idDocente': idDocente,
    'condicion': condicion,
    'idMateria': idMateria
  };

  $scope.confirmModal('¿Desea desvincular al docente ' + condicion + ' de la materia? ', $scope.desvincularDocente, params);

};

$scope.desvincularDocente = function(params){
  var desvincularParams = [];

  desvincularParams.push( params.condicion == 'Titular'? params.idDocente: null );
  desvincularParams.push( params.condicion == 'Suplente'? params.idDocente: null );
  desvincularParams.push( params.idMateria );

  spinnerService.show('searchDocenteSpinner');
  academicoService.matDesvin(desvincularParams)
  .then(
    function(response){
      showServerSuccess('El docente se ha desvinculado con éxito ', response);
      $scope.search();
    },
    function(response){
      showServerError(response);
    })
  .finally(function(){
    spinnerService.hide('searchDocenteSpinner');
  });
};

//-------------------------------------------------------

//-- [docente/nuevo-personal]
//-- [docente/nuevo-personal] variables
//-- [docente/nuevo-personal] Form Management
//-- [docente/nuevo-personal] filters
//-- [docente/nuevo-personal] modals

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

$scope.telefonoAvanzado = function(){//esta deberia ser una funcion que pida la libreta de inasistencias del docente que recibe
  ModalService.showModal({
    templateUrl: 'scripts/directivo/docente/modal/addphonedetails.tpl.html',
    controller: 'telefonoAvanzadoDocenteModalController',
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
    controller: 'mailAvanzadoDocenteModalController',
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

//-- [docente/nuevo-personal] utils
//-- [docente/nuevo-personal] service calls

$scope.requiredPassPutDirectivo = function(docente) {
  ModalService.showModal({
    templateUrl: 'scripts/utils/requiredPassword/modalRequiredPassword.tpl.html',
    controller: 'RequiredPasswordModalController',
    inputs: {
      title: "Confirmar contraseña",
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      alumnoService.getById(result) //llamada a validar el DNI
      .then(function(response){
        // spinnerService.show('searchDocenteSpinner');
        console.log(response);
        putNewDirectivo(docente);
      }, function(response){
        showServerError(response)
        console.log(response);
      })
      .finally(function(){
        // spinnerService.hide('searchDocenteSpinner');
      }); 
    });
  });
};

$scope.requiredPassDeleteDirectivo = function(docente) {
  ModalService.showModal({
    templateUrl: 'scripts/utils/requiredPassword/modalRequiredPassword.tpl.html',
    controller: 'RequiredPasswordModalController',
    inputs: {
      title: "Confirmar contraseña",
    }
  }).then(function(modal) {
    modal.element.modal();
    modal.close.then(function(result){
      alumnoService.getById(result) //llamada a validar el DNI
      .then(function(response){
        // spinnerService.show('searchDocenteSpinner');
        console.log(response);
        eliminarDirectivo(docente);
      }, function(response){
        showServerError(response);
        console.log(response);
      })
      .finally(function(){
        // spinnerService.hide('searchDocenteSpinner');
      }); 
    });
  });
};

//-- fin Modal

//-- Llamadas al servicio
$scope.deleteDocente = function (docente) {
  $scope.deleteFunction = $scope.button.radio == 'doc'? eliminarDocente: $scope.requiredPassDeleteDirectivo;
  var tipoPersonal = $scope.button.radio == 'doc'? 'docente': 'directivo';
  $scope.confirmModal('¿Desea eliminar al ' + tipoPersonal + ' ' + docente.nombre + ' ' + docente.apellido + '? ', $scope.deleteFunction, docente);
  // $scope.requiredPassDeleteDirectivo();
  //Hay que actualizar de nuevo la lista de docentes
};

//esto tiene que ser una llamada al service que elimine el docente
function eliminarDocente (docente){
  if (docente.listaMaterias) {
    $scope.showMessage('El docente no debe materias a cargo para poder ser eliminado.', 'ERROR!', false);
  } else{
    spinnerService.show('searchDocenteSpinner');
    docenteService.delByDni(docente.nroDocumento)
    .then(function(response){
      showServerSuccess('docente con dni: ' + docente.nroDocumento + ' eliminado con exito. ', response);
      $scope.search();
    },function(response){
      showServerError(response);
    })
    .finally(function(){
      spinnerService.hide('searchDocenteSpinner');
    });
  };
};

  function eliminarDirectivo (docente){

    spinnerService.show('searchDocenteSpinner');
    directivoService.delByDni(docente.nroDocumento)
    .then(function(response){
      showServerSuccess('directivo con dni: ' + docente.nroDocumento + ' eliminado con exito. ', response);
      $scope.search();
    },function(response){
      showServerError(response);
    })
    .finally(function(){
      spinnerService.hide('searchDocenteSpinner');
    });
};

$scope.newDocente = function(personal){
  personal.nombreUsuario = ModalService.makeId(5);
  personal.cuil = $scope.cuilHead + personal.nroDocumento + $scope.cuilTail;
  if ($scope.nuevoTelefonoSimple.nroTelefono && !_.includes(personal.listaTelefonos, $scope.nuevoTelefonoSimple)) { //solo hace el pushsi el telefono no se encuentra en la lista
    personal.listaTelefonos.push($scope.nuevoTelefonoSimple);
  };

  if ($scope.nuevoMailSimple.direccionMail && !_.includes(personal.listaMails, $scope.nuevoMailSimple)) { //solo hace el pushsi el Mail no se encuentra en la lista
    personal.listaMails.push($scope.nuevoMailSimple);
  };

  if ($scope.nuevoTituloSimple.nombreTitulo && !_.includes(personal.listaTitulos, $scope.nuevoTituloSimple)) { //solo hace el pushsi el titulo no se encuentra en la lista
    personal.listaTitulos.push($scope.nuevoTituloSimple);
  };

  if (personal.rolDirectivo) {
    $scope.requiredPassPutDirectivo(personal);
  } else {
    putNewDocente(personal);
  };
};

var putNewDocente = function (docente){
  spinnerService.show('putPersonalSpinner');
  docenteService.putNew(docente)
  .then(function(response){
    $scope.clearFormDoc();
    showServerSuccess('El docente se ha dado de alta con éxito. ID n°: ', response);
  },
  function(response){
    showServerError(response);
  })
  .finally(function(){
    spinnerService.hide('putPersonalSpinner');
  });
};

var putNewDirectivo = function (directivo){
  console.log ('putDirectivo');
  spinnerService.show('searchDocenteSpinner');
  directivoService.putNew(directivo)
  .then(function(response){
    console.log(response);
    showServerSuccess('El directivo se ha dado de alta con éxito. ID n°: ', response);
    $scope.clearFormDoc();
  },
  function(response){
    showServerError(response);
  })
  .finally(function(){
    spinnerService.hide('searchDocenteSpinner');
  });
};

//-----------------------------------------

}]);
