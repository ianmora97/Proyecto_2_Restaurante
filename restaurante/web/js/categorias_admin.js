function loaded(event) {
    fillCategories();
    ingresar();
    deleteCategorias();
    editCategoria();
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
                $("#alertS").alert();
            },
            error: function (status) {
                $("#alertB").alert();
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
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="'+nombre+'"  class="list-col-index-2 list-col-name-table-name list-col-type-text id="'+id+'selectid">' + id + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="'+nombre+'"  class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + nombre + '</td>' +
            '<td class="list-setup">&nbsp;</td></tr>'
            );
}
function editCategoria(categoria){
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var recipient = button.data('whatever');
        
        var modal = $(this);
        modal.find('.modal-title').text('Modificar' + recipient);
        modal.find('.modal-body input').val(recipient);
    })
}
function deleteCategorias() {
    $("#eliminarCat").click(function () {
        var Options = $("[id*=checkbox-]");                  

        var OpSelected = [];
        for (let i = 0; i < Options.length; i++) {
            if (Options[i].checked) {
                OpSelected.push(Options[i].value);
            }
        }
        $.ajax({
            type: "POST",
            url: "api/restaurante/deleteCate",
            data: JSON.stringify(OpSelected),
            success: function () {
                location.href ="categoriasAdmin.html";
            },
            error: function (status) {
                alert(errorMessage(status));
            }
        });
        
    });
}

function errorMessage(status) {
    return "Ha ocurrido un error";
}
document.addEventListener("DOMContentLoaded", loaded);