M.AutoInit();

$(document).ready(function(){
    $('.modal').modal();
});

$('#color-picker').spectrum({
    type: "component"
});

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
    var pos = $(this).attr('name');
    $('#selector').modal('open');

    // Saving Text Fragment
    $("#openAddText").off("click").click(function() {
      $('#selector').modal('close');
      $('#addText').modal('open');
    });

    $('#saveAddText').off("click").click(function() {
      console.log("dawdwad");
      $.ajax({
        type: "POST",
        url: "./module/save",
        data: {'data': JSON.stringify({
            "title": $('#addTextTitle').val(),
            "text": $('#addTextText').val(),
            "position": pos,
            "type": "text",
            "token": getCookie("token")
          })}
      });
    })

    // Saving Image Fragment
    $("#openAddImage").off("click").click(function() {
      $('#selector').modal('close');
      $('#addImage').modal('open');
    });

    $('#saveAddImage').off("click").click(function() {
      $.ajax({
        type: "POST",
        url: "./module/save",
        data: {'data': JSON.stringify({
            "title": $('#addImageTitle').val(),
            "image": $('#addImageFile').val(),
            "position": pos,
            "type": "image" 
          })}
      });
    })

    // Saving Contact Informations Fragment
    $("#openAddContact").off("click").click(function() {
      $('#selector').modal('close');
      $('#addContact').modal('open');
    });

    $('#saveAddContact').off("click").click(function() {
      $.ajax({
        type: "POST",
        url: "./module/save",
        data: {'data': JSON.stringify({
            "title": $('#addConctactTitle').val(),
            "text": $('#addContactText').val(),
            "position": pos,
            "type": "contact" 
          })}
      });
    })
 
  });
});

