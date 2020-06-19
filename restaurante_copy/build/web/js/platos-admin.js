function loaded(event) {
    fillPlates();
    openAddPlatillo();
    fillCategorias();
    //fillAdicionalesAdd();
    deletePlatos();
    editPlato();
    editSend();
    insertarPlatos();
    //buttonAdicionales();
}
//function newAdicional(){
//    $(document).ready(function () {
//        $("#openAdicional").click(function () {
//            
//        });
//    });
//}
//function buttonAdicionales(){
//    $('#modalAdicional').on('show.bs.modal', function (event) {
//        var button = $(event.relatedTarget);
//        var recipient = button.data('whatever');
//        var parameter = recipient + "\n";
//
//        $.ajax({ //consulta el plato
//            type: "POST",
//            url: "api/restaurante/findPlato",
//            data: parameter,
//            success: function (plato) {
//                console.log(plato.nombrePlatillo);
//                $('#modalAdicional').find('.modal-title').text('Añadir Adicionales a ' + plato.nombrePlatillo);
//                $('#idInput').val(plato.idPlatillo);                
//            },
//            error: function (status) {
//                alert(errorMessage(status));
//            }
//        });
//    })
//}
function insertarPlatos() {
    $("#ingresarPlato").click(function () {
        var nombre = $('#modalAdd').find('.modal-body input[name="nombrePlatoI"]').val();
        var precio = $('#modalAdd').find('.modal-body input[name="precioPlatoI"]').val();
        var descri = $('#modalAdd').find('.modal-body textarea[name="descripcionI"]').val();
        var categoria = $('#opcionesCategorias').children('option:selected').val();
        var idCategoria = {idCategoria: categoria};
        var platillo = {
            nombrePlatillo: nombre,
            precio: precio,
            descripcion: descri,
            idCategoria: idCategoria
        };
        console.log(platillo);
        $.ajax({
            type: "POST",
            url: "api/platosAdmin",
            data: JSON.stringify(platillo),
            contentType: "application/json"
        }).then(() => {
            fillPlates();
        }, (error) => {
        });

    });
}

