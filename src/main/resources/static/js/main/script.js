
  M.AutoInit();
  

function toggleSwitch() {
    var switchDmChecked = document.getElementById("switch-dm-js").checked;

    if(switchDmChecked){
      //console.log("Switched on");
      //Todark();

    } else {
      //console.log("Switch off");
        //Tolight();

    }

  } 






// Check to see if Media-Queries are supported
if (window.matchMedia) {
  // Check if the dark-mode Media-Query matches
  if(window.matchMedia('(prefers-color-scheme: dark)').matches){

    Todark();
    document.getElementById("switch-dm-js").checked = 1;

  } else {

    Tolight();
    document.getElementById("switch-dm-js").checked = 0;

  }
} else {
  // Default (when Media-Queries are not supported)
    alert("Fehler. Media Queruies in JS not supported");
}




function Todark() {
    $('body').addClass('dark');
    $('.main_wrapper').addClass('dark');
    $(':root').addClass('dark');
    $('.btn-dg').addClass('dark');
    $('.ct-ueb').addClass('dark');
    $('.ct-card-p').addClass('dark');
    $('.page-footer').addClass('dark');
    $('.main-content').addClass('dark');
    $('.ct-card-icon').addClass('dark');
    $('.ct-last-icon').addClass('dark');

    $('.side-line-menu').addClass('dark');
    $('.nav-mob').addClass('dark');
    $('.side-line-menu:before').addClass('dark');
    $('.side-line-menu:after').addClass('dark');
    $('.nav-mob-a').addClass('dark');
    $('.nav-mob-ico').addClass('dark');

    $('.main-search').addClass('dark');



    $('.sr-main-content').addClass('dark');
    $('.sr-restaurants').addClass('dark');
    $('.sr-rest-ueb').addClass('dark');
    $('.sr-rest-p').addClass('dark');
    $('.sr-rest-enter-ico').addClass('dark');
    $('.sr-mob-add-btn').addClass('dark');

    $('.modal').addClass('dark');
    $('.modal .modal-footer').addClass('dark');
    $('.modal-close').addClass('dark');
    $('.coll-profile').addClass('dark');
    $('.secondary-content').addClass('dark');

    $('.coll-profile .collection-item').addClass('dark');
    $('.aendern-modal-ueb').addClass('dark');
    $('.inp-aendern-modal').addClass('dark');

    $('.sr-filter').addClass('dark');

    $('.sr-filter-ueb').addClass('dark');
    $('.sr-filter-u-ueb').addClass('dark');
    $('.sr-chip').addClass('dark');
    $('.sr-chip-active').addClass('dark');

    $('.collapsible-body').addClass('dark');
    $('.sr-filter-mob-submit').addClass('dark');
    $('.sr-filter-auswählen-p label *').addClass('dark');

    $('.login-form-ueb').addClass('dark');
    $('.btn-login').addClass('dark');
    $('.login-div-pw .helper-text').addClass('dark');
    $('.login-inp').addClass('dark');

    $('.login-body').addClass('dark');

    $('.step-title').addClass('dark');
    $('.white-dm').addClass('dark');


    $('ul.stepper.horizontal').addClass('dark');
    $('ul.stepper .step:not(:last-of-type)').addClass('dark');

}

function Tolight() {
    $('body').removeClass('dark');
    $('.main_wrapper').removeClass('dark');
    $(':root').removeClass('dark');
    $('.btn-dg').removeClass('dark');
    $('.ct-ueb').removeClass('dark');
    $('.ct-card-p').removeClass('dark');
    $('.page-footer').removeClass('dark');
    $('.main-content').removeClass('dark');
    $('.ct-card-icon').removeClass('dark');
    $('.ct-last-icon').removeClass('dark');

    $('.side-line-menu').removeClass('dark');
    $('.nav-mob').removeClass('dark');
    $('.side-line-menu:before').removeClass('dark');
    $('.side-line-menu:after').removeClass('dark');
    $('.nav-mob-a').removeClass('dark');
    $('.nav-mob-ico').removeClass('dark');

    $('.main-search').removeClass('dark');

    $('.sr-main-content').removeClass('dark');

    $('.sr-main-content').removeClass('dark');
    $('.sr-restaurants').removeClass('dark');
    $('.sr-rest-ueb').removeClass('dark');
    $('.sr-rest-p').removeClass('dark');
    $('.sr-rest-enter-ico').removeClass('dark');
    $('.sr-mob-add-btn').removeClass('dark');


    $('.modal').removeClass('dark');
    $('.modal .modal-footer').removeClass('dark');
    $('.modal-close').removeClass('dark');
    $('.coll-profile').removeClass('dark');
    $('.secondary-content').removeClass('dark');
    $('.coll-profile .collection-item').removeClass('dark');
    $('.aendern-modal-ueb').removeClass('dark');
    $('.inp-aendern-modal').removeClass('dark');
    $('.sr-filter').removeClass('dark');


    $('.sr-filter-ueb').removeClass('dark');
    $('.sr-filter-u-ueb').removeClass('dark');
    $('.sr-chip').removeClass('dark');
    $('.sr-chip-active').removeClass('dark');

    $('.collapsible-body').removeClass('dark');
    $('.sr-filter-mob-submit').removeClass('dark');
    $('.sr-filter-auswählen-p label *').removeClass('dark');

    $('.login-form-ueb').removeClass('dark');
    $('.btn-login').removeClass('dark');

    $('.login-div-pw .helper-text').removeClass('dark');
    $('.login-inp').removeClass('dark');

    $('.login-body').removeClass('dark');

    $('.step-title').removeClass('dark');
    $('.white-dm').removeClass('dark')


    $('ul.stepper.horizontal').removeClass('dark');
    $('ul.stepper .step:not(:last-of-type)').removeClass('dark');

}













