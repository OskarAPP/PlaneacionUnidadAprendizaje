$(document).ready(function() {
	var x = $("#x").val();
	if(x=="academia"){
		tablaAcademias();
	}
	if(x=="materias"){
		tablaMaterias();
	}
	if(x=="docentes"){
		tablaDocentes();
	}
	tablaCarrerasMaterias();
	if(x=="carreras"){
		tablaCarreras();
	}
	if(x=="especificas"){
		tablaEspecificas();
	}
	if(x=="genericas"){
		tablaGenericas();
	}
	obtenerCarreras();	
});

//Modulo de docentes
function obtenerCarreras(){
	var idFacultad = $("#facultad").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=carreras&idFacultad="+idFacultad,
	   success: function(msg){
	     	$("#carreras").html(msg);
	    }
	});
}

function cargo(){
	var idCargo = $("#tipo").val();
	var idFacultad = $("#facultad").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=cargo&idCargo="+idCargo+"&idFacultad="+idFacultad,
	   success: function(msg){
	     	$("#cargo").html(msg);
	    }
	});
}

function tablaDocentes(){
	var docente=$("#nombreDocente").val();
	var orden = $("#orden").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=tablaDocentes&orden="+orden+"&nombreDocente="+docente,
	   success: function(msg){
	     	$("#listaDocentes").html(msg);
	    }
	});
}

function actualizarDocente(idDocente){
	var nombre= $("#nombre"+idDocente).html();
	var apaterno= $("#apaterno"+idDocente).html();
	var amaterno= $("#amaterno"+idDocente).html();
	var correo= $("#correo"+idDocente).html();
	var prefijo= $("#prefijo"+idDocente).html();
	var valores="&docenteDTO.idDocente="+idDocente+"&docenteDTO.nombre="+nombre+"&docenteDTO.apellidoPaterno="+apaterno
	+"&docenteDTO.apellidoMaterno="+amaterno+"&docenteDTO.correo="+correo+"&docenteDTO.prefijo="+prefijo;
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=updateDocente"+valores,
	   success: function(msg){
		   $("#mensaje").html(msg);
	    }
	});
}

function cambiarEstado(idDocente,estado, idAcceso){
	var valores="&accesoDTO.idDocente="+idDocente+"&accesoDTO.estado="+estado+"&accesoDTO.idAcceso="+idAcceso;
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=cambiarEstadoDocentes"+valores,
	   success: function(msg){
		   tablaAccesos(idDocente);
	     	$("#mensaje").html(msg);
	    }
	});
}

function cambiarFacultad(idDocente, facultad, matAntigua){
	var id=$("#"+facultad).val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=cambiarFacultad&docenteDTO.idDocente="+idDocente+"&alerta="+id+"&idFacultad="+matAntigua,
	   success: function(msg){
		   $("#mensaje").html(msg);
	   }
	});
}

function eliminarDocente(idDocente,idCargo){
    $.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	var valores="&docenteDTO.idDocente="+idDocente+"&docenteDTO.idTipo="+idCargo;
            	$.ajax({
            	   type: "POST",
            	   url: "Administrador.action",
            	   data: "tipoAccion=eliminarDocente"+valores,
            	   success: function(msg){
            	     	tablaDocentes();
            	     	 $("#mensaje").html(msg);
            	    }
            	});
            },
            cancelar: function () {
                $.alert('Proceso Cancelado!');
            }
        }
    });
}

function eliminarDocenteFacultad(idDocente,idFacultad,idCargo){
	$.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	var valores="&docenteDTO.idDocente="+idDocente+"&facultadDTO.facultad="
            	+idFacultad+"&docenteDTO.idTipo="+idCargo;
            	$.ajax({
            	   type: "POST",
            	   url: "Administrador.action",
            	   data: "tipoAccion=eliminarDocenteFacultad"+valores,
            	   success: function(msg){
            	     	tablaDocentes();
            	     	 $("#mensaje").html(msg);
            	    }
            	});
            },
            cancelar: function () {
                $.alert('Proceso Cancelado!');
            }
        }
    });
}

function generarFacultad(idDocente) {
	var datos = "tipoAccion=generarFacultad&docenteDTO.idDocente="+idDocente+"&idFacultad=1";
	$.ajax({
		type: "POST",
		url: "Administrador.action",
		data: datos,
		success: function(msg){
	     	tablaDocentes();
	     	 $("#mensaje").html(msg);
		}
	});
}

function cambiarCargo(idTipo, idFacultad,origen){
	var idCargo = $("#"+origen).val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=cargo&idCargo="+idCargo+"&idFacultad="+idFacultad,
	   success: function(msg){
	     	$("#"+idTipo).html(msg);
	    }
	});
}

function eliminarAcceso(idAcceso, idDocente){
	$.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	$.ajax({
				   type: "POST",
				   url: "Administrador.action",
				   data: "tipoAccion=eliminarAcceso&accesoDTO.idAcceso="+idAcceso,
				   success: function(msg){
					   $("#mensaje").html(msg);
					   tablaAccesos(idDocente);
				    }
				});
            },
            cancelar: function () {
                $.alert('Proceso Cancelado!');
            }
        }
    });
	
}

