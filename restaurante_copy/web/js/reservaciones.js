/* 
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */

function loaded(event) {
    getReservaciones();
    deleteReservacion();
}
function getReservaciones() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/reservation",
            contentType: "application/json"
        }).then((reservaciones) => {
            console.log(reservaciones);
            showReservaciones(reservaciones);
        }, (error) => {
            alert(errorMessage(error.status));
        });
    });
}
function showReservaciones(reservaciones) {
    $("#listaReservaciones").html("");
    reservaciones.forEach((re) => {
        fillReservaciones(re);
    });
}
function fillReservaciones(reservacion) {
    var id = reservacion.idReservacion;
    var usuario = reservacion.usuarioCorreo.usuarioCorreo;
    var id_mesa = reservacion.mesaIdMesa.idMesa;
    var fecha = reservacion.fecha;
    var cant = reservacion.cantidadPersonas;
    $("#listaReservaciones").append(
            '<tr>' +
            '<td class="list-action">' +
            '<div class="custom-control custom-checkbox">' +
            '<input type="checkbox" id="checkbox-' + id + '" class="custom-control-input" value="' + id + '" name="checked[]"/>' +
            '<label class="custom-control-label" for="checkbox-' + id + '">&nbsp;</label></div></td>' +
            '<td class="list-col-index-2 list-col-name-table-name list-col-type-text id="' + id + 'selectid">' + id + '</td>' +
            '<td class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + usuario + '</td>' +
            '<td class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + id_mesa + '</td>' +
            '<td class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + fecha + '</td>' +
            '<td class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + cant + '</td>' +
            '<td class="list-setup">&nbsp;</td></tr>'
            );
}
function deleteReservacion() {
    $("#eliminarRe").click(function () {
        var Options = $("[id*=checkbox-]");
        var OpSelected = [];
        for (let i = 0; i < Options.length; i++) {
            if (Options[i].checked) {
                OpSelected.push(Options[i].value);
            }
        }
        console.log(OpSelected);
        $.ajax({
            type: "POST",
            url: "api/reservation/delete",
            data: JSON.stringify(OpSelected),
            contentType: "application/json"
        }).then(() => {
            $("#listaReservaciones").html("");
            getReservaciones();
        }, (error) => {
            alert(errorMessage(error.status));
        });

    });
}
function errorMessage(status) {
    return "Ha ocurrido un error";
}
document.addEventListener("DOMContentLoaded", loaded);
