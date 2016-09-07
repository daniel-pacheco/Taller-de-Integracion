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
 	//address: 'http://PROYECTOR-PC:8080/',
 	address: 'http://localhost:8080/',
 	// address: 'http://localhost:8099/',
 	sAlumno: 'EscuelaSantaLucia/rest/sAlumno/',
 	sAcademico: 'EscuelaSantaLucia/rest/sAcademico/',
 	sConfiguracion: 'EscuelaSantaLucia/rest/sConfiguracion/',
 	sDesempenio: 'EscuelaSantaLucia/rest/sDesempenio/',
 	sDirectivo: 'EscuelaSantaLucia/rest/sDirectivo/',
 	sDocente: 'EscuelaSantaLucia/rest/sDocente/',
 	login: 'EscuelaSantaLucia/rest/sLogin/',
 })

 .constant('AUTH_EVENTS', {
 	notAuthenticated: 'auth-not-authenticated',
 	notAuthorized: 'auth-not-authorized',
 	notFound: 'url-not-found',
 	internalError: 'internal-error'
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
 	// notas: ['.notas', 'Notas'],
 	// aula: ['.aula', 'Aula'],
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
 	// desemp1: ['.desemp', 'Desempeño1'],
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

 .constant('MANUAL',{
 	ALUMNO: 'https://drive.google.com/file/d/0B3zCRcWbTMf1Sk9oWjZWOEZmVE0/view?usp=sharing',
 	DIRECTIVO: 'https://drive.google.com/file/d/0B3zCRcWbTMf1Sk9oWjZWOEZmVE0/view?usp=sharing',
 	DOCENTE: 'https://drive.google.com/file/d/0B3zCRcWbTMf1Sk9oWjZWOEZmVE0/view?usp=sharing',
 	ADMINISTRADOR: 'https://drive.google.com/file/d/0B3zCRcWbTMf1Sk9oWjZWOEZmVE0/view?usp=sharing'
 })
/*
 .constant('PARAM_CONFIG',{
 	COMIENZO_ACADEMICO: ['COMIENZO_ACADEMICO' ,1],
 	FIN_ACADEMICO: ['FIN_ACADEMICO', 2],
 	COMIENZO_TRIM_1: ['COMIENZO_TRIM_1', 3],
 	FIN_TRIM_1: ['FIN_TRIM_1', 4], 	
 	COMIENZO_TRIM_2: ['COMIENZO_TRIM_2', 5],
 	FIN_TRIM_2: ['FIN_TRIM_2', 6],
 	COMIENZO_TRIM_3: ['COMIENZO_TRIM_3', 7],
 	FIN_TRIM_3: ['FIN_TRIM_3', 8],
 	LIMITE_DIAS_INSCRIP: ['LIMITE_DIAS_INSCRIP', 9],
 	VIS_DIAS_LLAMADO: ['VIS_DIAS_LLAMADO', 10],
 	VIS_LLAMADO: ['VIS_LLAMADO', 11]
 })*/

.constant('DESEMP_LIMITES', {
	ins: [0, 4],
	reg: [4, 6],
	bien: [6,8],
	mBien: [8,9],
	exc: [9,10]
})
.constant('appConstants', 42);
