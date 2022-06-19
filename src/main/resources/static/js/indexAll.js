const view = document.getElementById("view");
( async ()=>{
    const res = await fetch(`api/village/list/villageViewCnt/8?villageActivity=&villageName=&address=&page=0`);
    const data = await res.json();

    if(res.ok){
        for(let i in data){
            view.innerHTML+= `
            <div class="col-lg-3 col-md-6 col-12">
                    <!-- Start Single Product -->
                    <div class="single-product">
                        <div class="product-image">
                            <img src=${data[i].villagePhoto!==null ? data[i].villagePhoto : "https://via.placeholder.com/335x335"} alt="#">
                            <div class="button">
                                <a href="mapsearch?villageActivity=&villageName=${data[i].villageName}&address=" class="btn"><i class="lni lni-map"></i>지도보기</a>
                            </div>
                        </div>
                        <div class="product-info">
                            <span class="category">${data[i].villageAdrMain}</span>
                            <h4 class="title">
                                <a href=villageinfo?vid=${data[i].villageId}>${data[i].villageName}</a>
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
                </div>
            `
        }
    }

})();