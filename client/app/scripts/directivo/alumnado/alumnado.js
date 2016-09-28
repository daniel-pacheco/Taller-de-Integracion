'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:AlumnadoCtrl
 * @description
 * # AlumnadoCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES) {
 	$stateProvider
 	.state('directivo.alumnado', {
 		url: '/alumnado',
 		templateUrl: 'scripts/directivo/alumnado/alumnado.html',
 		controller: 'AlumnadoCtrl',
 		data: {
 			pageTitle: 'Alumnado',
 			authorizedRoles: [
 			USER_ROLES.admin,
 			USER_ROLES.directivo]
 		}
 	});
 })
 .controller('AlumnadoCtrl', [ '$scope', '$timeout', 'academicoService', 'alumnoService', 'configuracionService', 'desempenioService', 'exportTableService', 'modalService', 'ModalService', 'ObjectsFactory', 'spinnerService', 'Upload' ,function ($scope, $timeout, academicoService, alumnoService, configuracionService, desempenioService, exportTableService, modalService, ModalService, ObjectsFactory, spinnerService, Upload) { 	
//-- [Alumnado]
//-- [Alumnado] variables

$scope.listado = true;
$scope.listFilterIsEnabled = false;

//-- [Alumnado] Form Management

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

var setActiveAlu = function(menuItemAlu) {
	$scope.activeMenuIzqAlu = menuItemAlu;
};

$scope.seleccionar = function (id){

	if ($scope.showEditProfileMenuIzq) { // si estaba en modo editar y cambian de pestaña limpia el form
		$scope.clearFormAlu();
	};

	$scope.nuevoPerfil = false;
	$scope.showEditProfileMenuIzq = false;
	$scope.notas = false;
	$scope.listado = false;

	switch (id){
		case 'listado':
		$scope.subtitle = 'Listado';
		$scope.listado = true;
		setActiveAlu(1); 
		clearList();
		break;
		case 'nuevoPerfil':
		$scope.subtitle = 'Nuevo Alumno';
		$scope.nuevoPerfil = true;
		setActiveAlu(2);
		break;
		case 'notas':
		$scope.subtitle = 'Notas';
		$scope.notas = true;
		setActiveAlu(3);
		break;
		case 'editar':
		$scope.subtitle = "Editar Alumno";
		$scope.showEditProfileMenuIzq = true;
		$scope.nuevoPerfil = true;
		setActiveAlu(4);
		break;
	}
};
$scope.$on('$viewContentLoaded', function(){
	$scope.seleccionar('listado');
});


$scope.editProfile = function(alumno) {	
	$scope.nuevoAlumno = angular.copy(alumno);
	$scope.nuevoAlumno.fechaNacimiento = new Date(alumno.fechaNacimiento);
	$scope.mostrarListaMails = $scope.nuevoAlumno.listaMails.length > 0? true: false;
	$scope.mostrarListaTelefonos = $scope.nuevoAlumno.listaTelefonos.length > 0? true: false;
	$scope.nuevoAlumno.sexo = $scope.nuevoAlumno.sexo == "Masculino"? "M":"F";
	$scope.seleccionar('editar');
};

//-- [Alumnado] filters

$scope.alumnoFilter = function (alumno) {//la clave de este comparador es q transofrma todo a string y va comparando las posiciones, no tiene en cuenta los espacios
	
	if (alumno) {
		return (_.includes(_.lowerCase(alumno.apellido), _.lowerCase($scope.filterByName) || '') ||
			_.includes(_.lowerCase(alumno.nombre), _.lowerCase($scope.filterByName) || '') ||
			_.includes(_.lowerCase(alumno.nroDocumento), _.lowerCase($scope.filterByName) || '')
			);		
	};
};

//-- [Alumnado] modals

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
				alumno: alumno,
				ModalService: ModalService
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


$scope.showModalInasistencias = function(alumno){//esta deberia ser una funcion que pida la libreta de inasistencias del alumno que recibe

	var boletinInasistencias = {};

	var primerTrimestre = [];
	var segundoTrimestre = [];
	var tercerTrimestre = [];

	primerTrimestre.push(_.find($scope.configParametros, ['nombre', "COMIENZO_TRIM_1"]));
	primerTrimestre.push(_.find($scope.configParametros, ['nombre', "FIN_TRIM_1"]));

	segundoTrimestre.push(_.find($scope.configParametros, ['nombre', "COMIENZO_TRIM_2"]));
	segundoTrimestre.push(_.find($scope.configParametros, ['nombre', "FIN_TRIM_2"]));
	
	tercerTrimestre.push(_.find($scope.configParametros, ['nombre', "COMIENZO_TRIM_3"]));
	tercerTrimestre.push(_.find($scope.configParametros, ['nombre', "FIN_TRIM_3"]));

	desempenioService.getBoletinInasistByDni(alumno)
	.then(function(response){
		boletinInasistencias = response.data;

		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/showInasistenciasAlumno.tpl.html',
			controller: 'showInasistenciasModalController',
			inputs: {
				title: "Boletín de inasistencias",
				libInasistencias: boletinInasistencias,
				datePrimerTrimestre: primerTrimestre,
				dateSegundoTrimestre: segundoTrimestre,
				dateTercerTrimestre: tercerTrimestre,
			}
		}).then(function(modal) {
			modal.element.modal();
			modal.close.then(function(result){
				boletinInasistencias = result.lib;
				//console.log(boletinInasistencias);
				if(result.modif){
					updateBoletinInasistencias(boletinInasistencias);
				}				
			});
		});
	},
	function(response){
		showServerError(response);
	});
};

$scope.showModalLibreta = function(alumno){//esta deberia ser una funcion que pida la libreta de calificaciones del alumno que recibe

	var boletinCalif = {};
	// var bol = libCalificacionesdata;

	// console.log(bol);
	spinnerService.show('searchSpinner');
	desempenioService.getBoletinCalif(alumno)
	.then(function(response){
		boletinCalif = response.data;

		//console.log(boletinCalif);

		ModalService.showModal({
			templateUrl: 'scripts/directivo/alumnado/modal/showLibretaAlumno.tpl.html',
			controller: 'showLibretaAlumnoModalController',
			inputs: {
				title: "Libreta de calificaciones",
				libCalificaciones: boletinCalif,
			}
		}).then(function(modal) {
			modal.element.modal();
		});
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchSpinner');
	});		
};


//-- [Alumnado] utils (spinners, mensajes impresion etc)

//-- Export Table
$scope.exportAction = function(id){ 
	exportTableService.exportAction(id);
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
	// console.log(response);
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
	// console.log(response);
	var msg = message;
	
	if ( response && response.data) {
		msg += ' ' + response.data;
	};			
	$scope.showMessage(msg, 'Operación exitosa' , true);
};

//-- [Alumnado] service calls

function getAllConfig() {
	spinnerService.show('searchSpinner');
	configuracionService.getParametroConfigAll()
	.then(
		function(response){
			$scope.configParametros = response.data;
		},
		function(response){
			showServerError (response);
		})
	.finally(function(){
		spinnerService.hide('searchSpinner')
	});
};

//----------------------------------

//-- [Anio/Listado]
//-- [Anio/Listado] variables
$scope.listAlumnosInasistencia = [];
// $scope.dropDownSearchOptions = ['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto', '5to año'];
$scope.dropDownSearchValue = '';

// $scope.dropDownCursoOptions = ['5 Mat', '4 Mat', '3 Mat', '2 Mat'];
$scope.dropDownCursoValue = '';



$scope.filterByName = '';

$scope.nuevoAlumno = ObjectsFactory.newAlumno();
$scope.nuevoTelefonoSimple = ObjectsFactory.newTelefono();
$scope.nuevoAlumno.domicilio = ObjectsFactory.newDomicilio();
$scope.nuevoMailSimple = ObjectsFactory.newMail();

$scope.alumnoData = [];

$scope.searchByDni = undefined;

//-- [Anio/Listado] Form Management

$scope.editarNotas = true;


var count = 0;

$scope.seleccionarCheckbox = function(alumno) {//al presionar un td de la lista de alumnos pone checkbox en true y muestra boton colocar inasistencias

	if (alumno.selected) {
		count--;
		alumno.selected = false;
		$scope.listAlumnosInasistencia.splice($scope.listAlumnosInasistencia.indexOf(alumno),1);
		//console.log($scope.listAlumnosInasistencia);
	}
	else  {
		count++;
		alumno.selected = true;
		$scope.listAlumnosInasistencia.push(alumno);
		//console.log($scope.listAlumnosInasistencia);

	}
	if (count > 0) {
		$scope.mostrarBtnInasistencias = true;
	} else $scope.mostrarBtnInasistencias = false;
};

$scope.unCheckAll = function () {
	angular.forEach($scope.alumnoData, function (item) {
		item.selected = false;
	});
	$scope.listAlumnosInasistencia = [];
	$scope.mostrarBtnInasistencias = false;
	count = 0;
};

$scope.multiplesInasistencias = function (){

	ModalService.showModal({
		templateUrl: 'scripts/directivo/alumnado/modal/showInasistenciasMultiples.tpl.html',
		controller: 'showInasistenciasMultiplesModalController',
		inputs: {
			title: "Colocar inasistencias",
			alumnoData: $scope.listAlumnosInasistencia,
			count: count,
		}
	}).then(function(modal) {
		modal.element.modal();
		modal.close.then(function(result){
			if (result.seleccionados > 0) {
				$scope.mostrarBtnInasistencias = true;
			} else {
				$scope.mostrarBtnInasistencias = false;
				}
			$scope.listAlumnosInasistencia = result.listAluIna;
			count = result.count;
			if (result.guardo) {
				//llamar al service que pone inasistencias con result.nuevaInasistencia y a la funcion $scope.unCheckAll()
					var listadoDNIAlumnos = [];
				    angular.forEach(result.listAluIna, function (item) {
  						listadoDNIAlumnos.push(item.nroDocumento);
					});
				result.nuevaInasistencia.alumnos = listadoDNIAlumnos;
				console.log(result.nuevaInasistencia);
				$scope.unCheckAll();
				spinnerService.hide('searchSpinner');
			}
		});
	});
};

var clearList = function(){
	$scope.dropDownSearchValue = '';
	$scope.searchByDni = '';
	$scope.mostrarBtnInasistencias = false;
	hideListFilter();
	$scope.alumnoData.length = 0;
};

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

//-- [Anio/Listado] filters
//-- Order list
$scope.predicate = 'nombre';
$scope.reverse = true;
$scope.orderDocente = function(predicate) {
	$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
	$scope.predicate = predicate;
};
//-- [Anio/Listado] modals

//modals
$scope.domicilioAvanzado = function() {
	// $scope.nuevoAlumno.domicilio = ObjectsFactory.newDomicilio();
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

//-- [Anio/Listado] utils
//-- [Anio/Listado] service calls

function getAnios() {
	spinnerService.show('searchSpinner');
	academicoService.anioGetAllMin()
	.then(
		function(response){
			$scope.dropDownSearchOptions = response.data;
		},
		function(response){
			showServerError (response);
		})
	.finally(function(){
		spinnerService.hide('searchSpinner')
	});
};
$scope.$on('$viewContentLoaded', function(){
	getAnios();
	getAllConfig();
});

$scope.search = function (option, dni) {
	
	if (option) {
		if (option == "DNI/MAT") {
			if ($scope.searchByDni) {
				spinnerService.show('searchSpinner');
				alumnoService.getByDniMin($scope.searchByDni)
				.then(function(response){
					// console.log(response);
					$scope.alumnoData.length = 0;
					$scope.alumnoData.push(response.data);
					showListFilter();
				},
				function(response){
					// console.log(response);
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
			// alert(option); // buscar por id anio

			spinnerService.show('searchSpinner');
			alumnoService.getByAnio(option)
			.then(function(response){
				$scope.alumnoData = response.data;
				showListFilter();
			},
			function(response){
				showServerError(response);
				hideListFilter();
			})
			.finally(function(){
				spinnerService.hide('searchSpinner');
			})
		};
	};	
};

$scope.deleteAlumnoComfirm = function(alumno){
	$scope.confirmModal('¿Desea eliminar al alumno ' + alumno.nombre + ' ' + alumno.apellido + '? ', $scope.deleteAlumno, alumno.idUsuario);
};

$scope.deleteAlumno = function (id) {

	spinnerService.show('searchSpinner');
	alumnoService.alumnoDel(id)
	.then(function (response) {
		// $scope.postAnswer = response.data;
		showServerSuccess('El alumno ha sido eliminado con éxito. ', response);
		if ($scope.searchByDni) {
			clearList();
		} else{
			$scope.search($scope.dropDownSearchValue, $scope.searchByDni);
		};
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchSpinner');
	});
};

//--------------------------------

//-- [Anio/nuevoPerfil]
//-- [Anio/nuevoPerfil] variables
//-- [Anio/nuevoPerfil] Form Management
//-- [Anio/nuevoPerfil] filters
//-- [Anio/nuevoPerfil] modals
//-- [Anio/nuevoPerfil] utils
//-- [Anio/nuevoPerfil] service calls

$scope.newAlumno = function (alumno){
	
	spinnerService.show('searchSpinner');
	alumno.nombreUsuario = modalService.makeId(5);

	if ($scope.nuevoTelefonoSimple.nroTelefono && !_.includes(alumno.listaTelefonos, $scope.nuevoTelefonoSimple)) { //solo hace el pushsi el telefono no se encuentra en la lista
		alumno.listaTelefonos.push($scope.nuevoTelefonoSimple);
	};

	if ($scope.nuevoMailSimple.direccionMail && !_.includes(alumno.listaMails, $scope.nuevoMailSimple)) { //solo hace el push si el Mail no se encuentra en la lista
		alumno.listaMails.push($scope.nuevoMailSimple);
	};
	
	alumnoService.putNew(alumno)
	.then(function(response){
		showServerSuccess('El alumno se ha dado de alta con éxito. ID n°: ', response);
		if ($scope.showEditProfileMenuIzq){
			$scope.search($scope.dropDownSearchValue, $scope.searchByDni);
			$scope.seleccionar('listado');
		}
		$scope.clearFormAlu();
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchSpinner');
	});
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
	$scope.nuevoMailSimple = ObjectsFactory.newMail();
};

//------------------------------------

// $scope.alumnoEdit = null;


  // $scope.asignarPlantillaTrim = function (trim){//esto debería consultar la planilla del trim
  // 	$scope.notasTrim = {};
  // 	$scope.notasTrim = plantillaTrimestralData;

  // };


  

  // $scope.answer = [];
  // $scope.showData = function() {

  // 	$scope.alumnoData = alumnoData;

  // }


//-- [Alumnado/Notas]
//-- [Alumnado/Notas] variables

// $scope.dropDownCursoOptions = ['U', 'A', 'B', 'C'];
// $scope.dropDownCursoValue = '';

// $scope.dropDownAnioOptions = []; //['Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto', '5to año'];
// $scope.dropDownAnioValue = '';

$scope.planillas = {};

var planillaTrimDTO = ObjectsFactory.newPlanillaTrimDTO();
var planillaTrimUpdateDTO = ObjectsFactory.newPlanillaTrimUpdateDTO();
$scope.buscarButtonIsDisabled = false;

//-- [Alumnado/Notas] Form Management

$scope.multiplePanels = {
	activePanels: []
};

function multipanelCollapseAll() {
	$scope.multiplePanels.activePanels.length = 0; 											//estas lineas
	$scope.multiplePanels.activePanels = $scope.multiplePanels.activePanels.concat([-1]);	//collapsan los panles
};

$scope.showAsignarNotas = function () {
	if (!$scope.asignarNotas) {
		$scope.asignarNotas = true;
	};
};
//-- [Alumnado/Notas] filters

   // copiaLibInasistencias.listaInasistencias = _.sortBy(copiaLibInasistencias.listaInasistencias, function(value) {return value.fecha; });

//-- [Alumnado/Notas] modals
//-- [Alumnado/Notas] utils

function initPlanillaTrimDTO(trim) { // para get
	planillaTrimDTO.trimestre = trim;
	planillaTrimDTO.anio = $scope.dropDownSelectedAnio.nombre;
	planillaTrimDTO.curso = $scope.dropDownSelectedCurso.division;
	planillaTrimDTO.cicloLectivo = 2016;

	// console.log(planillaTrimDTO);

	return planillaTrimDTO;
};

function initPlanillaTrimUpdateDTO(trim, planilla) { // para put
	planillaTrimUpdateDTO.trimestre = trim;
	planillaTrimUpdateDTO.anio = $scope.dropDownSelectedAnio.nombre;
	planillaTrimUpdateDTO.curso = $scope.dropDownSelectedCurso.division;
	planillaTrimUpdateDTO.planilla = planilla;
	planillaTrimUpdateDTO.cicloLectivo = 2015;

	// console.log(planillaTrimUpdateDTO);

	return planillaTrimUpdateDTO;
};

$scope.resetPlanillaTrimDTO = function() {
	
	multipanelCollapseAll();
	$scope.asignarNotas = false;
	$scope.planillas = {};
	planillaTrimDTO = ObjectsFactory.newPlanillaTrimDTO();

	// console.log(planillaTrimDTO);

};

function updatePlanilla(trim, planilla) {
	switch (trim){
		case 1:
		$scope.planillas.primerTrim = orderByMateria(planilla, $scope.materiasArrayBoolPrimerTrim);
		break;
		case 2:
		$scope.planillas.segundoTrim = orderByMateria(planilla, $scope.materiasArrayBoolSegundoTrim);
		break;
		case 3:
		$scope.planillas.tercerTrim = orderByMateria(planilla, $scope.materiasArrayBoolTercerTrim);		
		break;
	}
};
$scope.materiasArrayBoolTercerTrim = [];
$scope.materiasArrayBoolSegundoTrim = [];
$scope.materiasArrayBoolPrimerTrim = [];

function orderByMateria(planilla, materiasArrayBool){
	angular.forEach(planilla, function (item) {
		item.notas = _.sortBy(item.notas, function(value){return value.materia;});
	});
	angular.forEach(planilla[0].notas, function (item){
		materiasArrayBool.push(false);
	});
	// console.log(materiasArrayBool);
	return planilla;
};

//-- [Alumnado/Notas] service calls

$scope.updatePlanillaTrimestre = function(trim, planillaParam) {
	spinnerService.show('searchSpinner');
	desempenioService.updatePlanillaTrimestral(initPlanillaTrimUpdateDTO(trim, planillaParam))
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

}]);