function tablaAccesos(idDocente){
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=tablaAcceso&docenteDTO.idDocente="+idDocente,
	   success: function(msg){
		   $("#tablaAcceso").html(msg);
	    }
	});
}


//Modulo de materias
function tablaMaterias(){
	var materia=$("#nombreMateria").val();
	var orden = $("#orden").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=tablaMaterias&orden="+orden+"&nombreMateria="+materia,
	   success: function(msg){
	     	$("#listaMaterias").html(msg);
	    }
	});
}


function actualizarMateria(idMateria){
	var materia=$("#nombreMateria").val();
	var orden = $("#orden").val();
	var nombre=$("#nombre"+idMateria).html();
	var area=$("#area"+idMateria).val();
	var tipo=$("#tipo"+idMateria).val();
	var nucleo=$("#nucleo"+idMateria).val();
	var art=$("#art"+idMateria).val();
	var hp=$("#hp"+idMateria).html();
	var hte=$("#hte"+idMateria).html();
	var ht=$("#ht"+idMateria).html();
	var ct=$("#ct"+idMateria).html();
	
	var datos="tipoAccion=updateMateria&orden="+orden+"&nombreMateria="+materia+
	"&materiaDTO.art57="+art+"&materiaDTO.idNucleo="+nucleo
	+"&materiaDTO.nombreMateria="+nombre+"&materiaDTO.idArea="+area
	+"&materiaDTO.idTipo="+tipo+"&materiaDTO.creditosTotales="+ct
	+"&materiaDTO.horasTotales="+ht+"&materiaDTO.horasTeoricas="+
	hte+"&materiaDTO.horasPracticas="+hp+"&materiaDTO.idMateria="+idMateria;
	$.ajax({
		   type: "POST",
		   url: "Administrador.action",
		   data: datos,
		   success: function(msg){
			   $("#mensaje").html(msg);
		     	tablaMaterias();
		    }
		});
}

function eliminarMateria(idMateria){
	$.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	var datos="tipoAccion=eliminarMateria&materiaDTO.idMateria="+idMateria
				$.ajax({
					   type: "POST",
					   url: "Administrador.action",
					   data: datos,
					   success: function(msg){
						   $("#mensaje").html(msg);
					     	tablaMaterias();
					    }
					});
            },
            cancelar: function () {
                $.alert('Proceso Cancelado!');
            }
        }
    });
}

function cambiarAcademia(idMateria){
	var idAcademia=$("#academia"+idMateria).val();
	var datos="tipoAccion=cambiarAcademia&idMateria="+idMateria+"&idAcademia="+idAcademia
	console.log(datos);
	$.ajax({
		   type: "POST",
		   url: "Administrador.action",
		   data: datos,
		   success: function(msg){
			   $("#mensaje").html(msg);
		    }
		});
}


//Modulo de carreras
function regresarACarrera(){
	$.ajax({
		   type: "POST",
		   url: "Administrador.action",
		   data: "tipoAccion=listaCarreras",
		   success: function(msg){
		     	$("#pagina").html(msg);
		    }
		});
}

function tablaCarreras(){
	var carrera=$("#nombreCarrera").val();
	var orden = $("#orden").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=tablaCarreras&orden="+orden+"&carreraDTO.nombreCarrera="+carrera,
	   success: function(msg){
	     	$("#listaCarreras").html(msg);
	    }
	});
}


function actualizarCarrera(idCarrera){
	var nombre=$("#nombre"+idCarrera).html();
	var facultad=$("#facultad"+idCarrera).val();
	var plan=$("#plan"+idCarrera).html();
	
	var datos="tipoAccion=updateCarrera&carreraDTO.nombreCarrera="+nombre+"&carreraDTO.idFacultad="+facultad
	+"&carreraDTO.idCarrera="+idCarrera+"&carreraDTO.planEstudios="+plan;
	$.ajax({
		   type: "POST",
		   url: "Administrador.action",
		   data: datos,
		   success: function(msg){
			   $("#mensaje").html(msg);
		    }
		});
}

function eliminarCarrera(idCarrera){
	$.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	var datos="tipoAccion=eliminarCarrera&carreraDTO.idCarrera="+idCarrera;
				$.ajax({
					   type: "POST",
					   url: "Administrador.action",
					   data: datos,
					   success: function(msg){
						   $("#mensaje").html(msg);
					     	tablaCarreras();
					    }
					});
            },
            cancelar: function () {
                $.alert('Proceso Cancelado!');
            }
        }
    });
	
}

function listaCarrerasMaterias(idCarrera, nombre){
	var materia=$("#nombreMateria").val();
	var orden = $("#orden").val();
	console.log(nombre);
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=listaCarrerasMaterias&carreraDTO.idCarrera="+idCarrera+"&carreraDTO.nombreCarrera="+nombre,
	   success: function(msg){
	     	$("#materias").html(msg);
	    }
	});
}

