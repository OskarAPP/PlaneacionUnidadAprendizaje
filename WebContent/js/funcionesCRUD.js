$(document).ready(function() {
	cargarNabvar();
	$("#containerFooter").load("footer.html");
	obtenerCarreras();
});

function cargarNabvar(){
	$.ajax({
		   type: "POST",
		   url: "ProcesarPua.action",
		   data: "tipoAccion=navbar",
		   success: function(msg){
		     	$("#containerNav").html(msg);
		   }
		});
}

function obtenerFacultades(tipo){
	$.ajax({
		   type: "POST",
		   url: "ProcesarPua.action",
		   data: "tipoAccion=obtenerFacultades&tipo="+tipo,
		   success: function(msg){
		     	$("#facultad").html(msg);
		     	obtenerCarreras();
		     	$("#materia").html("");
		   }
		});
}

function obtenerCarreras(){
	var idFacultad = $("#facultad").val();
	var tipo=$("#tipo").val();
	$.ajax({
	   type: "POST",
	   url: "ProcesarPua.action",
	   data: "tipoAccion=carreras&idFacultad="+idFacultad+"&tipo="+tipo,
	   success: function(msg){
	     	$("#carreras").html(msg);
	   }
	});
}

function obtenerMaterias(){
	var idCarrera = $("#carreras").val();
	var tipo=$("#tipo").val();
	
	$.ajax({
	   type: "POST",
	   url: "ProcesarPua.action",
	   data: "tipoAccion=materias&idCarrera="+idCarrera+"&tipo="+tipo,
	   success: function(msg){
	     	$("#materia").html(msg);
	    }
	});

}


function obtenerInfoPua(){
	var idMateria = $("#materia").val();
	var idCarrera = $("#carreras").val();
	var idFacultad = $("#facultad").val();

	$.ajax({
	   type: "POST",
	   url: "ProcesarPua.action",
	   data: "tipoAccion=infoPua&idMateria="+idMateria+"&idCarrera="+idCarrera+"&idFacultad="+idFacultad,
	   success: function(msg){
			$("#containerSecundario").html(msg);
			obtenerSubcompetencias();
			obtenerDocentePua();
			
	    }
	});

}

function imprimirPua(){
	var idMateria = $("#materia").val();
	var idFacultad = $("#facultad").val();
	var idCarrera = $("#carreras").val();
	location.href = "ProcesarPua.action?tipoAccion=imprimirPUA&idMateria="+idMateria+"&idFacultad="+idFacultad
	+"&idCarrera="+idCarrera;

}

function obtenerGenericasPua() {
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerGenericasPua&idPua="+idPua,
		success: function(msg) {
			$("#genericas_pua").html(msg);
		}
	});
}

function agregarGenericaPua() {
	var idPua = $("#idPua").val();
	var idGenerica;
	var genericas = [];


	$("input[class='genericas']:checked").each(function() {
		idGenerica = $(this).val();
		genericas.push(idGenerica);

		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarCompetenciaGenericaPua"+
				  "&genericaDTO.idPua="+idPua+
				  "&genericaDTO.idGenerica="+idGenerica,

			success: function(msg) {
				$("#mensaje").html(msg);
				obtenerGenericasPua();
			}

		});
	});

	$("input[class='genericas']").removeAttr('checked');

	if(genericas.length < 1) {
		alert("Debe seleccionar al menos un item.");
	}
}

function eliminarGenericaPua(idPua, idGenerica) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarCompetenciaGenericaPua" +
				"&genericaDTO.idPua=" + idPua +
				"&genericaDTO.idGenerica=" + idGenerica,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerGenericasPua();
		}
	});
}

function actualizarPlanEstudio(idPua, planEstudio) {
	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=actualizarPlanEstudio" +
				"&puaDTO.idPua=" + idPua +
				"&puaDTO.planEstudio=" + planEstudio,
		success: function(msg) {
			$("#mensaje").html(msg);
		}
	});
}

function obtenerEspecificasPua() {
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerEspecificasPua&idPua="+idPua,
		success: function(msg) {
			$("#especificas_pua").html(msg);
		}
	});
}

