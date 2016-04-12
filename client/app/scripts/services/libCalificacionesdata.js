'use strict';

/* ##### LIBRETA DE CALIFICACIONES (4) ##### */

/* PRIMERO */
angular.module('clientAppApp')
.constant('libCalificacionesdata', 
{	
	"idUsuario"     : 35,
	"DNIUsuario"    : 40565680,
	"nombre"        : "Mariano Eliseo",
	"apellido"      : "Rodriguez y Duque",
	"observaciones" : "-",
	"ciudad"        : "Paraná",
	"rector"        : "Roberto Richar",
	"ano"           : 5,
	"division"		: "U",
	"turno"			: "Tarde",
	"anoEscolar"	: 2016,
	"notas" :[ 
	{
		Materia: "Música",
		NotaPrimT: 1,
		NotaSeguT: 6,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Artes Visuales",
		NotaPrimT: 8,
		NotaSeguT: 6,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Matemática",
		NotaPrimT: 2,
		NotaSeguT: 5,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Formación Ética y ciudadana", 
		NotaPrimT: 3,
		NotaSeguT: 4,
		NotaTercT: 7,
		NotaFinal: 5,
		EvalDic: 9

	},
	{
		Materia: "Educación Física", 
		NotaPrimT: 10,
		NotaSeguT: 8,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Lengua Extranjera: Inglés",
		NotaPrimT: 8,
		NotaSeguT: 6,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Educación Tecnológica",
		NotaPrimT: 9,
		NotaSeguT: 10,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Historia",
		NotaPrimT: 6,
		NotaSeguT: 6,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia:"Geografía", 
		NotaPrimT: 9,
		NotaSeguT: 6,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Biología",
		NotaPrimT: 8,
		NotaSeguT: 5,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Física y Química",
		NotaPrimT: 9,
		NotaSeguT: 6,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia:"Educación en la Fé",
		NotaPrimT: 6,
		NotaSeguT: 6,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia:"Tutoría",
		NotaPrimT: 9,
		NotaSeguT: 6,
		NotaTercT: 7,
		NotaFinal: 7.33
	},
	{
		Materia: "Proyecto: Salud y Adolesc.",
		NotaPrimT: 8,
		NotaSeguT: 7,
		NotaTercT: 7,
		NotaFinal: 5,
		EvalDic: 9
	},
	],
	"Inasistencias":[
	{
		tipo: "Justificadas",
		PrimerTrim: 2,
		SegundoTrim: 3,
		TercerTrim: 5
	},
	{
		tipo: "Injustificadas",
		PrimerTrim: 8,
		SegundoTrim: 1,
		TercerTrim: 2

	},
	{
		tipo: "Advertencias Discsiplinarias",
		AdvertDiscPrimerT: "ni idea",
		AdvertDiscSegunT: "ni idea",
		AdvertDiscTercerT: "ni idea",
		AdvertDiscTotales: "ni idea",
	},
	],
}
);