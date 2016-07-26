'use strict';

/**
 * @ngdoc function
 * @name clientAppApp.controller:DesempenioCtrl
 * @description
 * # DesempenioCtrl
 * Controller of the clientAppApp
 */

 angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.desempenio', {
 		url: '/desempenio',
 		templateUrl: 'scripts/directivo/desemp/desempenio.html',
 		controller: 'DesempenioCtrl',
 		data: {
 			pageTitle: 'Alumnado'
 		}
 	});
 })
 .controller('DesempenioCtrl', [ '$scope', '$timeout', 'academicoService', 'alumnoService', 'desempenioService', 'exportTableService', 'modalService', 'ModalService', 'ObjectsFactory', 'spinnerService', 'Upload' ,function ($scope, $timeout, academicoService, alumnoService, desempenioService, exportTableService, modalService, ModalService, ObjectsFactory, spinnerService, Upload) { 

 	
 	$scope.listaAnios = [{nombre:'primero'}, {nombre:'segundo'}, {nombre:'tercero'}, {nombre:'cuarto'}];


//-- [Desempenio]
//-- [Desempenio] variables


//-- [Desempenio] Form Management

$scope.tooltip = {
	tooltipExport: {
		'title': 'Exportar para impresión'
	}
};

$scope.seleccionar = function (opcion){
	$scope.activeMenuIzq = '';
	$scope.dropDownSelectedAnio = '';
	$scope.dropDownSelectedCurso = '';
	$scope.showCuadroHonor = false;
	$scope.todos = false;
	$scope.porAnio = false;

	if (opcion == 'todos') {
		$scope.activeMenuIzq = opcion;
		$scope.todos = true;
		$scope.title = 'Cuadro de Honor: ';

	} else {
		$scope.activeMenuIzq = opcion;
		$scope.porAnio = true;
		$scope.title = 'Desempeño anual: ';
	};
	
};
$scope.$on('$viewContentLoaded', function(){
	$scope.seleccionar('todos');
});

$scope.drawGraphics = function(opcion){

	if (opcion == 'todos') {
		// $scope.showCuadroHonor = true;

		$timeout(function(){
			drawPieChartTodos();
			drawColumnChartTodos();
		}, 100);
	} else{
		$timeout(function(){
		drawStackedBarAnio(),
		drawStackedBarTrimestre();
		drawPieChartAnual();
		}, 100);
	};

};

//-- [Desempenio] filters
//-- [Desempenio] modals
//-- [Desempenio] utils (spinners, mensajes impresion etc)

$scope.exportAction = function(id){ 
	exportTableService.exportAction(id);
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

//-- [Desempenio] service calls

function getAnios() {
	spinnerService.show('searchDesempenioSpinner');
	academicoService.anioGetAllMin()
	.then(
		function(response){
			$scope.listaAnios = response.data;
		},
		function(response){
			showServerError (response);
		})
	.finally(function(){
		spinnerService.hide('searchDesempenioSpinner')
	});
};
$scope.$on('$viewContentLoaded', function(){
	getAnios();
});


//-- [Desempenio/Todos]
//-- [Desempenio/Todos] variables
//-- [Desempenio/Todos] Form Management
//-- [Desempenio/Todos] filters
//-- [Desempenio/Todos] modals
//-- [Desempenio/Todos] utils (spinners, mensajes impresion etc)
//-- [Desempenio/Todos] service calls

function getCuadroHonor (idAnio) {

	spinnerService.show('searchDesempenioSpinner')
	academicoService.getCuadroHonor(idAnio)
	.then(function (response) {
		// asign result to variable
		drawGraphics('todos');
	},
	function (response) {
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchDesempenioSpinner');
	});
	
};

function getDesempAnual (idAnio) {

	spinnerService.show('searchDesempenioSpinner')
	academicoService.getDesempAnual(idAnio)
	.then(function (response) {
		// asign result to variable
		drawGraphics('todos');
	},
	function (response) {
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchDesempenioSpinner');
	});
	
};

//-----------------------------------

//-- [Desempenio/PorAnio]
//-- [Desempenio/PorAnio] variables
//-- [Desempenio/PorAnio] Form Management
//-- [Desempenio/PorAnio] filters
//-- [Desempenio/PorAnio] modals
//-- [Desempenio/PorAnio] utils (spinners, mensajes impresion etc)
//-- [Desempenio/PorAnio] service calls

function getBoletinAcademicoList (idAnio, idCurso) {
	
	spinnerService.show('searchDesempenioSpinner')
	academicoService.getBoletinesByAnioCurso(idAnio, idCurso)
	.then(function (response) {
		// asign result to variable
		loadListaDesempenioAnioCursoMateria(response.data);
		loadListaDesempenioAnioCurso(response.data);
		drawGraphics('desempenio');
	},
	function (response) {
		showServerError(response);
	})
	.finally(function(){
		spinnerService.hide('searchDesempenioSpinner');
	});
}

//-------------------------------------

//------------MODELOS--------------------

$scope.cuadroHonor = { //promedio historico considerando tdas las notas, faltas historicas de todos los años
	anio:'5to Año',
	curso: 'U',
	cicloLectivo: '2016',
	listaAlumnos: [
	{nombre:'John', apellido:'Lennon', nroDocumento:'464564664', cantInasist: 5, promedio: 9.85},
	{nombre:'Sebastian', apellido:'Gomez', nroDocumento:'555555555', cantInasist: 5, promedio: 9.95},
	{nombre:'David', apellido:'Guzman', nroDocumento:'35464231', cantInasist: 15, promedio: 6.00},
	{nombre:'John', apellido:'Lavezzi', nroDocumento:'555555555', cantInasist: 45, promedio: 8.85},
	{nombre:'Veronica', apellido:'Díaz', nroDocumento:'154684351', cantInasist: 55, promedio: 3.85},
	{nombre:'John', apellido:'Otamendi', nroDocumento:'55340255', cantInasist: 57, promedio: 4.85},
	{nombre:'Catalina', apellido:'Lopez', nroDocumento:'97480344', cantInasist: 52, promedio: 7.85},
	{nombre:'John', apellido:'Romero', nroDocumento:'55432255', cantInasist: 25, promedio: 6.85}
	]
};

$scope.desempenioAnual = { //toda la escuela
	// anio:'5to Año',
	// curso: 'U',
	cicloLectivo: '2016',
	cicloLectivoAnterior: '2015',
	aprobados: 30,
	debenUna: 90,
	debenDosOMas: 100,
	desempenioCicloLectivoActual: [45,64,29,56,32], //total de alumnos [insuficiente, regular, bien, muybien, excelente]
	desempenioCicloLectivoAnterior: [54,81,23,44,12]

};

$scope.listaBoletines = [ //pedir lista boletines por año y curso
{
	"idBoletinNotas": 5,
	"nombre": "Julian",
	"apellido": "Solo",
	"nroDocumento": 45667889,
	"anio": "4to año",
	"curso": "U",
	"cicloLectivo": 0,
	"listaNotas": [
	{
		"materia": "Reparación de Servidores",
		"notaTrim1": 6,
		"notaTrim2": 5,
		"notaTrim3": 8,
		"notaFinal": 6.3
	},
	{
		"materia": "Administración de Recursos",
		"notaTrim1": 4,
		"notaTrim2": 4,
		"notaTrim3": 8,
		"notaFinal": 6
	},
	{
		"materia": "Seguridad y auditoría",
		"notaTrim1": 4,
		"notaTrim2": 4,
		"notaTrim3": 6,
		"notaFinal": 5
	},
	{
		"materia": "Reparación de PC",
		"notaTrim1": 9,
		"notaTrim2": 9,
		"notaTrim3": 10,
		"notaFinal": 9
	}
	],
	"detalleInasistencias": {
		"justificadasTrim1": 2,
		"justificadasTrim2": 0,
		"justificadasTrim3": 4,
		"injustificadasTrim1": 0,
		"injustificadasTrim2": 5,
		"injustificadasTrim3": 5
	}
},{
	"idBoletinNotas": 5,
	"nombre": "Julian",
	"apellido": "Solo",
	"nroDocumento": 45667889,
	"anio": "4to año",
	"curso": "U",
	"cicloLectivo": 0,
	"listaNotas": [
	{
		"materia": "Reparación de PC",
		"notaTrim1": 8,
		"notaTrim2": 7,
		"notaTrim3": 8,
		"notaFinal": 7
	},
	{
		"materia": "Administración de Recursos",
		"notaTrim1": 9,
		"notaTrim2": 9,
		"notaTrim3": 10,
		"notaFinal": 9
	},
	{
		"materia": "Seguridad y auditoría",
		"notaTrim1": 10,
		"notaTrim2": 10,
		"notaTrim3": 10,
		"notaFinal": 10
	},
	{
		"materia": "Reparación de Servidores",
		"notaTrim1": 4,
		"notaTrim2": 4,
		"notaTrim3": 4,
		"notaFinal": 4
	}
	],
	"detalleInasistencias": {
		"justificadasTrim1": 6,
		"justificadasTrim2": 2,
		"justificadasTrim3": 1,
		"injustificadasTrim1": 1,
		"injustificadasTrim2": 1,
		"injustificadasTrim3": 1
	}
},{
	"idBoletinNotas": 5,
	"nombre": "Julian",
	"apellido": "Solo",
	"nroDocumento": 45667889,
	"anio": "4to año",
	"curso": "U",
	"cicloLectivo": 0,
	"listaNotas": [
	{
		"materia": "Reparación de PC",
		"notaTrim1": 8,
		"notaTrim2": 8,
		"notaTrim3": 8,
		"notaFinal": 8
	},
	{
		"materia": "Administración de Recursos",
		"notaTrim1": 7,
		"notaTrim2": 7,
		"notaTrim3": 8,
		"notaFinal": 7
	},
	{
		"materia": "Seguridad y auditoría",
		"notaTrim1": 5,
		"notaTrim2": 5,
		"notaTrim3": 6,
		"notaFinal": 6
	},
	{
		"materia": "Reparación de Servidores",
		"notaTrim1": 6,
		"notaTrim2": 6,
		"notaTrim3": 5,
		"notaFinal": 6
	}
	],
	"detalleInasistencias": {
		"justificadasTrim1": 5,
		"justificadasTrim2": 3,
		"justificadasTrim3": 3,
		"injustificadasTrim1": 2,
		"injustificadasTrim2": 2,
		"injustificadasTrim3": 2
	}
},{
	"idBoletinNotas": 5,
	"nombre": "Julian",
	"apellido": "Solo",
	"nroDocumento": 45667889,
	"anio": "4to año",
	"curso": "U",
	"cicloLectivo": 0,
	"listaNotas": [
	{
		"materia": "Reparación de Servidores",
		"notaTrim1": 3,
		"notaTrim2": 3,
		"notaTrim3": 4,
		"notaFinal": 5
	},
	{
		"materia": "Administración de Recursos",
		"notaTrim1": 5,
		"notaTrim2": 5,
		"notaTrim3": 6,
		"notaFinal": 6
	},
	{
		"materia": "Seguridad y auditoría",
		"notaTrim1": 3,
		"notaTrim2": 3,
		"notaTrim3": 3,
		"notaFinal": 5
	},
	{
		"materia": "Reparación de PC",
		"notaTrim1": 6,
		"notaTrim2": 6,
		"notaTrim3": 6,
		"notaFinal": 6
	}
	],
	"detalleInasistencias": {
		"justificadasTrim1": 6,
		"justificadasTrim2": 5,
		"justificadasTrim3": 1,
		"injustificadasTrim1": 1,
		"injustificadasTrim2": 1,
		"injustificadasTrim3": 1
	}
},{
	"idBoletinNotas": 5,
	"nombre": "Julian",
	"apellido": "Solo",
	"nroDocumento": 45667889,
	"anio": "4to año",
	"curso": "U",
	"cicloLectivo": 0,
	"listaNotas": [
	{
		"materia": "Reparación de Servidores",
		"notaTrim1": 6,
		"notaTrim2": 5,
		"notaTrim3": 4,
		"notaFinal": 5
	},
	{
		"materia": "Administración de Recursos",
		"notaTrim1": 6,
		"notaTrim2": 7,
		"notaTrim3": 8,
		"notaFinal": 7
	},
	{
		"materia": "Seguridad y auditoría",
		"notaTrim1": 8,
		"notaTrim2": 7,
		"notaTrim3": 6,
		"notaFinal": 7
	},
	{
		"materia": "Reparación de PC",
		"notaTrim1": 9,
		"notaTrim2": 9,
		"notaTrim3": 9,
		"notaFinal": 9
	}
	],
	"detalleInasistencias": {
		"justificadasTrim1": 3,
		"justificadasTrim2": 3,
		"justificadasTrim3": 3,
		"injustificadasTrim1": 3,
		"injustificadasTrim2": 3,
		"injustificadasTrim3": 3
	}
}
];


$scope.listaDesempenioAnioCurso = [];
$scope.listaDesempenioAnioCursoMateria = [];
$scope.listaMaterias = [];
$scope.listaAprobados = [];
$scope.listaDesaprobados = [];

var desaprobadosTotalTrim1 = {aprobados: 0, debenUna: 0, debenDosOMas: 0};
var desaprobadosTotalTrim2 = {aprobados: 0, debenUna: 0, debenDosOMas: 0};
var desaprobadosTotalTrim3 = {aprobados: 0, debenUna: 0, debenDosOMas: 0};
var desaprobadosTotal = {aprobados: 0, debenUna: 0, debenDosOMas: 0};

function loadListaDesempenioAnioCursoMateria(listaBoletines){
	angular.forEach(listaBoletines, function(boletin, key){

		var desempenioPorTrimestre = ObjectsFactory.newEstadisticaTrimestre();

		var desaprobadosTrim1 = 0;
		var desaprobadosTrim2 = 0;
		var desaprobadosTrim3 = 0;
		var desaprobadosFinal = 0;

		angular.forEach(boletin.listaNotas, function(nota, key){			

			if (_.find($scope.listaDesempenioAnioCursoMateria, ['nombre', nota.materia])) {

				if (nota.notaFinal < 6) {
					_.find($scope.listaDesempenioAnioCursoMateria, ['nombre', nota.materia]).desaprobados ++;
				} else {
					_.find($scope.listaDesempenioAnioCursoMateria, ['nombre', nota.materia]).aprobados ++;
				};

			} else{

				var desempenioMat = ObjectsFactory.newEstadisticaMateria();			

				desempenioMat.nombre = nota.materia;
				$scope.listaMaterias.push(nota.materia);

				if (nota.notaFinal < 6) {
					desempenioMat.desaprobados ++;
				} else {
					desempenioMat.aprobados ++;
				};

				$scope.listaDesempenioAnioCursoMateria.push(desempenioMat);

			};


			//if q suma lo q se lleva por trimestre y en total
			if (nota.notaTrim1 < 6) {
				desaprobadosTrim1 ++;
			} 
			if (nota.notaTrim2 < 6) {
				desaprobadosTrim2 ++;
			} 
			if (nota.notaTrim3 < 6) {
				desaprobadosTrim3 ++;
			}
			if (nota.notaFinal < 6) {
				desaprobadosFinal ++;
			} 

		});

if (desaprobadosTrim1 == 0) {
	desaprobadosTotalTrim1.aprobados++;
} else{
	if (desaprobadosTrim1 < 2) {
		desaprobadosTotalTrim1.debenUna++;
	} else{
		desaprobadosTotalTrim1.debenDosOMas++;
	};
};

if (desaprobadosTrim2 == 0) {
	desaprobadosTotalTrim2.aprobados++;
} else{
	if (desaprobadosTrim2 < 2) {
		desaprobadosTotalTrim2.debenUna++;
	} else{
		desaprobadosTotalTrim2.debenDosOMas++;
	};
};

if (desaprobadosTrim3 == 0) {
	desaprobadosTotalTrim3.aprobados++;
} else{
	if (desaprobadosTrim3 < 2) {
		desaprobadosTotalTrim3.debenUna++;
	} else{
		desaprobadosTotalTrim3.debenDosOMas++;
	};
};

if (desaprobadosFinal == 0) {
	desaprobadosTotal.aprobados++;
} else{
	if (desaprobadosFinal < 2) {
		desaprobadosTotal.debenUna++;
	} else{
		desaprobadosTotal.debenDosOMas++;
	};
};

		// if q suma si cada alumno por trimestre si lleva 0 1 o 2

	});

angular.forEach($scope.listaDesempenioAnioCursoMateria, function(element, key){
	$scope.listaAprobados.push(element.aprobados);
	$scope.listaDesaprobados.push(element.desaprobados);
});

console.log($scope.listaDesempenioAnioCursoMateria);
};

loadListaDesempenioAnioCursoMateria($scope.listaBoletines);

function loadListaDesempenioAnioCurso(listaBoletines) {
	
	angular.forEach(listaBoletines, function(boletin, key){
		var desempenioAlu = ObjectsFactory.newDesempenioAlumno();

		desempenioAlu.nombre = boletin.nombre;
		desempenioAlu.apellido = boletin.apellido;
		desempenioAlu.nroDocumento = boletin.nroDocumento;

		angular.forEach(boletin.listaNotas, function(nota, key){
			


			if (nota.notaTrim1 < 6) {
				desempenioAlu.primerTrimDesap++;
			};
			if (nota.notaTrim2 < 6) {
				desempenioAlu.segundoTrimDesap++;
			};
			if (nota.notaTrim3 < 6) {
				desempenioAlu.tercerTrimDesap++;
			};
			if (nota.notaFinal < 6) {
				desempenioAlu.totalDesap++;
			};
		});

		desempenioAlu.totalAp = boletin.listaNotas.length - desempenioAlu.totalDesap;
		desempenioAlu.totalInasist = boletin.detalleInasistencias.justificadasTrim1 + boletin.detalleInasistencias.justificadasTrim2 + boletin.detalleInasistencias.justificadasTrim3 + boletin.detalleInasistencias.injustificadasTrim1 + boletin.detalleInasistencias.injustificadasTrim2 + boletin.detalleInasistencias.injustificadasTrim3;
		$scope.listaDesempenioAnioCurso.push(desempenioAlu);
	});

console.log($scope.listaDesempenioAnioCurso);
};

loadListaDesempenioAnioCurso($scope.listaBoletines);



//-------------CHARTS---------------------

//Pie chart

function drawPieChartTodos () {
	$('#TodosPieChart').highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: 'Desempeño alumnos Enero a Diciembre de ' + $scope.desempenioAnual.cicloLectivo
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				showInLegend: true,
				dataLabels: {
					enabled: false,
					format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					style: {
						color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					}
				}                
			}
		},
		series: [{
			name: 'Cantidad',
			colorByPoint: true,
			data: [{
				name: 'Aprobados',
				y: $scope.desempenioAnual.aprobados
			}, {
				name: 'Deben 1 materia',
				y: $scope.desempenioAnual.debenUna,
				sliced: true,
				selected: true
			}, {
				name: 'Deben 2 o más materias',
				y: $scope.desempenioAnual.debenDosOMas
			}]
		}]
	});
};

