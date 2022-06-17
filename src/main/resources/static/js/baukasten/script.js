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
  $(".fragment-add").off("click").click(function() {

    // Current Position of the added Fragment
    var pos = $(this).attr('name');

    $('#selector').modal('open');

    // Open specific modal for fragment 
    $(".AddFragment").off("click").click(function() {
      $('#selector').modal('close');
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
            "title": $('#addImageTitle').val(),
            "position": pos,
            "type": "image",
            "token": getCookie("token")
          }));
          formData.append('image', $('#addImageFile')[0].files[0]);
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

      // Post
      
      $.ajax({
        type: "POST",
        url: "./module/save",
        async: false,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        data: formData,
        success: function ( data ) {
          $('#' + pos).html(data);
        }
      });
      
      
    })
  });
});

