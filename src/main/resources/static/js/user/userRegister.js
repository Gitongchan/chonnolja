//jquery 가져와서 사용하는 곳
// let script = document.createElement('script');
// script.src = 'https://code.jquery.com/jquery-3.4.1.min.js';
// script.type = 'text/javascript';
// document.getElementsByTagName('head')[0].appendChild(script);

// 공백 정규식
// var regExp = /^[0-9]+$/;

//  영문 대문자, 소문자, 숫자, 문자 사이 공백 및 특수문자.
// /^[a-zA-Z0-9-_/,.][a-zA-Z0-9-_/,. ]*$/;

//  아이디 체크(영문자 또는 숫자 6~20자)
//  /^[a-z]+[a-z0-9]{5,19}$/g;

//blur란? input태그에서 focus가 벗어나면? 이벤트가 발생하도록 하는 것

const useridCheck = /^[a-z]+[a-z0-9]{5,19}$/g; //[A-Za-z\d]{4,15}$/; // 최소4자, 최대 15자의 문자

const passwordCheck = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/; //최소 8 자, 최소 하나의 문자 및 하나의 숫자

const numberCheck = /^[0-9]{2,3}[0-9]{3,4}[0-9]{4}/;	// 숫자인경우

const nameCheck = /[가-힣]/; // 한글, 영어만

const emailCheck = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

<!-- 회원가입 폼 값들 -->
<!-- 유저아이디, 비밀번호, 이름, 이메일, 전화번호 저장하는 객체 -->
const userData = {
    userid: document.getElementById('reg-ID'),
    password1: document.getElementById('reg-pass'),
    password2: document.getElementById('reg-pass-confirm'),
    username: document.getElementById('reg-name'),
    email: document.getElementById('reg-email'),
    phone: document.getElementById('reg-phone'),
    postCode: document.getElementById('sample4_postcode'),
    sample4_roadAddress: document.getElementById('sample4_roadAddress'),
    sample4_jibunAddress: document.getElementById('sample4_jibunAddress'),
    sample4_detailAddress: document.getElementById('sample4_detailAddress')
}

//마을 값들
const villId = document.getElementById('vill-id');
const villimg = document.getElementById('thumb-file');

<!-- 유효성검사가 정상실행 되어서 값이 입력되어있다면 실행하는 함수-->
function userButtoncheck() {
    const result = (userData.userid.classList.contains("_success") &&
        userData.password1.classList.contains("_success") &&
        userData.password2.classList.contains("_success") &&
        userData.username.classList.contains("_success") &&
        userData.email.classList.contains("_success") &&
        userData.phone.classList.contains("_success") &&
        userData.sample4_detailAddress.classList.contains("_success"))
    document.querySelector('#register-pass.btn.uregister-pass').disabled = !result;
    villButtonCheck();
}

function villButtonCheck() {
    const result = (userData.userid.classList.contains("_success") &&
        userData.password1.classList.contains("_success") &&
        userData.password2.classList.contains("_success") &&
        userData.username.classList.contains("_success") &&
        userData.email.classList.contains("_success") &&
        userData.phone.classList.contains("_success") &&
        villId.value.trim()!=="" &&
        villimg.value !== "")

        document.querySelector('#register-pass.btn.vregister-pass').disabled = !result;
}

