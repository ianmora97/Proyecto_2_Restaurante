

$("#calendar").on("click", function () {
    $("#modal-date").modal();
});

var min = new Date();
var max = new Date();
 max.setDate(min.getDate() + 5);

$(document).ready(function () {
    $('#datetimepicker1').datetimepicker({
        format: 'dd-mm-yyyy hh:ii',
        maxViewMode: 0,
        clearBtn: true,
        todayHighlight: true,
        autoclose: true,
        minDate: 0,
        maxDate: max
    });
});
