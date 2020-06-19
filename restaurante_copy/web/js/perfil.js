function loaded(event) {
    fillAddressBook();
    editDireccion();
    editSend();
    insertarDireccion();
    getUserInSession();
    createOrden();
    reservationTable();
    logOut();
}

function getUserInSession() {
    var usuario = JSON.parse(sessionStorage.getItem("usuario"));
    if (usuario !== null && usuario !== undefined) {
        $("#registerAnchor").hide();
        $("#loginAnchor").hide();
        $("#dropdownMenuBtn").show();
        $("#dropdownMenuBtn").html(" ");
        $("#dropdownMenuBtn").append(usuario.cliente.nombre);
        $("#bienvenidoName").text("Bienvenido " + usuario.cliente.nombre);
        $("#nombre").val(usuario.cliente.nombre);
        $("#apellido").val(usuario.cliente.apellidos);
        $("#telefono").val(usuario.cliente.telefono);
        $("#correo").val(usuario.cliente.usuarioCorreo);
    } else {
        $("#registerAnchor").show();
        $("#loginAnchor").show();
        $("#dropdownMenuBtn").hide();
    }
}//listo

function fillAddressBook() {
    $.ajax({
        type: "POST",
        url: "/restaurante_copy/api/chekOut/addressBook",
        data: sessionStorage.getItem("usuario"),
        contentType: "application/json"
    }).then((addressBook) => {
        if (addressBook.length !== 0) {
            addressBook.forEach((address) => {
                 $("#SelectAddress").html(" ");
                 $("#direccionesAd").html(" ");
                var res = address.direccion.replace(/ /g, "");
                $("#SelectAddress").append('<i class="fa fa-dot-circle-o" aria-hidden="true"></i>&nbsp;<span style="color:black;" value="' + address.idUbicacion + '" >' + address.direccion + '</span> <br /><br />');
                $("#SelectAddress").append('<address class="text-left">' + address.provincia + '<br>' + address.canton + '<br>' + address.direccion + '<br>' + address.codigoPostal + '</address>');
                $("#direccionesAd").append(
                        '<div class="container"><div class="row"><div class="col-8"><i class="fa fa-dot-circle-o" aria-hidden="true"></i>&nbsp;<span style="color:black;" value="' + address.idUbicacion + '" >' + address.direccion + '</span><br />'
                        + '<address class="text-left">' + address.provincia + '<br>' + address.canton + '<br>' + address.direccion + '<br>' + address.codigoPostal + '</address></div>'
                        + '<div class="col-4"><div class="btn orange" data-toggle="modal" data-target="#modalEdit" data-edit="' + address.idUbicacion + '" ><i class="fa fa-edit"></i>Editar</div></div></div></div>');
            });
        } else {
            $("#SelectAddress").append('<p>Usted no tiene direcciones</p>');
        }
    }, (error) => {
        alert(error.status);
    });
}//LISTO
function reservationTable() {
    $("#reservationTable").html(" ");
    $.ajax({
        type: "POST",
        url: "/restaurante_copy/api/reservation/list",
        data: sessionStorage.getItem("usuario"),
        contentType: "application/json"
    }).then((reservaciones) => {
        reservaciones.forEach((reservacion) => {
            $("#reservationTable").append('<tr><th scope="row">' + reservacion.idReservacion + '</th>' +
                    '<td>' + reservacion.mesaIdMesa.idMesa + '</td>' +
                    '<td>' + reservacion.usuarioCorreo.cliente.nombre + "&nbsp;&nbsp;" + reservacion.usuarioCorreo.cliente.apellidos + '</td>' +
                    '<td>' + reservacion.cantidadPersonas + '</td>' +
                    '<td>' + reservacion.fecha + '</td>' +
                    '</tr>');
        });
    }, (error) => {
        alert(error.status);
    });
    ;
}//LISTO

