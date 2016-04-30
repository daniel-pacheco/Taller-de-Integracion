angular.module('clientAppApp')
.config(function($stateProvider) {
	$stateProvider
	.state('directivo.desemp.primero', {
		url: '/primero',
		templateUrl: 'scripts/directivo/desemp/primero/desempPrimero.html',
		controller: 'DesempPrimeroCtrl',
		data: {
			pageTitle: 'Primero'
		}
	});
})
.controller('DesempPrimeroCtrl', function ($scope, modalService, ObjectsFactory, CURSOS) {

	$scope.titulo = "Primero Controler";

	$scope.calcNota = function(n1, n2, n3){
		return Number((n1 + n2 + n3)/3).toFixed(2);
	};


var addDiezMaterias = function(materias){

    for (var i = 0; i < 10; i++) {
        var aprobados = modalService.getRandomInt(0,materias.cantAlumnos);

        var materia = ObjectsFactory.newEstadisticaMateria();
        materia.nombre = modalService.makeId(6);
        materia.aprobados = aprobados;
        materia.desaprobados = (materias.cantAlumnos - aprobados);

        materias.materias.push(materia);
    };
}

var addTrimestres = function(materias){
    var aprobados = modalService.getRandomInt(0,materias.cantAlumnos);

    for (var i = 0; i < 3; i++) {
        
        var trimestre = ObjectsFactory.newEstadisticaTrimestre();
        trimestre.aprobados = aprobados;
        trimestre.debenUna = modalService.getRandomInt(0,(materias.cantAlumnos - aprobados));
        trimestre.debenDosOMas = (materias.cantAlumnos - (aprobados + trimestre.debenUna));

        materias.trimestres.push(trimestre);
    }

    var desempenio = ObjectsFactory.newEstadisticaTrimestre();
    desempenio.aprobados = aprobados;
    desempenio.debenUna = modalService.getRandomInt(0,(materias.cantAlumnos - aprobados));
    desempenio.debenDosOMas = (materias.cantAlumnos - (aprobados + desempenio.debenUna));

    materias.desempenioAnual = desempenio;


}

$scope.statsAnioCurso = ObjectsFactory.newEstadisticaAnioCurso();
$scope.statsAnioCurso.cicloLectivo = 2016;
$scope.statsAnioCurso.anio = 'Primero';
$scope.statsAnioCurso.cantAlumnos = modalService.getRandomInt(15,30);
addDiezMaterias($scope.statsAnioCurso);
addTrimestres($scope.statsAnioCurso);

console.log($scope.statsAnioCurso);


//graficas generales (no especifican materia)
//stackedbarchart desempeño año

$scope.listaMaterias = [];
$scope.listaAprobados = [];
$scope.listaDesaprobados = [];
$scope.listaPromocionados = [];
$scope.listaDebenUna = [];
$scope.listaDebenDosOMas = [];

function listarMaterias(materia, indice, arreglo){
    $scope.listaMaterias.push(materia.nombre);
    $scope.listaAprobados.push(materia.aprobados);
    $scope.listaDesaprobados.push(materia.desaprobados);
}

function listarTrimestres(trimestre, indice, arreglo){
    $scope.listaPromocionados.push(trimestre.aprobados);
    $scope.listaDebenUna.push(trimestre.debenUna);
    $scope.listaDebenDosOMas.push(trimestre.debenDosOMas);
}

$scope.statsAnioCurso.materias.forEach(listarMaterias);
$scope.statsAnioCurso.trimestres.forEach(listarTrimestres);
console.log($scope.listaMaterias);
console.log($scope.listaDesaprobados);
console.log($scope.listaAprobados);
console.log($scope.listaPromocionados);
console.log($scope.listaDebenUna);
console.log($scope.listaDebenDosOMas);

$(function () {
    $('#DesempStackedBar').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Aprobados/desaprobados por materia de ' + $scope.statsAnioCurso.anio + ' ' + $scope.statsAnioCurso.division + ' año ' + $scope.statsAnioCurso.cicloLectivo
        },
        xAxis: {
            categories: $scope.listaMaterias
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Cantidad de alumnos: ' + $scope.statsAnioCurso.cantAlumnos
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
});

//stacked barr por trimestre

$(function () {
    $('#TrimestresStackedBar').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Desempeño por trimestres de ' + $scope.statsAnioCurso.anio + ' ' + $scope.statsAnioCurso.division + ' año ' + $scope.statsAnioCurso.cicloLectivo
        },
        xAxis: {
            categories: ['Primer Trimestre', 'Segundo Trimestre', 'Tercer Trimestre']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Cantidad de alumnos: ' + $scope.statsAnioCurso.cantAlumnos
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
            data: $scope.listaPromocionados
        }, {
            name: 'Deben una materia',
            data: $scope.listaDebenUna
        }, {
            name: 'Deben dos o mas',
            data: $scope.listaDebenDosOMas
        }]
    });
});

//Pie chart

