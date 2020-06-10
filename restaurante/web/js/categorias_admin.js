function loaded(event) {
    fillCategories();
    ingresar();
}
function fillCategories() {
    $(document).ready(function () {
        $.ajax({
            type: "POST",
            url: "api/restaurante/categoriasAdmin",
            success: function (categorias) {
                showCategorias(categorias);
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
    });
}
function ingresar(status) {
    $("#sendLogin").click(function () {
        var nombre = $("#nombreCategoria").val();
        $.ajax({
            type: "POST",
            url: "api/restaurante/addCategoria",
            data: nombre,
            success: function (categoria) {
                location.href ="categoriasAdmin.html";
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
    });
}
function showCategorias(categorias) {
    categorias.forEach((cat) => {
        fillCategorias(cat);
    });
}
function fillCategorias(categoria) {
    var nombre = categoria.nombre.replace(/ /g, "");
    var id = categoria.idCategoria;
    var event = nombre + id;
    $("#listaCategorias").append(
            '<tr>' +
            '<td class="list-action">' +
            '<div class="custom-control custom-checkbox">' +
            '<input type="checkbox" id="checkbox-' + id + '" class="custom-control-input" value="' + id + '" name="checked[]"/>' +
            '<label class="custom-control-label" for="checkbox-' + id + '">&nbsp;</label></div></td>' +
            '<td class="list-col-index-2 list-col-name-table-name list-col-type-text ">' + id + '</td>' +
            '<td class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + nombre + '</td>' +
            '<td class="list-setup">&nbsp;</td></tr>'
            );
    $("#" + event).click(function () {
        editCategoria(categoria);
    });
}
document.addEventListener("DOMContentLoaded", loaded);