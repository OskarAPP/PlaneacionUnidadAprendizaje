function popovers() {
	$('.popovers').popover({
		toggle: 'popover',
		placement: 'bottom',
		trigger: 'focus',
		content: 'Este campo es requerido.'
	});

	//$('.popover').css('color', '#d43f3a');
}

function mostrarModalTema(id) {

	$('#idsub_tema').val(id);
	$('#btnAgregarTema').val(id);
	$('#modal-agregart').modal('show');

}

function mostrarModalSubtema(id) {
	$('#idtema_sub').val(id);
	$('#btnAgregarSubtema').val(id);
	$('#modal-agregarst').modal('show');
}

function mostrarModalActividad(id) {
	$("#btnAgregarActividad").val(id);
	$("#modal-agregarac").modal('show');
}

function mostrarModalCriterios(id) {
	$('#idsub_criterios').val(id);
	$('#btnAgregarCriterio').val(id);
	$('#modal-agregarc').modal('show');
}

function mostrarModalEvidencias(id, pond) {
	$('#pond_sub_evidencia').val(pond);
	$('#btnAgregarEvidencia').val(id);
	$('#modal-agregare').modal('show');
}

function verificarEvidencia() {
	var evidencia = $("#select_evidencia").val();
	
	if(evidencia == 2) {
		$("#ponderacion_evidencia").val(40);
	
	} else {
		$("#ponderacion_evidencia").val("");
	} 
}

