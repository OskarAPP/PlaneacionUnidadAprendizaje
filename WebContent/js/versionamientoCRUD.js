$(document).ready(function() {
	cargarNabvar();
	var x = $("#x").val();
	if(x=="puas"){
		tablaPuas();
	}
	if(x=="version"){
		tablaVersion();
	}
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

function tablaPuas(){
	var datos = $("#panelBusqueda").serialize();
	$.ajax({
	   type: "POST",
	   url: "PuaVersion.action",
	   data: "tipoAccion=tablaPua&"+datos,
	   success: function(msg){
	     	$("#listaPuas").html(msg);
	    }
	});
}



function eliminarVersion(idVersion){
    $.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
            confirmar: function () {
            	$.ajax({
            	   type: "POST",
            	   url: "PuaVersion.action",
            	   data: "tipoAccion=eliminarVersion&idPua="+idVersion,
            	   success: function(msg){
            		   	tablaPuas();
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


function eliminarPua(idPua){
    $.confirm({
        title: 'Confirmar para continuar!',
        content: 'Estas seguro que desea eliminar!',
        buttons: {
        	confirmar: function () {
            	$.ajax({
            	   type: "POST",
            	   url: "PuaVersion.action",
            	   data: "tipoAccion=eliminarPua&idPua="+idPua,
            	   success: function(msg){
            		   	tablaPuas();
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

function tablaVersion(){
	var datos = $("#panelBusqueda").serialize();
	$.ajax({
	   type: "POST",
	   url: "PuaVersion.action",
	   data: "tipoAccion=tablaPuaVersion&"+datos,
	   success: function(msg){
	     	$("#listaVersion").html(msg);
	    }
	});
}