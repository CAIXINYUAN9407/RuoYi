
function aaa() {

    var code =  $('#code').text();
    $.ajax({
        type: "post",
        url: ctx + "OtherLoginAjax",

        data: {
            "code": code
        },
        beforeSend: function () {
            // $.modal.loading($("#btnSubmit").data("loading"));
            $.modal.loading("请稍后");
        },
        success: function(r) {
            if (r.code == web_status.SUCCESS) {
                location.href = ctx + 'index';
            } else {
                $('.imgcode').click();
                $(".code").val("");
                $.modal.msg(r.msg);
            }
            $.modal.closeLoading();
        }
    });
};


