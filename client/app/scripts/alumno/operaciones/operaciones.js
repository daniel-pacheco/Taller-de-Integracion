
angular.module('clientAppApp')
.config(function($stateProvider) {
	$stateProvider
	.state('alumno.operaciones', {
		url: '/operaciones',
		templateUrl: 'scripts/alumno/operaciones/operaciones.html',
		controller: 'OperacionesCtrl',
		data: {
			pageTitle: 'Operaciones'
		}
	});
})

.controller('OperacionesCtrl', function ($scope, boletinInasistenciasData, libCalificacionesdata, ModalService) {

//-- filters
	$scope.titulo = 'Operaciones';
	$scope.desemp = true;
	$scope.subtitle = "Desempeño";
	$scope.boletinInasistencias = boletinInasistenciasData;
	$scope.libCalificaciones = libCalificacionesdata;

	$scope.seleccionar = function (id){
		switch (id){
			case 'desemp':
			$scope.examenes = false;
			$scope.subtitle = "Desempeño";
			$scope.certificados = false;
			$scope.desemp = true;
			break;
			case 'examenes':
			$scope.certificados = false;
			$scope.subtitle = "Exámenes";
			$scope.desemp = false;
			$scope.examenes = true;
			break;
			case 'certificados':
			$scope.examenes = false;
			$scope.desemp = false;
			$scope.subtitle = "Certificados";
			$scope.certificados = true;
			break;
		}
	}

	$scope.activeMenuIzqAlu = 1;
	$scope.setActiveAlu = function(menuItemAlu) {
		$scope.activeMenuIzqAlu = menuItemAlu;
	};

/*collapse*/
$scope.panels = {};
$scope.panels.activePanel = 0;

$scope.multiplePanels = {
	activePanels: [null]
};

// --Test
$scope.listaPrevias = [
	{	"materia": 'Matemática', 
		"anioCursado": '2008', 
		"observacion": 'Usted adeuda esta materia'
	},
	{	"materia": 'Lengua', 
		"anioCursado": '2010', 
		"observacion": 'Usted adeuda esta materia'
	},
	{	"materia": 'Plástica', 
		"anioCursado": '1995', 
		"observacion": 'Usted adeuda esta materia'
	},
	{	"materia": 'Matemática', 
		"anioCursado": '2009', 
		"observacion": 'Usted adeuda esta materia'
	}
];
//-- Modals
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
});