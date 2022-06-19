const id = document.getElementById('userId');
const name = document.getElementById('userName');
const phone = document.getElementById('userPhone');
const email = document.getElementById('userEmail');
const villname = document.getElementById('villname');
const villStreet = document.getElementById('villStreet');
const villRepName = document.getElementById('villRepName');
const villUrl = document.getElementById('villUrl');
const villProvider = document.getElementById('villProvider');
const villbank = document.getElementById('vill-bankname');
const villbanknum = document.getElementById('vill-bankNum');
const villDesc = document.getElementById('vill-desc');
const villnotify = document.getElementById('vill-notify');
const villAct  = document.getElementById('vill-activity');

const Thumb = document.getElementById('thumb-preview');

(async function() {
    try {
        const res = await fetch('/api/user')
        const data = await res.json();

        console.log(data);
        if(res.status === 200){
            try{
                const res = await fetch('/api/manager')
                const data = await res.json();

                Thumb.innerHTML = `
                <div class="current-img">
                <img class="img-item" src=${data.villagePhoto!==null?data.villagePhoto:"https://via.placeholder.com/335x335/?text=No+Image"} alt="#"/>
                </div>
                `
                villname.value = data.villageName;
                villStreet.value = data.villageStreetAdr
                villRepName.value = data.villageRepName;
                villUrl.value = data.villageUrl;
                villProvider.value = data.villageProviderCode;
                villbank.value = data.villageBankName;
                villbanknum.value = data.villageBankNum;
                villDesc.value = data.villageDescription;
                villnotify.value = data.villageNotify;
                villAct.value = data.villageActivity;

            }catch (e) {

            }

        }
    }
    catch (e) {
    }
})();