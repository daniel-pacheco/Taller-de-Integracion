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

 .controller('AlumnadoCtrl', function ($scope, $q, $http, boletinInasistenciasData, $alert, libCalificacionesdata, plantillaTrimestralData, alumnoService, Upload, $timeout, alumnoData, ModalService, ObjectsFactory, modalService, spinnerService, desempenioService, exportTableService, $interval) {
 	$scope.listado = true;
 	$scope.listFilterIsEnabled = false;

 	$scope.nuevoAlumno = ObjectsFactory.newAlumno();
 	$scope.nuevoTelefonoSimple = ObjectsFactory.newTelefono();

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
	},
	tooltipExport: {
		'title': 'Exportar para impresión'
	}
};
$scope.editarNotas=true;

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
			showServerError(response);
		});
	}; 

	$scope.fromDate = '';
	$scope.untilDate = '';
	$scope.showModalInasistencias = function(alumno){//esta deberia ser una funcion que pida la libreta de inasistencias del alumno que recibe
		
		var boletinInasistencias = {};

		desempenioService.getBoletinInasistByDni(alumno)
		.then(function(response){
			boletinInasistencias = response.data;

			ModalService.showModal({
				templateUrl: 'scripts/directivo/alumnado/modal/showInasistenciasAlumno.tpl.html',
				controller: 'showInasistenciasModalController',
				inputs: {
					title: "Boletín de inasistencias",
					libInasistencias: boletinInasistencias,
					fromDate: $scope.fromDate,
					untilDate: $scope.untilDate,
				}
			}).then(function(modal) {
				modal.element.modal();
				modal.close.then(function(result){
					boletinInasistencias = result.lib;
					//console.log(boletinInasistencias);
					if(result.modif){
						updateBoletinInasistencias(boletinInasistencias);
					}
					$scope.fromDate = result.fromDate;
					$scope.untilDate = result.untilDate;				
				});
			});
		},
		function(response){
			showServerError(response);
		});

	}; 

	$scope.showModalLibreta = function(alumno){//esta deberia ser una funcion que pida la libreta de calificaciones del alumno que recibe

		var boletinCalif = {};
		var bol = libCalificacionesdata;

		console.log(bol);

		desempenioService.getBoletinCalif(alumno)
		.then(function(response){
			boletinCalif = response.data;

			console.log(boletinCalif);

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
		},
		function(response){
			showServerError(response);
		})
		.finally(function(){});
		
	};

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

$scope.clearList = function(){
	$scope.mostrarBtnInasistencias = false;
	$scope.listFilterIsEnabled = false;
	$scope.alumnoData.length = 0;
	console.log($scope.alumnoData[0]);
};

$scope.dropDownSearchOptions = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto', '5to año'];
$scope.dropDownSearchValue = '';

$scope.dropDownCursoOptions = ['5 Mat', '4 Mat', '3 Mat', '2 Mat'];
$scope.dropDownCursoValue = '';



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

function showListFilter(){
	if (!$scope.listFilterIsEnabled) {
		$scope.listFilterIsEnabled = true;
	};
};

function hideListFilter(){
	if ($scope.listFilterIsEnabled) {
		$scope.listFilterIsEnabled = false;
	};
};


//---spinners
$scope.progreso = 0;
$scope.loginSpinner = function () {
	spinnerService.show('searchSpinner');
	$interval(function(){ $scope.progreso ++; },100)
	$timeout(function () {
		spinnerService.hide('searchSpinner');
		$scope.loggedIn = true;
		showServerSuccess('El proceso se ha realizado con éxito.');
	}, 10000);
};


//-- Export Table
$scope.exportAction = function(id){ 
	exportTableService.exportAction(id);
};

//---Llamadas al servicio ALUMNO---

$scope.alumnoData = [];


$scope.search = function (option, dni) {
	
	if (option) {
		if (option == "DNI/MAT") {
			if ($scope.searchByDni) {
				spinnerService.show('searchSpinner');
				alumnoService.getByDniMin($scope.searchByDni)
				.then(function(response){
					console.log(response);
					$scope.alumnoData.length = 0;
					$scope.alumnoData.push(response.data);
					showListFilter();
				},
				function(response){
					console.log(response);
					// alert('Se ha producido un error al intentar cotactar al servidor: ' + response.statusText + " - " + response.data.mensaje + " " + response.data.severidad);
					showServerError(response);
					hideListFilter();					
				})
				.finally(function(){spinnerService.hide('searchSpinner')});
			} 
		};

		if (option == "TODOS"){
			spinnerService.show('searchSpinner');
			showListFilter();
			
			alumnoService.getAllMin()
			.then(function(response){
				$scope.alumnoData = response.data;
				showListFilter();
			},
			function(response){
				// alert('Se ha producido un error al intentar cotactar al servidor: ' + response.statusText);
				showServerError(response);
				hideListFilter();
			})
			.finally(function(){spinnerService.hide('searchSpinner')});
		};

		if (option !== "TODOS" && option !== "DNI/MAT") {
			alert(option);
		};
	};
	
}

$scope.newAlumno = function (alumno){
	
	spinnerService.show('searchSpinner');
	alumno.nombreUsuario = modalService.makeId(5);
	if ($scope.nuevoTelefonoSimple) {
		alumno.listaTelefonos.push($scope.nuevoTelefonoSimple);
	};

	alumnoService.putNew(alumno)
	.then(function(response){
		console.log(response);
		showServerSuccess('El alumno se ha dado de alta con éxito. ID n°: ', response);
		$scope.clearFormAlu();
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){spinnerService.hide('searchSpinner');});

};

