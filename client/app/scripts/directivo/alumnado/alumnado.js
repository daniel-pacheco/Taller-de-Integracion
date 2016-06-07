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

 .controller('AlumnadoCtrl', function ($scope, $q, $http, boletinInasistenciasData, $alert, libCalificacionesdata, plantillaTrimestralData, alumnoService, Upload, $timeout, alumnoData, ModalService, ObjectsFactory, modalService) {
 	$scope.listado = true;
 	$scope.listFilterIsEnabled = false;

 	$scope.nuevoAlumno = ObjectsFactory.newAlumno();

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
	$scope.nuevoAlumno.domicilio = ObjectsFactory.newDomicilio();
	ModalService.showModal({
		templateUrl: 'scripts/directivo/alumnado/modal/addaddressdetails.tpl.html',
		controller: 'addAddressDetailsModalController',
		inputs: {
			title: "Nuevo Domicilio",
			domicilioAvanzado: $scope.nuevoAlumno.domicilio,
		}
	}).then(function(modal) {
		modal.element.modal();
		modal.close.then(function(result){
			$scope.nuevoAlumno.domicilio = result;
		});
	});
};


	$scope.telefonoAvanzado = function(){//esta deberia ser una funcion que pida la libreta de inasistencias del alumno que recibe
		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/addphonedetails.tpl.html',
			controller: 'telefonoAvanzadoModalController',
			inputs: {
				title: "Teléfono",
				listaTelefonos: $scope.nuevoAlumno.listaTelefonos,
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result){
				$scope.nuevoAlumno.listaTelefonos = result;
				if ($scope.nuevoAlumno.listaTelefonos.length > 0){//Esto es para listar los telefonos en una lista en el form principal	
					$scope.mostrarListaTelefonos = true;
				}else{ 
					$scope.mostrarListaTelefonos = false;
				}
			});
		});
	}; 

	$scope.mailAvanzado = function(){
		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/addmaildetails.tpl.html',
			controller: 'mailAvanzadoModalController',
			inputs: {
				title: "Mails",
				listaMails: $scope.nuevoAlumno.listaMails,
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result){
				$scope.nuevoAlumno.listaMails = result;
				if ($scope.nuevoAlumno.listaMails.length > 0){//Esto es para listar los telefonos en una lista en el form principal	
					$scope.mostrarListaMails = true;
				}else{ 
					$scope.mostrarListaMails = false;
				}
			});
		});
	};


	$scope.showModalProfile = function(alumnoDni){
		//consultar al back toda la info del alumno
		var alumno = {};

		alumnoService.getByDni(alumnoDni)
		.then(function(response){
			alumno = response.data;
			
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
		},
		function(response){
			alert('Se ha producido un error al intentar cotactar al servidor: ' + response.statusText);
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
	
	if (alumno) {
		return (angular.lowercase(alumno.apellido).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||
			angular.lowercase(alumno.nombre).indexOf(angular.lowercase($scope.filterByName) || '') !== -1 ||
			angular.lowercase(alumno.nroDocumento.toString()).indexOf(angular.lowercase($scope.filterByName) || '') !== -1
			);
	};
};

$scope.dropDownSearchOptions = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto'];
$scope.dropDownSearchValue = '';

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
}

//---Llamadas al servicio ALUMNO---

$scope.alumnoData = [];


$scope.search = function (option, dni) {
	
	if (option) {
		if (option == "DNI/MAT") {
			if ($scope.searchByDni) {
				// alert($scope.searchByDni);
				alumnoService.getByDniMin($scope.searchByDni)
				.then(function(response){
					console.log(response);
					$scope.alumnoData.length = 0;
					$scope.alumnoData.push(response.data);
				},
				function(response){
					console.log(response);
					alert('Se ha producido un error al intentar cotactar al servidor: ' + response.statusText + " - " + response.data.detailMessage);
				})
			} 
		};

		if (option == "TODOS"){
			// this.showData();	$scope.alumnoData
			alumnoService.getAllMin()
			.then(function(response){
				$scope.alumnoData = response.data;
			},
			function(response){
				alert('Se ha producido un error al intentar cotactar al servidor: ' + response.statusText);
			});
		};

		if (option !== "TODOS" && option !== "DNI/MAT") {
			alert(option);
		};

		if (!$scope.listFilterIsEnabled) {
			$scope.listFilterIsEnabled = true;
		};
	};
	
}

$scope.newAlumno = function (alumno){
	$scope.nuevoPerfil = false;
	$scope.showLoading = true;
	alumno.nombreUsuario = modalService.makeId(5);

	alumnoService.putNew(alumno)
	.then(function(response){
		console.log(response);
		alert('El alumno se ha dado de alta con éxito. ID n°: ' + response.data);
		// $scope.clearFormAlu();
		$scope.showLoading = false;
		$scope.nuevoPerfil = true;
	},
	function(response){
		//alert('Se ha producido un error al intentar cotactar al servidor: ' + response.statusText);
		errorConectToServer.show();//este es el alert
		$scope.showLoading = false;
		$scope.nuevoPerfil = true;
	});

};

$scope.clearFormAlu = function (){
	$scope.formAlu.$setUntouched();
	$scope.nuevoAlumno = ObjectsFactory.newAlumno();
}

//--Form management

$scope.activeMenuIzqAlu = 1;
$scope.setActiveAlu = function(menuItemAlu) {
	$scope.activeMenuIzqAlu = menuItemAlu;
};


$scope.searchByDni = undefined;

$scope.alumnoEdit = null;
$scope.editProfile = function(alumno) {
	$scope.listado = false;
	$scope.subtitle = "Editar Alumno"
	$scope.nuevoAlumno = angular.copy(alumno);
	$scope.showEditProfileMenuIzq = true;
	$scope.setActiveAlu(4);//esto pinta editar perfil en el menú izq
	if ($scope.nuevoAlumno.listaTelefonos.length > 0){//Esto es para listar los telefonos en una lista en el form principal	
		$scope.mostrarListaTelefonos = true;
	}else{ 
		$scope.mostrarListaTelefonos = false;
	}
	if ($scope.nuevoAlumno.listaMails.length > 0){//Esto es para listar los telefonos en una lista en el form principal	
		$scope.mostrarListaMails = true;
	}else{ 
		$scope.mostrarListaMails = false;
	}
	$scope.nuevoPerfil = true;

}



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
	activePanels: []
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
  $scope.showData = function() {

  	$scope.alumnoData = alumnoData;

  }


/*$scope.getAll = alumnoService.alumnoGetAll().then(function(response){
	$scope.answer = response.data;  
});*/

$scope.prueba = function (){
		console.log($scope.nuevoAlumno);//esto se deberia guardar
	}


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

//-- Alerts
var errorConectToServer = $alert({
	title: 'Error:', 
	placement: 'top',
	content: 'Se ha producido un error al intentar cotactar al servidor: ', 
	type: 'danger', 
	keyboard: true, 
	show: false,
	duration: 5,
	container: 'body'
});

});

