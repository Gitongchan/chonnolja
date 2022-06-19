
// 일반검색
document.getElementById('index-search-btn').addEventListener('click',function (){
    const searchSelect = document.getElementById('main-search-select');
    const text = document.getElementById('main-search-input').value;
    const val = searchSelect.options[searchSelect.selectedIndex].value;
    if(val===''){
        location.replace(`/villagelist?villageActivity=&villageName=&address=`);
    }else if(val==='address'){
        location.replace(`/villagelist?villageActivity=&villageName=&address=${text}`);
    }else if(val==='villname'){
        location.replace(`/villagelist?villageActivity=&villageName=${text}&address=`);
    }else{
        location.replace(`/villagelist?villageActivity${text}&villageName=&address=`);
    }
})


// 지도검색
document.getElementById('mapSearch').addEventListener('click',function (){
    const searchSelect = document.getElementById('main-search-select');
    const text = document.getElementById('main-search-input').value;
    const val = searchSelect.options[searchSelect.selectedIndex].value;
    if(val===''){
        location.replace(`/mapsearch?villageActivity=&villageName=&address=`);
    }else if(val==='address'){
        location.replace(`/mapsearch?villageActivity=&villageName=&address=${text}`);
    }else if(val==='villname'){
        location.replace(`/mapsearch?villageActivity=&villageName=${text}&address=`);
    }else{
        location.replace(`/mapsearch?villageActivity${text}&villageName=&address=`);
    }
})