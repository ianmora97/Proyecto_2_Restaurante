

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.progra.restaurante.logic.*"%>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->

<%
    if (session.getAttribute("order") != null) {
        session.setAttribute("order", new Orden());
    }
%>

<html>
    <head>
        <title>Dely Restaurant </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" type="image/x-icon" href="/restaurante/images/logo.png" />

        <link rel="stylesheet" href="/restaurante/css/bootstrap-datetimepicker.min.css">
        <link rel="stylesheet" href="/restaurante/css/order.css">

        <!--bootstrap css-->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"  crossorigin="anonymous">
        <!--Link del css de la página original-->
        <link rel="stylesheet" type="text/css" href="https://demo.tastyigniter.com/themes/tastyigniter-orange/assets/css/app.css" name="app-css">

    </head>
    <body class="bg-light">
        <!--MODAL PARA LA FECHA-->
        <div class="modal fade" tabindex="-1" id="modal-date" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Pick a day</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <div class='input-group date' id='datetimepicker1'>
                                <input type='text' class="form-control" id="dateText"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                                <span class="input-group-text">
                                    <i class="fas fa-calendar-alt"></i>
                                </span>
                            </div>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="saveDate">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN DEL MODAL--> 

        <!--MODAL PARA LAS ADICIONALES-->
        <div class="modal" tabindex="-1" role="dialog" id="modalOptions">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="nombrePlatilloModal"> </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p class="text-muted text-center" id="descPlatilloModal"></p>

                    </div>

                    <div class="modal-body " id="adtionalModal" >

                    </div>

                    <div class="modal-footer">
                        <div class="row no-gutters w-100">
                            <div class="col-sm-5 pb-3 pb-sm-0">
                                <input type="number" value="1" min="1" max="30" step="1" id="quantityModal"/>
                            </div>
                            <div class="col-sm-7 pl-sm-2">
                                <button type="submit" class="btn btn-primary btn-block" data-attach-loading="" id="saveDish">
                                    ADD TO ORDER              
                                    <span class="pull-right" id="priceModal"> </span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN DEL MODAL ADICIONALES-->



        <!--Barra de navegación principal superior-->
        <nav class="navbar navbar-expand-sm bg-white  navbar-inverse">

            <div class="container">
                <div class="justify-content-start">
                    <a href="#">
                        <img src="/restaurante/images/logo.png" height="40"/>
                    </a>
                </div>
                <button class="navbar-toggler" data-toggle="collapse" data-target="#mainNav">
                    <span class="fas fa-align-justify"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="mainNav">
                    <div class="navbar-nav">
                        <a class="nav nav-link active "  href="#" > View Menu </a>
                        <a class="nav-item nav-link"  href="#"> Reservation</a>
                        <a class="nav-item nav-link"  href="#"> Login</a>
                        <a class="nav-item nav-link"  href="#"> Register</a>
                    </div>
                </div>
            </div>
        </nav>

        <!--Fin de la barra de navegación principal superior-->

        <div class="container ">
            <div class="row ">
                <!--inicio de la primera seccion de categorias-->

                <div class="col-sm-4 col-lg-2 ">
                    <nav class="nav justify-content-center nav-pills flex-column my-4" id="menuCategories">
                        <label id="lbCategories"> Categories</label>
                    </nav>
                </div>
                <!--final de la primera parte de categorias-->

                <!--inicio de la parte intermedia de pedidos-->

                <div class="col-sm-6 col-lg-6  mt-4 ">
                    <div class="container bg-white">
                        <div class="container bg-white  py-4 mb-1" >
                            <div class="dropdown show ">
                                <a class="btn btn-light dropdown-toggle btn-block" href="#" role="button" id="dropdownMenuFecha" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fas fa-clock"></i> &nbsp ASAP
                                </a>

                                <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                    <a href="#" class='dropdown-item' id="ASAP"> <i class="fas fa-clock"></i> &nbsp  ASAP</a>
                                    <div class="dropdown-divider"></div>
                                    <button class='dropdown-item ' id='calendar' type="button"> <i class="fas fa-calendar-alt"></i> &nbsp Schedule Order </button>
                                </div>
                            </div>
                        </div>

                    </div>


                    <div class="panel panel-local">
                        <div class="panel-body">
                            <div class="row boxes">
                                <div class="box-one col-sm-6">
                                    <dl class="no-spacing">
                                        <dd><h1 class="h3">TastyIgniter</h1></dd>
                                        <dd class="text-muted">
                                            <div class="rating rating-sm">
                                                <span class="fa fa-star"></span>
                                                <span class="fa fa-star"></span>
                                                <span class="fa fa-star"></span>
                                                <span class="fa fa-star-half-o"></span>
                                                <span class="fa fa-star-o"></span>
                                                <span class="small">()</span>
                                            </div>
                                        </dd>
                                        <dd>
                                            <span class="text-muted">345 Lewisham Road, London SE13 7AB, United Kingdom</span>
                                        </dd>
                                    </dl>                    </div>
                                <div class="box-divider d-block d-sm-none"></div>
                                <div class="box-two col-sm-6">
                                    <dl class="no-spacing">
                                        <dt><span class="text-success">We are open</span></dt>

                                        <dd>
                                            <span class="fa fa-clock"></span>&nbsp;&nbsp;
                                            <span>24 hours, 7 days.</span>
                                        </dd>

                                        <dd class="text-muted">
                                            Delivery and pick-up available            </dd>
                                        <dd class="text-muted">
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--El siguiente codigo es para presentar los platillos según la categoría.--> 

                    <div class="accordion" >
                        <div class="tab-content my-1" id="dishes">

                        </div>
                    </div>


                </div>
                <!--final de la parte inermedia de pedidos-->


                <!-- inicio De la tercera parte-->
                <div class="col-sm-4 col-lm-4 ">

                    <div id="cart-box" class="module-box py-3 mt-2">
                        <div class="panel panel-cart ">
                            <div class="cart-header justify-between-center">
                                <div class="btn-group " data-toggle='buttons'>
                                    <label class="btn btn-primary active  " >
                                        <input type="radio" name="type" id='delivery' autocomplete='off'  style="display: none;" checked/>
                                        <strong> Delivery <br/></strong> 
                                        <span  class="small center-block" >
                                            in 15 min  
                                        </span>
                                    </label>
                                    <label class="btn btn-primary ">
                                        <input type="radio" name="type" id='pickUp' autocomplete='off'  style="display: none;" />
                                        <strong> Pick Up <br/></strong>
                                        <span class="small center-block">
                                            Starts 06:00 am  
                                        </span>
                                    </label>
                                </div>
                            </div>
                            <div class="panel-body py-4 px-3 bg-white justify-content-center" style="overflow-y: auto;height: 250px;">
                                <div id="cart-control" class="">
                                    <ul id="dishSelectedList" >


                                    </ul>

                                </div>
                            </div>

                            <div  style="width: 80%; margin: 0 auto; border-top: 1px solid #999; " >
                                <span class="text-muted "  style="float: left; ">Total:</span>
                                <span  style="float: right; font-weight: bold;" id="cart-totals"> </span>
                            </div>

                            <div id="cart-buttons" class="mt-3">
                                <button class="checkout-btn btn btn-primary  btn-block btn-lg">
                                    Checkout
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <!--final-->
        </div>

    </div>

    <!--SCRIPTS--> 

    <!--JQUERY-->

    <script src="https://code.jquery.com/jquery-3.5.1.js"  crossorigin="anonymous"></script>


    <!--date picket src-->       
    <script src="/restaurante/js/bootstrap-datetimepicker.min.js"></script>

    <!--BOOTSTRAP-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"  crossorigin="anonymous"></script>       
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" crossorigin="anonymous"></script>

    <script src="/restaurante/js/calendar.js"  ></script>

    <!--Incrementar o decrementar platillo-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap-input-spinner@1.13.5/src/bootstrap-input-spinner.min.js"></script>        <script>
        $("input[type='number']").inputSpinner();
    </script>

    <!--iconos de font awesome-->
    <script src="https://kit.fontawesome.com/39f4ebbbea.js" crossorigin="anonymous"></script>
    <script>
        $(function () {
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>


    <!--Scripts del programa-->
    <script>
        /*window.addEventListener('beforeunload', function (e) {
         e.preventDefault();
         //var e = e.toString();
         e.returnValue = document.activeElement;
         
         });*/
    
//         VARIABLE GLOBAL DE PLATILLOS EN LA ORDEN
        var p_selected = [];
        
        function loaded(event) {
            fillCategories();
            events();
        }

        function fillCategories() {
            $(document).ready(function () {
                $.ajax({
                    type: "POST",
                    url: "api/restaurante/categorias/get",
                    success: function (categorias) {
                        showCategorias(categorias);
                    },
                    error: function (status) {
                        alert(errorMessage(status));
                    }
                });
            });
        }
        function showCategorias(categorias) {
            categorias.forEach((cat) => {
                fillListCategories(cat);
            });
        }
        function fillListCategories(categoria) {
            var nombre = categoria.nombre.replace(/ /g, "");
            var targetAccordion = nombre + "Target";
            var platilloId = nombre + "Platillo";
            $("#menuCategories").append('<a class="nav-link " href="#' + nombre + '" data-toggle="tab">' + categoria.nombre + '</a>');
            $("#dishes").append(
                    '<div class="tab-pane bg-white" id="' + nombre + '">' +
                    '<div class="card">' +
                    '<div class="card-header">' +
                    ' <h1 class="mb-0">' +
                    '<button class="btn btn-link btn-block text-left " type="button" data-toggle="collapse"' +
                    'data-target="#' + targetAccordion + '">' + categoria.nombre + ' </button>' +
                    ' </h1>' +
                    ' </div>' +
                    '<div id="' + targetAccordion + '" class="collapse show">' +
                    '<div class="card-body" id="' + platilloId + '">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>'
                    );
            var platillos = categoria.platilloCollection;
            platillos.forEach((platillo) => {
                fillDishes(platillo, platilloId);
            });
        }
        function fillDishes(platillo, platilloId) {
            var idEvtAditional = platillo.nombrePlatillo.replace(/ /g, "") + "Adicional";
            $("#" + platilloId).append(
                    '<div class="d-flex flex-row mr-3 mt-3 btn-block">' +
                    ' <div class=" flex-grow-1 " id="firstPart">' +
                    '<h6>' + platillo.nombrePlatillo + '</h6>' +
                    '<p class="text-muted mb-0" id="dishDescription">'
                    + platillo.descripcion + '</p>' +
                    ' </div>' +
                    '<div class=" align-self-start col-3 text-right p-0" id="secondPart">' +
                    '<span class=" pr-sm-3" id="price">' +
                    '<b>' + platillo.precio + '</b>' +
                    ' </span><span class="btnPlus">' +
                    '<button type="button" class="btn btn-light btn-sm btn-cart " data-toggle="modal"' +
                    'data-target="#modalOptions" id="' + idEvtAditional + '" >' +
                    '<i class="fa fa-plus"></i>' +
                    '</button>' +
                    '</span>' +
                    '</div>' +
                    '</div>'
                    );
            $("#" + idEvtAditional).click(function () {
                getAditional(platillo);
            });
        }

        function getAditional(platillo) {
            var Adicionales = platillo.adicionalCollection;
            $("#adtionalModal").html(" ");
            $("#nombrePlatilloModal").html(" ");
            $("#descPlatilloModal").html(" ");
            $("#priceModal").html(" ");
            $("#nombrePlatilloModal").append(platillo.nombrePlatillo);
            $("#descPlatilloModal").append(platillo.descripcion);
            $("#priceModal").append("$ " + platillo.precio);
            if (Adicionales.length !== 0) {
                Adicionales.forEach((adicional) => {
                    var requerida = "Requerida";
                    if (adicional.requerida === 0) {
                        requerida = "Opcional";
                    }
                    var adicionalSinEspacios = adicional.nombre.replace(/ /g, "");
                    $("#adtionalModal").append('<div class="container " id="' + adicionalSinEspacios + '">' +
                            '<div class="row bg-light py-2 my-2">' +
                            '<div class="col-9">' +
                            '<h5>' + adicional.nombre + '</h5>' +
                            ' </div>' +
                            '<div class="col-3 text-muted">' +
                            requerida +
                            '</div>' +
                            '</div>' +
                            '</div>');
                    fillOptions(adicional);
                });
            }


        }


        function fillOptions(adicional) {
            var opciones = adicional.opcionCollection;
            var adicionalSinEspacios = adicional.nombre.replace(/ /g, "");
            opciones.forEach((opcion) => {
                var htmlOption = " ";
                var opcionesSinEspacios = opcion.nombre.replace(/ /g, "");
                if (adicional.tipo === 0) {
                    htmlOption = '<div class="row justify-content-between my-1">' +
                            ' <div class="col-8">' +
                            ' <div class="custom-control custom-radio">' +
                            '<input type="radio" class="custom-control-input" id="' + opcionesSinEspacios + "OpId" + '" name="' + adicionalSinEspacios + "Name" + '" value="customEx">' +
                            '<label class="custom-control-label" for="' + opcionesSinEspacios + "OpId" + '" id="' + opcionesSinEspacios + "OpIdLabel" + '">' + opcion.nombre + '</label>' +
                            '</div>' +
                            '</div>' +
                            '<div class="col-3">' +
                            opcion.precio +
                            '</div>' +
                            '</div>';
                } else {
                    htmlOption = '<div class="row justify-content-between my-1">' +
                            '<div class="col-8">' +
                            '<div class="custom-control custom-checkbox">' +
                            '<input type="checkbox" class="custom-control-input" id="' + opcionesSinEspacios + "OpId" + '">' +
                            '<label class="custom-control-label" for="' + opcionesSinEspacios + "OpId" + '" id="' + opcionesSinEspacios + "OpIdLabel" + '">' + opcion.nombre + '</label>' +
                            ' </div>' +
                            '</div>' +
                            '<div class="col-3">' +
                            opcion.precio +
                            '</div>' +
                            '</div>';
                }
                $("#" + adicionalSinEspacios).append(htmlOption);
            });
        }

        function events() {
            //                Evento de fecha y ASAP
            $("#ASAP").click(function () {
                $("#dropdownMenuFecha").html(" ");
                $("#dropdownMenuFecha").append($("#ASAP").html());
            });
            $("#saveDate").click(function () {
                if ($("#dateText").val() !== "") {
                    $("#dropdownMenuFecha").html(" ");
                    $("#dropdownMenuFecha").append($("#dateText").val());
                }
            }
            );
            //                Evento de guardar el platillo en el carrito.

            keepDishInOrder(-1);
        }

        function keepDishInOrder( idPlatilloInDish) {
            console.log(idPlatilloInDish);
            $("#saveDish").click(function () {
                var Options = $("[id*=OpId]");
                var OpSelected = [];
                for (let i = 0; i < Options.length; i++) {
                    if (Options[i].checked) {
                        OpSelected.push($("#" + Options[i].id + "Label").text());
                    }
                }
                var nombre = $("#nombrePlatilloModal").text();
                var cantidad = $("#quantityModal").val();
                var OptionsSelected = JSON.stringify(OpSelected);
                var sendData = nombre + "\n" + cantidad + "\n" + OptionsSelected;
                $.ajax({
                    type: "POST",
                    url: "api/restaurante/AddToCart",
                    data: sendData,
                    success: function (orden) {
                        p_selected = orden.platilloseleccionadoCollection;
                        fillCart(p_selected);
                        $("#cart-totals").html(" ");
                        $("#cart-totals").append(orden.total);
                    },
                    error: function (status) {
                        alert(errorMessage(status));
                    }
                });
            });
        }

        function getOrder() {
            $.ajax({
                type: "POST",
                url: "api/restaurante/GetCartSession",
                data: sendData,
                success: function (orden) {
                    p_selected = orden.platilloseleccionadoCollection;
                    fillCart(p_selected);
                    $("#cart-totals").html(" ");
                    $("#cart-totals").append("$ "+orden.total);
                },
                error: function (status) {
                    alert(errorMessage(status));
                }
            });
        }

        function fillCart(platillos) {
            $("#dishSelectedList").html(" ");
            var cuenta = 0;

            platillos.forEach((platillo) => {
                var precio = 0;
                var platilloSinEspacios = platillo.nombrePlatillo.replace(/ /g, "");

                $("#dishSelectedList").append(
                        '<li>' +
                        '<button style="position: relative; top: -15px; " type="button" class="cart-btn btn btn-light btn-sm text-muted" id="' + platilloSinEspacios + 'Menos' + cuenta + '">' +
                        '<i class="fa fa-minus"></i>' +
                        '</button>' +
                        '<button style="width: 80%;background-color: white; border:none;" type="button" id="'+platilloSinEspacios+cuenta+'" class="btn btn-light btn-sm btn-cart " data-toggle="modal" data-target="#modalOptions">' +
                        '<span  style="float:left; font-size: 15px; font-weight: bold;"> ' + platillo.cantidad + 'x ' + platillo.nombrePlatillo + '</span> <span style="float:right;" id="' + platilloSinEspacios + 'PlatilloCartPrecio' + cuenta + '"></span>' +
                        '<br><br>' +
                        '<div id="' + platilloSinEspacios + 'PlatilloCart' + cuenta + '" class="text-muted" style="display: block;">' +
                        '</div>' +
                        '</button>' +
                        '</li>'
                        );

                var adicionales = platillo.adicionalCollection;
                adicionales.forEach((adi) => {
                    var adicionalSinEspacios = adi.nombre.replace(/ /g, "");

                    $('#' + platilloSinEspacios + 'PlatilloCart' + cuenta).append(
                            '<div id="' + adicionalSinEspacios + 'AdicionalCart' + cuenta + '" class="text-muted" style="display: block;">' +
                            '<span  style="float:left;">' + adi.nombre + '</span> <br>' +
                            '</div>'
                            );
                    adi.opcionCollection.forEach((op) => {
                        $('#' + adicionalSinEspacios + 'AdicionalCart' + cuenta).append('<span  style="float:left;">' + op.nombre + ' ' + op.precio + '</span><br>');
                        precio += op.precio;
                    });
                });
                precio += platillo.precio;
                precio = precio * platillo.cantidad;
                $("#" + platilloSinEspacios + 'PlatilloCartPrecio' + cuenta).append('$'+precio);

                console.log(platilloSinEspacios + 'Menos' + cuenta);
                $("#" + platilloSinEspacios + 'Menos' + cuenta).click(function () {
                    decreseDish(platillo);
                });
                $("#" + platilloSinEspacios+cuenta).click(function () {
                    getDishInOrder(cuenta);
                });
                cuenta++;

//        CIERRA EL FOR DE PLATILLOS
            });

            function decreseDish(platillo) {
                $.ajax({
                    type: "POST",
                    url: "api/restaurante/decreseQuant",
                    data: JSON.stringify(platillo),
                    contentType: "application/json",
                    success: function (orden) {
                        console.log(orden);
                        p_selected = orden.platilloseleccionadoCollection;
                        fillCart(p_selected);
                        $("#cart-totals").html(" ");
                        $("#cart-totals").append(orden.total);
                    },
                    error: function (status) {
                        alert(errorMessage(status));
                    }
                });
            }

            function getDishInOrder(posDishInCart) {
                
                
                $.ajax({
                    type: "POST",
                    url: "api/restaurante/getDishInCart",
                    data: posDishInCart,
                    contentType: "application/json",
                    success: function (orden) {
                        console.log(orden);
                        p_selected = orden.platilloseleccionadoCollection;
                        fillCart(p_selected);
                        $("#cart-totals").html(" ");
                        $("#cart-totals").append(orden.total);
                    },
                    error: function (status) {
                        alert(errorMessage(status));
                    }
                });
            }

        }
        function errorMessage(status) {
            return "Ha ocurrido un error";
        }

        document.addEventListener("DOMContentLoaded", loaded);

    </script>
</body>
</html>