//Column Chart

function drawColumnChartTodos () {
	$('#TodosColumnChart').highcharts({
		chart: {
			type: 'column'
		},
		title: {
			text: 'Desempeño agrupado por intervalos'
		},
		subtitle: {
			text: 'Escuela Santa Lucía'
		},
		xAxis: {
			categories: [
			$scope.desempenioAnual.cicloLectivo,
			$scope.desempenioAnual.cicloLectivoAnterior
			],
			crosshair: true
		},
		yAxis: {
			min: 0,
			title: {
				text: 'Cantidad de alumnos'
			}
		},
		tooltip: {
			headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			'<td style="padding:0"><b>{point.y:.1f} %</b></td></tr>',
			footerFormat: '</table>',
			shared: true,
			useHTML: true
		},
		plotOptions: {
			column: {
				pointPadding: 0.2,
				borderWidth: 0
			}
		},
		series: [{
			name: 'Insuficiente',
			data: [$scope.desempenioAnual.desempenioCicloLectivoActual[0], $scope.desempenioAnual.desempenioCicloLectivoAnterior[0]]

		}, {
			name: 'Regular',
			data: [$scope.desempenioAnual.desempenioCicloLectivoActual[1], $scope.desempenioAnual.desempenioCicloLectivoAnterior[1]]

		}, {
			name: 'Bien',
			data: [$scope.desempenioAnual.desempenioCicloLectivoActual[2], $scope.desempenioAnual.desempenioCicloLectivoAnterior[2]]

		}, {
			name: 'Muy Bien',
			data: [$scope.desempenioAnual.desempenioCicloLectivoActual[3], $scope.desempenioAnual.desempenioCicloLectivoAnterior[3]]

		}, {
			name: 'Excelente',
			data: [$scope.desempenioAnual.desempenioCicloLectivoActual[4], $scope.desempenioAnual.desempenioCicloLectivoAnterior[4]]

		}]
	});
};



