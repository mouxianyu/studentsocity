var hasLoadColleges = false;

$(function () {
    $("#sidebar_student_management").addClass("active");
});

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
        title:'提 示',
        content:'是否确定删除？',
        type:'green',
        buttons:{
            确认:function () {
                $.ajax({
                    url: "/user/delete",
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
            取消:{
            }
        }
    });
}

function showDetail(idObj,collegeObj,majorObj,method) {
    loadColleges();
    method = "#"+method+"_";
    var val = $(idObj).children();
    $.ajax({
        url: "/user/" + $(val[0]).val(),
        success: function (data) {
            $(method+"id").val(data.id);
            $(method+"no").val(data.no);
            $(method+"name").val(data.name);
            $(method+"email").val(data.email);
            $(method+"phone").val(data.phone);
            $(method+"origin").val(data.origin);
            $(method+"grade").val(data.grade);
            collegeObj.val(data.college);
            loadMajors(collegeObj,majorObj);
            majorObj.val(data.major);
            $(method+"authority").val(data.authority);
            if (data.gender) {
                $(method+"gender input[name='gender'][value=true]").prop("checked", true);
            } else {
                $(method+"gender input[name='gender'][value=false]").prop("checked", true);
            }

            if (data.status === 0) {
                $(method+"status").prop("checked", true);
            } else {
                $(method+"status").prop("checked", false);
            }

        },
        error: function (error) {
            myAlert("发生异常");
            console.log(error);
        }
    });
}

function resetPassword() {
    $.ajax({
        url: "/user/resetPassword",
        data: {
            id: $("#update_id").val()
        },
        success: function () {
            myAlert("重置成功")
        },
        error: function (error) {
            myAlert("重置失败");
            console.log(error);
        }
    });
}

// 加载学院
function loadColleges() {
    if(hasLoadColleges)return;
    var addSelectObj = $("#add_college_select");
    var updateSelectObj = $("#update_college_select");
    var detailSelectObj = $("#detail_college_select");
    var addOptionCount = addSelectObj.children().length;
    var updateOptionCount = updateSelectObj.children().length;
    var detailOptionCount = detailSelectObj.children().length;
    if (addOptionCount <= 1 || updateOptionCount <= 1 || detailOptionCount <= 1) {
        $.ajax({
            url: "/college/queryAll",
            success: function (data) {
                data.forEach(function (obj, index) {
                    addSelectObj.append("<option value=" + obj.id + ">" + obj.name + "</option>");
                    updateSelectObj.append("<option value=" + obj.id + ">" + obj.name + "</option>");
                    detailSelectObj.append("<option value=" + obj.id + ">" + obj.name + "</option>");
                    hasLoadColleges=true;
                });
            },
            error: function (error) {
                myAlert("后台服务发生错误，请联系管理员");
                console.log(error);
            }
        });
    }
}

// 加载专业
function loadMajors(collegeObj,majorObj) {
    majorObj.empty();
    majorObj.append("<option value=''>——— 请 选 择 ———</option>");
    var collegeId = collegeObj.val();
    var majorCount = majorObj.children().length;
    if (majorCount <= 1 && collegeId !== "") {
        $.ajax({
            url: "/major/queryByCollegeId/" + collegeId,
            async : false,
            success: function (data) {
                data.forEach(function (obj, index) {
                    majorObj.append("<option value=" + obj.id + ">" + obj.name + "</option>")
                });
            },
            error: function (error) {
                myAlert("后台服务发生错误，请联系管理员");
                console.log(error);
            }
        });
    }
}