function agregarEspecificaPua() {
	var idPua = $("#idPua").val();
	var idEspecifica;
	var especificas = [];

	$("input[class='especificas']:checked").each(function() {
		idEspecifica = $(this).val();
		especificas.push(idEspecifica);

		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarCompetenciaEspecificaPua"+
				  "&especificaDTO.idPua="+idPua+
				  "&especificaDTO.idEspecifica="+idEspecifica,
			success: function(msg) {
				$("#mensaje").html(msg);
				obtenerEspecificasPua();
			}

		});
	});

	$("input[class='especificas']").removeAttr('checked');

	if(especificas.length < 1) {
		alert("Debe seleccionar al menos un item.");
	}
}

function eliminarEspecificaPua(idPua, idEspecifica) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarCompetenciaEspecificaPua" +
				"&especificaDTO.idPua=" + idPua +
				"&especificaDTO.idEspecifica="+idEspecifica,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerEspecificasPua();
		}
	});
}

function guardarCompetenciasPua(){
	var idPua = $("#idPua").val();
	var competenciaFormacion = $("#competenciaFormacion").val();
	var competenciaUda = $("#competenciaUda").val();

	if(competenciaFormacion == "" || competenciaUda == "") {
		alert("Por favor, rellene los campos que faltan.");
		return;

	} else {
		var datos = "tipoAccion=guardarCompetenciasPua" +
					"&puaDTO.idPua=" + idPua +
					"&puaDTO.competenciaFormacion=" + competenciaFormacion +
					"&puaDTO.competenciaUda=" + competenciaUda;

		$.ajax({
		   type: "POST",
		   url: "ProcesarPua.action",
		   data: datos,
		   success: function(msg){
		     	$("#mensaje").html(msg);
		     	$("#competencias").collapse('hide');
		    }
		});
	}
}

function obtenerSubcompetencias() {
	var idPua = $("#idPua").val();

	$.ajax({
	   type: "POST",
	   url: "ProcesarPua.action",
	   data: "tipoAccion=infoSubcompetencias&idPua="+idPua,
	   success: function(msg){
	     	$(".containerSubcompetencias").html(msg);
	    }
	});

}

function generarSubcompetencia(numSubcompetencia) {

	var idPua = $("#idPua").val();
	var subcompetencia = "";
	var sesiones = "";
	var ponderacion = "";
	var ponderacionRestante = "";

		var datos = "tipoAccion=generarSubcompetencia&subcompetenciaDTO.idPua="+idPua+
		"&subcompetenciaDTO.numSubcompetencia="+numSubcompetencia+
		"&subcompetenciaDTO.subcompetencia="+subcompetencia+
		"&subcompetenciaDTO.sesiones="+sesiones+
		"&subcompetenciaDTO.ponderacion="+ponderacion;

		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: datos,
			success: function(msg){
				obtenerSubcompetencias();
			}

		});

	$('#modalagregars').modal('hide');
	$('body').removeClass('modal-open');
	$('.modal-backdrop').remove();

}

function guardarCambiosSubcompetencia(idSubcompetencia, numSubcompetencia) {
	var subcompetencia = $("#descripcionSub"+idSubcompetencia).val();
	var sesiones = $("#sesiones"+idSubcompetencia).val();
	var ponderacion = $("#ponderacion"+idSubcompetencia).val();
	var parcial = $("#numPar"+idSubcompetencia).val();
	var idPua = $("#idPua").val();

	if(subcompetencia == "" || sesiones == "" || ponderacion =="" || parcial == "") {
		alert("Por favor, rellene los campos que faltan.");
		return;

	} else {
		var datos = "tipoAccion=guardarSubcompetencia&subcompetenciaDTO.idSubcompetencia="+idSubcompetencia+
					"&subcompetenciaDTO.subcompetencia="+subcompetencia+
					"&subcompetenciaDTO.sesiones="+sesiones+
					"&subcompetenciaDTO.ponderacion="+ponderacion+
					"&subcompetenciaDTO.parcial="+parcial+
					"&idPua="+idPua;
		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: datos,
			success: function(msg){
				$("#mensaje").html(msg);
				$("#subcompetencia"+numSubcompetencia).collapse('hide');
			}
		});
	}
}

