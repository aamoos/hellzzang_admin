let id = "";

//설정버튼에서 클릭한 사용자 인덱스 추출
function setting(target){
    id = $(target).attr("data-id");
}

//사용자 검색
function userList(){
    let searchVal = document.querySelector('#searchVal').value;
    location.href = "/manage/user/list?searchVal="+searchVal;
}

//계정 정지 설정
function blockTime(){
    const selectVal = document.querySelector('#selectBlock').value;

    const params = {
        "id" : Number(id)
        ,"selectVal" : selectVal
    };
    console.log(params);

    $.ajax({
        type : 'POST'
        ,url : '/manage/user/block'
        ,dataType : 'json'
        ,data : JSON.stringify(params)
        ,contentType : "application/json;charset=UTF-8"
        ,success : function(result){
            console.log(result);
            location.href="/manage/user/list";
        }
        ,error : function(error){
            console.log(error);
        }

    });
}