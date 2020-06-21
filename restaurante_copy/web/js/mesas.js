function loaded(event) {
    getMesas();
    insertarMesas();
    editModal();
    editSend();
    deleteMesas();
}
function getMesas() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/mesasAdmin",
            contentType: "application/json"
        }).then((mesas) => {
            console.log(mesas);
            showMesas(mesas);
        }, (error) => {
            alert(error.status);
        });
    });
}
function showMesas(mesas) {
    $("#listaMesas").html("");
    mesas.forEach((mesa) => {
        fillMesas(mesa);
    });
}

function fillMesas(mesa) {
    var id = mesa.idMesa;
    var capMax = mesa.capMax;
    var capMin = mesa.capMin;
    var estado = mesa.estado;
    $("#listaMesas").append(
            '<tr>' +
            '<td class="list-action">' +
            '<div class="custom-control custom-checkbox">' +
            '<input type="checkbox" id="checkbox-' + id + '" class="custom-control-input" value="' + id + '" name="checked[]"/>' +
            '<label class="custom-control-label" for="checkbox-' + id + '">&nbsp;</label></div></td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-max="' + capMax + '" data-min="' + capMin + '" data-estado="' + estado + '" data-whatever="' + id + '"  class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + id + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-max="' + capMax + '" data-min="' + capMin + '" data-estado="' + estado + '" data-whatever="' + id + '"  class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + capMax + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-max="' + capMax + '" data-min="' + capMin + '" data-estado="' + estado + '" data-whatever="' + id + '"  class="list-col-index-5 list-col-name-min-capacity list-col-type-text ">' + capMin + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-max="' + capMax + '" data-min="' + capMin + '" data-estado="' + estado + '" data-whatever="' + id + '"  class="list-col-index-6 list-col-name-min-capacity list-col-type-text ">' + estado + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-setup">&nbsp;</td></tr>'
            );
}

function insertarMesas() {
    $("#ingresarMesa").click(function () {
        var max = $('#modalAdd').find('.modal-body input[name="capMax"]').val();
        var min = $('#modalAdd').find('.modal-body input[name="capMin"]').val();
        var estado = $('#modalAdd').find('.modal-body input[name="estado"]').val();
        
        var mesa = {
                capMax: max,
                capMin: min,
                estado: estado
            };
        console.log(mesa);
        $.ajax({
            type: "POST",
            url: "api/mesasAdmin",
            data: JSON.stringify(mesa),
            contentType: "application/json"
        }).then(() => {
            $("#listaMesas").html("");
            getMesas();
        }, (error) => {
        });

    });
}

function deleteMesas() {
    $("#eliminarMesas").click(function () {
        var Options = $("[id*=checkbox-]");

        var OpSelected = [];
        for (let i = 0; i < Options.length; i++) {
            if (Options[i].checked) {
                OpSelected.push(Options[i].value);
            }
        }
        $.ajax({
            type: "POST",
            url: "api/mesasAdmin/delete",
            data: JSON.stringify(OpSelected),
            contentType: "application/json"
        }).then(() => {
            $("#listaMesas").html("");
            getMesas();
        }, (error) => {
            alert(errorMessage(error.status));
        });

    });
}
function editModal() {
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var id = button.data('whatever');
        var max = button.data('max');
        var min = button.data('min');
        var estado = button.data('estado');

        $('#modalEdit').find('.modal-title').text('Modificar ' + id);
        $('#modalEdit').find('.modal-body input[name="capMax"]').val(max);
        $('#modalEdit').find('.modal-body input[name="capMin"]').val(min);
        $('#modalEdit').find('.modal-body input[name="estado"]').val(estado);
        $('#idInput').val(id);
    });
}
function editSend() {
    $(document).ready(function () {
        $("#sendChange").click(function () {
            var id = $('#idInput').val();
            var max = $('#modalEdit').find('.modal-body input[name="capMax"]').val();
            var min = $('#modalEdit').find('.modal-body input[name="capMin"]').val();
            var estado = $('#modalEdit').find('.modal-body input[name="estado"]').val();

            var mesa = {
                idMesa: id,
                capMax: max,
                capMin: min,
                estado: estado
            };
            console.log(mesa);
            $.ajax({
                type: "PUT",
                url: "api/mesasAdmin",
                data: JSON.stringify(mesa),
                contentType: "application/json"
            }).then(() => {
                $("#listaMesas").html("");
                getMesas();
            }, (error) => {
                alert(error.status);
            });

        });
    });
}

document.addEventListener("DOMContentLoaded", loaded);