function insertarEvaluacionFinalValidado(){
	var idPua = $("#idPua").val();
	
	var pond1 = $("#ponderacion_final1").val();
	var pond2 = $("#ponderacion_final2").val();
	var pond3 = $("#ponderacion_final3").val();
	var pond4 = $("#ponderacion_final4").val();
	
	var evaluacion_final1 = $("#evaluacion_final1").val();
	var evaluacion_final2 = $("#evaluacion_final2").val();
	var evaluacion_final3 = $("#evaluacion_final3").val();
	var evaluacion_final4 = $("#evaluacion_final4").val();
	
	if(pond1==''){
		pond1=0;
	}
	if(pond2==''){
		pond2=0;
	}
	if(pond3==''){
		pond3=0;
	}
	if(pond4==''){
		pond4=0;
	}
	var total = parseInt(pond1)+parseInt(pond2)+parseInt(pond3)+parseInt(pond4);
	
	if(total==100){
		eliminarEvaluacionFinal(idPua);
		insertarEvaluacionFinal(evaluacion_final1,evaluacion_final2,evaluacion_final3,evaluacion_final4,pond1,pond2,pond3,pond4,idPua)
		
	}else if(total<100){
		alert('Ponderacion no alcanza el 100%')
	}else if(total>100){
		alert('Ponderacion sobrepasa el 100%');
	}
	
}

function insertarEvaluacionCompetenciasValidado(){

	var idPua = $("#idPua").val();
	
	var eval1 = $("#ponderacion_competencia1").val();
	var eval2 = $("#ponderacion_competencia2").val();
	var eval3 = $("#ponderacion_competencia3").val();
	var eval4 = $("#ponderacion_competencia4").val();
	
	var exades = $("#evaluacion_competencias1").val();
	var evaluacion_competencias_2 = $("#evaluacion_competencias2").val();
	var evaluacion_competencias_3 = $("#evaluacion_competencias3").val();
	var evaluacion_competencias_4 = $("#evaluacion_competencias4").val();
	
	if(eval1==''){
		eval1=0;
	}
	if(eval2==''){
		eval2=0;
	}
	if(eval3==''){
		eval3=0;
	}
	if(eval4==''){
		eval4=0;
	}
	var total = parseInt(eval1)+parseInt(eval2)+parseInt(eval3)+parseInt(eval4);
	
	if(total==100){
		eliminarEvaluacionCompetencias(idPua);
		insertarEvaluacionCompetencias(exades,evaluacion_competencias_2,evaluacion_competencias_3,evaluacion_competencias_4
				,eval1,eval2,eval3,eval4,idPua);
		
		
	}else if(total<100){
		alert('Ponderacion no alcanza el 100%')
		alert(total);
	}else if(total>100){
		alert('Ponderacion sobrepasa el 100%');
	}	
}

function insertarEvaluacionFinal(evaluacion,evaluacion2,evaluacion3,evaluacion4,ponderacion1,ponderacion2,
		ponderacion3,ponderacion4,idPua){
	
	
	
	
	$.ajax({
	   type: "POST",
	   url: "ProcesarPua.action",
	   data: "tipoAccion=insertarEvaluacionFinal&evaluacionDTO.evaluacion="+evaluacion+"&evaluacionDTO.evaluacion2="+
	   evaluacion2+"&evaluacionDTO.evaluacion3="+evaluacion3+"&evaluacionDTO.evaluacion4="+evaluacion4+"&evaluacionDTO.ponderacion1="+
	   ponderacion1+"&evaluacionDTO.ponderacion2="+ponderacion2+"&evaluacionDTO.ponderacion3="+ponderacion3+"&evaluacionDTO.ponderacion4="+
	   ponderacion4+"&evaluacionDTO.idPua="+idPua,
	   success: function(msg){
		   $("#mensaje").html(msg);
	    }
	});
}

