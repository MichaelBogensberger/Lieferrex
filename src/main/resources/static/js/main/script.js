
  M.AutoInit();


function toggleSwitch() {
    var switchDmChecked = document.getElementById("switch-dm-js").checked;

    if(switchDmChecked){
      //console.log("Switched on");
      //alert("switch on");
      Cookies.set('dark-mode', 'on');
      location.reload();
      //Todark();

    } else {
      //console.log("Switch off");
        //Tolight();
        //alert("switch off");
        Cookies.set('dark-mode', 'off');
        location.reload();

    }

  } 

  
