function drawStackedBarAnio () {
	$('#DesempStackedBar').highcharts({
		chart: {
			type: 'column'
		},
		title: {
			text: 'Aprobados/desaprobados por materia de ' //+ $scope.statsAnioCurso.anio + ' ' + $scope.statsAnioCurso.division + ' año ' + $scope.statsAnioCurso.cicloLectivo
		},
		xAxis: {
			categories: $scope.listaMaterias
		},
		yAxis: {
			min: 0,
			title: {
				text: 'Cantidad de alumnos: ' //+ $scope.statsAnioCurso.cantAlumnos
			},
			stackLabels: {
				enabled: true,
				style: {
					fontWeight: 'bold',
					color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
				}
			}
		},
		legend: {
			align: 'right',
			x: -30,
			verticalAlign: 'top',
			y: 25,
			floating: true,
			backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
			borderColor: '#CCC',
			borderWidth: 1,
			shadow: false
		},
		tooltip: {
			headerFormat: '<b>{point.x}</b><br/>',
			pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
		},
		plotOptions: {
			column: {
				stacking: 'normal',
				dataLabels: {
					enabled: true,
					color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
					style: {
						textShadow: '0 0 3px black'
					}
				}
			}
		},
		series: [{
			name: 'Desaprobados',
			data: $scope.listaDesaprobados,
			color: 'red'
		}, {
			name: 'Aprobados',
			data: $scope.listaAprobados
		}]
	});
};

