function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

const villID = getParameterByName('vid');

const title = document.getElementById('title');
const name = document.getElementById('name');
const info = document.querySelector('.info-text');
const phone = document.querySelector('.phone');


(async ()=>{
    const res = await fetch(`/api/village/item/${villID}`)
    const data = await res.json();

    if(res.ok){
        console.log(data);
        title.innerText = data.villageName;
        name.innerText = data.villageRepName;
        phone.innerText = "대표번호: " + data.villageNum;
        info.innerText = "주소 : " + data.villageStreetAdr;
    }
})();
