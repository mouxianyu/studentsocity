var addUploadFile = [];
var updateUploadFile = [];
$(function () {
    $("#sidebar_activity_management").addClass("active");
});

function scaleJudge(select,method) {
    method = "#"+method+"_society_select";
    var scale = $(select).val();
    if(scale==0){
        $(method).css("display","none");
    }
    if(scale==1){
        $(method).css("display","block");
    }
}

function changeStatus(button,method) {
    var activityId = $(button).val();
    $.ajax({
        url:"/activity/"+method,
        data:{
            id:activityId
        },
        success:function () {
            location.reload()
        },
        error:function (error) {
            myAlert("操作失败");
            console.log(error);
        }
    })
}

function page(obj) {
    $("#start").val($(obj).text());
    $("#query_form").submit();
}

function prePage() {
    var goTo = parseInt($("#start").val()) - 1;
    if (goTo > 0) {
        $("#start").val(goTo);
        $("#query_form").submit();
    }
}

function nextPage() {
    var goTo = parseInt($("#start").val()) + 1;
    if (goTo <= parseInt($("#totalPage").val())) {
        $("#start").val(goTo);
        $("#query_form").submit();
    }
}

function goToPage() {
    var page = $("#goto").val();
    if (page <= 0) {
        myAlert("请输入正确页数");
        return;
    }
    if (page > parseInt($("#totalPage").val())) {
        myAlert("超出最大页数");
        return;
    }
    $("#start").val(page);
    $("#query_form").submit();
}

function inputIntOnly() {
    if (event.keyCode === 13) {
        goToPage();
        return;
    }
    if (event.keyCode < 48) {
        return event.keyCode === 8;
    }
    return event.keyCode <= 57;
}

function deleteByIds() {
    var objs = $("input[name='id']:checked");
    var values = [];
    if ($(objs).length <= 0) {
        myAlert("请选择");
        return;
    }
    $(objs).each(function (index, obj) {
        values.push($(obj).val());
    });
    $.confirm({
        title: "提 示",
        content: "是否确定删除？",
        type: "green",
        buttons: {
            确认: function () {
                $.ajax({
                    url: "/activity/delete",
                    type: "post",
                    traditional: true,
                    data: {
                        ids: values
                    },
                    success: function () {
                        location.reload();
                    },
                    error: function () {
                        myAlert("删除失败");
                    }
                });
            },
            取消: {}
        }
    });

}

function showDetail(obj, method) {
    method = "#" + method + "_";
    var val = $(obj).children();
    $(method + "img_pre_div").empty();
    $.ajax({
        url: "/activity/more/" + $(val[0]).val(),
        async: true,
        success: function (data) {
            $(method + "id").val(data.id);
            $(method + "title").val(data.title);
            $(method + "content").val(data.content);
            if(data.scale==1){
                $(method+"society_select").css("display","block");
                $(method+"societyId").val(data.societyId);
            }
            $(method + "scale").val(data.scale);
            $(method + "status").val(data.status);
            data.imgs.forEach(function (imgObj, index) {
                if (method === "#detail_") {
                    $(method + "img_pre_div").append("<div class='img_wrap'><img alt='图片' src='/img/activity/" + imgObj.relName + "'></div>");
                }
                if (method === "#update_") {
                    $(method + "img_pre_div").append("<div class='img_wrap'><i onclick='deleteOneImgWithDataBase(this," + imgObj.id + ")' class='fa fa-trash-o' aria-hidden='true'></i><img alt='图片' src='/img/activity/" + imgObj.relName + "'></div>");
                }
            });
        },
        error: function (error) {
            myAlert("发生异常");
            console.log(error);
        }
    });
}

function addImg() {
    $("#img_file").click();
}

function addImgUpdate() {
    $("#update_img_file").click();
}

function readImg(event) {
    var files = event.target.files, file;
    if (files && files.length > 0) {
        for (var i = 0; i < files.length; i++) {
            file = files[i];
            if (file.size > 1024 * 1024 * 2) {
                myAlert('图片大小不能超过 2MB!');
                return false;
            }
            if (addUploadFile.length < 12) {
                addUploadFile.push(file);
            } else {
                myAlert("最多上传12张图片");
                return false;
            }
            var URL = window.URL || window.webkitURL;
            var imgURL = URL.createObjectURL(file);
            $("#add_img_pre_div").append("<div class='img_wrap'><i onclick='deleteOneImg(this)' class='fa fa-trash-o' aria-hidden='true'></i><img alt='图片' src='" + imgURL + "'></div>");
        }
    }
}

