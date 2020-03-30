$(function () {
    $("#sidebar_zone").addClass("active");
});

function modify(button) {
    $(button).css("display", "none");
    $("#save_btn").css("display", "inline");
    $("#cancle_btn").css("display", "inline");
    $("#update_name").attr("readonly", false);
    $("#update_email").attr("readonly", false);
    $("#update_phone").attr("readonly", false);
}

function save() {
    $.ajax({
        url: "/user/updateIncludeSession",
        data: {
            id: $("#update_id").val(),
            name: $("#update_name").val(),
            email: $("#update_email").val(),
            phone: $("#update_phone").val()
        },
        success: function () {
            window.location.reload();
        },
        error: function (error) {
            myAlert("后台服务器发生错误");
            console.log(error);
        }
    });
}

function cancel(button) {
    $(button).css("display", "none");
    $("#save_btn").css("display", "none");
    $("#modify_btn").css("display", "inline");
    $("#update_name").attr("readonly", true);
    $("#update_email").attr("readonly", true);
    $("#update_phone").attr("readonly", true);
}

function changePassword() {
    var oldPassword = $("#old_password").val();
    var newPassword =$("#new_password").val();
    if (oldPassword===""||oldPassword===null){
        myAlert("请输入旧密码");
        return;
    }
    if (newPassword===""||newPassword===null){
        myAlert("请输入新密码");
        return;
    }
    $.ajax({
        url:"/user/changePassword",
        data:{
            id: $("#update_id").val(),
            oldPassword: oldPassword,
            newPassword: newPassword,
        },
        success:function (data) {

            if(data===""){
                myAlert("修改成功")
                window.location.reload();
            }else {
                myAlert(data);
            }
        },
        error:function (error) {
            myAlert("后台服务器异常");
            console.log(error);
        }
    })
}