//stacked barr por trimestre

function drawStackedBarTrimestre () {
	$('#TrimestresStackedBar').highcharts({
		chart: {
			type: 'column'
		},
		title: {
            text: 'Desempeño por trimestres de ' //+ $scope.statsAnioCurso.anio + ' ' + $scope.statsAnioCurso.division + ' año ' + $scope.statsAnioCurso.cicloLectivo
        },
        xAxis: {
        	categories: ['Primer Trimestre', 'Segundo Trimestre', 'Tercer Trimestre']
        },
        yAxis: {
        	min: 0,
        	title: {
                text: 'Cantidad de alumnos: '// + $scope.statsAnioCurso.cantAlumnos
            },
            stackLabels: {
            	enabled: true,
            	style: {
            		fontWeight: 'bold',
            		color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
            	}
            }
        },
        legend: {
        	align: 'right',
        	x: -30,
        	verticalAlign: 'top',
        	y: 25,
        	floating: true,
        	backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
        	borderColor: '#CCC',
        	borderWidth: 1,
        	shadow: false
        },
        tooltip: {
        	headerFormat: '<b>{point.x}</b><br/>',
        	pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
        },
        plotOptions: {
        	column: {
        		stacking: 'normal',
        		dataLabels: {
        			enabled: true,
        			color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
        			style: {
        				textShadow: '0 0 3px black'
        			}
        		}
        	}
        },
        series: [{
        	name: 'Aprobados',
            data: [desaprobadosTotalTrim1.aprobados, desaprobadosTotalTrim2.aprobados, desaprobadosTotalTrim3.aprobados]//$scope.listaPromocionados
        }, {
        	name: 'Deben una materia',
            data: [desaprobadosTotalTrim1.debenUna, desaprobadosTotalTrim2.debenUna, desaprobadosTotalTrim3.debenUna]//$scope.listaDebenUna
        }, {
        	name: 'Deben dos o mas',
            data: [desaprobadosTotalTrim1.debenDosOMas, desaprobadosTotalTrim2.debenDosOMas, desaprobadosTotalTrim3.debenDosOMas]//$scope.listaDebenDosOMas
        }]
    });
};


