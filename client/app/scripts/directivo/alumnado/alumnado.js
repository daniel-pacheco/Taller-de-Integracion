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
 	.state('directivo.alumnado', {
 		url: '/alumnado',
 		templateUrl: 'scripts/directivo/alumnado/alumnado.html',
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
 .controller('AlumnadoCtrl', function ($scope, $q, $http, boletinInasistenciasData, $alert, libCalificacionesdata, plantillaTrimestralData, alumnoService, Upload, $timeout, alumnoData, ModalService, ObjectsFactory) {
 	$scope.listado = true;
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
	}, tooltipEdit : {
		'title' : 'Editar'
	}, tooltipDelete : {
		'title' : 'Eliminar'
	}
};

//modals
$scope.domicilioAvanzado = function() {
	ModalService.showModal({
		templateUrl: 'scripts/directivo/alumnado/modal/addaddressdetails.tpl.html',
		controller: 'addAddressDetailsModalController',
		inputs: {
			title: "Nuevo Domicilio",
			domicilioAvanzado: $scope.alumno.domicilio,

		}
	}).then(function(modal) {
		modal.element.modal();
		modal.close.then(function(result){
			$scope.alumno.domicilio = result;
			$scope.alumnoDomicilioLight = result.calle+" "+result.numero; 
		});
	});
};

$scope.alumno = ObjectsFactory.newAlumno();
$scope.alumno.domicilio = ObjectsFactory.newDomicilio();

	$scope.telefonoAvanzado = function(){//esta deberia ser una funcion que pida la libreta de inasistencias del alumno que recibe
		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/addphonedetails.tpl.html',
			controller: 'telefonoAvanzadoModalController',
			inputs: {
				title: "Teléfono",
				listaTelefonos: $scope.alumno.listaTelefonos,
				ObjectsFactory: ObjectsFactory,
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result){
				$scope.alumno.listaTelefonos = result;
			});
		});

	}; 

	$scope.mailAvanzado = function(){
		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/addmaildetails.tpl.html',
			controller: 'mailAvanzadoModalController',
			inputs: {
				title: "Mails",
				listaMails: $scope.alumno.listaMails,
				ObjectsFactory: ObjectsFactory,
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result){
				$scope.alumno.listaMails = result;
			});
		});
	};

	$scope.showModalProfile = function(alumno){
		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/showProfileAlumno.tpl.html',
			controller: 'showProfileAlumnoModalController',
			inputs: {
				title: "Perfil",
				alumno: alumno
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result) {        
        $scope.editProfile(result); //$scope.algo.nroDocumento = result;
    });
		});

	}; 

	$scope.showModalInasistencias = function(alumno){//esta deberia ser una funcion que pida la libreta de inasistencias del alumno que recibe
		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/showInasistenciasAlumno.tpl.html',
			controller: 'showInasistenciasModalController',
			inputs: {
				title: "Boletín de inasistencias",
				libInasistencias: boletinInasistenciasData,
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result){
				boletinInasistenciasData = result;//llamada al servicio para update en la BD
			});
		});

	}; 

	$scope.showModalLibreta = function(alumno){//esta deberia ser una funcion que pida la libreta de calificaciones del alumno que recibe
		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/showLibretaAlumno.tpl.html',
			controller: 'showLibretaAlumnoModalController',
			inputs: {
				title: "Libreta de calificaciones",
				libCalificaciones: libCalificacionesdata,
			}
		}).then(function(modal) {
			modal.element.modal();
		});
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
var count = 0;
$scope.seleccionarCheckbox = function(alumno) {//al presionar un td de la lista de alumnos pone checkbox en true y muestra boton colocar inasistencias
	if (alumno.selected) {
		count--;
		alumno.selected = false;
	}
	else  {
		count++;
		alumno.selected = true;
	}
	if (count > 0) {
		$scope.mostrarBtnInasistencias = true;
	} else $scope.mostrarBtnInasistencias = false
};

$scope.checkAll = function () {
	angular.forEach($scope.alumnoData, function (item) {
		item.selected = false;
	});
	$scope.mostrarBtnInasistencias = false;
};

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

$scope.dropDownAnioOptions = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto'];
$scope.dropDownAnioValue = '';

$scope.filterByName = '';

$scope.subtitle = "Listado";
$scope.seleccionar = function (id){
	switch (id){
		case 'listado':
		$scope.nuevoPerfil = false;
		$scope.subtitle = "Listado";
		$scope.showEditProfileMenuIzq = false;
		$scope.notas = false;
		$scope.listado = true;
		break;
		case 'nuevoPerfil':
		$scope.listado = false;
		$scope.subtitle = "Nuevo Alumno";
		$scope.nuevoAlumno = null;
		$scope.showEditProfileMenuIzq = false; 
		$scope.notas = false;
		$scope.nuevoPerfil = true;
		break;
		case 'notas':
		$scope.listado = false;
		$scope.nuevoPerfil = false;
		$scope.subtitle = "Notas";
		$scope.showEditProfileMenuIzq = false;
		$scope.notas = true;
		break;


	}
	if (id === 'listado') {
		$scope.nuevoPerfil = false;
		$scope.subtitle = "Listado";
		$scope.listado = true;
		$scope.showEditProfileMenuIzq = false;

	}else if (id === 'nuevoPerfil'){
		$scope.listado = false;
		$scope.nuevoPerfil = true;
		$scope.subtitle = "Nuevo Alumno"
		$scope.nuevoAlumno = null;
		$scope.showEditProfileMenuIzq = false;
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


$scope.alumnoEdit = null;
$scope.editProfile = function(alumno) {
	$scope.listado = false;
	$scope.subtitle = "Editar Alumno"
	$scope.nuevoPerfil = true;
	$scope.nuevoAlumno = alumno;
	$scope.showEditProfileMenuIzq = true;
	$scope.setActiveAlu(4);//esto pinta editar perfil en el menú izq
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

/*collapse*/
$scope.panels = {};
$scope.panels.activePanel = 0;

$scope.multiplePanels = {
	activePanels: [null]
};

  $scope.asignarPlantillaTrim = function (trim){//esto debería consultar la planilla del trim
  	$scope.notasTrim = {};
  	$scope.notasTrim = plantillaTrimestralData;

  };


  $scope.showAsignarNotas = function () {
  	if (!$scope.asignarNotas) {
  		$scope.asignarNotas = true;
  	};
  };

  $scope.answer = [];
  $scope.alumnoData = [];
  $scope.showData = function() {
	/*$scope.loading = true;
	  $http.get(alumnoData) esto es para que muestre el loading mientras carga, el http da 404 xq no tengo servidor
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