function tablaCarrerasMaterias(){
	var materia=$("#nombreMateria").val();
	var orden = $("#orden").val();
	var idCarrera=$("#idCarrera").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=tablaCarrerasMaterias&orden="+orden+"&nombreMateria="+materia+
	   "&carreraDTO.idCarrera="+idCarrera,
	   success: function(msg){
	     	$("#listaCarrerasMaterias").html(msg);
	    }
	});
}

function asignarMateria(idCarrera, idMateria, opc){
	var datos="tipoAccion=asignarMateria&carreraDTO.idCarrera="+idCarrera+"&materiaDTO.idMateria="
	+idMateria+"&idCargo="+opc;
	$.ajax({
		   type: "POST",
		   url: "Administrador.action",
		   data: datos,
		   success: function(msg){
			   $("#mensaje").html(msg);
		     	tablaCarrerasMaterias();
		    }
		});
}

//modulo academia
function tablaAcademias(){
	var carrera=$("#nombreAcademia").val();
	var orden = $("#orden").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=tablaAcademia&orden="+orden+"&academiaDTO.nombreAcademia="+carrera,
	   success: function(msg){
	     	$("#listaAcademias").html(msg);
	    }
	});
}


function actualizarAcademia(idAcademia){
	var nombre=$("#nombre"+idAcademia).html();
	var facultad=$("#facultad"+idAcademia).val();
	
	var datos="tipoAccion=updateAcademia&academiaDTO.nombreAcademia="+nombre+"&academiaDTO.idFacultad="+facultad
	+"&academiaDTO.idAcademia="+idAcademia;
	$.ajax({
		   type: "POST",
		   url: "Administrador.action",
		   data: datos,
		   success: function(msg){
			   $("#mensaje").html(msg);
		    }
		});
}

function eliminarAcademia(idAcademia){
	$.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	var datos="tipoAccion=eliminarAcademia&academiaDTO.idAcademia="+idAcademia;
				$.ajax({
					   type: "POST",
					   url: "Administrador.action",
					   data: datos,
					   success: function(msg){
						   $("#mensaje").html(msg);
					     	tablaAcademias();
					    }
					});
            },
            cancelar: function () {
                $.alert('Proceso Cancelado!');
            }
        }
    });
	
}

//modulo competencia especifica
function tablaEspecificas(){
	var especifica=$("#nombreEspecifica").val();
	var orden = $("#orden").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=tablaEspecifica&orden="+orden+"&nombreCompetencia="+especifica,
	   success: function(msg){
	     	$("#listaEspecificas").html(msg);
	    }
	});
}


function actualizarEspecifica(idEspecifica){
	var nombre=$("#nombre"+idEspecifica).html();
	var carrera=$("#carrera"+idEspecifica).val();
	
	var datos="tipoAccion=updateEspecifica&especificaDTO.perfilEspecifica="+nombre+"&especificaDTO.idCarrera="+carrera
	+"&especificaDTO.idEspecifica="+idEspecifica;
	$.ajax({
		   type: "POST",
		   url: "Administrador.action",
		   data: datos,
		   success: function(msg){
			   $("#mensaje").html(msg);
		    }
		});
}

function eliminarEspecifica(idEspecifica){
	$.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	var datos="tipoAccion=deleteEspecifica&especificaDTO.idEspecifica="+idEspecifica;
				$.ajax({
					   type: "POST",
					   url: "Administrador.action",
					   data: datos,
					   success: function(msg){
						   $("#mensaje").html(msg);
					     	tablaEspecificas();
					    }
					});
            },
            cancelar: function () {
                $.alert('Proceso Cancelado!');
            }
        }
    });
	
}

//modulo competencia genericas
function tablaGenericas(){
	var especifica=$("#nombreGenerica").val();
	var orden = $("#orden").val();
	$.ajax({
	   type: "POST",
	   url: "Administrador.action",
	   data: "tipoAccion=tablaGenerica&orden="+orden+"&nombreCompetencia="+especifica,
	   success: function(msg){
	     	$("#listaGenericas").html(msg);
	    }
	});
}


function actualizarGenerica(idGenerica){
	var nombre=$("#nombre"+idGenerica).html();
	
	var datos="tipoAccion=updateGenerica&genericaDTO.nombreGenerica="+nombre+"&genericaDTO.idGenerica="+idGenerica;
	$.ajax({
		   type: "POST",
		   url: "Administrador.action",
		   data: datos,
		   success: function(msg){
			   $("#mensaje").html(msg);
		    }
		});
}

function eliminarGenerica(idGenerica){
	$.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	var datos="tipoAccion=deleteGenerica&genericaDTO.idGenerica="+idGenerica;
				$.ajax({
					   type: "POST",
					   url: "Administrador.action",
					   data: datos,
					   success: function(msg){
						   $("#mensaje").html(msg);
					     	tablaGenericas();
					    }
					});
            },
            cancelar: function () {
                $.alert('Proceso Cancelado!');
            }
        }
    });
	
}