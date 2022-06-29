$(document).ready(function(){
    $('.modal').modal();
});

$('#color-picker').spectrum({
    type: "component"
});


$(document).ready(function(){

  var layout;

  // Fragment Add and Delete
  $(document).off("click").on("click", ".fragment-add, .fragment-delete, .fragment-edit, .layout-change, .saveSettings, .site-add", function() {

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
              "type": "text"
            }));
            break;

          case "saveImage":
            formData.append('data', JSON.stringify({
              "title": $(name + " > .modal-content div:nth-child(2) > div > input").val(),
              "position": pos,
              "type": "image"
            }));
            formData.append('image', $(name + " > .modal-content div:nth-child(3) > div > div .btn > input")[0].files[0]);
            break;
          
          case "saveContact":
            formData.append('data', JSON.stringify({
              "position": pos,
              "type": "kontakt"
            }));
            break;
          case "saveZeiten":
            formData.append('data', JSON.stringify({
              "position": pos,
              "type": "zeiten"
            }));
            break;
          case "saveKarte":
            formData.append('data', JSON.stringify({
              "position": pos,
              "type": "karte"
            }));
            break;
          case "saveMap":
            formData.append('data', JSON.stringify({
              "position": pos,
              "type": "map"
            }));
            break;
        }

        $.ajax({
          type: "POST",
          url: "baukasten/module/save",
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

      // Delete -------------------------------------------------------------------------------------------

      $('#selector').modal('close');
      $('#deleteFragment').modal('open');

      var namepos = $(this);

      $('#deleteFragmentConfirm').off("click").click(function() {
        var pos = $(namepos).attr('name').split("-")[0];
        var type = $(namepos).attr('name').split("-")[1];
        var formData = new FormData();
    
        formData.append('data', JSON.stringify({
          "position": pos,
          "type": type
        }));
  
  
    
        $.ajax({
          type: "POST",
          url: "baukasten/module/delete",
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
    } else if ($(this).attr("class").indexOf("layout-change") >= 0) {

      // Change Layout Var ----------------------------------------------------------------------------------------

      $('#changeLayout').modal('open');

      $('.setLayout').off("click").click(function() {
        $('#changeLayout').modal('close');
        $('#warningLayout').modal('open');

        var tmp = $(this).attr("name");

        $('#saveLayout').off("click").click(function() {
          layout = tmp;
        })
      })

    } else if ($(this).attr("class").indexOf("saveSettings") >= 0) {

      // Edit Settings -------------------------------------------------------------------------------------------

      var formData = new FormData();

      console.log(layout);

      formData.append('data', JSON.stringify({
        "restaurantName": $("#firmenname").val(),
        "color": ($("#color").val() || ""),
        "layout": (layout || "")
      }));

      $.ajax({
        type: "POST",
        url: "baukasten/update",
        async: false,
        processData: false,
        contentType: false,
        data: formData,
        success: function ( data ) {
          setTimeout(
            function () {
              location.reload();
            }, 1000
          )
        }
      });

    } else if ($(this).attr("class").indexOf("site-add") >= 0) {
      // ADD Site

      $('#addPage').modal('open');

      $(".AddSite").off("click").click(function() {
        $('#addPage').modal('close');
        name = '#' + $(this).attr('name')
        $('#' + $(this).attr('name')).modal('open');
      });
    } else {

      // Edit Header -------------------------------------------------------------------------------------------

      $('#selector').modal('close');
      $('#editHeader').modal('open');

      $('#saveHeader').off("click").click(function() {

        var formData = new FormData();
        formData.append('data', JSON.stringify({
          "title": $('#editHeaderTitle').val(),
          "text": $('#editHeaderText').val(),
          "position": "r1c1",
          "type": $(".fragment-header").attr("name")
        }));
  
        if ($('#headerImageNew').is(":checked")) {
          formData.append('image', $("#editHeaderImage")[0].files[0]);
        }
        
        $.ajax({
          type: "POST",
          url: "baukasten/module/save",
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

