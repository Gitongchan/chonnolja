//토근값 가져오기
const token = document.querySelector('meta[name="_csrf"]').content;

const span_id = document.createElement('span');
const login_info = document.querySelector('.userinfo');
const div_id = document.querySelector('.view_id');

//로그인 안되어있다면 로그인, 회원가입 > 로그인 되어있다면 내정보|로그아웃

const left_a = document.createElement('a');
const left_span = document.createElement('span');
const right_a = document.createElement('a');
const right_span = document.createElement('span');

const stick = document.createElement('span');
stick.innerText = '|';

const logout_form = document.createElement('form');
const hidden_token = document.createElement('input');
const logout_btn = document.createElement('button');

const checkID = document.getElementById('checkUserId');
const checkVill = document.getElementById('checkVillage');

(async function() {
    try {
        const res = await fetch('/api/user')
        if(res.status === 200){
            const data = await res.json();
            checkID.value = data.id;
            const mRes = await fetch('/api/manager');
            if(mRes.status === 200){
                const mData = await mRes.json();
                checkVill.value = mData.villageId;
                console.log(mData);
                span_id.innerText = `${data.userid}님! 안녕하세요!`;
                left_a.href = '/manager/mypage';
                left_span.innerText = '내 정보';
                left_a.appendChild(left_span);

                hidden_token.type = 'hidden';
                hidden_token.name = '_csrf';
                hidden_token.value = token;
                logout_btn.innerText = '로그아웃';

                logout_form.method = 'POST';
                logout_form.action = '/api/user/logout';
                logout_form.appendChild(hidden_token);
                logout_form.appendChild(logout_btn);

                div_id.appendChild(span_id);
                login_info.appendChild(left_a);
                login_info.appendChild(logout_form);
            }else{
                span_id.innerText = `${data.userid}님! 안녕하세요!`;

                left_a.href = '/user/mypage';
                left_span.innerText = '내 정보';
                left_a.appendChild(left_span);

                hidden_token.type = 'hidden';
                hidden_token.name = '_csrf';
                hidden_token.value = token;
                logout_btn.innerText = '로그아웃';

                logout_form.method = 'POST';
                logout_form.action = '/api/user/logout';
                logout_form.appendChild(hidden_token);
                logout_form.appendChild(logout_btn);

                div_id.appendChild(span_id);
                login_info.appendChild(left_a);
                login_info.appendChild(logout_form);
            }
        }
    }
    catch (e) {
        console.log("에러발생!");
        span_id.innerText = `로그인이 필요합니다!`;
        left_a.href = '/pages/login';
        left_span.innerText = '로그인';
        left_a.appendChild(left_span);

        right_a.href = '/register';
        right_span.innerText = '회원가입';
        right_a.appendChild(right_span);

        div_id.appendChild(span_id);
        login_info.appendChild(left_a);
        login_info.appendChild(right_a);
    }
})();