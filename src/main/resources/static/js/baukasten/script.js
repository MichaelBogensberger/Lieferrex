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

    // Saving Text Fragment
    $("#openAddText").click(function() {
      $('#selector').modal('close');
      $('#addText').modal('open');
      var type = "text";
      $('#saveAddText').click(function() {
        $.ajax({
          type: "POST",
          url: "./module/save",
          data: {'data': JSON.stringify({
              "title": $('#addTextTitle').val(),
              "text": $('#addTextText').val(),
              "position": pos,
              "type": type 
            })},
          success: function(){
            alert("success")
          }
        });
      })
    });

    // Saving 
    $("#openAddImage").click(function() {
      $('#selector').modal('close');
      $('#addImage').modal('open');
      var type = "image";
      $('#saveAddImage').click(function() {
        $.ajax({
          type: "POST",
          url: "./module/save",
          data: {'data': JSON.stringify({
              "title": $('#addImageTitle').val(),
              "image": $('#addImageFile').val(),
              "position": pos,
              "type": type 
            })},
          success: function(){
            alert("success")
          }
        });
      })
    })

  });
});

