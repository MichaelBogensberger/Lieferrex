M.AutoInit();

$(document).ready(function(){
    $('.modal').modal();
});

$('#color-picker').spectrum({
    type: "component"
});

// $(document).ready(function() {
//   $.get( "/baukasten/modul/r2c2", function( data ) {
//   $( ".result" ).html( data );
//   alert( "Load was performed." );
//   })
// });

// $('#addTextForm')
    // .ajaxForm({
        // url : 'local', // or whatever
        // dataType : 'json',
        // success : function (response) {
            // alert("The server says: " + response);
        // }
    // });

$( "#addTextForm" ).submit(function( event ) {
  alert( "Handler for .submit() called." );
  event.preventDefault();
});

$(document).ready(function(){
  $(".fragment-add").click(function() {
    var pos = $(this).attr('name');
    $('#selector').modal('open');

    $("#openAddText").click(function() {
      $('#addText').modal('open');
      var type = "text";
      $('#saveAddText').click(function() {

        var data = {
          "title": $('#addTextTitle').val(),
          "text": $('#addTextText').val(),
          "position": pos,
          "type": type 
        }

        var json = JSON.stringify(data);
        alert(data.length);
        alert(json);

        alert(json);
        $.ajax({
          type: "POST",
          url: "http://localhost:8080/baukasten/module/save",
          data: {'data': json},
          success: function(){
            alert("success")
          }
        });
      })
    })

  });
});

