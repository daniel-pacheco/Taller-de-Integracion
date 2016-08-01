'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DocenteMateriasCtrl
 * @description
 * # DocenteMateriasCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider, USER_ROLES) {
 	$stateProvider
 	.state('docente.materias', {
 		url: '/materias',
 		templateUrl: 'scripts/docente/materias/materias.html',
 		controller: 'DocenteMateriasCtrl',
 		data: {
 			pageTitle: 'Materias',
 			authorizedRoles: [
 			USER_ROLES.admin,
 			USER_ROLES.docente]
 		}
 	});
 })
 .controller('DocenteMateriasCtrl', ['$scope', 'academicoService', 'docenteService', 'exportTableService', 'ModalService', 'ObjectsFactory', 'spinnerService', 'primerTrimData', 'todosLosTrimData', function ($scope, academicoService, docenteService, exportTableService, ModalService, ObjectsFactory, spinnerService, primerTrimData, todosLosTrimData) {

//-- [Seccion]
//-- [Seccion] variables

var myData = {};
$scope.selectedMAteria = {};
$scope.titulo = 'Materias';

//-- [Seccion] Form Management

$scope.tooltip = {
  tooltipExport: {
    'title': 'Exportar para impresión'
  }
};

$scope.listaPlantillasTrim = false;

$scope.setActive = function(menuItem) {
	$scope.activeMenu = menuItem;
	$scope.titulo = menuItem.nombre;
	$scope.selectedMAteria = menuItem;
	$scope.dropDownSelectedAnio = selectAnioByName(menuItem.anio);
	$scope.selecMateriaMensaje = false;
	

 		// hacerQueSePuedaElegirElCursoParaHacerInitDelDTODePlanilla(menuItem, anio );
 	};

$scope.toggleListaPlantillasTrim = function (param){
	$scope.listaPlantillasTrim = param;
};

$scope.resetAdministrarTable = function(){

};
//-- [Seccion] filters
//-- [Seccion] modals
//-- [Seccion] utils (spinners, mensajes impresion etc)

function selectAnioByName (nombreAnio) {
	return  _.find($scope.listaAnios, ['nombre', nombreAnio]);
}

//-- Export Table
$scope.exportAction = function(id){ 
	exportTableService.exportAction(id);
};

$scope.showMessage = function(mesagge, title, isGood) { //isGood recibe true si salio bien o false si salio mal
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

//-- [Seccion] service calls

function getMyData () {

	spinnerService.show('searchMatDocenteSpinner');
	docenteService.getMyData().then(
		function(response) {
			console.log(response);
			myData = response.data;
		listAllMaterias(myData.idUsuario); //cambiar esto cuando se pueda elegir el año
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchMatDocenteSpinner');
	});	
};

function listAllMaterias() {
	spinnerService.show('searchMatDocenteSpinner');
	academicoService.matGetAllMin()
	.then(function(response){
		$scope.listaMaterias = response.data;
	},
	function(response){
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchMatDocenteSpinner');
	});
};
$scope.$on('$viewContentLoaded', function(){
	getMyData();
	listAllAnios();
	// listAllMaterias(); //cambiar esto cuando se pueda elegir el año
});

function listAllAnios() {
	spinnerService.show('searchMatDocenteSpinner');
	academicoService.anioGetAllMin()
	.then(function(response){
		$scope.listaAnios = response.data;
	},
	function(response){
		showServerError (response);
	})
	.finally(function(){
		spinnerService.hide('searchMatDocenteSpinner')
	});
};

function initPlanillaTrimDTO(trim){
	var planillaTrimDTO = ObjectsFactory.newPlanillaTrimDTO();

	planillaTrimDTO.materia = $scope.selectedMAteria.idMateria;
	planillaTrimDTO.anio = selectAnioByName($scope.selectedMAteria.anio).idAnio;
	planillaTrimDTO.trimestre = trim;
	planillaTrimDTO.curso = $scope.dropDownSelectedCurso.division;

	console.log(planillaTrimDTO);

	return planillaTrimDTO;
};

$scope.getPlanillas = function(trim) {

	// spinnerService.show('searchMatDocenteSpinner');
	initPlanillaTrimDTO(trim);
  desempenioService.getplanillatrimestral(initPlanillaTrimDTO(trim)) //no existe aun
  .then(function(response){
  	$scope.planillas = response.data;
  },
  function(response){
  	showServerError (response);
  })
  .finally(function(){
  	spinnerService.hide('searchMatDocenteSpinner')
  });
};

//----------------------------------

$scope.listaMaterias = [
 	// { 
 	// 	"nombre":'1° Matemática',
 	// 	"id": 8,
 	// },
 	// {
 	// 	"nombre":'1°Física',
 	// 	"id": 15,
 	// },
 	// {
 	// 	"nombre":'2° Matemática',
 	// 	"id": 3,
 	// },
 	// {
 	// 	"nombre":'3° Matemática',
 	// 	"id": 11,
 	// } 
 	];

 	$scope.selecMateriaMensaje = true;
 	$scope.listaPlantillasTrim = false;
 	
 	$scope.panels = {};
 	$scope.panels.activePanel = 0;
 	$scope.multiplePanels = {
 		activePanels: []
 	};


 	$scope.planillaPrimerTrim = null;
 	$scope.planillaSegundoTrim = null;
 	$scope.planillaTercerTrim = null;
 	$scope.planillaDiciembre = null;
 	$scope.planillaMarzo = null;
 	$scope.planillaTodos = null;
 	$scope.cargarPlanilla = function(trim, idMateria){//esta funcion es para que asigne solo la primera vez la planilla
 		switch (trim){
 			case 1:
 			if ($scope.planillaPrimerTrim == null){
			$scope.planillaPrimerTrim = primerTrimData;//se debe asignar la plantilla de este trmiestre de la materia que corresponda
		}
		break;
		case 2:
		if ($scope.planillaSegundoTrim == null){
			$scope.planillaSegundoTrim = plantillaTrimestralData;//se debe asignar la plantilla de este trmiestre
		}
		break;
		case 3:
		if ($scope.planillaTercerTrim == null){
			$scope.planillaTercerTrim = plantillaTrimestralData;//se debe asignar la plantilla de este trmiestre
		}
		break;
		case 'diciembre':
		if ($scope.planillaDiciembre == null){
			$scope.planillaDiciembre = plantillaTrimestralData;//se debe asignar la plantilla de este trmiestre
		}
		break;
		case 'marzo':
		if ($scope.planillaMarzo == null){
			$scope.planillaMarzo = plantillaTrimestralData;//se debe asignar la plantilla de este trmiestre
		}
		break;
		case "todos":
		if ($scope.planillaTodos == null){
			$scope.planillaTodos = todosLosTrimData;//se debe asignar la planilla de todos los trim
		}
		break;
	}
};

$scope.cargarPlanilla(1, 8);

$scope.prueba = function (){
	alert($scope.filteredAlumnos);
}

$scope.createCopy = function(lista){
	$scope.copiaLista = angular.copy(lista);
}

$scope.saveEditNota = function (copiaListaNotas){
	$scope.planillaPrimerTrim.listaTrimestre = copiaListaNotas;
}


}]);