function readImgUpdate(event) {
    var files = event.target.files, file;
    if (files && files.length > 0) {
        for (var i = 0; i < files.length; i++) {
            file = files[i];
            if (file.size > 1024 * 1024 * 2) {
                myAlert('图片大小不能超过 2MB!');
                return false;
            }
            if ($("#update_img_pre_div").children().length < 12) {
                updateUploadFile.push(file);
            } else {
                myAlert("最多上传12张图片");
                return false;
            }
            var URL = window.URL || window.webkitURL;
            var imgURL = URL.createObjectURL(file);
            $("#update_img_pre_div").append("<div class='img_wrap update_temp'><i onclick='deleteOneImgUpdate(this)' class='fa fa-trash-o' aria-hidden='true'></i><img alt='图片' src='" + imgURL + "'></div>");
        }
    }
}

function cleanImg() {
    var imgDiv = $("#add_img_pre_div");
    if (imgDiv.children().length <= 0) {
        myAlert("未选择图片");
        return;
    }
    $.confirm({
        title: "提 示",
        content: "是否清空图片",
        type: "green",
        buttons: {
            确定: function () {
                $("#add_img_pre_div").empty();
                addUploadFile = [];
            },
            取消: {}
        }
    });
}

function cleanImgUpdate() {
    var imgDiv = $("#update_img_pre_div");
    var objId = $("#update_id").val();
    if (imgDiv.children().length <= 0) {
        myAlert("未选择图片");
        return;
    }
    $.confirm({
        title: "提 示",
        content: "是否清空图片",
        type: "green",
        buttons: {
            确定: function () {

                $.ajax({
                    url: "/img/deleteByTypeAndObjId",
                    data: {
                        objId: objId,
                        type: 0
                    },
                    success: function () {
                        $("#update_img_pre_div").empty();
                        updateUploadFile = [];
                    },
                    error: function (error) {
                        myAlert("发生异常");
                        console.log(error);
                    }
                })
            },
            取消: {}
        }
    });
}


function deleteOneImg(icon) {
    var imgDiv = $(icon).parent();
    var index = imgDiv.index();
    imgDiv.remove();
    addUploadFile.splice(index, 1);
}

function deleteOneImgUpdate(icon) {
    var imgDiv = $(icon).parent();
    var index = imgDiv.index(".update_temp");
    imgDiv.remove();
    updateUploadFile.splice(index,1);
}

function deleteOneImgWithDataBase(icon, id) {
    $.confirm({
        title: "提 示",
        type: "green",
        content: "是否删除图片？",
        buttons: {
            确认: function () {
                $.ajax({
                    url: "/img/delete/" + id,
                    success: function () {
                        var imgDiv = $(icon).parent()
                        imgDiv.remove();
                    }, error: function (error) {
                        myAlert("发生异常");
                        console.log(error);
                    }
                })
            },
            取消: {}
        }
    });

}

function addNewActivity() {
    var formData = new FormData();
    var title = $("#add_title").val();
    var content = $("#add_content").val();
    var scale = $("#add_scale").val();
    var societyId = $("#add_societyId").val();
    for (var i = 0; i < addUploadFile.length; i++) {
        formData.append("file", addUploadFile[i]);
    }
    if(scale==1){
        formData.append("societyId", societyId);
    }
    formData.append("title", title);
    formData.append("content", content);
    formData.append("scale", scale);
    if (title === "") {
        myAlert("请填写活动标题");
        return;
    }
    if (content === "") {
        myAlert("请填写活动内容");
        return;
    }
    $.confirm({
        title: "提 示",
        type: "green",
        content: function () {
            var self = this;
            return $.ajax({
                type: 'post',
                url: "/activity/add",
                data: formData,
                cache: false,
                traditional: true,
                processData: false,
                contentType: false
            }).done(function (msg) {
                self.setContentAppend("保存成功");
            }).fail(function (error) {
                self.setContentAppend("保存失败");
                console.log(error);
            });
        },
        buttons: {
            关闭: function () {
                location.reload();
            }
        }
    });
}

function updateActivity() {
    var formData = new FormData();
    var id =$("#update_id").val();
    var title = $("#update_title").val();
    var content = $("#update_content").val();
    var scale = $("#update_scale").val();
    var societyId = $("#update_societyId").val();
    for (var i = 0; i < updateUploadFile.length; i++) {
        formData.append("file", updateUploadFile[i]);
    }
    if(scale==1){
        formData.append("societyId", societyId);
    }
    formData.append("id", id);
    formData.append("title", title);
    formData.append("content", content);
    formData.append("scale", scale);
    if (title === "") {
        myAlert("请填写活动标题");
        return;
    }
    if (content === "") {
        myAlert("请填写活动内容");
        return;
    }
    $.confirm({
        title: "提 示",
        type: "green",
        content: function () {
            var self = this;
            return $.ajax({
                type: 'post',
                url: "/activity/update",
                data: formData,
                cache: false,
                traditional: true,
                processData: false,
                contentType: false
            }).done(function () {
                self.setContentAppend("保存成功");
            }).fail(function (error) {
                self.setContentAppend("保存失败");
                console.log(error);
            });
        },
        buttons: {
            关闭: function () {
                location.reload();
            }
        }
    });
}