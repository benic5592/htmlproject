/**
 * Created by Administrator on 2017/6/16.
 */
$(function () {
    $.ajaxSetup({cache:false});

    $("header").load(
        "/sysmgr/html/header.html"
        ,function (data) {
            startTime();
        }
    );


    $("#idUser").blur(function () {

        if( "" != $("#idUser").val()){
            $("#userSpan").load(
                "/sysmgr/CheckUserByName",{

                    name:$("#idUser").val(),
                    page:"login"
                }
            );
        } else {
            $("#userSpan").html("请填写用户名");
        }

    });

    $("#idUser").focus(function () {
        $("#userSpan").html("");
    });

    $("#idPwd").blur(function () {

        if( "" == $("#idPwd").val()){

            $("#pwdSpan").html("请填写密码");
        }
    });

    $("#idPwd").focus(function () {
        $("#pwdSpan").html("");
    });

    $("#btnLogin").click(function () {

        if( "" == $("#idUser").val()){

            $("#userSpan").html("请填写用户名");
            return;
        }
        if( "" == $("#idPwd").val()){

            $("#pwdSpan").html("请填写密码");
            return;
        }


        $.post(
            "/sysmgr/login",{

                name:$("#idUser").val(),
                pwd:$("#idPwd").val()
            },function (data,textStatus) {
                if("success" == data.status){
                    window.location.href = data.msg;
                } else {
                    alert(data.msg);
                }
            },"json"
        );

    });
});