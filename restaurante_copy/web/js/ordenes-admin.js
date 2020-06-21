/* 
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */

function loaded(event) {
    getOrdersClient();
    buscarOrdenes();
    openModalOrder();
    changeEditModel();
}
function getOrdersClient() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/ordenAdmin",
            contentType: "application/json"
        }).then((ordenes) => {
            console.log(ordenes);
            showOrdersClient(ordenes);
        }, (error) => {
            alert(errorMessage(error.status));
        });
    });
}
function showOrdersClient(ordenes) {
    $("#listaOrdenesCliente").html("");
    ordenes.forEach((or) => {
        fillOrdersClient(or);
    });
}
function fillOrdersClient(orden) {
    var nombre = orden.nombre + " " + orden.apellidos;
    var id = orden.id;
    var status = orden.status;
    $("#listaOrdenesCliente").append(
            '<tr>' +
            '<td class="list-action">' +
            '<div class="custom-control custom-checkbox">' +
            '<input type="checkbox" id="checkbox-' + id + '" class="custom-control-input" value="' + id + '" name="checked[]"/>' +
            '<label class="custom-control-label" for="checkbox-' + id + '">&nbsp;</label></div></td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-col-index-2 list-col-name-table-name list-col-type-text id="' + id + 'selectid">' + nombre + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + id + '</td>' +
            '<td class="list-col-index-3 list-col-name-min-capacity list-col-type-text "><div class="btn orange" data-toggle="modal" data-target="#modalChange" data-status="'+status+'" data-id="'+id+'"><i class="fa fa-edit"></i>' + status + '</div></td>' +
            '<td class="list-setup">&nbsp;</td></tr>'
            );
}
function buscarOrdenes() {
    $("#buscarCat").click(function () {
        var OpSelected = {nombre: $("#nombreBus").val()};
        $.ajax({
            type: "POST",
            url: "/restaurante_copy/api/ordenAdmin/buscar",
            data: JSON.stringify(OpSelected),
            contentType: "application/json"
        }).then((Ordenes) => {
            console.log(Ordenes);
            showOrdersClient(Ordenes);
        },(error) => {
            alert(error.status);
        });

    });
}
function fillOrdenesRecu(orden) {
    var nombre = orden.nombre;
    var status = orden.estatus;
    var tipoEntrega = orden.tipoEntrega === 1 ? "Delivery" : "Pick Up";
    var direccion = orden.direccion;
    var plato = orden.nombrePlatillo;
    var adicional = orden.nombreAdicional;
    var opcion = orden.nombreOpcion;
    var total = orden.total;
    $("#listaOrdenes").append(
            '<tr>' +
            '<td class="list-col-index-1 list-col-name-min-capacity list-col-type-text ">' + nombre + '</td>' +
            '<td class="list-col-index-2 list-col-name-min-capacity list-col-type-text ">' + status + '</td>' +
            '<td class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + tipoEntrega + '</td>' +
            '<td class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + direccion + '</td>' +
            '<td class="list-col-index-5 list-col-name-min-capacity list-col-type-text ">' + plato + '</td>' +
            '<td class="list-col-index-6 list-col-name-min-capacity list-col-type-text ">' + adicional + '</td>' +
            '<td class="list-col-index-7 list-col-name-min-capacity list-col-type-text ">' + opcion + '</td>' +
            '<td class="list-col-index-8 list-col-name-min-capacity list-col-type-text ">' + total + '</td>' +
            '</tr>'
            );
}
function openModalOrder(categoria) {
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var recipient = button.data('whatever');

        $(document).ready(function () {
            $.ajax({
                type: "POST",
                url: "api/ordenAdmin/buscar",
                data: JSON.stringify(recipient),
                contentType: "application/json"
            }).then((ordenes) => {
                console.log(ordenes);
                $("#listaOrdenes").html("");
                ordenes.forEach((or) => {
                    fillOrdenesRecu(or);
                });
            }, (error) => {
                alert(errorMessage(error.status));
            });
        });
        var modal = $(this);
        modal.find('.modal-title').text('Ver Orden #' + recipient);
    });
}
function changeEditModel(categoria) {
    $('#modalChange').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var estatus = button.data('status');
        var id = button.data('id');

        var cambio;
        $('#modalChange').modal('hide');
        if(estatus === "En preparacion"){
            cambio = "Listo";
        }
        if(estatus === "Listo"){
            cambio = "Entregado";
        }
        if(estatus === "Entregado"){
            cambio = "Entregado";
        }
        var orden = {
            estatus:cambio,
            idOrden:id
        };
        $(document).ready(function () {
            $.ajax({
                type: "POST",
                url: "api/ordenAdmin/add",
                data: JSON.stringify(orden),
                contentType: "application/json"
            }).then(() => {
                $("#listaOrdenes").html("");
                getOrdersClient();
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