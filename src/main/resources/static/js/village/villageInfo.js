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
const selectAct = document.getElementById('act-select');
const mainImg = document.getElementById('vill-main-img');
const villDesc = document.getElementById('desc');
const villNoti = document.getElementById('notify');
const actText = document.getElementById('testtt');


(async ()=>{
    const res = await fetch(`/api/village/item/${villID}`)
    const data = await res.json();

    if(res.ok){
        console.log(data);
        title.innerText = data.villageName;
        name.innerText = data.villageRepName;
        phone.innerText = "대표번호: " + data.villageNum;
        info.innerText = "주소: " + data.villageStreetAdr;
        actText.innerText = "활동목록: "+ data.villageActivity;
        mainImg.innerHTML+=
            `
                <img src=${data.villagePhoto!==null? data.villagePhoto:"https://via.placeholder.com/1000x670"} id="current" alt="#">
            `
        villDesc.innerText = data.villageDescription;
        villNoti.innerText = data.villageNotify;

        try{
            const actRes = await fetch(`/api/activity/list/${villID}`);

            if(actRes.status === 200){
                const actData = await actRes.json();
                for(let i in actData){
                    selectAct.innerHTML+=
                        `
                    <option value="${actData[i].activityId}" id="${actData[i].activityPrice}">${actData[i].activityName}</option>
                        `
                }
            }
        }catch (e) {
            selectAct.innerHTML+=
                `
                    <option value="없음">체험활동 없음</option>
                `
        }

    }
})();