function insertarEvaluacionCompetencias(evaluacion,evaluacion2,evaluacion3,evaluacion4,ponderacion1,ponderacion2,
		ponderacion3,ponderacion4,idPua){
	
	
	
	
	$.ajax({
	   type: "POST",
	   url: "ProcesarPua.action",
	   data: "tipoAccion=insertarEvaluacionCompetencias&evaluacionDTO.evaluacion="+evaluacion+"&evaluacionDTO.evaluacion2="+
	   evaluacion2+"&evaluacionDTO.evaluacion3="+evaluacion3+"&evaluacionDTO.evaluacion4="+evaluacion4+"&evaluacionDTO.ponderacion1="+
	   ponderacion1+"&evaluacionDTO.ponderacion2="+ponderacion2+"&evaluacionDTO.ponderacion3="+ponderacion3+"&evaluacionDTO.ponderacion4="+
	   ponderacion4+"&evaluacionDTO.idPua="+idPua,
	   success: function(msg){
		   $("#mensaje").html(msg);
	    }
	});
}

function eliminarEvaluacionFinal(idPua) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarEvaluacionFinal&evaluacionFinalDTO.idPua="+idPua,
		success: function(msg) {
				
		}

	});
}

function eliminarEvaluacionCompetencias(idPua) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarEvaluacionCompetenicias&evaluacionDTO.idPua="+idPua,
		success: function(msg) {
				
		}

	});
}

function obtenerEvaluacionCompetencias() {
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=ontenerEvaluacionCompetencias&idPua="+idPua,
		success: function(msg) {
			$("#especificas_pua").html(msg);
		}
	});
}



function eliminarSubcompetencia(idSubcompetencia) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarSubcompetencia&idSubcompetencia="+idSubcompetencia,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerSubcompetencias();
		}

	});
}

function obtenerTemas(idSubcompetencia) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerTemas&idSubcompetencia="+idSubcompetencia,
		success: function(msg) {
			$("#temasde"+idSubcompetencia).html(msg);
		}
	});
}

function agregarTema(idSubcompetencia) {

	var numtema = $("#numtema").val();
	var tema = $("#tema").val();

	if(numtema == "" || tema == "") {
		alert("Por favor, rellene los campos que faltan.")
		return;

	} else {
		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarTema&temaDTO.idSubcompetencia="+idSubcompetencia+
				  "&temaDTO.tema="+tema+
				  "&temaDTO.numeroTema="+numtema,

			success: function(msg) {
				$("#mensaje").html(msg);
				obtenerTemas(idSubcompetencia);
			}
		});

		$('#modal-agregart').modal('hide');
		$('body').removeClass('modal-open');
		$('.modal-backdrop').remove();
	}
}

function eliminarTema(idTema, idSubcompetencia) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarTema&temaDTO.idTema="+idTema,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerTemas(idSubcompetencia);
		}

	});
}

function obtenerSubtemas(idTema) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerSubtemas&temaDTO.idTema="+idTema,
		success: function(msg) {
			$("#subtemasde"+idTema).html(msg);
		}
	});
}

function agregarSubtema(idTema) {

	var numsubtema = $("#numsubtema").val();
	var subtema = $("#subtema").val();

	if(numsubtema == "" || subtema == "") {
		alert("Por favor, rellene los campos que faltan.");
		return;

	} else {
		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarSubtema&subtemaDTO.idTema="+idTema+
				  "&subtemaDTO.numeroSubtema="+numsubtema+
				  "&subtemaDTO.subtema="+subtema,
			success: function(msg) {
				$("#mensaje").html(msg);
				obtenerSubtemas(idTema);
			}
		});

			$('#modal-agregarst').modal('hide');
			$('body').removeClass('modal-open');
			$('.modal-backdrop').remove();
	}
}

function eliminarSubtema(idSubtema, idTema) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarSubtema&subtemaDTO.idSubtema="+idSubtema,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerSubtemas(idTema);
		}
	});
}

function obtenerActividades(idSubcompetencia) {
	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerActividades&idSubcompetencia="+idSubcompetencia,
		success: function(msg) {
			$("#actividadesde"+idSubcompetencia).html(msg);
		}
	});
}

function agregarActividad(idSubcompetencia) {
	var actividad = $("#actividad").val();
	var rol = $("#rolde_actividad").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=insertarActividad" +
				"&actividadDTO.actividad=" + actividad +
				"&actividadDTO.rolDeActividad=" + rol +
				"&actividadDTO.idSubcompetencia=" + idSubcompetencia,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerActividades(idSubcompetencia);
		}
	});


	$('#modal-agregarac').modal('hide');
	$('body').removeClass('modal-open');
	$('.modal-backdrop').remove();

}

