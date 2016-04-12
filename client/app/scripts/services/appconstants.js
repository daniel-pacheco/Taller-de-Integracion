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
 	address : 'http://192.168.1.3:8080/',
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

 .constant('MENU_DIRECTIVO',{ //la dirección relativa entre los estados anidados se separa por .
 	alumnado: ['.alumnado', 'Alumnado'],
 	materias: ['.materias', 'Materias'],
 	docente: ['.docente', 'Docente'],
 	anio: ['.anio', 'Año'],
 	desemp: ['.desemp', 'Desempeño'],
 	test: ['.test', 'Test']
 })
 
 .constant('CURSOS',{
 	todos: 'Todos',
 	primero: 'Primero',
 	segundo: 'Segundo',
 	tercero: 'Tercero',
 	cuarto: 'Cuarto',
 	quinto: 'Quinto',
 	sexto: 'Sexto'
 })

 .constant('appConstants', 42);