function updateBoletinInasistencias (boletin){
	desempenioService.updateBoletinInasist(boletin)
	.then(function(response){
		showServerSuccess('El las inasistencias se han actualizado éxito.', response);
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){spinnerService.hide('searchSpinner');});
};

$scope.clearFormAlu = function (){
	$scope.formAlu.$setUntouched();
	$scope.mostrarListaTelefonos = false;
	$scope.mostrarListaMails = false;
	$scope.nuevoAlumno = ObjectsFactory.newAlumno();
	$scope.nuevoTelefonoSimple = ObjectsFactory.newTelefono();
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

//-- [Alumnado/Notas]
//-- [Alumnado/Notas] variables

$scope.dropDownCursoOptions = ['U', 'A', 'B', 'C'];
$scope.dropDownCursoValue = '';

$scope.dropDownAnioOptions = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto', '5to año'];
$scope.dropDownAnioValue = '';

$scope.planillas = {};

var planillaTrimDTO = ObjectsFactory.newPlanillaTrimDTO();
var planillaTrimUpdateDTO = ObjectsFactory.newPlanillaTrimUpdateDTO();
$scope.buscarButtonIsDisabled = false;
// {
// 	"nroTrimestre" : null,
// 	"nombreAnio" : "",
// 	"curso" : "",
// 	"cicloLectivo" : 2016
// }

//-- [Alumnado/Notas] Form Management

/*collapse*/
// $scope.panels = {};
// $scope.panels.activePanel = 0;

$scope.toggleBuscarButton = function(param) {
	$scope.buscarButtonIsDisabled = !param;
};

$scope.multiplePanels = {
	activePanels: []
};

function multipanelCollapseAll() {
	$scope.multiplePanels.activePanels.length = 0; 											//estas lineas
	$scope.multiplePanels.activePanels = $scope.multiplePanels.activePanels.concat([-1]);	//collapsan los panles
};



//-- [Alumnado/Notas] filters

   // copiaLibInasistencias.listaInasistencias = _.sortBy(copiaLibInasistencias.listaInasistencias, function(value) {return value.fecha; });

//-- [Alumnado/Notas] modals
//-- [Alumnado/Notas] utils

function initPlanillaTrimDTO(trim) {
	planillaTrimDTO.trimestre = trim;
	planillaTrimDTO.anio = $scope.dropDownAnioValue;
	planillaTrimDTO.curso = $scope.dropDownCursoValue;
	planillaTrimDTO.cicloLectivo = 2015;

	console.log(planillaTrimDTO);

	return planillaTrimDTO;
};

function initPlanillaTrimUpdateDTO(trim) {
	planillaTrimUpdateDTO.trimestre = trim;
	planillaTrimUpdateDTO.anio = $scope.dropDownAnioValue;
	planillaTrimUpdateDTO.curso = $scope.dropDownCursoValue;
	planillaTrimUpdateDTO.planilla = $scope.planillas.tercerTrim;
	planillaTrimUpdateDTO.cicloLectivo = 2015;

	console.log(planillaTrimUpdateDTO);

	return planillaTrimUpdateDTO;
};

$scope.resetPlanillaTrimDTO = function() {
	
	multipanelCollapseAll();
	$scope.asignarNotas = false;
	$scope.planillas = {};
	planillaTrimDTO = ObjectsFactory.newPlanillaTrimDTO();

	console.log(planillaTrimDTO);

};

function updatePlanilla(trim, planilla) {
	switch (trim){
		case 1:
		$scope.planillas.primerTrim = orderByMateria(planilla);
		break;
		case 2:
		$scope.planillas.segundoTrim = orderByMateria(planilla);
		break;
		case 3:
		$scope.planillas.tercerTrim = orderByMateria(planilla);		
		break;
	}
};
$scope.materiasArrayBool = [];
function orderByMateria(planilla){
	angular.forEach(planilla, function (item) {
		item.notas = _.sortBy(item.notas, function(value){return value.materia;});
	});
	angular.forEach(planilla[0].notas, function(item){
		$scope.materiasArrayBool.push(false);
	});
	console.log($scope.materiasArrayBool);
	return planilla;
};
//-- [Alumnado/Notas] service calls
$scope.updatePlanillaTrimestre = function(trim) {
	spinnerService.show('searchSpinner');
	desempenioService.updatePlanillaTrimestral(initPlanillaTrimUpdateDTO(trim))
	.then(function(response){
		showServerSuccess('El las notas se han actualizado éxito.', response.data);
		$scope.getPlanillaTrimestre(trim);
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchSpinner');
	});
};

$scope.getPlanillaTrimestre = function(trim) {

	if (!_.includes($scope.multiplePanels.activePanels, (trim-1))) { //evita que haga el llamado al cerrar la pestaña del trimestre
		spinnerService.show('searchSpinner');
		desempenioService.getPlanillaTrimestral(initPlanillaTrimDTO(trim))
		.then(function(response){
			updatePlanilla(trim, response.data);
			//console.log($scope.planillas);
		},
		function(response){
			showServerError(response);
		})
		.finally(function(){
			spinnerService.hide('searchSpinner');
		});
	};
};




//---------------------------------

});

