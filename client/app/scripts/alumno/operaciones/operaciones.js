
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

.controller('OperacionesCtrl', function ($scope, boletinInasistenciasData, libCalificacionesdata, ModalService, mesasData) {

//-- Tooltips
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
		$scope.listaMesas = mesasData;
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

//-- Llamadas a services
$scope.register = function(mesa) {
	$scope.confirmModal("¿Desea inscribirse a la mesa de "+mesa.materia.nombre+" de "+mesa.materia.anio+mesa.materia.division+"?",$scope.inscribirse, mesa);
};

$scope.inscribirse = function (mesa){
	//inscribir al alumno
	mesa.inscripto = true;	
};
$scope.unregister = function(mesa) {
	$scope.confirmModal("¿Desea desinscribirse de la mesa de "+mesa.materia+" de "+mesa.materia.anio+mesa.materia.division+"?",$scope.desinscribirse, mesa);
};
$scope.desinscribirse = function (mesa){
	//desinscribir al alumno
	mesa.inscripto = false;		
};

$scope.solicitarCert = function(certificado)
{
	$scope.confirmModal("¿Desea solicitar el certificado de "+certificado.nombre+"?",$scope.requestCertificado, certificado);
};

$scope.requestCertificado = function(certificado){
	certificado.solicitado = true;
};

});