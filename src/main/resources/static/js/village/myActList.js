// (상품번호, 상품아이디), 사업자 등록 번호, 상품종류, 상품 서브 종류, 상품 브랜드, 상품명
// 상품대표사진, 상품내용, 가격, 재고, 상품등록일, 조회수, 누적 구매수
const actlist = document.getElementById("act-list");

document.getElementById('product-listBtn').addEventListener('click',async function () {
    const res = await fetch(`/api/activity/list/${checkVill.value}`)
    const data = await res.json();
    console.log(data);
    try{
        if(res.status===200){

            for(let i in data) {

                let tr = document.createElement('tr');
                tr.innerHTML = `
                        <td id="${data[i].activityId}">${data[i].activityId}</td>
                        <td class="uid">${checkVill.value}</td>
                        <td class="actName">${data[i].activityName}</td>
                        <td class="price">${data[i].activityPrice}</td>
                        <td class="stock">${data[i].activityStock}</td>
                        <td class="info"><a href="#">상세정보</a></td>
                `
                actlist.appendChild(tr);
            }
        }
    }catch (e) {

    }
});