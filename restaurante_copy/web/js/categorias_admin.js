function loaded(event) {
    fillCategories();
    ingresar();
    deleteCategorias();
    editCategoria();
    editCategoriaSend();
    buscarCategorias();
}
function fillCategories() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/categoriasAdmin",
            contentType: "application/json"
        }).then((categorias) => {
            showCategorias(categorias);
        }, (error) => {
            alert(errorMessage(error.status));
        });
    });
}
function showCategorias(categorias) {
    $("#listaCategorias").html("");
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
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + nombre + '"  class="list-col-index-2 list-col-name-table-name list-col-type-text id="' + id + 'selectid">' + id + '</td>' +
            '<td data-toggle="modal" data-target="#modalEdit" data-whatever="' + nombre + '"  class="list-col-index-3 list-col-name-min-capacity list-col-type-text ">' + nombre + '</td>' +
            '<td class="list-setup">&nbsp;</td></tr>'
            );
}
function ingresar(status) {
    $("#sendAdd").click(function () {
        var categoria = {
            nombre: $("#nombreCategoria").val()
        };
        $.ajax({
            type: "POST",
            url: "api/categoriasAdmin",
            data: JSON.stringify(categoria),
            contentType: "application/json"
        }).then(() => {
            fillCategories();
            $("#alertS").alert();
        }, (error) => {
            $("#alertB").alert();
        });
    }
    );
}

function editCategoria(categoria) {
    $('#modalEdit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var recipient = button.data('whatever');

        var modal = $(this);
        modal.find('.modal-title').text('Modificar ' + recipient);
        modal.find('.modal-body input').val(recipient);
    });
}
function editCategoriaSend() {
    $("#editModalCat").click(function () {

        var obj = [];
        obj.push($("#nombreCategoriaO").val());
        obj.push($("#nombreCategoriaN").val());

        $.ajax({
            type: "PUT",
            url: "api/categoriasAdmin",
            data: JSON.stringify(obj), 
            contentType: "application/json"
        }).then(()=>{
            fillCategories();
            alert("Categoria Editada Correctamente!");
            
        },
        (error)=>{alert(error.status)});

    });
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
        console.log(JSON.stringify(OpSelected));
        $.ajax({
            type: "POST",
            url: "api/categoriasAdmin/delete",
            data: JSON.stringify(OpSelected),
            contentType: "application/json"
        }).then(
            ()=>{
                alert("Categoria Eliminada");
                fillCategories();
            },
        (error)=>{alert(error.status);
        });

    });
}
function buscarCategorias() {
    $("#buscarCat").click(function () {
        var OpSelected = {nombre:$("#nombreBus").val()};
        $.ajax({
            type: "POST",
            url: "api/categoriasAdmin/buscar",
            data: JSON.stringify(OpSelected),
            contentType: "application/json"
        }).then(
            
            (categorias)=>{
                console.log(categorias);
                showCategorias(categorias);
            },
        (error)=>{alert(error.status);
        });

    });
}
function errorMessage(status) {
    return "Ha ocurrido un error";
}
document.addEventListener("DOMContentLoaded", loaded);