<!-- 회원가입 아이디 및 패스워드 확인 함수-->
const regeisterCheck = {
    ID: () => {
        const checkBool = useridCheck.test(userData.userid.value);
        console.log(useridCheck.test(userData.userid.value));
        if (checkBool === false) {
            <!-- 정규식 실패 시-->
            console.log("정규식 실패에요!!!")
            document.getElementById('id_check').innerHTML = '공백 및 특수문자를 제외한 영문 및 숫자 4~20자를 입력해주세요!';
            document.getElementById('id_check').style.display = 'block';
            document.getElementById('id_check').style.color = '#B02A37';
            userData.userid.classList.remove("_success");
            userData.userid.classList.add("_error");
            userButtoncheck();
        } else {
            console.log("정규식 성공이에요!!!");
            <!-- 정규식 성공 시-->
            fetch(`/api/userid_check?userid=${userData.userid.value}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result === 1) {
                        document.getElementById('id_check').innerHTML = '사용 가능한 아이디입니다!';
                        document.getElementById('id_check').style.display = 'block';
                        document.getElementById('id_check').style.color = '#2fb423';
                        userData.userid.classList.remove("_error");
                        userData.userid.classList.add("_success");
                        userButtoncheck();
                    } else {
                        document.getElementById('id_check').innerHTML = '사용 중인 아이디입니다!!';
                        document.getElementById('id_check').style.display = 'block';
                        document.getElementById('id_check').style.color = '#B02A37';
                        userData.userid.classList.remove("_success");
                        userData.userid.classList.add("_error");
                        userButtoncheck();
                    }
                })
                .catch(error => console.log(error))
        }
    },
    PW: () => {
        <!-- 두번째 비밀번호 input에  입력시 비밀번호 두 개가 맞는지 확인하는 함수-->
        if (userData.password1.value !== userData.password2.value) {
            <!-- 두 비밀번호가 일치하지 않을 때-->
            document.getElementById('pw_confirmcheck').style.display = 'block';
            document.getElementById('pw_confirmcheck').innerHTML = '비밀번호가 일치하지 않습니다!';
            userData.password2.classList.remove("_success");
            userData.password2.classList.add("_error");
            userButtoncheck();
        } else {
            <!-- 두 비밀번호가 일치할때-->
            document.getElementById('pw_confirmcheck').style.display = "none";
            userData.password2.classList.remove("_error");
            userData.password2.classList.add("_success");
            userButtoncheck();
        }
    },
    EMAIL: () => {
        const checkBool = emailCheck.test(userData.email.value);
        if (checkBool === false) {
            <!-- 정규식 실패 시-->
            document.getElementById('em_check').innerHTML = '이메일 형식을 올바르게 작성해주세요!';
            document.getElementById('em_check').style.display = 'block';
            document.getElementById('em_check').style.color = '#B02A37';
            userData.email.classList.remove("_success");
            userData.email.classList.add("_error");
            userButtoncheck();
        } else {
            <!-- 정규식 성공 시-->
            fetch(`/api/email_check?email=${userData.email.value}`)
                .then(response => response.json())
                .then(data => {
                    if (data.result === 1) {
                        document.getElementById('em_check').innerHTML = '사용 가능한 이메일입니다!';
                        document.getElementById('em_check').style.display = 'block';
                        document.getElementById('em_check').style.color = '#2fb423';
                        userData.email.classList.remove("_error");
                        userData.email.classList.add("_success");
                        userButtoncheck();
                    } else {
                        document.getElementById('em_check').innerHTML = '사용 중인 이메일입니다!!';
                        document.getElementById('em_check').style.display = 'block';
                        document.getElementById('em_check').style.color = '#B02A37';
                        userData.email.classList.remove("_success");
                        userData.email.classList.add("_error");
                        userButtoncheck();
                    }
                })
                .catch(error => console.log(error))
        }
    }
}


<!-- 첫 번째 비밀번호 정규식 체크하는 부분(password input에 focus가 떠나게 되면)-->
userData.password1.onblur = function () {
    console.log(passwordCheck.test(userData.password1.value));
    if (!passwordCheck.test(userData.password1.value)) {
        <!--비밀번호 정규화 실패 시-->
        document.getElementById('pw_check').style.display = "block";
        document.getElementById('pw_check').innerHTML = "숫자, 영문, 특수문자 포함 최소8자 이상입력해주세요!";
        userData.password1.classList.remove('_success');
        userData.password1.classList.add('_error');
        userButtoncheck();
    } else {
        <!-- 비밀번호 정규화 성공시-->
        document.getElementById("pw_check").style.display = "none";
        userData.password1.classList.remove('_error');
        userData.password1.classList.add('_success');
        regeisterCheck.PW();
        userButtoncheck();
    }
};

<!-- 이름 입력 후 focus 벗어나면 유효성 검사-->
userData.username.onblur = function () {
    if (!nameCheck.test(userData.username.value)) {
        <!-- 실패 시 -->
        document.getElementById("name_check").style.display = 'block';
        userData.username.classList.remove("_success");
        userData.username.classList.add("_error");
        userButtoncheck();
    } else {
        <!-- 성공 시 -->
        document.getElementById("name_check").style.display = 'none';
        userData.username.classList.remove("_error");
        userData.username.classList.add("_success");
        userButtoncheck();
    }
};


<!-- 번호 입력 후 focus 벗어나면 유효성 검사 -->
userData.phone.onblur = function () {
    if (!numberCheck.test(userData.phone.value)) {
        document.getElementById("phone_check").style.display = 'block';
        userData.phone.classList.remove("_success");
        userData.phone.classList.add("_error");
        userButtoncheck();
    } else {
        document.getElementById("phone_check").style.display = 'none';
        userData.phone.classList.remove("_error");
        userData.phone.classList.add("_success");
        userButtoncheck();
    }
};

<!-- 지번 입력 후 focus 벗어나면 유효성 검사 -->
userData.sample4_detailAddress.onblur = function () {
    if (!(userData.sample4_detailAddress.value.length === 0)) {
        //성공
        userData.sample4_detailAddress.classList.remove("_error");
        userData.sample4_detailAddress.classList.add("_success");
        userButtoncheck();
    } else {
        //실패
        userData.sample4_detailAddress.classList.remove("_success");
        userData.sample4_detailAddress.classList.add("_error");
        userButtoncheck();
    }
};

const post_btn = document.getElementById("PostCode");
post_btn.addEventListener('click',sample4_execDaumPostcode);

//버튼 값 가져오기
const utap_css = document.getElementById('tap-member');
const ctap_css = document.getElementById('tap-company');

const uRegisterTap = document.querySelector('#user_tap');
const vRegisterTap = document.querySelector('#village_tab');

const uRegisterBtn = document.querySelector('#register-pass.btn.uregister-pass');
const vRegisterBtn = document.querySelector('#register-pass.btn.vregister-pass');

//일반회원 탭을 눌렀을 때
const userTap = document.getElementById('tap-member');
userTap.addEventListener('click',()=>{
    document.getElementById('vill-check').style.display='none';
    uRegisterBtn.style.display = 'block';
    vRegisterBtn.style.display = "none";
    ctap_css.classList.remove('_select');
    utap_css.classList.add('_select');
    vRegisterTap.style.display = 'none';
    uRegisterTap.style.display = 'block';

});


//사업자 회원 탭을 눌렀을 때
const companyTap = document.getElementById('tap-company');
companyTap.addEventListener('click',()=>{
    uRegisterBtn.style.display = 'none';
    vRegisterBtn.style.display = "block";
    utap_css.classList.remove('_select');
    ctap_css.classList.add('_select');
    uRegisterTap.style.display = 'none';
    vRegisterTap.style.display = 'block';
    //사업자 회원일 때 회원가입 기능 ON
    vRegisterBtn.addEventListener('click',companyBtn);

});

// 사업자 버튼 눌렸을 때 버튼 동작하는 함수
const companyBtn = function() {
    event.preventDefault();
    // api에 요청을 보낼 때 header에 _csrf토큰값을 가져와서 넘김
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    //폼 생성
    const formData = new FormData();

    const postData = {
        userid: userData.userid.value,
        password: userData.password1.value,
        name: userData.username.value,
        phone: userData.phone.value,
        email: userData.email.value
    }

    formData.append("thumbFile", villimg.files[0]);
    formData.append("villageUserInfoDto",
        new Blob([JSON.stringify(postData)], {type:"application/json"}))

    fetch(`/api/village/signup/${villId.value}`, {
        method: "POST",
        headers: {
            'header': header,
            'X-CSRF-Token': token
        },
        body: formData
    })
        .then(res => {
            if (res.status === 200 || res.status === 201) { // 성공을 알리는 HTTP 상태 코드면
                alert('사업자 회원가입 성공')
            } else { // 실패를 알리는 HTTP 상태 코드면
                console.error(res.statusText);
                console.error(res);
            }
        })
        .then(data => console.log(data))
        .catch(error => console.log(error))
}

//일반 회원일 때
uRegisterBtn.addEventListener('click',  function () {
    event.preventDefault();
    // api에 요청을 보낼 때 header에 _csrf토큰값을 가져와서 넘김
    const header = document.querySelector('meta[name="_csrf_header"]').content;
    const token = document.querySelector('meta[name="_csrf"]').content;

    const postData = {
        userid: userData.userid.value,
        password: userData.password1.value,
        name: userData.username.value,
        phone: userData.phone.value,
        email: userData.email.value,
        userAdrNum: userData.postCode.value,
        userLotAdr: userData.sample4_jibunAddress.value,
        userStreetAdr: userData.sample4_roadAddress.value,
        userDetailAdr: userData.sample4_detailAddress.value
    }
    fetch("/api/signup", {
        method: "POST",
        headers: {
            'header': header,
            'X-Requested-With': 'XMLHttpRequest',
            "Content-Type": "application/json",
            'X-CSRF-Token': token
        },
        body: JSON.stringify(postData)
    })
        .then(res => {
            if (res.status === 200 || res.status === 201) { // 성공을 알리는 HTTP 상태 코드면
                alert('유저 회원가입 성공')
            } else { // 실패를 알리는 HTTP 상태 코드면
                console.error(res.statusText);
                console.error(res);
            }
        })
        .then(data => console.log(data))
        .catch(error => console.log(error))
});