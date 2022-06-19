const reBtn = document.getElementById('act-reservation');

reBtn.addEventListener('click',async ()=>{

    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    const select = document.getElementById('act-select');
    const quan = document.getElementById('quan');

    const sum = Number(quan.options[quan.selectedIndex].value) * Number(select.options[select.selectedIndex].id);
    console.log(Number(quan.options[quan.selectedIndex].value));
    console.log(Number(select.options[select.selectedIndex].id));
    console.log(sum);
    const reserData = {
        reservationQuantity:quan.options[quan.selectedIndex].value,
        reservationPrice:sum
    }
    const res = await fetch(`/api/user/reservation/${select.options[select.selectedIndex].value}`,{
        method : 'POST',
        headers: {
            'header': header,
            'X-CSRF-Token': token,
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body : JSON.stringify(reserData)
    })
    const data = await res.json();

    if(res.ok){
        console.log(data);
        alert(`예약완료 : ${sum}원 입금`);
    }
})