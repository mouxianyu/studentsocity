$(function () {
    $("#sidebar_society_management").addClass("active");
});

$(function () {
    loadUsers("add");
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
        title: "提 示",
        content: "是否确定删除？",
        type: "green",
        buttons: {
            确认: function () {
                $.ajax({
                    url: "/society/delete",
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
    $.ajax({
        url: "/society/" + $(val[0]).val(),
        async: false,
        success: function (data) {
            $(method + "id").val(data.id);
            $(method + "name").val(data.name);
            $(method + "detail").val(data.detail);
            $(method + "status").val(data.status);
            $(method + "scale").val(data.scale);
            if (method === "#update_") {
                loadUpdateUser(data.presidentId);
                $(method + "president").val(data.presidentId);
            }
            if (method === "#detail_") {
                $(method + "president").val(data.presidentName);
            }
            $('#dataIdSelect').selectpicker('refresh');
        },
        error: function (error) {
            myAlert("发生异常");
            console.log(error);
        }
    });
}

function loadUpdateUser(id) {
    var presidentIdSelect = $("#update_president");
    presidentIdSelect.empty();
    $.ajax({
        url: "/user/queryAll",
        data: {
            id: id
        },
        success: function (data) {
            data.forEach(function (obj, index) {
                presidentIdSelect.append("<option value =" + obj.id + ">" + obj.name + "&nbsp&nbsp&nbsp————&nbsp&nbsp&nbsp学号：" + obj.no + "</option>");
            });
        },
        error: function (error) {
            myAlert("发生异常");
            console.log(error);
        }
    })
}

function loadUsers(method) {
    var presidentIdSelect = $("#" + method + "_president");
    var searchName = $("#" + method + "_name_input");
    presidentIdSelect.empty();
    presidentIdSelect.append("<option value=''>——— 请 选 择 ———</option>");

    $.ajax({
        url: "/user/queryAll",
        data: {
            name: searchName.val()
        },
        success: function (data) {
            data.forEach(function (obj, index) {
                presidentIdSelect.append("<option value =" + obj.id + ">" + obj.name + "&nbsp&nbsp&nbsp————&nbsp&nbsp&nbsp学号：" + obj.no + "</option>");
            });
        },
        error: function (error) {
            myAlert("发生异常");
            console.log(error);
        }
    })
}

function verifySociety(button) {
    $.confirm({
        title: "提 示",
        content: "是否通过审核？",
        type: "green",
        buttons: {
            确认: function () {
                $.ajax({
                    url: "/society/update",
                    data: {
                        id: $(button).val(),
                        status: 0,
                        estTime: new Date()
                    },
                    success: function () {
                        myAlert("成功");
                        location.reload();
                    },
                    error: function (error) {
                        myAlert("发生异常");
                        console.log(error);
                    }
                });
            },
            取消: {}
        }
    });
}

function disableSociety(button) {
    $.confirm({
        title: "提 示",
        content: "是否关闭社团？",
        type: "green",
        buttons: {
            确认: function () {
                $.ajax({
                    url: "/society/update",
                    data: {
                        id: $(button).val(),
                        status: 1
                    },
                    success: function () {
                        myAlert("成功");
                        location.reload();
                    },
                    error: function (error) {
                        myAlert("发生异常");
                        console.log(error);
                    }
                });
            },
            取消: {}
        }
    });

}

function unbanSociety(button) {
    $.confirm({
        title: "提 示",
        content: "是否重新启用社团？",
        type: "green",
        buttons: {
            确认: function () {
                $.ajax({
                    url: "/society/update",
                    data: {
                        id: $(button).val(),
                        status: 0
                    },
                    success: function () {
                        myAlert("成功");
                        location.reload();
                    },
                    error: function (error) {
                        myAlert("发生异常");
                        console.log(error);
                    }
                });
            },
            取消: {}
        }
    });
}

function moreInfo() {
    var id = $("#detail_id").val();
    window.location.href = "/society/queryUserBySocietyId/" + id;
}