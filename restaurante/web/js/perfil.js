function loaded(event) {
	cargarDatos();
	fillAddressBook();
	editDireccion();
	editSend();
    insertarDireccion();
}	
function cargarDatos(){
	$.ajax({
        type: "POST",
        url: "/restaurante/api/restaurante/getUser",
        success: function (usuario) {
        	$("#bienvenidoName").text("Bienvenido "+usuario.cliente.nombre);
        	$("#nombre").val(usuario.cliente.nombre);
        	$("#apellido").val(usuario.cliente.apellidos);
        	$("#telefono").val(usuario.cliente.telefono);
        	$("#correo").val(usuario.cliente.usuarioCorreo);

        	console.log(usuario);
        },
        error: function (status) {
            $("#alertB").alert();
        }
    });
}

function fillAddressBook() {
	$.ajax({
		type: "POST",
		url: "/restaurante/api/restaurante/getAddressBook",
		success: function (addressBook) {
			
			console.log(addressBook);
			if (addressBook.length) {
				addressBook.forEach((address) => {
					var res = address.direccion.replace(/ /g, "");
					$("#SelectAddress").append('<i class="fa fa-dot-circle-o" aria-hidden="true"></i>&nbsp;<span style="color:black;" value="'+ address.idUbicacion + '" >' + address.direccion + '</span> <br /><br />');
					$("#SelectAddress").append('<address class="text-left">'+address.provincia+'<br>'+address.canton+'<br>'+address.direccion+'<br>'+address.codigoPostal+'</address>');
					$("#direccionesAd").append(
						'<div class="container"><div class="row"><div class="col-8"><i class="fa fa-dot-circle-o" aria-hidden="true"></i>&nbsp;<span style="color:black;" value="'+ address.idUbicacion + '" >' + address.direccion + '</span><br />'
						+'<address class="text-left">'+address.provincia+'<br>'+address.canton+'<br>'+address.direccion+'<br>'+address.codigoPostal+'</address></div>'
						+'<div class="col-4"><div class="btn orange" data-toggle="modal" data-target="#modalEdit" data-whatever="'+address.idUbicacion+'" ><i class="fa fa-edit"></i>Editar</div></div></div></div>');
				});
			}
			if(addressBook.length === 0){
				$("#SelectAddress").append('<p>Usted no tiene direcciones</p>');
			}
		},
		error: function (status) {
			alert(errorMessage(status));
		}
	});
}
function editDireccion(categoria){
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var recipient = button.data('whatever');
        var parameter = recipient + "\n";
        var categoriaName;

        $.ajax({ //consulta la direccion
            type: "POST",
            url: "/restaurante/api/restaurante/getDireccion",
            data: parameter,
            success: function (direccion) {
                console.log(direccion.nombrePlatillo);
                $('#modalEdit').find('.modal-title').text('Modificar ' + direccion.direccion);
                $('#modalEdit').find('.modal-body input[name="provincia"]').val(direccion.provincia);
                $('#modalEdit').find('.modal-body input[name="canton"]').val(direccion.canton);
                $('#modalEdit').find('.modal-body input[name="codigo"]').val(direccion.codigoPostal);
                $('#modalEdit').find('.modal-body textarea[name="direccion"]').val(direccion.direccion);
                $('#idInput').val(direccion.idUbicacion);
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
    });
}
function editSend(){
    $(document).ready(function () {
        $("#sendChange").click(function () {

            var provincia = $('#text-input-1').val();
            var canton =  $('#text-input-2').val();
            var codigo = $('#text-input-3').val();
            var direccion = $('#textarea-input-4').val();
            var id = $('#idInput').val();
            var para = provincia + "\n" + canton + "\n" + codigo + "\n" + direccion +"\n" + id;

            $.ajax({
                type: "POST",
                url: "/restaurante/api/restaurante/sendEditUbicacion",
                data: para,
                success: function () {
                },
                error: function (status) {
                    $("#alertB").alert();
                }
            });
            
        });
    });
}
function insertarDireccion(){
    $("#ingresarDireccion").click(function () {
        var provincia = $('#text-input-1').val();
        var canton =  $('#text-input-2').val();
        var codigo = $('#text-input-3').val();
        var direccion = $('#textarea-input-4').val();

        var para = provincia + "\n" + canton + "\n" + codigo + "\n" + direccion;
        console.log(para);
        $.ajax({
            type: "POST",
            url: "/restaurante/api/restaurante/ingresarDireccion",
            data: para,
            success: function () {
                
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
        
    });
}
document.addEventListener("DOMContentLoaded", loaded);