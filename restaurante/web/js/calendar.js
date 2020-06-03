

$("#calendar").on("click", function () {
    $("#modal-date").modal();
});

$(document).ready(function () {
    $('#datetimepicker1').datetimepicker({
        format: 'dd-mm-yyyy hh:ii',
        maxViewMode: 0,
        clearBtn: true,
        todayHighlight: true,
        autoclose:true
    });
});


$("#datetime").datetimepicker({
    format: 'yyyy-mm-dd hh:ii',
    autoclose: true
});