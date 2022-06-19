function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

const adr = getParameterByName('address');
const vill = getParameterByName('villageName');
const act = getParameterByName('villageActivity');

const wrap = document.getElementById('grid-wrap');
console.log(adr);
console.log(vill);
console.log(act);

(async ()=>{
    const res = await fetch(`api/village/list/villageId/100?villageActivity=${act}&villageName=${vill}&address=${adr}&page=0`);
    const data = await res.json();
    try{
        if(res.ok){
        for(let i in data){
            wrap.innerHTML+=
                `        <div class="col-lg-4 col-md-6 col-12">
                <!-- Start Single Product -->
                <div class="single-product">
                    <div class="product-image">
                        <img src=${data[i].villagePhoto === null ?"https://via.placeholder.com/335x335/?text=No+Image": data[i].villagePhoto} alt="#">
                            <div class="button">
                                <a href=/villageinfo?vid=${data[i].villageId} class="btn"><i
                                    class="lni lni-cart"></i>상세정보</a>
                            </div>
                    </div>
                    <div class="product-info">
                        <span class="category">${data[i].villageAdrMain}</span>
                        <h4 class="title">
                            <a href=/villageinfo?vid=${data[i].villageId}>${data[i].villageName}</a>
                        </h4>
                        <ul class="review">
                            <li><i class="lni lni-star-filled"></i></li>
                            <li><i class="lni lni-star-filled"></i></li>
                            <li><i class="lni lni-star-filled"></i></li>
                            <li><i class="lni lni-star-filled"></i></li>
                            <li><i class="lni lni-star"></i></li>
                            <li><span>4.0 Review(s)</span></li>
                        </ul>
                        <div class="price">
                            <span>공지참고</span>
                        </div>
                    </div>
                </div>
                <!-- End Single Product -->
            </div>`
        }
        }
    }catch (e) {
        alert("오류발생!")
    }
})();
