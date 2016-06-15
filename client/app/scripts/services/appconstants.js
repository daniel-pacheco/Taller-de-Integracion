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
 	// address: 'http://192.168.0.100:8080/',
 	address: 'http://192.168.1.4:8080/',
 	sAlumno: 'EscuelaSantaLucia/rest/sAlumno/',
 	sDesempenio: 'EscuelaSantaLucia/rest/sDesempenio/',
 	sDirectivo: 'EscuelaSantaLucia/rest/sDirectivo/',
 	sDocente: 'EscuelaSantaLucia/rest/sDocente/',
 	login: 'EscuelaSantaLucia/rest/sLogin/login'
 })

 .constant('AUTH_EVENTS', {
 	notAuthenticated: 'auth-not-authenticated',
 	notAuthorized: 'auth-not-authorized'
 })
 
 .constant('USER_ROLES', {
 	admin: 'ADMINISTRADOR',
 	docente: 'DOCENTE',
 	alumno: 'ALUMNO',
 	directivo: 'DIRECTIVO'
 })

 .constant('MENU_ALUMNO',{ //la dirección relativa entre los estados anidados se separa por .
 	operaciones: ['.operaciones', 'Operaciones'],
 	micuenta: ['.micuenta', 'Mi Cuenta'],
 	aula: ['.aula', 'Aula']
 })

.constant('LANDING_ROUTES',{ //la dirección relativa entre los estados anidados se separa por .
	ADMINISTRADOR: 'administrador.principal',
	DOCENTE: 'docente.principal',
	ALUMNO: 'alumno.operaciones',
	DIRECTIVO: 'directivo.alumnado'
})

 .constant('MENU_DIRECTIVO',{ //la dirección relativa entre los estados anidados se separa por .
 	alumnado: ['.alumnado', 'Alumnado'],
 	materias: ['.materias', 'Materias'],
 	docente: ['.docente', 'Docente'],
 	anio: ['.anio', 'Año'],
 	desemp: ['.desemp', 'Desempeño'],
 	micuenta: ['.micuenta', 'Mi Cuenta'],
 	test: ['.test', 'Test']
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
 	VALORES: [0.25, 0.5, 1.0],
 	JUSTIFICADA: [
 		["Injustificada", "I"],
	 	["Justificada", "J"]
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
