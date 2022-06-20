M.AutoInit();

$(document).ready(function(){
    $('.modal').modal();
});

$('#color-picker').spectrum({
    type: "component"
});

// Helper function to get the token of logged in user
function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for(let i = 0; i <ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}


$(document).ready(function(){

  // Fragment Add and Delete
  $(document).off("click").on("click", ".fragment-add, .fragment-delete, .fragment-edit", function() {

    // Check if Add or Delete
    if($(this).attr("class").indexOf("fragment-add") >= 0){

      // ADD ---------------------------------------------------------------------------
      // Current Position of the added Fragment
      var pos = $(this).attr('name');
      var name;
      $('#selector').modal('open');

      // Open specific modal for fragment 
      $(".AddFragment").off("click").click(function() {
        $('#selector').modal('close');
        name = '#' + $(this).attr('name')
        $('#' + $(this).attr('name')).modal('open');
      });

      
      // On button save
      $('.saveFragment').off("click").click(function() {
        
        var formData = new FormData();
        // Set data depending on fragment type
        switch ($(this).attr('id')) {
          case "saveText":
            formData.append('data', JSON.stringify({
              "title": $('#addTextTitle').val(),
              "text": $('#addTextText').val(),
              "position": pos,
              "type": "text",
              "token": getCookie("token")
            }));
            break;

          case "saveImage":
            formData.append('data', JSON.stringify({
              "title": $(name + " > .modal-content div:nth-child(2) > div > input").val(),
              "position": pos,
              "type": "image",
              "token": getCookie("token")
            }));
            formData.append('image', $(name + " > .modal-content div:nth-child(3) > div > div .btn > input")[0].files[0]);
            break;
          
          case "saveContact":
            formData.append('data', JSON.stringify({
              "title": $('#addConctactTitle').val(),
              "text": $('#addContactText').val(),
              "position": pos,
              "type": "contact",
              "token": getCookie("token")
            }));
            break;
        }

        $.ajax({
          type: "POST",
          url: "./module/save",
          async: false,
          enctype: 'multipart/form-data',
          processData: false,
          contentType: false,
          data: formData,
          success: function ( data ) {
            setTimeout(
              function () {
                $('#' + pos).html(data);
              }, 1000
            )
          }
        });
        
        
      });

    } else if ($(this).attr("class").indexOf("fragment-delete") >= 0) {

      // Delete ---------------------------------------------------------------------------

      $('#selector').modal('close');
      $('#deleteFragment').modal('open');

      var namepos = $(this);

      $('#deleteFragmentConfirm').off("click").click(function() {
        var pos = $(namepos).attr('name').split("-")[0];
        var type = $(namepos).attr('name').split("-")[1];
        var formData = new FormData();
    
        formData.append('data', JSON.stringify({
          "position": pos,
          "type": type,
          "token": getCookie("token")
        }));
  
  
    
        $.ajax({
          type: "POST",
          url: "./module/delete",
          async: false,
          processData: false,
          contentType: false,
          data: formData,
          success: function ( data ) {
            setTimeout(
              function () {
                $('#' + pos).html(data);
              }, 500
            )
          }
        });
      });
    } else {
      $('#selector').modal('close');
      $('#editHeader').modal('open');

      $('#saveHeader').off("click").click(function() {

        var formData = new FormData();
        formData.append('data', JSON.stringify({
          "title": $('#editHeaderTitle').val(),
          "text": $('#editHeaderText').val(),
          "position": "r1c1",
          "type": "header",
          "token": getCookie("token")
        }));
  
        if ($('#headerImageNew').is(":checked")) {
          formData.append('image', $("#editHeaderImage")[0].files[0]);
        }
        
        $.ajax({
          type: "POST",
          url: "./module/save",
          async: false,
          enctype: 'multipart/form-data',
          processData: false,
          contentType: false,
          data: formData,
          success: function ( data ) {
            setTimeout(
              function () {
                $('#r1c1').html(data);
              }, 1000
            )
          }
        });
      });


    }
  });

  $("#headerImageNew").click(function(){
    if ($(this).is(":checked")) {
      $('#newImage').show();
    } else {
      $('#newImage').hide();
    }
  })

});

