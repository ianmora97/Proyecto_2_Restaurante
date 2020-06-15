function loaded(event) {
    fillPlates();
    openAddPlatillo();
    fillCategorias();
    fillAdicionalesAdd();
    deletePlatos();
    editPlato();
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
            url: "api/restaurante/deletePlatos",
            data: JSON.stringify(OpSelected),
            success: function () {
                location.href ="platosAdmin.html";
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
        
    });
}
function fillAdicionalesAdd() {
    $(document).ready(function () {
        $.ajax({
            type: "POST",
            url: "api/restaurante/fillAdicionalesAdmin",
            success: function (adicionales) {
                adicionales.forEach((adicional) => {
                    fillAdi(adicional);
                });
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
    });

}
function fillAdi(adicional) {
    var nombre = adicional.nombre.replace(/ /g, "");
    var id = adicional.idAdicional;
    $("#fillAdicionales").append(
            '<div class="form-check">'+
            '<input class="form-check-input" style="" type="checkbox" name="checkbox-input" id="formCheck-'+id+'">'+
            '<label class="form-check-label" for="formCheck-'+id+'">'+nombre+'</label></div>'
        );
}
function fillCategorias() {
    $(document).ready(function () {
        $.ajax({
            type: "POST",
            url: "api/restaurante/categoriasAdmin",
            success: function (categoria) {
                categoria.forEach((c) => {
                    fillCate(c);
                });
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
    });

}
function fillCate(cate) {
    var nombre = cate.nombre.replace(/ /g, "");
    var id = cate.idCategoria;

    $("#opcionesCategorias").append(
            '<option value="'+id+'">'+nombre+'</option>'
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
            type: "POST",
            url: "api/restaurante/platosAdmin",
            success: function (platos) {
                showPlatos(platos);
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
    });
}
function showPlatos(platos) {
    platos.forEach((p) => {
        fillPlatos(p);
    });
}
function editPlato(categoria){
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var recipient = button.data('whatever');
        var parameter = recipient + "\n";
        var categoriaName;

        $.ajax({ //consulta el plato
            type: "POST",
            url: "api/restaurante/findPlato",
            data: parameter,
            success: function (plato) {
                console.log(plato.nombrePlatillo);
                $('#modalEdit').find('.modal-title').text('Modificar ' + plato.nombrePlatillo);
                $('#modalEdit').find('.modal-body input[name="nombrePlato"]').val(plato.nombrePlatillo);
                $('#modalEdit').find('.modal-body input[name="precioPlato"]').val(plato.precio);
                $('#modalEdit').find('.modal-body textarea[name="descripcion"]').val(plato.descripcion);
                categoriaName = plato.idCategoria.nombre.replace(/ /g, "");
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
        $("#opcionesCategoriasEdit").html(" ");
        $(document).ready(function () { //consulta categorias
            $.ajax({
                type: "POST",
                url: "api/restaurante/categoriasAdmin",
                success: function (categoria) {
                    categoria.forEach((cate) => {
                        var nombre = cate.nombre.replace(/ /g, "");
                        var id = cate.idCategoria;
                        console.log(categoriaName + " - " + nombre);
                        if(nombre === categoriaName){
                            $("#opcionesCategoriasEdit").append(
                                '<option selected value="'+id+'">'+nombre+'</option>'
                            );
                        }
                        else{
                            $("#opcionesCategoriasEdit").append(
                                '<option value="'+id+'">'+nombre+'</option>'
                            );
                        }
                    });
                },
                error: function (status) {
                    alert(errorMessage(status));
                }
            });
        });
    })
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
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="'+id+'"  class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + id + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="'+id+'"  class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + categoria + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="'+id+'"  class="list-col-index-5 list-col-name-min-capacity list-col-type-text ">' + nombre + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="'+id+'"  class="list-col-index-6 list-col-name-min-capacity list-col-type-text ">' + descripcion + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="'+id+'"  class="list-col-index-2 list-col-name-table-name list-col-type-text ">' + precio + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="'+id+'"  class="list-setup">&nbsp;</td></tr>'
            );
    $("#" + idEvento).click(function () {
        editplato(plato);
    });
}
document.addEventListener("DOMContentLoaded", loaded);