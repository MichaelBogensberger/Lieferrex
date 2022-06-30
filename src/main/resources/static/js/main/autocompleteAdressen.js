let timer; // Timer identifier
const waitTime = 500; // Wait time in milliseconds
const api_url = "../seach/adresse/";

var data;

// Search function
// Defining async function
async function search(adresse) {

    // Storing response
    const response = await fetch(api_url + adresse);

    // Storing data in form of JSON
    data = await response.json();

    if (await data.length != 0){
        show(data);
    }else {
        clear();
    }
}


// Listen for `keyup` event
const input = document.querySelector('#adresse');
input.addEventListener('keyup', (e) => {
    const text = e.currentTarget.value;

    // Clear timer
    clearTimeout(timer);
    // Wait for X ms and then process the request
    timer = setTimeout(() => {
        search(text);
    }, waitTime);
});

$(document).ready(function () {

    const text = $(input).val();

    // Clear timer
    clearTimeout(timer);
    // Wait for X ms and then process the request
    timer = setTimeout(() => {
        search(text);
    }, waitTime);

});


async function show(data) {
    let tab = '<br>';
    // Loop to access all rows

    await data.forEach((r, index) => {
        tab += `
                    <button id="fill" class="btn-inline-search fade-in" type="button" onclick='fillAdresse("${index}")'> ${r.adresse} </button>
                `;
    })
    fillHidden(0);
    document.getElementById("adressen").innerHTML = tab;
}

async function clear() {
    document.getElementById("adressen").innerHTML = null;
    document.getElementById("ort").value = null;
    document.getElementById("hausnummer").value = null;
    document.getElementById("strasse").value = null;
    document.getElementById("land").value = null;
    document.getElementById("placeId").value = null;
}


function fillAdresse(r) {
    var adresse = data[r].adresse;
    var ort = data[r].ort;
    var hausnummer = data[r].hausnummer;
    var strasse = data[r].strasse;
    var land = data[r].land;
    var placeId = data[r].placeId;


    document.getElementById("adresse").value = adresse;
    document.getElementById("ort").value = ort;
    document.getElementById("hausnummer").value = hausnummer;
    document.getElementById("strasse").value = strasse;
    document.getElementById("land").value = land;
    document.getElementById("placeId").value = placeId;

}

function fillHidden(r) {
    var ort = data[r].ort;
    var hausnummer = data[r].hausnummer;
    var strasse = data[r].strasse;
    var land = data[r].land;
    var placeId = data[r].placeId;


    document.getElementById("ort").value = ort;
    document.getElementById("hausnummer").value = hausnummer;
    document.getElementById("strasse").value = strasse;
    document.getElementById("land").value = land;
    document.getElementById("placeId").value = placeId;

}