function eliminarActividad(rol, idActividad, idSubcompetencia) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarActividad" +
		 		"&actividadDTO.idActividad=" + idActividad +
				"&actividadDTO.rolDeActividad=" + rol,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerActividades(idSubcompetencia);
		}
	});
}

function obtenerCriterios(id) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerCriterios&idSubcompetencia="+id,
		success: function(msg) {
			$("#criteriosde"+id).html(msg);
		}
	});
}

function agregarCriterio(id) {
	var criterio = $("#criterio").val();

	if(criterio == "") {
		alert("Por favor rellene los campos que faltan.");
		return;

	} else {
		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarCriterio&criterioDTO.idSubcompetencia="+id+
				  "&criterioDTO.criterio="+criterio,
			success: function(msg) {
				$("mensaje").html(msg);
				obtenerCriterios(id);
			}

		});
	}

	$('#modal-agregarc').modal('hide');
	$('body').removeClass('modal-open');
	$('.modal-backdrop').remove();

}

function eliminarCriterio(idCriterio, idSubcompetencia) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarCriterio&criterioDTO.idCriterio=" + idCriterio,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerCriterios(idSubcompetencia);
		}
	});
}

function obtenerEvidencias(idSubcompetencia) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerEvidencias&idSubcompetencia=" + idSubcompetencia,
		success: function(msg) {
			$("#evidenciasde"+idSubcompetencia).html(msg);
		}

	});
}

function agregarEvidencia(idSubcompetencia) {
	var evidencia = $("#select_evidencia").val();
	var pond_ev = $("#ponderacion_evidencia").val();

	if(evidencia == "" || pond_ev == "") {
		alert("Por favor, rellene los campos que faltan.")
		return;

	} else {

		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarEvidencia&evidenciaDTO.idSubcompetencia=" + idSubcompetencia +
				  "&evidenciaDTO.idEvidencia=" + evidencia +
				  "&evidenciaDTO.ponderacion=" + pond_ev,
			success: function(msg) {
				$("#mensaje").html(msg);
				obtenerEvidencias(idSubcompetencia);
			}
		});
		$('#modal-agregare').modal('hide');
		$('body').removeClass('modal-open');
		$('.modal-backdrop').remove();
	}
}

function eliminarEvidencia(idSubcompetencia, idEvidencia) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarEvidencia" +
				"&evidenciaDTO.idSubcompetencia=" + idSubcompetencia +
				"&evidenciaDTO.idEvidencia=" + idEvidencia,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerEvidencias(idSubcompetencia);
		}

	});
}

function obtenerDocentePua(){
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerDocentePua&idPua="+idPua,
		success: function(msg) {
			$("#obtenerDocentePua").html(msg);
		}
	});
}

function agregarDocentePua() {
	var idPua = $("#idPua").val();
	var docentes = $("#selectDocentes").select2('data');

	for(i=0;i<docentes.length; i++){
		var docenteSelect = parseInt(docentes[i].id);

		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarDocentePua&idPua="+idPua+"&mensaje="+docenteSelect,

			success: function(msg) {
				$("#obtenerDocentePua").html(msg);
				obtenerDocentePua();
			}
		});
	}
}

function eliminarDocentePua(idDocente) {
	var idPua = $("#idPua").val();
	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarDocentePua&mensaje="+idDocente+"&idPua="+idPua,
		success: function(msg) {
			$("#obtenerDocentePua").html(msg);
			obtenerDocentePua();
		}
	});
}

function obtenerBibliografia() {
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerBibliografiaPua&idPua=" + idPua,
		success: function(msg) {
			$("#bibliografia").html(msg);
		}
	});
}

