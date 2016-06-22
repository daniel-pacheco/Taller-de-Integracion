'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DocenteMateriasCtrl
 * @description
 * # DocenteMateriasCtrl
 * Controller of the clientAppApp
 */
 angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('docente.materias', {
 		url: '/materias',
 		templateUrl: 'scripts/docente/materias/materias.html',
 		controller: 'DocenteMateriasCtrl',
 		data: {
 			pageTitle: 'Materias'
 		}
 	});
 })
 .controller('DocenteMateriasCtrl', function ($scope, primerTrimData, todosLosTrimData) {

 	$scope.listaMaterias = [
 	{ 
 		"nombre":'1° Matemática',
 		"id": 8,
 	},
 	{
 		"nombre":'1°Física',
 		"id": 15,
 	},
 	{
 		"nombre":'2° Matemática',
 		"id": 3,
 	},
 	{
 		"nombre":'3° Matemática',
 		"id": 11,
 	} 
 	];

 	$scope.selecMateriaMensaje = true;
 	$scope.listaPlantillasTrim = false;
 	
 	$scope.panels = {};
 	$scope.panels.activePanel = 0;
 	$scope.multiplePanels = {
 		activePanels: []
 	};

 	$scope.setActive = function(menuItem) {
 		$scope.activeMenu = menuItem;
 		$scope.titulo = menuItem.nombre;
 		$scope.selecMateriaMensaje = false;
 		$scope.listaPlantillasTrim = true;
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

$scope.createCopy = function(lista){
	$scope.copiaLista = angular.copy(lista);
}

$scope.saveEditNota = function (copiaListaNotas){
	$scope.planillaPrimerTrim.listaTrimestre = copiaListaNotas;
}


});