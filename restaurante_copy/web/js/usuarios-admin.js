/* 
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */

function loaded(event) {
    getClientes();
    openModalEdit();
    editInfo();
    addInfo();
}
function getClientes() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/usuariosAdmin",
            contentType: "application/json"
        }).then((clientes) => {
            console.log(clientes);
            showClientes(clientes);
        }, (error) => {
            alert(errorMessage(error.status));
        });
    });
}
function showClientes(clientes) {
    $("#listaClientes").html("");
    clientes.forEach((cl) => {
        fillClientes(cl);
    });
}
function fillClientes(cliente) {
    var nombre = cliente.nombre;
    var apellido = cliente.apellidos;
    var id = cliente.usuarioCorreo;
    var telefono = cliente.telefono;
    $("#listaClientes").append(
            '<tr>' +
            '<td class="list-action">' +
            '<div class="custom-control custom-checkbox">' +
            '<input type="checkbox" id="checkbox-' + id + '" class="custom-control-input" value="' + id + '" name="checked[]"/>' +
            '<label class="custom-control-label" for="checkbox-' + id + '">&nbsp;</label></div></td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-nombre="' + nombre + '" data-apellido="' + apellido + '" data-tel="' + telefono + '" data-whatever="' + id + '"  class="list-col-index-2 list-col-name-table-name list-col-type-text id="' + id + 'selectid">' + nombre + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-nombre="' + nombre + '" data-apellido="' + apellido + '" data-tel="' + telefono + '"  data-whatever="' + id + '"  class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + apellido + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-nombre="' + nombre + '" data-apellido="' + apellido + '" data-tel="' + telefono + '"  data-whatever="' + id + '"  class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + id + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-nombre="' + nombre + '" data-apellido="' + apellido + '" data-tel="' + telefono + '"  data-whatever="' + id + '"  class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + telefono + '</td>' +
            '<td class="list-setup">&nbsp;</td></tr>'
            );
}
function openModalEdit() {
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var correo = button.data('whatever');
        var nombre = button.data('nombre');
        var apellido = button.data('apellido');
        var telefono = button.data('tel');

        var modal = $(this);
        modal.find('.modal-title').text('Editar ' + correo);
        modal.find('.modal-body input[name="nombre"]').val(nombre);
        modal.find('.modal-body input[name="apellido"]').val(apellido);
        modal.find('.modal-body input[name="correo"]').val(correo);
        modal.find('.modal-body input[name="telefono"]').val(telefono);

    });
}
function editInfo() {
    $("#editModalButton").click(function () {
        var modal = $("#modalEdit");
        var nombre = modal.find('.modal-body input[name="nombre"]').val();
        var apellido = modal.find('.modal-body input[name="apellido"]').val();
        var correo = modal.find('.modal-body input[name="correo"]').val();
        var telefono = modal.find('.modal-body input[name="telefono"]').val();

        var obj = {
            nombre: nombre,
            apellidos: apellido,
            usuarioCorreo: correo,
            telefono: telefono
        };
        console.log(obj);
        $(document).ready(function () {
            $.ajax({
                type: "PUT",
                url: "api/usuariosAdmin",
                data: JSON.stringify(obj),
                contentType: "application/json"
            }).then(() => {
                $("#listaClientes").html("");
                getClientes();
            }, (error) => {
                alert(errorMessage(error.status));
            });
        });
    });
}
function addInfo() {
    $("#sendAdd").click(function () {
        var modal = $("#modalAdd");
        var nombre = modal.find('.modal-body input[name="nombre"]').val();
        var apellido = modal.find('.modal-body input[name="apellido"]').val();
        var correo = modal.find('.modal-body input[name="email"]').val();
        var telefono = modal.find('.modal-body input[name="telefono"]').val();
        var clave = modal.find('.modal-body input[name="clave"]').val();
        var rol = document.getElementById('rol').checked;
        var user = correo.substring(0, correo.indexOf('@'));
        var usuario = {
            usuarioCorreo: correo,
            username: user,
            contrasena: clave,
            rol:+rol
        };
        var obj = {
            nombre: nombre,
            apellidos: apellido,
            usuarioCorreo: correo,
            telefono: telefono,
            usuario: usuario
        };
        console.log(obj);
        $(document).ready(function () {
            $.ajax({
                type: "POST",
                url: "api/usuariosAdmin/add",
                data: JSON.stringify(obj),
                contentType: "application/json"
            }).then(() => {
                $("#listaClientes").html("");
                getClientes();
            }, (error) => {
                alert(errorMessage(error.status));
            });
        });
    });
}
function errorMessage(status) {
    return "Ha ocurrido un error";
}
document.addEventListener("DOMContentLoaded", loaded);