//Pie chart

function drawPieChartAnual() {
	$('#AnualPieChart').highcharts({
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
            text: 'Desempeño alumnos anual '// + $scope.statsAnioCurso.anio + ' ' + $scope.statsAnioCurso.division + ' año ' + $scope.statsAnioCurso.cicloLectivo
        },
        tooltip: {
            pointFormat: '{series.name}: {point.y} <br><b>{point.percentage:.1f}%</b> del total (' //+ $scope.statsAnioCurso.cantAlumnos + ')'
        },
        plotOptions: {
        	pie: {
        		allowPointSelect: true,
        		cursor: 'pointer',
        		showInLegend: true,
        		dataLabels: {
        			enabled: false,
        			format: '<b>{point.name}</b>: {point.percentage:.1f} %',
        			style: {
        				color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
        			}
        		}                
        	}
        },
        series: [{
        	name: 'Cantidad de alumnos',
        	colorByPoint: true,
        	data: [{
        		name: 'Aprobados',
                y: desaprobadosTotal.aprobados//$scope.statsAnioCurso.desempenioAnual.aprobados
            }, {
            	name: 'Deben 1 materia',
                y: desaprobadosTotal.debenUna, //$scope.statsAnioCurso.desempenioAnual.debenUna,
                sliced: true,
                selected: true
            }, {
            	name: 'Deben 2 o más materias',
                y: desaprobadosTotal.debenDosOMas //$scope.statsAnioCurso.desempenioAnual.debenDosOMas
            }]
        }]
    });
};

}]);