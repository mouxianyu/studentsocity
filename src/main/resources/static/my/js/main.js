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

function saveImg(url) {
    var formData = new FormData();
    formData.append("imgFile", imgFile);
    $.confirm({
        title: "提 示",
        type: "green",
        content: function () {
            var self = this;
            return $.ajax({
                type: 'post',
                url: url,
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

function goBack() {
    window.location.go(-1);
}

function mobileAlert(text) {
    $(document).dialog({
        titleShow: false,
        content: text
    });
}