function agregarBibliografia() {
	var idPua = $("#idPua").val();
	var idLibro = $("#seleccionLibro").val();
	var idTipoBibliografia = $("input[name='radioTipos']:checked").val();

	if(idLibro == "" || idTipoBibliografia < 1) {
		alert("Por favor rellene los campos que faltan.")
	}

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=insertarBibliografiaPua" +
				"&bibliografiaDTO.idPua=" + idPua +
				"&bibliografiaDTO.idLibro=" + idLibro +
				"&bibliografiaDTO.idTipoBibliografia=" + idTipoBibliografia,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerBibliografia();
		}
	});
}

function eliminarBibliografia(idLibro) {
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarBibliografiaPua" +
				"&bibliografiaDTO.idPua=" + idPua +
				"&bibliografiaDTO.idLibro=" + idLibro,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerBibliografia();
		}
	});
}

function obtenerAmbientesSubcompetencia(idSubcompetencia) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerAmbientesSubcompetencia&idSubcompetencia=" + idSubcompetencia,
		success: function(msg) {
			$("#ambientesde"+idSubcompetencia).html(msg);
		}
	});
}

function insertarAmbiente(idSubcompetencia) {
	var ambientes = $("#select2Ambientesde" + idSubcompetencia).select2('data');
	var ambienteSelect;

	for (var i = 0; i < ambientes.length; i++) {
		ambienteSelect = ambientes[i].id;

		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarAmbienteSubcompetencia" +
					"&ambienteDTO.idSubcompetencia=" + idSubcompetencia +
					"&ambienteDTO.idAmbiente=" + ambienteSelect,
			success: function(msg) {
				$("#mensaje").html(msg);
				obtenerAmbientesSubcompetencia(idSubcompetencia);
			}
		});
	}
}

function eliminarAmbiente(idSubcompetencia, idAmbiente) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarAmbiente" +
				"&ambienteDTO.idSubcompetencia=" + idSubcompetencia +
				"&ambienteDTO.idAmbiente=" + idAmbiente,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerAmbientesSubcompetencia(idSubcompetencia);
		}
	});
}

function obtenerBibliografiaSubcompetencia(idSubcompetencia) {
	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerBibliografiaSubcompetencia&" +
				"idSubcompetencia=" + idSubcompetencia,
		success: function(msg) {
			$("#bibliografiadesubcomp" + idSubcompetencia).html(msg);
		}
	});
}

function agregarBibliografiaSubcompetencia(idSubcompetencia) {
	var idLibro = $("#seleccionBibliografia" + idSubcompetencia).val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=insertarBibliografiaSubcompetencia" +
				"&bibliografiaDTO.idSubcompetencia=" + idSubcompetencia +
				"&bibliografiaDTO.idLibro=" + idLibro,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerBibliografiaSubcompetencia(idSubcompetencia);
		}
	});
}

function eliminarBibliografiaSub(idSubcompetencia, idLibro) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarBibliografiaSubcompetencia" +
				"&bibliografiaDTO.idSubcompetencia=" + idSubcompetencia +
				"&bibliografiaDTO.idLibro=" + idLibro,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerBibliografiaSubcompetencia(idSubcompetencia);
		}
	});
}

function obtenerPerfilAcademico() {
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerPerfilAcademico&idPua=" + idPua,
		success: function(msg) {
			$("#perf_acad").html(msg);
		}
	});
}

function obtenerPerfilProfesional() {
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerPerfilProfesional&idPua=" + idPua,
		success: function(msg) {
			$("#perf_prof").html(msg);
		}
	});
}

function obtenerPerfilDocente() {
	var idPua = $("#idPua").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerPerfilDocente&idPua=" + idPua,
		success: function(msg) {
			$("#perf_doc").html(msg);
		}
	});
}

function agregarPerfilAcademico() {
	var idPua = $("#idPua").val();
	var perfil = $("#perfil_ac").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=insertarPerfilAcademico" +
				"&perfilDTO.perfil=" + perfil +
				"&perfilDTO.idPua=" + idPua,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerPerfilAcademico();
		}
	});

	$('#modal-agregarpa').modal('hide');
	$('body').removeClass('modal-open');
	$('.modal-backdrop').remove();

}

function agregarPerfilProfesional() {
	var idPua = $("#idPua").val();
	var perfil = $("#perfil_pro").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=insertarPerfilProfesional" +
				"&perfilDTO.perfil=" + perfil +
				"&perfilDTO.idPua=" + idPua,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerPerfilProfesional();
		}
	});

	$('#modal-agregarpp').modal('hide');
	$('body').removeClass('modal-open');
	$('.modal-backdrop').remove();
}

