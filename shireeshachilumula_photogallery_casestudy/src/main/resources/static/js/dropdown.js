setInterval(() => {
   var dt = new Date();
   document.getElementById("datetime").innerHTML = dt.toLocaleTimeString();
}, 100);

$(function() {
    $('#toggle-sharing').change(function() {
        $('#settings').submit();
    })
})
