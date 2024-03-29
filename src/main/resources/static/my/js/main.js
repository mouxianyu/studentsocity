var imgFile;
var clipArea = new PhotoClip("#clipArea", {
    size: [260, 260],
    outputSize: 640,
    file: "#imgFile",
    view: "#view",
    ok: "#clipBtn",
    loadStart: function () {
        $('.cover-wrap').fadeIn();
    },
    loadComplete: function () {
    },
    done: function (dataURL) {
        $('.cover-wrap').fadeOut();
        $('#view').css('background-size', '100% 100%');
        var newFile = dataURLtoFile(dataURL, "a.jpg");
        imgFile = newFile;
    }
});

function coverFadeOut() {
    $('.cover-wrap').fadeOut();
}

function dataURLtoFile(dataurl, filename) {
    var arr = dataurl.split(',');
    var mime = arr[0].match(/:(.*?);/)[1];
    var bstr = atob(arr[1]);
    var n = bstr.length;
    var u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, {type: mime});
}

function saveImg(objType, objId) {
    var formData = new FormData();
    if(imgFile==undefined){
        myAlert("请选择文件");
        return;
    }
    formData.append("imgFile", imgFile);
    formData.append("objType", objType);
    if(objId!==undefined){
        formData.append("objId", objId);
    }
    $.confirm({
        title: "提 示",
        type: "green",
        content: function () {
            var self = this;
            return $.ajax({
                type: 'post',
                url: '/img/upload',
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

function saveImgMobile(objType, objId) {
    var formData = new FormData();
    if(imgFile==undefined){
        mobileAlert("请选择文件");
        return;
    }
    formData.append("imgFile", imgFile);
    formData.append("objType", objType);
    if(objId!==undefined){
        formData.append("objId", objId);
    }
    $.ajax({
        type: 'post',
        url: '/img/upload',
        data: formData,
        cache: false,
        traditional: true,
        processData: false,
        contentType: false,
        success:function () {
            mobileAlertThenBack("上传成功");
        },
        error:function (error) {
            mobileAlert("上传失败");
            console.log(error);
        }
    });
}

function myAlert(text) {
    $.dialog({
        icon: 'glyphicon glyphicon-alert',
        title: "提 示",
        content: text,
        type: 'green'
    });
}

function logout() {
    $.confirm({
        title: "提 示",
        content: "是否退出？退出后需重新登录",
        type: "green",
        buttons: {
            确认: function () {
                window.location.href = '/user/logout'
            },
            取消: {}
        }
    });
}

function mobileLogout() {
    $(document).dialog({
        type: 'confirm',
        titleShow: false,
        content: "是否退出？退出后需重新登录",
        buttons: [
            {
                name: "确定",
                callback: function () {
                    window.location.href ="/client/logout";
                }
            },
            {
                name:"取消",
                callback:function () {

                }
            }
        ]
    });
}

function checkAll(obj) {
    if ($(obj).prop("checked")) {
        $("input[type='checkbox'][class='delete_check']").prop("checked", true);
    } else {
        $("input[type='checkbox'][class='delete_check']").prop("checked", false);
    }
}

function goBack() {
    window.location.go(-1);
}

function mobileAlert(text) {
    $(document).dialog({
        titleShow: false,
        content: text
    });
}

function mobileAlertThenReload(text) {
    $(document).dialog({
        type: 'confirm',
        titleShow: false,
        content: text,
        buttons: [
            {
                name: "确定",
                callback: function () {
                    location.reload();
                }
            }
        ]
    });
}

function mobileAlertThenBack(text) {
    $(document).dialog({
        type: 'confirm',
        titleShow: false,
        content: text,
        buttons: [
            {
                name: "确定",
                callback: function () {
                    window.location.href = document.referrer;
                }
            }
        ]
    });
}

function mobileAlertThenLinkTo(text,url,map) {
    $(document).dialog({
        type: 'confirm',
        titleShow: false,
        content: text,
        buttons: [
            {
                name: "确定",
                callback: function () {
                    if(map==undefined){
                        window.location.href=url;
                    }else {
                        postLinkTo(url,map);
                    }

                }
            }
        ]
    });
}

function postLinkTo(url,data) {
    var form =$("<form>").attr("method","post").attr("action",url).css("display","none");
    data.forEach(function (obj,key) {
        var input=$("<input>").attr("name",key).val(obj);
        form.append(input);
    })
    $("body").append(form);
    form.submit();
}