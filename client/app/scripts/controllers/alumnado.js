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
  .directive('loading', function () {
      return {
        restrict: 'E',
        replace:true,
        template: '<div class="loading"><img src="http://www.nasa.gov/multimedia/videogallery/ajax-loader.gif" width="20" height="20" />LOADING...</div>',
        link: function (scope, element, attr) {
              scope.$watch('loading', function (val) {
                  if (val)
                      $(element).show();
                  else
                      $(element).hide();
              });
        }
      }
  })
 .controller('AlumnadoCtrl', function ($scope, $q, $http, $modal, alumnoService, Upload, $timeout, alumnoData) {
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

//modals
	
$scope.modalx = {}; //inicializa un objeto para pasar data al modal
var myModal = {};
  
  $scope.showAddresModal = function() {
  	myModal =  $modal({
  		controller: 'AlumnadoCtrl', 
  		title: 'Nuevo Domicilio', 
  		content: 'Opciones Avanzadas', 
  		templateUrl: '/views/templates/addaddressdetails.tpl.html', 
  		show: false
  	});

    myModal.$promise.then(myModal.show);
  };

$scope.modalx.addressData = {};
$scope.modalx.addressData.calle = "";
$scope.afsdkjakfshkasjfha = 'hola che todo bien?';


	$scope.telefonoAvanzado = function () {
		
		myModal =  $modal({
  		controller: 'AlumnadoCtrl', 
  		title: 'Teléfonos', 
  		content: 'Opciones Avanzadas', 
  		templateUrl: '/views/templates/addphonedetails.tpl.html', 
  		show: false
  	});
	myModal.$promise.then(myModal.show);		 
	}
	

	$scope.modalx = {}; //inicializa un objeto para pasar data al modal
$scope.showProfile = function(alumno){
  var myModal1 = {};
  myModal1 = $modal({
    controller: 'AlumnadoCtrl', 
    title: 'Perfil', 
    content: 'Detalles del perfil', 
    templateUrl: '/views/templates/showProfileAlumno.tpl.html', 
    show: false
  })

  myModal1.$promise.then(myModal1.show);
};


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
};

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

$scope.activeMenuIzqAlu = 1;
$scope.setActiveAlu = function(menuItemAlu) {
	$scope.activeMenuIzqAlu = menuItemAlu;
};


$scope.subtitle = "Nuevo Alumno"

/*
$scope.alumnoEdit = null;
$scope.editProfile = function(alumno) {
  $scope.listado = false;
  $scope.subtitle = "Editar Alumno"
  $scope.nuevoPerfil = true;
  $scope.alumnoEdit = alumno;
}*/


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
 $scope.alumnoData = [];
$scope.showData = function() {
	 $scope.loading = true;
	  /*$http.get(alumnoData) esto es para que muestre el loading mientras carga, el http da 404 xq no tengo servidor
	   .success(function(data) {
           $scope.alumnoData = data[0].alumnoData;
            $scope.loading = false;
        });*/
			$scope.alumnoData = alumnoData;
			$scope.loading = false;

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
