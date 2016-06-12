'use strict';
/* JSON PARA REALIZAR PRUEBAS DE SERVICIOS */


/* ##### DOCENTE (4) ##### */

/* PRIMERO */
angular.module('clientAppApp')
.constant('materiasData', 

	[
	{
		"idMateria": 0,
		"ano": 2,
		"division": "U",
		"nombre": "Física y química",
		"descripcion": "Materia del plan 2010 1er año",
		"docenteTitular": "Ana Luna",
		"docenteSuplente": "Alicia Quenari",
		"area": {
			"idArea": 4,
			"nombre": "Hay química"
		},
		"activo": true
	},
	{
		"idMateria": 1,
		"ano": 3,
		"division": "U",
		"nombre": "Música",
		"descripcion": "Materia del plan 2010 3to año",
		"docenteTitular": "Gisela Franco",
		"docenteSuplente": "",
		"area": {
			"idArea": 3,
			"nombre": "lalala"
		},
		"activo": true
	},
	{
		"idMateria": 2,
		"ano": 2,
		"division": "U",
		"nombre": "Lengua",
		"descripcion": "Materia del plan 2010 2to año",
		"docenteTitular": "Lorena Gomez",
		"docenteSuplente": "Marita Gianotti",
		"area": {
			"idArea": 2,
			"nombre": "jijiji"
		},
		"activo": true
	},
	{
		"idMateria": 3,
		"ano": 6,
		"division": "U",
		"nombre": "Matemática",
		"descripcion": "-",
		"docenteTitular": "Marta Blanco",
		"docenteSuplente": "Marcela Lalela",
		"area": {
			"idArea": 0,
			"nombre": "peperepe"
		},
		"activo": true
	},
	{
		"idMateria": 4,
		"ano": 3,
		"division": "U",
		"nombre": "Historia",
		"descripcion": "Materia de prueba",
		"docenteTitular": "Gloria Herrlein",
		"docenteSuplente": "",
		"area": {
			"idArea": 8,
			"nombre": "sin área"
		},
		"activo": true
	},
	{
		"idMateria": 5,
		"ano": 1,
		"division": "U",
		"nombre": "Educación física",
		"descripcion": "Materia del plan 2010 5to año",
		"docenteTitular": "Roberto Smith",
		"docenteSuplente": "Mariana Pagola",
		"area": {
			"idArea": 12,
			"nombre": "Recreación"
		},
		"activo": true
	},
	{
		"idMateria": 6,
		"ano": 1,
		"division": "U",
		"nombre": "Matemática",
		"descripcion": "Materia del plan 2010 6to año",
		"docenteTitular": "Marta Blanco",
		"docenteSuplente": "Marcela Lalela",
		"area": {
			"idArea": 0,
			"nombre": "peperepe"
		},
		"activo": true
	},
	]
	);