function deletePlatos() {
    $("#eliminarPlatos").click(function () {
        var Options = $("[id*=checkbox-]");

        var OpSelected = [];
        for (let i = 0; i < Options.length; i++) {
            if (Options[i].checked) {
                OpSelected.push(Options[i].value);
            }
        }
        $.ajax({
            type: "POST",
            url: "api/platosAdmin/delete",
            data: JSON.stringify(OpSelected),
            contentType: "application/json"
        }).then(() => {
            fillPlates();
        }, (error) => {
            alert(errorMessage(error.status));
        });

    });
}
function fillAdicionalesAdd() {
    $(document).ready(function () {
        $.ajax({
            type: "POST",
            url: "api/restaurante/fillAdicionalesAdmin",
            contentType: "application/json"
        }).then((adicionales) => {
            adicionales.forEach((adicional) => {
                fillAdi(adicional);
            });
        }, (error) => {
            alert(error.status);
        });
    });
}
//function fillAdi(adicional) {
//    var nombre = adicional.nombre.replace(/ /g, "");
//    var id = adicional.idAdicional;
//    $("#fillAdicionales").append(
//            '<div class="form-check">' +
//            '<input class="form-check-input" style="" type="checkbox" name="checkbox-input" id="formCheck-' + id + '">' +
//            '<label class="form-check-label" for="formCheck-' + id + '">' + nombre + '</label></div>'
//            );
//}
function fillCategorias() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/categoriasAdmin",
            contentType: "application/json"
        }).then((categorias) => {
            categorias.forEach((c) => {
                fillCate(c);
            });
        }, (error) => {
            alert(errorMessage(error.status));
        });
    });

}
function fillCate(cate) {
    var nombre = cate.nombre.replace(/ /g, "");
    var id = cate.idCategoria;

    $("#opcionesCategorias").append(
            '<option value="' + id + '">' + nombre + '</option>'
            );
}
function openAddPlatillo(event) {
    $("#bntEdit").click(function () {
        $("#modalConteiner").show();
    });
}
function fillPlates() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/platosAdmin",
            contentType: "application/json"
        }).then((platos) => {
            showPlatos(platos);
        },
                (error) => {
            alert(error.status);
        });
    });
}
function showPlatos(platos) {
    $("#listaPlatillosAdmin").html("");
    platos.forEach((p) => {
        fillPlatos(p);
    });
}
function editPlato(categoria) {
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var recipient = button.data('whatever');
        var parameter = recipient;
        var categoriaName;
        $.ajax({//consulta el plato
            type: "POST",
            url: "api/platosAdmin/findPlatos",
            data: JSON.stringify(parameter),
            contentType: "application/json"
        }).then((plato) => {
            console.log(plato.nombrePlatillo);
            $('#modalEdit').find('.modal-title').text('Modificar ' + plato.nombrePlatillo);
            $('#modalEdit').find('.modal-body input[name="nombrePlato"]').val(plato.nombrePlatillo);
            $('#modalEdit').find('.modal-body input[name="precioPlato"]').val(plato.precio);
            $('#modalEdit').find('.modal-body textarea[name="descripcion"]').val(plato.descripcion);
            $('#idInput').val(plato.idPlatillo);
            $("#opcionesCategoriasEdit").html(" ");
            categoriaName = plato.idCategoria.nombre.replace(/ /g, "");
        }, (error) => {
            alert(error.status);
        }).then(() => {
            $.ajax({
                type: "GET",
                url: "api/categoriasAdmin",
                contentType: "application/json"
            }).then((categoria) => {
                categoria.forEach((cate) => {
                    var nombre = cate.nombre.replace(/ /g, "");
                    var id = cate.idCategoria;
                    console.log(categoriaName + " - " + nombre);
                    if (nombre === categoriaName) {
                        $("#opcionesCategoriasEdit").append(
                                '<option selected value="' + id + '">' + nombre + '</option>'
                                );
                    } else {
                        $("#opcionesCategoriasEdit").append(
                                '<option value="' + id + '">' + nombre + '</option>'
                                );
                    }
                });
            }, (error) => {
                alert(error.status);
            });
        });
    });
}
function editSend() {
    $(document).ready(function () {
        $("#sendChange").click(function () {
            var id = $('#idInput').val();
            var nombre = $('#modalEdit').find('.modal-body input[name="nombrePlato"]').val();
            var precio = $('#modalEdit').find('.modal-body input[name="precioPlato"]').val();
            var descri = $('#modalEdit').find('.modal-body textarea[name="descripcion"]').val();
            var cate = $('#opcionesCategoriasEdit').children('option:selected').val();

            var idCategoria = {idCategoria: cate};
            var platillo = {
                nombrePlatillo: nombre,
                precio: precio,
                descripcion: descri,
                idCategoria: idCategoria,
                idPlatillo: id
            };
            $.ajax({
                type: "PUT",
                url: "api/platosAdmin",
                data: JSON.stringify(platillo),
                contentType: "application/json"
            }).then(() => {
                fillPlates();
            }, (error) => {
                alert(error.status);
            });

        });
    });
}

function fillPlatos(plato) {
    var id = plato.idPlatillo;
    var categoria = plato.idCategoria.nombre.replace(/ /g, "");
    var nombre = plato.nombrePlatillo.replace(/ /g, "");
    var descripcion = plato.descripcion;
    var precio = plato.precio;

    var idEvento = nombre + id;

    $("#listaPlatillosAdmin").append(
            '<tr>' +
            '<td class="list-action">' +
            '<div class="custom-control custom-checkbox">' +
            '<input type="checkbox" id="checkbox-' + id + '" class="custom-control-input" value="' + id + '" name="checked[]"/>' +
            '<label class="custom-control-label" for="checkbox-' + id + '">&nbsp;</label></div></td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + id + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + categoria + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-col-index-5 list-col-name-min-capacity list-col-type-text ">' + nombre + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-col-index-6 list-col-name-min-capacity list-col-type-text ">' + descripcion + '</td>' +
            '<td class="list-col-index-6 list-col-name-min-capacity list-col-type-text ">' +
            '<div class="btn btn-primary" data-toggle="modal" data-target="#modalAdicional"  data-whatever="' + id + '" ><i class="fa fa-edit"></i>Adicionales</div></td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-col-index-2 list-col-name-table-name list-col-type-text ">' + precio + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + id + '"  class="list-setup">&nbsp;</td></tr>'
            );
    $("#" + idEvento).click(function () {
        editplato(plato);
    });
}
document.addEventListener("DOMContentLoaded", loaded);