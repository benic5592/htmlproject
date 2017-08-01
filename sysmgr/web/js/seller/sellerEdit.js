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

    $("#idDiv").hide();
    $("label").css("width","70");


    if($.query.get("id").length != 0){
        $.post(
            "/sysmgr/sellerSelectById",{
                id:$.query.get("id")
            },function (data,textstatus) {

                $("#idDiv").show();
                $("#idSpan").text(data.id);
                $("#snameId").val(data.name);
                $("#stelId").val(data.tel);
                $("input[type=radio][value='"+data.type+"']").prop("checked",true);
                $("#sdistrictId").val(data.district);
                $("#saddressId").val(data.address);
                $("#sperId").val(data.per);

            },"json"

        );
    }

    $("#sperId").focus(function () {
        $("#sperSpan").html("");
    });



    $("form").submit(function () {

        var patt = new RegExp("^\\d+$","");
        if(!patt.test($("#sperId").val()) || $("#sperId").val()*1 <= 0){
            $("#sperSpan").html("请输入大于0的数字");
            return false;
        }

        $.post(
            "/sysmgr/sellerEdit",{


                id:$("#idSpan").text(),
                name:$("#snameId").val(),
                tel:$("#stelId").val(),
                type:$("input[type=radio]:checked").next().text(),
                district:$("#sdistrictId option:selected").text(),
                address:$("#saddressId").val(),
                per:$("#sperId").val(),



            },function (data,textstatus) {
                if("success" == data.status){
                    alert("保存成功");
                    window.location.href = data.msg;
                } else {
                    alert(data.msg);
                }
            },"json"
        );


        return false;
    });

});