function editDireccion() {
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var recipient = button.data('edit');
        $.ajax({
            type: "POST",
            url: "/restaurante_copy/api/perfil/getDireccion",
            data: JSON.stringify(recipient),
            contentType: "application/json"
        }).then((direccion) => {
            $('#modalEdit').find('.modal-title').text('Modificar ' + direccion.direccion);
            $('#modalEdit').find('.modal-body input[name="provincia"]').val(direccion.provincia);
            $('#modalEdit').find('.modal-body input[name="canton"]').val(direccion.canton);
            $('#modalEdit').find('.modal-body input[name="codigo"]').val(direccion.codigoPostal);
            $('#modalEdit').find('.modal-body textarea[name="direccion"]').val(direccion.direccion);
            $('#sendChange').data('edit',recipient);
        }, (error) => {
            alert(error.status);
        });
    });
}
function editSend() {
    $(document).ready(function () {

        $("#sendChange").click(function () {
            var ubicaion = {
                provincia: $('#inputprovincia-edit').val(),
                canton: $('#inputCanton-edit').val(),
                codigoPostal: $('#inputCodigo-edit').val(),
                direccion: $('#inputDireccion-edit').val(),
                idUbicacion: $('#sendChange').data('edit')
            };
            $.ajax({
                type: "POST",
                url: "/restaurante_copy/api/perfil/edidAddre",
                data: JSON.stringify(ubicaion),
                contentType: "application/json"
            }).then(() => {
                fillAddressBook();
            }, (error) => {
                alert(error.status);
            });
        });
    }
    );
}
function insertarDireccion() {
    $("#ingresarDireccion").click(function () {
        var ubicaion = {
            provincia: $('#text-input-1').val(),
            canton: $('#text-input-2').val(),
            codigoPostal: $('#text-input-3').val(),
            direccion: $('#textarea-input-4').val(),
            idUbicacion: 0
        };
        var array = [];
        array.push(sessionStorage.getItem("usuario"));
        array.push(JSON.stringify(ubicaion));

        $.ajax({
            type: "POST",
            url: "/restaurante_copy/api/perfil/ingresarDireccion",
            data: JSON.stringify(array),
            contentType: "application/json"
        }).then(() => {
            fillAddressBook();
        }, (error) => {
            alert(error.status);
        });
        ;
    });
}

function logOut() {
    $("#logOutAnchor").click(function () {
        sessionStorage.removeItem("usuario");
        location.href = "/restaurante_copy/index.html";
    });
}//listo

//TRAER EL CARRITO DE LA SESION
function fillCart(platillos) {
    $("#dishSelectedList").html(" ");
    var cuenta = 0;
    platillos.forEach((platillo) => {
        var precio = 0;
        var platilloSinEspacios = platillo.nombrePlatillo.replace(/ /g, "");
        $("#dishSelectedList").append(
                '<li>' +
                '<button style="position: relative; top: -15px; " type="button" class="cart-btn btn btn-light btn-sm text-muted" id="' + platilloSinEspacios + 'Menos' + cuenta + '">' +
                '<i class="fa fa-minus"></i>' +
                '</button>' +
                '<button style="width: 80%;background-color: white; border:none;" type="button" id="' + platilloSinEspacios + cuenta + '" class="btn btn-light btn-sm btn-cart " data-toggle="modal" data-target="#modalOptions">' +
                '<span  style="float:left; font-size: 15px; font-weight: bold;"> ' + platillo.cantidad + 'x ' + platillo.nombrePlatillo + '</span> <span style="float:right;" id="' + platilloSinEspacios + 'PlatilloCartPrecio' + cuenta + '"></span>' +
                '<br><br>' +
                '<div id="' + platilloSinEspacios + 'PlatilloCart' + cuenta + '" class="text-muted" style="display: block;">' +
                '</div>' +
                '</button>' +
                '</li>'
                );
        var adicionales = platillo.adicionalCollection;
        adicionales.forEach((adi) => {
            var adicionalSinEspacios = adi.nombre.replace(/ /g, "");
            $('#' + platilloSinEspacios + 'PlatilloCart' + cuenta).append(
                    '<div id="' + adicionalSinEspacios + 'AdicionalCart' + cuenta + '" class="text-muted" style="display: block;">' +
                    '<span  style="float:left;">' + adi.nombre + '</span> <br>' +
                    '</div>'
                    );
            adi.opcionCollection.forEach((op) => {
                $('#' + adicionalSinEspacios + 'AdicionalCart' + cuenta).append('<span  style="float:left;">' + op.nombre + ' ' + op.precio + '</span><br>');
                precio += op.precio;
            });
        });
        precio += platillo.precio;
        precio = precio * platillo.cantidad;
        $("#" + platilloSinEspacios + 'PlatilloCartPrecio' + cuenta).append('$' + precio);
        $("#" + platilloSinEspacios + 'Menos' + cuenta).click(function () {
            decreseDish(platillo);
        });
        $("#" + platilloSinEspacios + cuenta).click(function () {
            getDishInOrder(platillo);
        });
        cuenta++;
        //        CIERRA EL FOR DE PLATILLOS
    });
}//listo
function getDishInOrder(platilloOrder) {
    var array = [];
    array.push(sessionStorage.getItem("orden"));
    array.push(JSON.stringify(platilloOrder));
    console.log(array);
    $.ajax({
        type: "POST",
        url: "/restaurante_copy/api/order/getPlatillo",
        data: JSON.stringify(array),
        contentType: "application/json"
    }).then((platilloCompleto) => {
        getAditional(platilloCompleto, platilloOrder);
        keepDishInOrder(platilloOrder);
    }, (error) => {
        alert(error.status);
    });

}//listo

