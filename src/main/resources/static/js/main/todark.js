


// Check to see if Media-Queries are supported
if (window.matchMedia) {
    // Check if the dark-mode Media-Query matches
    if(window.matchMedia('(prefers-color-scheme: dark)').matches){

  
      var stylesheet = $("<link>", {
          rel: "stylesheet",
          type: "text/css",
          href: "/css/main/dark.css"
      });
      stylesheet.appendTo("body");


    $().ready(function(){ $("body").css({display:'block'})});

      //Todark();
      //document.getElementById("switch-dm-js").checked = 1;
  
    } else {
  
      var stylesheet = $("<link>", {
        rel: "stylesheet",
        type: "text/css",
        href: "/css/main/light.css"
    });
    stylesheet.appendTo("body");
      //Tolight();
      //document.getElementById("switch-dm-js").checked = 0;

      $().ready(function(){ $("body").css({display:'block'})});


    }
  } else {
    // Default (when Media-Queries are not supported)
    alert("Fehler");
    $().ready(function(){ $("body").css({display:'block'})});
  }



