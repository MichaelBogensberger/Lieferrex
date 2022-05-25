M.AutoInit();

$(document).ready(function(){
    $('.modal').modal();
});

$('#color-picker').spectrum({
    type: "component"
});

$(document).ready(function() {
  $.get( "/baukasten/modul/r2c1", function( data ) {
  $( ".result" ).html( data );
  alert( "Load was performed." );
  })
});

