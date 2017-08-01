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
                    page:"regist"
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

    $("#idPwdAgain").focus(function () {
        $("#pwdAgainSpan").html("");
    });

    $("#idPwdAgain").on("input",function () {
        if($("#idPwd").val() == $("#idPwdAgain").val()){

            $("#pwdAgainSpan").html("两次密码输入一致").css("color","green");
        } else {

            $("#pwdAgainSpan").html("两次密码不一致").css("color","red");
        }
    });

    $("#btnRegist").click(function () {

        if( "" == $("#idUser").val()){

            $("#userSpan").html("请填写用户名");
            return;
        }

        var patt = new RegExp("^[a-zA-Z]([a-zA-Z0-9]{0,19})$","");
        if(!patt.test($("#idUser").val())){
            $("#userSpan").html("用户名必须英文数字组合,且第一位不能是数字,最长20位");
            return;
        }


        if( "" == $("#idPwd").val()){

            $("#pwdSpan").html("请填写密码");
            return;
        }


        patt.compile("^\\d+$","");
        if(patt.test($("#idPwd").val())){
            $("#pwdSpan").html("密码不能纯数字,最长8位");
            return;
        }



        if($("#idPwd").val() != $("#idPwdAgain").val()){

            $("#pwdAgainSpan").html("两次密码不一致").css("color","red");
            return;
        }




        $.post(
            "/sysmgr/regist",{

                name:$("#idUser").val(),
                pwd:$("#idPwd").val()
            },function (data,textStatus) {
                if("success" == data.status){
                    alert("注册成功");
                    window.location.href = data.msg;
                } else {
                    alert(data.msg);
                }
            },"json"
        );

    });
});