function createOrden() {
    var orden = JSON.parse(sessionStorage.getItem("orden"));
    if (orden === null) {
        $.ajax({
            type: "GET",
            url: "/restaurante_copy/api/order/getOrder",
            contentType: "application/json"
        }).then((orden) => {
            sessionStorage.setItem("orden", JSON.stringify(orden));
            fillCart(orden.platilloseleccionadoCollection);
            $("#cart-totals").html(" ");
            $("#cart-totals").append("$ " + orden.total);
        }, (error) => {
            alert(error.status);
        });
    } else {
        fillCart(orden.platilloseleccionadoCollection);
    }

}//LISTO
function keepDishInOrder(platilloInOrder) {
    $("#saveDish").off();
    $("#saveDish").click(function () {
        var Options = $("[id*=OpId]");
        var OpSelected = [];
        for (let i = 0; i < Options.length; i++) {
            if (Options[i].checked) {
                OpSelected.push($("#" + Options[i].id + "Label").text());
            }
        }
        var nombre = $("#nombrePlatilloModal").text();
        var cantidad = $("#quantityModal").val();
        var OptionsSelected = JSON.stringify(OpSelected);
        var platilloOrder = JSON.stringify(platilloInOrder);
        var sendData = [];
        sendData.push(nombre);
        sendData.push(cantidad);
        sendData.push(OptionsSelected);
        sendData.push(platilloOrder);
        sendData.push(sessionStorage.getItem("orden"));
        $.ajax({
            type: "POST",
            url: "/restaurante_copy/api/order",
            data: JSON.stringify(sendData),
            contentType: "application/json"
        }).then((orden) => {
            sessionStorage.setItem("orden", JSON.stringify(orden));
            p_selected = orden.platilloseleccionadoCollection;
            fillCart(p_selected);
            $("#cart-totals").html(" ");
            $("#cart-totals").append(orden.total);
        }, (error) => {
            $("#seletedItems").fadeTo(2000, 500).slideUp(500, function () {
                $("#seletedItems").slideUp(500);
            });
        });
    });
} //listo
function decreseDish(platillo) {
    var orden = sessionStorage.getItem("orden");
    var array = [];
    array.push(orden);
    array.push(JSON.stringify(platillo));
    $.ajax({
        type: "POST",
        url: "/restaurante_copy/api/order/decreseQuant",
        data: JSON.stringify(array),
        contentType: "application/json"
    }).then((orden) => {
        sessionStorage.setItem("orden", JSON.stringify(orden));
        p_selected = orden.platilloseleccionadoCollection;
        fillCart(p_selected);
        $("#cart-totals").html(" ");
        $("#cart-totals").append(orden.total);
    }, (error) => {
        alert(error.status);
    });
}//listo
function fillOptions(adicional, adicionalOrder) {

    var opciones = adicional.opcionCollection;
    var opcionesOrden = null;
    if (adicionalOrder !== null) {
        opcionesOrden = adicionalOrder.opcionCollection;
    }

    var adicionalSinEspacios = adicional.nombre.replace(/ /g, "");
    var index = 0;
    opciones.forEach((opcion) => {
        var htmlOption = " ";
        var opcionesSinEspacios = opcion.nombre.replace(/ /g, "");
        if (adicional.tipo === 0) {
            htmlOption = '<div class="row justify-content-between my-1">' +
                    ' <div class="col-8">' +
                    ' <div class="custom-control custom-radio">' +
                    '<input type="radio" class="custom-control-input" id="' + opcionesSinEspacios + "OpId" + '" name="' + adicionalSinEspacios + "Name" + '" value="' + adicional.requerida + adicional.nombre + '">' +
                    '<label class="custom-control-label" for="' + opcionesSinEspacios + "OpId" + '" id="' + opcionesSinEspacios + "OpIdLabel" + '">' + opcion.nombre + '</label>' +
                    '</div>' +
                    '</div>' +
                    '<div class="col-3">' +
                    opcion.precio +
                    '</div>' +
                    '</div>';
        } else {
            htmlOption = '<div class="row justify-content-between my-1">' +
                    '<div class="col-8">' +
                    '<div class="custom-control custom-checkbox">' +
                    '<input type="checkbox" class="custom-control-input" id="' + opcionesSinEspacios + "OpId" + '" value="' + adicional.requerida + '">' +
                    '<label class="custom-control-label" for="' + opcionesSinEspacios + "OpId" + '" id="' + opcionesSinEspacios + "OpIdLabel" + '">' + opcion.nombre + '</label>' +
                    ' </div>' +
                    '</div>' +
                    '<div class="col-3">' +
                    opcion.precio +
                    '</div>' +
                    '</div>';
        }
        $("#" + adicionalSinEspacios).append(htmlOption);
        if (opcionesOrden !== null) {
            for (let i = 0; i < opcionesOrden.length; i++) {
                var opSinEspOrder = opcionesOrden[i].nombre.replace(/ /g, "");
                if (opcionesSinEspacios === opSinEspOrder) {
                    $("#" + opcionesSinEspacios + "OpId").attr("checked", "checked");
                }
            }
        }

        index++;
    });
} //listo
function getAditional(platillo, platilloOrder) {
    var Adicionales = platillo.adicionalCollection;
    var AdicionalesOrden = null;
    if (platilloOrder !== null) {
        AdicionalesOrden = platilloOrder.adicionalCollection;
    }

    $("#adtionalModal").html(" ");
    $("#nombrePlatilloModal").html(" ");
    $("#descPlatilloModal").html(" ");
    $("#priceModal").html(" ");
    $("#nombrePlatilloModal").append(platillo.nombrePlatillo);
    $("#descPlatilloModal").append(platillo.descripcion);
    $("#priceModal").append("$ " + platillo.precio);
    var index = 0;
    if (Adicionales.length !== 0) {
        Adicionales.forEach((adicional) => {
            var requerida = "Requerida";
            if (adicional.requerida === 0) {
                requerida = "Opcional";
            }
            var adicionalSinEspacios = adicional.nombre.replace(/ /g, "");
            $("#adtionalModal").append('<div class="container " id="' + adicionalSinEspacios + '">' +
                    '<div class="row bg-light py-2 my-2">' +
                    '<div class="col-9">' +
                    '<h5>' + adicional.nombre + '</h5>' +
                    ' </div>' +
                    '<div class="col-3 text-muted">' +
                    requerida +
                    '</div>' +
                    '</div>' +
                    '</div>');
            if (AdicionalesOrden !== null) {
                var bandera = false;
                for (let i = 0; i < AdicionalesOrden.length; i++) {
                    var adicionalOrder = AdicionalesOrden[i].nombre.replace(/ /g, "");
                    if (adicionalOrder === adicionalSinEspacios) {
                        fillOptions(adicional, AdicionalesOrden[i]);
                        bandera = true;
                    }
                }
                if (!bandera) {
                    fillOptions(adicional, null);
                }
            } else {
                fillOptions(adicional, null);
            }

            index++;
        });
    }


} //listo


document.addEventListener("DOMContentLoaded", loaded);