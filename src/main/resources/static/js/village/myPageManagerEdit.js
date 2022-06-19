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

const content_Thumimg = document.getElementById('thumb-preview');
const content_img = document.getElementById('current-image-preview');

//썸네일 관련 태그들
const thumImg = document.createElement('img');
const thumDivImg = document.createElement('div');
const thumDeleteButton = document.createElement('button');

const delThumbFile = {
    deletedThumFile  : []
};

(async function() {
    try {
        const res = await fetch('/api/user')
        const data = await res.json();

        console.log(data);
        if(res.status === 200){
            try{
                const res = await fetch('/api/manager')
                const data = await res.json();

                //썸네일
                thumDivImg.classList.add(`current-img`);
                thumDivImg.id= `${data.villagePhoto.substr(21)}`
                thumDeleteButton.classList.add('img-btn-delete','btn-danger');
                thumDeleteButton.innerText = 'X';
                thumImg.classList.add(`img-item`);
                thumImg.src= data.villagePhoto;

                thumDeleteButton.addEventListener('click',(e)=>{
                    console.log(e);
                    delThumbFile.deletedThumFile.push({"value":e.target.offsetParent.id});
                    e.target.parentElement.remove();
                })

                thumDivImg.appendChild(thumImg);
                thumDivImg.appendChild(thumDeleteButton);
                content_Thumimg.appendChild(thumDivImg);

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