$(function () {
    $('#AnualPieChart').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Desempeño alumnos anual ' + $scope.statsAnioCurso.anio + ' ' + $scope.statsAnioCurso.division + ' año ' + $scope.statsAnioCurso.cicloLectivo
        },
        tooltip: {
            pointFormat: '{series.name}: {point.y} <br><b>{point.percentage:.1f}%</b> del total (' + $scope.statsAnioCurso.cantAlumnos + ')'
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
                y: $scope.statsAnioCurso.desempenioAnual.aprobados
            }, {
                name: 'Deben 1 materia',
                y: $scope.statsAnioCurso.desempenioAnual.debenUna,
                sliced: true,
                selected: true
            }, {
                name: 'Deben 2 o más materias',
                y: $scope.statsAnioCurso.desempenioAnual.debenDosOMas
            }]
        }]
    });
});


//graficas especificando materias
//pieChartDrillDown materia

	$(function () {
    // Create the chart
    $('#MateriaDrillDown').highcharts({
        chart: {
            type: 'pie'
        },
        title: {
            text: 'Materia XXXXXX aprobados/desaprobados primer año U 2015'
        },
        subtitle: {
            text: 'Click en el área para detalles'
        },
        plotOptions: {
            series: {
            	showInLegend: true,
                dataLabels: {
                    enabled: false,
                    format: '{point.name}: {point.y:.1f}%'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> del total<br/>'
        },
        series: [{
            name: 'Materias',
            colorByPoint: true,
            data: [{
                name: 'Aprobados',
                y: 66.33,
                drilldown: 'Rendimiento aprobados'
            }, {
                name: 'Desaprobados',
                y: 33.67,
                drilldown: 'Rendimiento reprobados'
            }]
        }],
        drilldown: {
            series: [{
                name: 'Aprobados',
                id: 'Rendimiento aprobados',
                data: [
                    ['Excelente', 31.13],
                    ['Muy bien', 19.26],
                    ['Bien', 14.54]                    
                ]
            }, {
                name: 'Reprobados',
                id: 'Rendimiento reprobados',
                data: [
                    ['Regular', 23],
                    ['Insuficiente', 10.67]
                ]
            }]
        },
        lang: {
        drillUpText: '◁ volver a {series.name}'
    }
    });
});

//stacked historic barchart

$(function () {
    $('#MateriaHistoricoStackedBar').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Desempeño historico de la división en la materia XXXX'
        },
        xAxis: {
            categories: ['2011', '2012', '2013', '2014', '2015']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Cantidad de Alumnos'
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
            data: [15, 3, 4, 5, 2],
            color: 'red'
        }, {
            name: 'Aprobados',
            data: [8, 21, 19, 20, 20]
        }]
    });
});

	
	$scope.alumnos = //aca va por cantidad de materias desaprobadas
	[{nro:'1', name:'John', surName:'Lennon', DNI:'46456466', primerTrim: 0, segundoTrim: 1, tercerTrim: 0, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'1', name:'Sebastian', surName:'Lennon', DNI:'55555555', primerTrim: 0, segundoTrim: 1, tercerTrim: 1, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'2', name:'David', surName:'Lennon', DNI:'35464231', primerTrim: 0, segundoTrim: 1, tercerTrim: 0, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'3', name:'John', surName:'Lennon', DNI:'55555555', primerTrim: 0, segundoTrim: 0, tercerTrim: 0, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'4', name:'Veronica', surName:'Lennon', DNI:'15468435', primerTrim: 1, segundoTrim: 0, tercerTrim: 2, diciembre: 1, marzo: 0, cantFaltas: 14},
	{nro:'5', name:'John', surName:'Lennon', DNI:'55555555', primerTrim: 2, segundoTrim: 0, tercerTrim: 1, diciembre: 1, marzo: 0, cantFaltas: 14},
	{nro:'6', name:'Catalina', surName:'Lennon', DNI:'55555555', primerTrim: 2, segundoTrim: 5, tercerTrim: 3, diciembre: 1, marzo: 2, cantFaltas: 14},
	{nro:'7', name:'John', surName:'Lennon', DNI:'55555555', primerTrim: 3, segundoTrim: 2, tercerTrim: 1, diciembre: 2, marzo: 1, cantFaltas: 14}];

	$scope.alumnosMat = 
	[{nro:'1', name:'John', surName:'Lennon', DNI:'46456466', primerTrim: 8, segundoTrim: 9, tercerTrim: 7, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'1', name:'Sebastian', surName:'Lennon', DNI:'55555555', primerTrim: 5, segundoTrim: 9, tercerTrim: 8, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'2', name:'David', surName:'Lennon', DNI:'35464231', primerTrim: 3, segundoTrim: 8, tercerTrim: 5, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'3', name:'John', surName:'Lennon', DNI:'55555555', primerTrim: 9, segundoTrim: 7, tercerTrim: 6, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'4', name:'Veronica', surName:'Lennon', DNI:'15468435', primerTrim: 7, segundoTrim: 6, tercerTrim: 4, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'5', name:'John', surName:'Lennon', DNI:'55555555', primerTrim: 8, segundoTrim: 4, tercerTrim: 7, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'6', name:'Catalina', surName:'Lennon', DNI:'55555555', primerTrim: 2, segundoTrim: 5, tercerTrim: 3, diciembre: 0, marzo: 0, cantFaltas: 14},
	{nro:'7', name:'John', surName:'Lennon', DNI:'55555555', primerTrim: 6, segundoTrim: 9, tercerTrim: 6, diciembre: 0, marzo: 0, cantFaltas: 14}];

})