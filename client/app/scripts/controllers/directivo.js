'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoCtrl
 * @description
 * # DirectivoCtrl
 * Controller of the clientAppApp
 */
angular.module('clientAppApp')
  .controller('DirectivoCtrl', function ($scope, $q, $http, $modal, alumnoService, modalService) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
$scope.listado1 = true;
$scope.listFilterIsEnabled = false;

//tooltips
$scope.tooltip = {
  tooltipProfile : {
    'title' : 'Perfil'
  }, tooltipAttendance : {
    'title' : 'Inasistencias'
  }, tooltipReportCard : {
    'title' : 'Libreta de Calificaciones'
  }, tooltipAcademicPerformance : {
    'title' : 'Desempeño académico'
  }
};

//modal

  //modal options 1
  $scope.modalx = {};
  $scope.modalx.controller =  'DirectivoCtrl';  
  $scope.modalx.title = 'title local directivo';
  $scope.modalx.content = 'content local directivo';
  $scope.modalx.templateUrl = 'views/templates/message.tpl.html';
  $scope.modalx.text = 'perooo, vo so loco vite?';
  $scope.modalx.show = false;

  //modal options 2
  $scope.modalxi = {};
  $scope.modalxi.controller =  'DirectivoCtrl';  
  $scope.modalxi.title = 'title local directivo cambiado';
  $scope.modalxi.content = 'content local directivo cambiado';
  $scope.modalxi.templateUrl = 'views/templates/message.tpl.html';
  $scope.modalxi.text = 'perooo, vo so loco vite?';
  $scope.modalxi.show = true;

  $scope.setM = modalService.setModal;

  $scope.showModal = modalService.showModal;
  $scope.hideModal = modalService.hideModal;

//filters
$scope.dropDownOptions = ['2014', '2015', '2016','DNI/MAT'];
$scope.dropDownValue = '';

$scope.dropDownCursoOptions = ['5 Mat', '4 Mat', '3 Mat', '2 Mat'];
$scope.dropDownCursoValue = '';

$scope.dropDownDivisionOptions = ['U', 'A', 'B', 'C'];
$scope.dropDownDivisionValue = '';

$scope.filterByName = '';

$scope.seleccionar = function (id){

	if (id === 'listado') {
		$scope.listado1 = true;
	}else if (id === 'nuevoPerfil'){
		$scope.listado1 = false;
	};
}

$scope.search = function () {
  if (!$scope.listFilterIsEnabled) {
    $scope.listFilterIsEnabled = true;
  };
}



//---Llamadas al servicio ALUMNO---
var alumnoJson = {  
  "idUsuario"       : null,
  "nroDocumento"    : 33333333,
  "tipoDocumento"   : "DNI",
  "nombre"          : "JoSe",
  "apellido"        : "Angular",
  "matricula"       : 33333333,
  "listaTelefonos" :[
    {
    "idTelefono"     : null,
    "caracteristica" : 343,
    "nroTelefono"    : 4232545,
    "tipoTelefono"   : "fijo/casa"
    },
    {
    "idTelefono"     : null,
    "caracteristica" : 343,
    "nroTelefono"    : 155423111,
    "tipoTelefono"   : "celular"
    }
  ],
  "listaMails"    :[
    {
    "idMail       " : null,
    "direccionMail" : "jmlinares_chiqui@hotmail.com",
    "tipoMail   " : "Personal"
    },
    {
    "idMail       " : null,
    "direccionMail" : "janetmlinares@gmail.com",
    "tipoMail   " : "Académico"
    }
  ],
  "domicilio"         :{
    "idDomicilio" : null,
    "calle"         : "Narciso Laprida",
    "numero"        : 1432,
    "piso"          : 0,
    "localidad"     : "Paraná",
    "dpto"          : "-",
    "departamento"  : "Paraná",
    "provincia"     : "Entre Ríos",
    "codigoPostal"   : 3100,
    "barrio"        : "Centro"
  },
  "sexo"            : "F",
  "nombreUsuario"   : "jose.angular",
  "fechaNacimiento" : "Dec 21, 1991 12:00:00 AM",
  "activo"          : true
  };

$scope.answer = [];
$scope.answer = [];
$scope.getAll = alumnoService.alumnoGetAll().then(function(response){
  $scope.answer = response.data;  
});

$scope.putAlumno = function () {
  alumnoService.alumnoPut(alumnoJson).then(function(response){
  $scope.postAnswer = response.data;
  $scope.getAll();
  })
};


$scope.deleteAlumno = function (id) {
  alumnoService.alumnoDel(id).then(function (response) {
    $scope.postAnswer = response.data;
    $scope.getAll();
  })
};


//---utils
$scope.update = function (variable, value) {
  variable = value;
}

//---test
$scope.friends = [{name:'John', phone:'555-1276'},
                         {name:'Mary', phone:'800-BIG-MARY'},
                         {name:'Mike', phone:'555-4321'},
                         {name:'Adam', phone:'555-5678'},
                         {name:'Julie', phone:'555-8765'},
                         {name:'Juliette', phone:'555-5678'}];

});
