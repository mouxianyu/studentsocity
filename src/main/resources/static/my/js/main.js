function myAlert(text) {
    $.dialog({
        icon: 'glyphicon glyphicon-alert',
        title : "提 示",
        content: text,
        type : 'green'
    });
}

function logout() {
    $.confirm({
        title:"提 示",
        content:"是否退出？退出后需重新登录",
        type:"green",
        buttons:{
            确认:function () {
                window.location.href='/user/toLogin'
            },
            取消:{

            }
        }
    });
}

function checkAll(obj) {
    if($(obj).prop("checked")){
        $("input[type='checkbox'][class='delete_check']").prop("checked",true);
    }else {
        $("input[type='checkbox'][class='delete_check']").prop("checked",false);
    }
}