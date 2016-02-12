'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DirectivoCtrl
 * @description
 * # DirectivoCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('alumnado', {
 		url: '/alumnado',
 		templateUrl: 'views/alumnado.html',
 		controller: 'AlumnadoCtrl',
 		data: {
 			pageTitle: 'Alumnado'
 		}
 	});
 })
 .controller('AlumnadoCtrl', function ($scope, $q, $http, $modal, alumnoService, modalService, Upload, $timeout, alumnoData) {
 	$scope.listado1 = true;
 	$scope.listFilterIsEnabled = false;

 	$scope.nuevoAlumno = {
    "listaTelefonos": [],
    "listaMails": []
  }

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
	$scope.modalx = {}; //inicializa un objeto
	$scope.modalx.controller =  'AlumnadoCtrl';  //nombre del controller que va a llamar al modal
	$scope.modalx.title = 'title local directivo'; // titulo del modal (accede directo)
	$scope.modalx.content = 'content local directivo'; //contenido del modal (accede directo)
	$scope.modalx.templateUrl = 'views/templates/message.tpl.html'; //plantilla del modal
	$scope.modalx.show = false; //si el modal se va a mosrtar al configurarlo o si habrá que llamarlo (por defecto false, hay q llamarlo)
	$scope.modalx.text = 'perooo, vo so loco vite?'; //contenido extra del modal (se peude poner el nombre que quiera, no accede directo)

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

//File-Select

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

//filters

$scope.alumnoFilter = function (alumno) {//la clave de este comparador es q transofrma todo a string y va comparando las posiciones, no tiene en cuenta los espacios
        return (angular.lowercase(alumno.apellido).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||
                angular.lowercase(alumno.nombre).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||
                angular.lowercase(alumno.nroDocumento.toString()).indexOf(angular.lowercase($scope.filterByName) || '') !== -1
                );
    };

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
	this.showData();
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

$scope.showData = function() {
$scope.alumnoData = alumnoData;
}

/*$scope.getAll = alumnoService.alumnoGetAll().then(function(response){
	$scope.answer = response.data;  
});*/

$scope.getAlumnoById = function (id) {
	alumnoService.alumnoGetById(id)
		.then(function(response){
			$scope.postAnswer = response.data;	
		})
}

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

//---Auth Test
$scope.logout = function() {
    AuthService.logout();
    $state.go('login');
  };
 
  $scope.performValidRequest = function() {
    $http.get('http://localhost:8100/valid').then(
      function(result) {
        $scope.response = result;
      });
  };
 
  $scope.performUnauthorizedRequest = function() {
    $http.get('http://localhost:8100/notauthorized').then(
      function(result) {
        // No result here..
      }, function(err) {
        $scope.response = err;
      });
  };
 
  $scope.performInvalidRequest = function() {
    $http.get('http://localhost:8100/notauthenticated').then(
      function(result) {
        // No result here..
      }, function(err) {
        $scope.response = err;
      });
  };

//---test
$scope.friends = [{name:'John', phone:'555-1276'},
{name:'Mary', phone:'800-BIG-MARY'},
{name:'Mike', phone:'555-4321'},
{name:'Adam', phone:'555-5678'},
{name:'Julie', phone:'555-8765'},
{name:'Juliette', phone:'555-5678'}];

});
