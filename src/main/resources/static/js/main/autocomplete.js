let timer; // Timer identifier
const waitTime = 500; // Wait time in milliseconds
const api_url = "http://localhost:8080/seach/restaurant/";

// Search function
// Defining async function
async function search(adresse) {

    // Storing response
    const response = await fetch(api_url + adresse);

    // Storing data in form of JSON
    var data = await response.json();
    console.log(await data);

    show(data);
}



// Listen for `keyup` event
const input = document.querySelector('#search');
input.addEventListener('keyup', (e) => {
    const text = e.currentTarget.value;

    // Clear timer
    clearTimeout(timer);

    // Wait for X ms and then process the request
    timer = setTimeout(() => {
        search(text);
    }, waitTime);
});




function show(data) {
    let tab = '<br>';
    // Loop to access all rows
    for (let r of data) {
        tab += `<form action="/seach" method="get">
                    <button class="btn-inline-search fade-in" name="search" value="${r}" type="submit"> ${r} </button>
                </form>
                `;
    }
    document.getElementById("adressen").innerHTML = tab;

}
