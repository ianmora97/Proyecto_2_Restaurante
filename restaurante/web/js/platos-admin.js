function loaded(event) {
    fillPlates();
    openAddPlatillo();
    fillCategorias();
    fillAdicionalesAdd();
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
            '<td class="list-col-index-2 list-col-name-table-name list-col-type-text ">' + id + '</td>' +
            '<td class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + categoria + '</td>' +
            '<td class="list-col-index-4 list-col-name-min-capacity list-col-type-text ">' + nombre + '</td>' +
            '<td class="list-col-index-5 list-col-name-min-capacity list-col-type-text ">' + descripcion + '</td>' +
            '<td class="list-col-index-6 list-col-name-min-capacity list-col-type-text ">' + precio + '</td>' +
            '<td class="list-setup">&nbsp;</td></tr>'
            );
    $("#" + idEvento).click(function () {
        editplato(plato);
    });
}
document.addEventListener("DOMContentLoaded", loaded);