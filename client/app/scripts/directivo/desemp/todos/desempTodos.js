angular.module('clientAppApp')
 .config(function($stateProvider) {
 	$stateProvider
 	.state('directivo.desemp.todos', {
 		url: '/todos',
 		templateUrl: 'scripts/directivo/desemp/todos/desempTodos.html',
 		controller: 'DesempTodosCtrl',
 		data: {
 			pageTitle: 'Todos'
 		}
 	});
 })
 .controller('DesempTodosCtrl', function ($scope, CURSOS) {
  
  $scope.titulo = "Todos Controler";

  
//Pie chart

$(function () {
    $('#TodosPieChart').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Desempeño alumnos Enero a Diciembre de 2015'
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
                y: 56.33
            }, {
                name: 'Deben 1 materia',
                y: 24.03,
                sliced: true,
                selected: true
            }, {
                name: 'Deben 2 o más materias',
                y: 10.38
            }]
        }]
    });
});

//Column Chart

$(function () {
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
                '2015',
                '2016'
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
                '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
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
            data: [49.9, 71.5]

        }, {
            name: 'Regular',
            data: [83.6, 78.8]

        }, {
            name: 'Bien',
            data: [48.9, 38.8]

        }, {
            name: 'Muy Bien',
            data: [42.4, 33.2]

        }, {
            name: 'Excelente',
            data: [42.4, 33.2]

        }]
    });
});
  })