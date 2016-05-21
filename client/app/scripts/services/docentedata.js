'use strict';
/* JSON PARA REALIZAR PRUEBAS DE SERVICIOS */


/* ##### DOCENTE (4) ##### */

/* PRIMERO */
angular.module('clientAppApp')
  .constant('docenteData', 

  	[
{	
	"idUsuario"     : 0,
	"nroDocumento"  : 12306952,
	"tipoDocumento" : "DNI",
	"nombre"        : "Mariano Eliseo",
	"apellido"      : "Rodriguez y Duque",
	"cuil"          : 20123069524,
    "rol"           : "DOCENTE",
	"listaTelefonos" :[
		{
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 4210055,
		"tipoTelefono"   : "fijo/casa"
		},                 
		{                  
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 155333000,
		"tipoTelefono"   : "celular"
		}
	],
	"listaMails"		:[
		{
		"idMail	      " : null,
		"direccionMail" : "m.eliseorodriguez@live.com.ar",
		"tipoMail	  " : "Personal"
		},                
		{                 
		"idMail	      " : null,
		"direccionMail" : "elisearodriguezduque@gmail.com",
		"tipoMail	  " : "Académico"
		}
	],
	"domicilio"         :{
		"idDomicilio"	: null,
		"calle"         : "Nuestra Señora del Perpetuo Socorro",
		"numero"        : 250,
		"piso"          : 0,
		"localidad"     : "Paraná",
		"dpto"          : "-",
		"departamento"  : "Paraná",
		"provincia"     : "Entre Ríos",
		"codigoPostal"  : 3100,
		"barrio"        : "Barrio Adventista del Plata por una ciudad mejor"
	},
	"listaTitulos": [
    {
        "idTitulo"         : null,
        "nombreTitulo"     : "Licenciatura en Filosofía",
        "descripcionTitulo": "Recibido en la Universidad De La Plata en el año 1979"
    },
	{
        "idTitulo"         : null,
        "nombreTitulo"     : "Teología Cristiana",
        "descripcionTitulo": "Recibido en la Seminario en el año 1982"
    }
	],
	"sexo"            : "M",
	"nombreUsuario"   : "m.eliseo.rodriguez.duque",
	"fechaNacimiento" : "Sep 23, 1956 12:00:00 AM",
	"activo"          : true
	},
	
/* SEGUNDO */
{	
	"idUsuario"     : 1,
	"nroDocumento"  : 17365441,
	"tipoDocumento" : "DNI",
	"nombre"        : "Paola Lucrecia Anabela",
	"apellido"      : "Di Marco",
	"cuil"          : 20173654417,
    "rol"           : "DOCENTE",
	"listaTelefonos" :[
		{
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 4239877,
		"tipoTelefono"   : "fijo/casa"
		},                 
		{                  
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 155987654,
		"tipoTelefono"   : "celular"
		}
	],
	"listaMails"		:[
		{
		"idMail	      " : null,
		"direccionMail" : "m.eliseorodriguez@live.com.ar",
		"tipoMail	  " : "Personal"
		},                
		{                 
		"idMail	      " : null,
		"direccionMail" : "elisearodriguezduque@gmail.com",
		"tipoMail	  " : "Académico"
		}
	],
	"domicilio"         :{
		"idDomicilio"	: null,
		"calle"         : "Dr. Enrique Carbó",
		"numero"        : 398,
		"piso"          : 0,
		"localidad"     : "Hasenkamp",
		"dpto"          : "-",
		"departamento"  : "Paraná",
		"provincia"     : "Entre Ríos",
		"codigoPostal"  : 3100,
		"barrio"        : "Caudillo Pancho Ramírez"
	},
	"listaTitulos": [
    {
        "idTitulo"         : null,
        "nombreTitulo"     : "Ingeniería en Agronomía",
        "descripcionTitulo": "Recibido en UNER en el año 1983"
    },
	{
        "idTitulo"         : null,
        "nombreTitulo"     : "Master en GIS aplicado a suelo pantanosos",
        "descripcionTitulo": "Recibido en UADER, Oro Verde en el año 2014"
    }
	],
	"sexo"            : "F",
	"nombreUsuario"   : "Paola.Di.Marco",
	"fechaNacimiento" : "Feb 10, 1960 12:00:00 AM",
	"activo"          : true
	},
    
/* TERCERO */
    {	
	"idUsuario"     : 2,
	"nroDocumento"  : 20111333,
	"tipoDocumento" : "DNI",
	"nombre"        : "Alejandro",
	"apellido"      : "Hadad",
	"cuil"          : 20201113337,
    "rol"           : "DOCENTE",
	"listaTelefonos" :[
		{
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 4239877,
		"tipoTelefono"   : "fijo/casa"
		},                 
		{                  
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 155987654,
		"tipoTelefono"   : "celular"
		}
	],
	"listaMails"		:[
		{
		"idMail	      " : null,
		"direccionMail" : "alehadad@live.com.ar",
		"tipoMail	  " : "Personal"
		}
	],
	"domicilio"         :{
		"idDomicilio"	: null,
		"calle"         : "Bv. Galvez 1200",
		"numero"        : 398,
		"piso"          : 0,
		"localidad"     : "Santa Fé",
		"dpto"          : "-",
		"departamento"  : "Santa Fé",
		"provincia"     : "Santa Fé",
		"codigoPostal"  : 3000,
		"barrio"        : "Barrio del Boulevard"
	},
	"listaTitulos": [
        {
            "idTitulo"         : null,
            "nombreTitulo"     : "Bioingeniería",
            "descripcionTitulo": "Recibido en UNL en el año 1983"
        }
	],
	"sexo"            : "F",
	"nombreUsuario"   : "ale.hadad",
	"fechaNacimiento" : "Feb 10, 1960 12:00:00 AM",
	"activo"          : true
	},
    
    
    /* CUARTO */
    {	
	"idUsuario"     : 3,
	"nroDocumento"  : 16444888,
	"tipoDocumento" : "DNI",
	"nombre"        : "Victor",
	"apellido"      : "Valotto",
	"cuil"          : 20164448887,
    "rol"           : "DOCENTE",
	"listaTelefonos" :[
		{
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 4239877,
		"tipoTelefono"   : "fijo/casa"
		},                 
		{                  
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 155987654,
		"tipoTelefono"   : "celular"
		}
	],
	"listaMails"		:[
		{
		"idMail	      " : null,
		"direccionMail" : "victor_valotto@microsoft.com.ar",
		"tipoMail	  " : "Personal"
		}
	],
	"domicilio"         :{
		"idDomicilio"	: null,
		"calle"         : "Mitre",
		"numero"        : 1325,
		"piso"          : 0,
		"localidad"     : "Paraná",
		"dpto"          : "-",
		"departamento"  : "Paraná",
		"provincia"     : "Entre Ríos",
		"codigoPostal"  : 3100,
		"barrio"        : "Parque Urquiza"
	},
	"listaTitulos": [
        {
            "idTitulo"         : null,
            "nombreTitulo"     : "Ingeniero en electrónica",
            "descripcionTitulo": "Recibido en UTN en el año 1972"
        }
	],
	"sexo"            : "F",
	"nombreUsuario"   : "victor.valotto",
	"fechaNacimiento" : "Feb 10, 1958 12:00:00 AM",
	"activo"          : true
	},
	
/* ##### DIRECTIVO (1) ##### */

/* PRIMERO */
{	
	"idUsuario"     : 4,
	"nroDocumento"  : 15966412,
	"tipoDocumento" : "DNI",
	"nombre"        : "Graciela Ema Dora",
	"apellido"      : "Diaz López",
	"cuil"          : 20159664127,
    "rol"           : "DIRECTIVO",
	"listaTelefonos" :[
		{
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 4259800,
		"tipoTelefono"   : "fijo/casa"
		},                 
		{                  
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 156987410,
		"tipoTelefono"   : "celular"
		}
	],
	"listaMails"		:[
		{
		"idMail	      " : null,
		"direccionMail" : "graciela_ema_21@live.com.ar",
		"tipoMail	  " : "Personal"
		},                
		{                 
		"idMail	      " : null,
		"direccionMail" : "emadoralopez@gmail.com",
		"tipoMail	  " : "Académico"
		}
	],
	"domicilio"         :{
		"idDomicilio"	: null,
		"calle"         : "Sagrada Concepción",
		"numero"        : 500,
		"piso"          : 2,
		"localidad"     : "Colonia Avellaneda",
		"dpto"          : "-",
		"departamento"  : "Paraná",
		"provincia"     : "Entre Ríos",
		"codigoPostal"  : 3100,
		"barrio"        : "Entre Ríos Federal"
	},
	"listaTitulos": [
    {
        "idTitulo"         : null,
        "nombreTitulo"     : "Profesorado en Matemática",
        "descripcionTitulo": "Recibido en Escuela Normal (actualmente UADER) en el año 1979"
    },
	{
        "idTitulo"         : null,
        "nombreTitulo"     : "Profesorado en Física",
        "descripcionTitulo": "Recibido en Escuela Normal (actualmente UADER) en el año 1979"
    },
	{
        "idTitulo"         : null,
        "nombreTitulo"     : "Master en Matemática Aplicada a Cálculos mediante Geolocalización",
        "descripcionTitulo": "UADER, año 2008"
    }
	],
	"sexo"            : "F",
	"nombreUsuario"   : "Paola.Di.Marco",
	"fechaNacimiento" : "Feb 10, 1960 12:00:00 AM",
	"activo"          : true
	},
 
 /* ###### DOCENTE Y DIRECTIVO (1) ##### */
 
 /* PRIMERO */
 {	
	"idUsuario"     : 5,
	"nroDocumento"  : 22695247,
	"tipoDocumento" : "DNI",
	"nombre"        : "Javier Exequiel",
	"apellido"      : "Marce",
	"cuil"          : 20226952474,
    "rol"           : "DOCENTE/DIRECTIVO",
	"listaTelefonos" :[
		{
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 4227011,
		"tipoTelefono"   : "fijo/casa"
		},                 
		{                  
		"idTelefono"     : null,
		"caracteristica" : 343,
		"nroTelefono"    : 156999888,
		"tipoTelefono"   : "celular"
		}
	],
	"listaMails"		:[
		{
		"idMail	      " : null,
		"direccionMail" : "javier_exequiel@yahoo.com.ar",
		"tipoMail	  " : "Personal"
		},            
		{                 
		"idMail	      " : null,
		"direccionMail" : "je_Marce@gmail.com",
		"tipoMail	  " : "Académico"
		}
	],
	"domicilio"         :{
		"idDomicilio"	: null,
		"calle"         : "Día del árbol",
		"numero"        : 411,
		"piso"          : 0,
		"localidad"     : "Paraná",
		"dpto"          : "-",
		"departamento"  : "Paraná",
		"provincia"     : "Entre Ríos",
		"codigoPostal"  : 3100,
		"barrio"        : "Barrio El Sol"
	},
	"listaTitulos": [
    {
        "idTitulo"         : null,
        "nombreTitulo"     : "Profesorado en Matemática",
        "descripcionTitulo": "Recibido en UADER, en el año 2010"
    },
	{
        "idTitulo"         : null,
        "nombreTitulo"     : "Maestría en geometría analítica asistida por computadora",
        "descripcionTitulo": "UADER, Oro Verde, 2013"
    }
	],
	"sexo"            : "M",
	"nombreUsuario"   : "javier.exequiel.marce",
	"fechaNacimiento" : "Mar 10, 1983 12:00:00 AM",
	"activo"          : true
	}
 ]
 );