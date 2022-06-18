const name = document.getElementById('reg-villageName');
const num = document.getElementById('reg-villageNum');
const street = document.getElementById('reg-street-adr');
const searchBtn = document.querySelector('#vill-search');
const villCheck = document.getElementById("vill-search");
const villRoadSearch = document.getElementById("vill-road-search");

villRoadSearch.addEventListener('click',villageRoad);

villCheck.addEventListener('click',function() {
    window.open('villageCheck','villSearch','width=1200px, height=500px, resizable=no location=no');
})

name.onblur = function () {
    if (name.value.trim()!=="") {
        name.classList.add('_success');
    } else {
        name.classList.remove('_success');
    }
    searchCheck();
};

num.onblur = function () {
    if (num.value.trim()!=="") {
        num.classList.add("_success");
    } else {
        num.classList.remove('_success');
    }
    searchCheck();

};

street.onblur = function () {
    if (street.value.trim()!=="") {
        street.classList.add("_success");
    } else {
        street.classList.remove('_success');
    }
    searchCheck();
};

function searchCheck(){
    const result = (name.classList.contains("_success")&&num.classList.contains("_success")&&street.classList.contains("_success"))
    searchBtn.disabled = !result;
}


