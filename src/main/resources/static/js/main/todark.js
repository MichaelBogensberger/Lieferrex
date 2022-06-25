if (Cookies.get("dark-mode") == null) {
  toDark();
  $("#switch-dm-js").attr("Checked", "Checked");
} else if (Cookies.get("dark-mode") == "on") {
  $("#switch-dm-js").attr("Checked", "Checked");
  toDark();
} else if (Cookies.get("dark-mode") == "off") {
  $("#switch-dm-js").removeAttr("Checked");
  toLight();
}

function toDark() {
  var stylesheet = $("<link>", {
    rel: "stylesheet",
    type: "text/css",
    href: "/css/main/dark.css",
  });
  stylesheet.appendTo("body");
  $().ready(function () {
    $("body").css({ display: "block" });
  });
}

function toLight() {
  var stylesheet = $("<link>", {
    rel: "stylesheet",
    type: "text/css",
    href: "/css/main/light.css",
  });
  stylesheet.appendTo("body");
  $().ready(function () {
    $("body").css({ display: "block" });
  });
}

/*
// Check to see if Media-Queries are supported
if (window.matchMedia) {
  // Check if the dark-mode Media-Query matches
  if (window.matchMedia("(prefers-color-scheme: dark)").matches) {
    var stylesheet = $("<link>", {
      rel: "stylesheet",
      type: "text/css",
      href: "/css/main/dark.css",
    });
    stylesheet.appendTo("body");

    $().ready(function () {
      $("body").css({ display: "block" });
    });

    //Todark();
    //document.getElementById("switch-dm-js").checked = 1;
  } else {
    var stylesheet = $("<link>", {
      rel: "stylesheet",
      type: "text/css",
      href: "/css/main/light.css",
    });
    stylesheet.appendTo("body");
    //Tolight();
    //document.getElementById("switch-dm-js").checked = 0;

    $().ready(function () {
      $("body").css({ display: "block" });
    });
  }
} else {
  // Default (when Media-Queries are not supported)
  alert("Fehler");
  $().ready(function () {
    $("body").css({ display: "block" });
  });
}
*/
