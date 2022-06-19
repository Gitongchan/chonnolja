$(function () {
    $("#act-date").datepicker({
        dateFormat:"yy/mm/dd",
        dayNamesMin:["일","월","화","수","목","금","토"],
        monthNames:["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
        onSelect:function(d){
            alert(d+" 선택되었습니다");

            var arr=d.split("/");
            var year=arr[0];
            var month=arr[1];
            var day=arr[2];

            $("#year").text(year);
            $("#month").text(month);
            $("#day").text(day);

            //요일 가져오기
            //데이터를 먼저 가져오고 (숫자로 넘어옴)
            var date=new Date($("#date").datepicker({dateFormat:"yy/mm/dd"}).val());
            //일요일 0~
            alert("date:"+date.getDay());

            week=new Array("일","월","화","수","목","금","토");
            $("#mydate").text(week[date.getDay()]);
        }
    });

});