/* 
 * UNIVERSIDAD NACIONAL DE COSTA RICA
 * Proyecto de programaci�n 4 
 * Integrantes:
 * David Aguilar Rojas
 * Ian Mora Rodriguez
 * I Ciclo lectivo 2020 
 */

function loaded(event) {
    getStatNumbers();
}
function cuadro() {
    var datos = [];
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/ordenAdmin/getOrdenesChart",
            contentType: "application/json"
        }).then((totales) => {
            for (var i = 0; i < totales.length; i++) {
                var obj = {nombre:"Venta", total:totales[i].total};
                datos[datos.length] = totales[i].total;
            }
        }, (error) => {
            alert(errorMessage(error.status));
        });

        var ctx = document.getElementById('charts');
        var myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Red'],
                datasets: [{
                        label: '# of Votes',
                        data: datos,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(255, 159, 64, 0.2)'
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(75, 192, 192, 1)',
                            'rgba(153, 102, 255, 1)',
                            'rgba(255, 159, 64, 1)'
                        ],
                        borderWidth: 1
                    }]
            },
            options: {
                scales: {
                    yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                }
            }
        });
    });
}
function getStatNumbers() {
    $(document).ready(function () {
        $.ajax({
            type: "GET",
            url: "api/dashboardAdmin",
            contentType: "application/json"
        }).then((trio) => {
            console.log(trio);
            $("#totalClientes").text(trio.nop);
            $("#totalEmpleados").text(trio.p);
            $("#totalVentas").text(trio.total);
            cuadro();
        }, (error) => {
            alert(errorMessage(error.status));
        });
    });
}
function errorMessage(status) {
    return "Ha ocurrido un error";
}
document.addEventListener("DOMContentLoaded", loaded);
