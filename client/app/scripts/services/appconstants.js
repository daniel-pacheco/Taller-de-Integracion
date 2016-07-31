'use strict';

/**
 * @ngdoc service
 * @name clientAppApp.appConstants
 * @description
 * # appConstants
 * Constant in the clientAppApp.
 */
 angular.module('clientAppApp')
 .constant('SERVER', {
 	// address: 'http://192.168.1.27:8080/',
 	address: 'http://localhost:8080/',
 	sAlumno: 'EscuelaSantaLucia/rest/sAlumno/',
 	sAcademico: 'EscuelaSantaLucia/rest/sAcademico/',
 	sDesempenio: 'EscuelaSantaLucia/rest/sDesempenio/',
 	sDirectivo: 'EscuelaSantaLucia/rest/sDirectivo/',
 	sDocente: 'EscuelaSantaLucia/rest/sDocente/',
 	login: 'EscuelaSantaLucia/rest/sLogin/',
 })

 .constant('AUTH_EVENTS', {
 	notAuthenticated: 'auth-not-authenticated',
 	notAuthorized: 'auth-not-authorized',
 	notFound: 'url-not-found'
 })
 
 .constant('USER_ROLES', {
 	admin: 'ADMINISTRADOR', 	
 	alumno: 'ALUMNO',
 	directivo: 'DIRECTIVO',
 	docente: 'DOCENTE'
 })

 .constant('MENU_ADMIN',{ //la dirección relativa entre los estados anidados se separa por .
 	configuracion: ['.configuracion', 'Configuracion']
 	// micuenta: ['.micuenta', 'Mi Cuenta']
 })

 .constant('MENU_ALUMNO',{ //la dirección relativa entre los estados anidados se separa por .
 	operaciones: ['.operaciones', 'Operaciones'],
 	micuenta: ['.micuenta', 'Mi Cuenta']
 	//aula: ['.aula', 'Aula']
 })

 .constant('MENU_DOCENTE',{ //la dirección relativa entre los estados anidados se separa por .
 	materias: ['.materias', 'Materias'],
 	notas: ['.notas', 'Notas'],
 	aula: ['.aula', 'Aula'],
 	micuenta: ['.micuenta', 'Mi Cuenta'],
 })

.constant('LANDING_ROUTES',{ //la dirección relativa entre los estados anidados se separa por .
	ADMINISTRADOR: 'administrador.configuracion',
	DOCENTE: 'docente.materias',
	ALUMNO: 'alumno.operaciones',
	DIRECTIVO: 'directivo.alumnado'
})

 .constant('MENU_DIRECTIVO',{ //la dirección relativa entre los estados anidados se separa por .
 	alumnado: ['.alumnado', 'Alumnado'],
 	materias: ['.materias', 'Materias'],
 	docente: ['.docente', 'Personal'],
 	anio: ['.anio', 'Año'],
 	llamados: ['.llamados', 'Llamados'],
 	desemp1: ['.desemp', 'Desempeño1'],
 	desemp: ['.desempenio', 'Desempeño'],
 	micuenta: ['.micuenta', 'Mi Cuenta'],
 	test: ['.test', '']
 })
 
 .constant('CURSOS',{
 	todos: ['.todos' ,'Todos'],
 	primero: ['.primero', 'Primero'],
 	segundo: ['.segundo','Segundo'],
 	tercero: ['.tercero','Tercero'],
 	cuarto: ['.cuarto','Cuarto'],
 	quinto: ['.quinto','Quinto'],
 	sexto: ['.sexto','Sexto']
 })

 .constant('INASISTENCIAS',{
 	CONCEPTO:["Clases", "Ed. Física", "Llegada tarde", "Se retira", "Otro"],
 	VALORES: [0.25, 0.5, 0.75, 1.0],
 	JUSTIFICADA: [
 		["Injustificada", false],
	 	["Justificada", true]
 	]
 })

 .constant('CONTACTOS', {
 	TELEFONO: ["Celular", "Casa", "Padre", "Madre", "Otro"],
 	MAIL: ["Propio", "Padre", "Madre", "Otro"],
 	TELEFONOD: ["Celular", "Casa", "Conyuge", "Otro"],
 	MAILD: ["Personal", "Conyuge", "Otro"]
 })

 .constant('DESEMP_LIMITES', {
	ins: [0, 4], //[,)
	reg: [4, 6], //[,)
 bien: [6,8],
 mBien: [8,9],
 exc: [9,10]
})
 .constant('appConstants', 42);