function agregarPerfilDocente() {
	var idPua = $("#idPua").val();
	var perfil = $("#perfil_doc").val();

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=insertarPerfilDocente" +
				"&perfilDTO.perfil=" + perfil +
				"&perfilDTO.idPua=" + idPua,
		success: function(msg) {
			$("#mensaje").html(msg);
			obtenerPerfilDocente();
		}
	});

	$('#modal-agregarpd').modal('hide');
	$('body').removeClass('modal-open');
	$('.modal-backdrop').remove();
}

function eliminarPerfilAcademico(idPerfil) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarPerfilAcad&perfilDTO.idPerfil=" + idPerfil,
		success: function(msg) {
				$("#mensaje").html(msg);
				obtenerPerfilAcademico();
		}
	});
}

function eliminarPerfilProfesional(idPerfil) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarPerfilProf&perfilDTO.idPerfil=" + idPerfil,
		success: function(msg) {
				$("#mensaje").html(msg);
				obtenerPerfilProfesional();
		}
	});
}

function eliminarPerfilDocente(idPerfil) {

	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=eliminarPerfilDoc&perfilDTO.idPerfil=" + idPerfil,
		success: function(msg) {
				$("#mensaje").html(msg);
				obtenerPerfilDocente();
		}
	});
}

function obtenerMateriales(idSubcompetencia) {
	
	$.ajax({
		type: "POST",
		url: "ProcesarPua.action",
		data: "tipoAccion=obtenerMaterialesSub&idSubcompetencia=" + idSubcompetencia,
		success: function(msg) {
			$("#materialesde" + idSubcompetencia);
		}
	});
}

function insertarMaterial(idSubcompetencia) {
	var materiales = $("#select2Materialesde" + idSubcompetencia).select2('data');
	var materialSeleccionado;

	for (var i = 0; i < materiales.length; i++) {
		materialSeleccionado = materiales[i].id;

		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=insertarMaterialSub" +
					"&materialDTO.idSubcompetencia=" + idSubcompetencia +
					"&materialDTO.idMaterial=" + materialSeleccionado,
			success: function(msg) {
				$("#mensaje").html(msg);
				obtenerMateriales(idSubcompetencia);
			}
		});
	}
	
	function eliminarMaterial(idSubcompetencia, idMaterial) {
		$.ajax({
			type: "POST",
			url: "ProcesarPua.action",
			data: "tipoAccion=eliminarMaterial" +
					"&materialDTO.idSubcompetencia=" + idSubcompetencia +
					"&materialDTO.idMaterial=" + idMaterial,
			success: function(msg) {
				$("#mensaje").html(msg);
				obtenerMateriales(idSubcompetencia);
			}
		});
	}
}

//vane

function graficarPorFacultad(){
	var idFacultad = $("#facultad").val();
	//alert(idFacultad);
	$.ajax({
		type: "POST",
		url: "grafica.action",
		data: "tipoAccion=tablaFacultad&idFacultad="+idFacultad,
		success: function(msg) {
			$("#graficaFacultadResultados").html(msg);
			$("#graficaCarreraResultados").html("");
		}
	});
}

function graficarPorCarrera(){
	var idFacultad = $("#facultad").val();
	var idCarrera = $("#carreras").val();
	//alert(idFacultad);
	$.ajax({
		type: "POST",
		url: "grafica.action",
		data: "tipoAccion=tablaCarrera&idFacultad="+idFacultad+"&idCarrera="+idCarrera,
		success: function(msg) {
			$("#graficaCarreraResultados").html(msg);
		}
	});
}

function graficarPorEspecificas(){
	var idFacultad = $("#facultad").val();
	var idCarrera = $("#carreras").val();
	$.ajax({
		type: "POST",
		url: "graficaEspecifica.action",
		data: "tipoAccion=tablaEspecifica&idFacultad="+idFacultad+"&idCarrera="+idCarrera,
		success: function(msg) {
			$("#graficaEspecificaResultados").html(msg);
		}
	});
}