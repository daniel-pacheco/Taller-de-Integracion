
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

.controller('OperacionesCtrl', ['$scope', 'alumnoService', 'desempenioService', 'ModalService', 'spinnerService', function ($scope, alumnoService, desempenioService, ModalService, spinnerService) {

//-- [operaciones]
//-- [operaciones] variables
$scope.titulo = 'Operaciones';

//-- [operaciones] Form Management

$scope.tooltip = {
	tooltipRegister : {
		'title' : 'Inscribirse a la mesa'
	},
	tooltipUnregister : {
		'title' : 'Desincsribirse de esta mesa'
	},
	tooltipCertif : {
		'title' : 'Solicitar certificado'
	},
	tooltipCertifSolicitado : {
		'title' : 'Este certificado ya se encuentra solicitado'
	},
	tooltipCertifNoSolicitado : {
		'title' : 'Todavía no se ha solicitado este certificado'
	} 
};

$scope.seleccionar = function (id){
	$scope.examenes = false;
	$scope.certificados = false;
	$scope.desemp = false;

	switch (id){
		case 'desemp':
		$scope.subtitle = "Desempeño";
		$scope.desemp = true;
		setActiveAlu(1);
		break;
		case 'examenes':
		$scope.subtitle = "Exámenes";
		$scope.listaMesas = mesasData;
		$scope.examenes = true;
		setActiveAlu(2);
		break;
		case 'certificados':
		$scope.subtitle = "Certificados";
		$scope.certificados = true;
		setActiveAlu(3);
		break;
	}
};

$scope.$on('$viewContentLoaded', function(){
	$scope.seleccionar("desemp");
	initCall();
});

function setActiveAlu (menuItemAlu) {
	$scope.activeMenuIzqAlu = menuItemAlu;
};

/*collapse*/
$scope.panels = {};
$scope.panels.activePanel = 0;

$scope.multiplePanels = {
	activePanels: [null]
};
//-- [operaciones] filters
//-- [operaciones] modals

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
  //console.log(response);
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
  //console.log(response);
  var msg = message;

  if (response.data) {
  	msg += ' ' + response.data;
  };      
  $scope.showMessage(msg, 'Operación exitosa' , true);
};


//-- [operaciones] utils
//-- [operaciones] service calls

//-- [operaciones/desempenio]
//-- [operaciones/desempenio] variables
$scope.totalIn = [];
$scope.totalInJ = [];
$scope.totalInI = [];
//-- [operaciones/desempenio] Form Management
//-- [operaciones/desempenio] filters
//-- [operaciones/desempenio] modals
//-- [operaciones/desempenio] utils

$scope.confirmInscribirse = function(mesa) {
	$scope.confirmModal("¿Desea inscribirse a la mesa de "+mesa.materia.nombre+" de "+mesa.materia.anio+mesa.materia.division+"?",$scope.inscribirse, mesa);
};

$scope.confirmDesinscribirse = function(mesa) {
	$scope.confirmModal("¿Desea desinscribirse de la mesa de "+mesa.materia+" de "+mesa.materia.anio+mesa.materia.division+"?",$scope.desinscribirse, mesa);
};

$scope.confirmSolicitarCert = function(certificado)
{
	$scope.confirmModal("¿Desea solicitar el certificado de "+certificado.nombre+"?",$scope.requestCertificado, certificado);
};

$scope.calcTotalInasistencias = function(arreglo){

  $scope.totalIn.length = 0;
  $scope.totalInJ.length = 0;
  $scope.totalInI.length = 0;
  $scope.totalInJ[0] = 0;
  $scope.totalInI[0] = 0;
  
  arreglo.forEach(function(value, index, array){
    var rdo = 0;

    if (index > 0) {
      rdo = $scope.totalIn[index - 1];
    };

    $scope.totalIn.push((parseFloat(value.cantidad) + parseFloat(rdo)).toFixed(2));

    if(value.justificada){
        $scope.totalInJ.push((parseFloat(value.cantidad) + parseFloat(_.last($scope.totalInJ))).toFixed(2));
      }else {
        $scope.totalInI.push((parseFloat(value.cantidad) + parseFloat(_.last($scope.totalInI))).toFixed(2));
      }
    });
};

//-- [operaciones/desempenio] service calls
function inscribirse (mesa){
	//inscribir al alumno
	mesa.inscripto = true;	
};
function desinscribirse (mesa){
	//desinscribir al alumno
	mesa.inscripto = false;		
};
function solicitarCertificado (certificado){
	certificado.solicitado = true;
};

$scope.getPlanillaInasistencias = function () {
	if (!_.includes($scope.multiplePanels.activePanels, (0))) {
		spinnerService.show('searchOperacionesSpinner');
		desempenioService.getBoletinInasistByDni($scope.alumno.nroDocumento)
		.then(
			function(response){
				$scope.boletinInasistencias = response.data;
				$scope.calcTotalInasistencias($scope.boletinInasistencias.listaInasistencias);
			},
			function(response){
				showServerError(response);
			})
		.finally(function(){
			spinnerService.hide('searchOperacionesSpinner');
		})
	}
}

function initCall () {
	spinnerService.show('searchOperacionesSpinner');
	alumnoService.getMyData()
	.then(
		function(response) {
			// console.log(response);
			$scope.alumno = response.data;
			$scope.alumno.sexo = ($scope.alumno.sexo === 'M') ? 'Masculino' : 'Femenino';
		},
		function(response){
			showServerError(response);
		})
	.finally(function(){
		spinnerService.hide('searchOperacionesSpinner');
	})
}; 

//-- [operaciones/sub-seccion]
//-- [operaciones/sub-seccion] variables
//-- [operaciones/sub-seccion] Form Management
//-- [operaciones/sub-seccion] filters
//-- [operaciones/sub-seccion] modals
//-- [operaciones/sub-seccion] utils
//-- [operaciones/sub-seccion] service calls




//-- filters






// --Test
$scope.listaPrevias = [
{	"materia": 'Matemática', 
"anioCursado": '2008', 
"observacion": 'Usted adeuda esta materia',	
"ano": 4,
"division": "U"
},
{	"materia": 'Lengua', 
"anioCursado": '2010', 
"observacion": 'Usted adeuda esta materia',	
"ano": 2,
"division": "U"
},
{	"materia": 'Plástica', 
"anioCursado": '1995', 
"observacion": 'Usted adeuda esta materia',	
"ano": 2,
"division": "U"
},
{	"materia": 'Matemática', 
"anioCursado": '2009', 
"observacion": 'Usted adeuda esta materia',	
"ano": 6,
"division": "U"
},
{	"materia": 'Química', 
"anioCursado": '2009', 
"observacion": 'Usted adeuda esta materia',	
"ano": 3,
"division": "U"
}
];

$scope.listaCertificados = [
{
	"nombre": 'Alumno regular',
	"solicitado": true
},
{
	"nombre": 'Inscripción al año académico',
	"solicitado": false
},
{
	"nombre": 'Inscripción a materia previa',
	"solicitado": false
}
];
//-- Modals


//-- Llamadas a services


}]);