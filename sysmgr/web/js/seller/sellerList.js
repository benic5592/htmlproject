/**
 * Created by Administrator on 2017/6/16.
 */
$(function () {
    $.ajaxSetup({cache:false});
    var linage = $("#linage").text();

    $("header").load(
        "/sysmgr/html/header.html"
        ,function (data) {
            startTime();
        }
    );


    $("#btnDel").hide();
    $("#pagediv").hide();

    $("label").css("width","70");

    // 添加按钮
    $("#btnCreate").click(function () {

        window.location.href = "/sysmgr/html/sellerEdit.html";
    });

    // 查询按钮
    $("form").submit(function () {
        if(!check()){
            return false;
        }
        $.post(
            "/sysmgr/sellerList",
            getSelectParam("select")
            ,function (data,textstatus) {


                $("#currentPage").text("1");
                showTable(data,"select");



            },"json"
        );

        return false;
    });

    // 首页按钮
    $("#btnHome").click(function () {

        $.post(
            "/sysmgr/sellerList",
            getSelectParam("home")
            ,function (data,textstatus) {
                showTable(data,"home");
            },"json"
        );
    });

    // 上一页按钮
    $("#btnPrev").click(function () {

        $.post(
            "/sysmgr/sellerList",
            getSelectParam("prev")
            ,function (data,textstatus) {

                showTable(data,"prev");
            },"json"
        );
    });

    // 下一页按钮
    $("#btnNext").click(function () {

        $.post(
            "/sysmgr/sellerList",
            getSelectParam("next")
            ,function (data,textstatus) {
                showTable(data,"next");
            },"json"
        );
    });

    // 末页按钮
    $("#btnLast").click(function () {
        $.post(
            "/sysmgr/sellerList",
            getSelectParam("last")
            ,function (data,textstatus) {
                showTable(data,"last");
            },"json"
        );
    });

    // 跳转到按钮
    $("#btnGoto").click(function () {

        var patt1 = new RegExp("^\\d+$","");

        if($("#gotoInput").val().length > 0){

            if(!patt1.test($("#gotoInput").val()) || $("#gotoInput").val()*1 <= 0){
                alert("请输入大于0的数字");
                return ;
            }
            if($("#gotoInput").val()*1 > $("#totalPage").text()*1){

                alert("没有第"+$("#gotoInput").val() +"页");
            }
        } else {
            alert("请填入正确的页数");
            return;
        }

        $.post(
            "/sysmgr/sellerList",
            getSelectParam("goto")
            ,function (data,textstatus) {
                showTable(data,"goto");
            },"json"
        );
    });

    //获取查询条件
    function getSelectParam(type) {
        var param = {
            id:$("#sidId").val(),
            name:$("#snameId").val(),
            tel:$("#stelId").val(),
            type:$("input[type=radio]:checked").val(),
            district:$("#sdistrictId option:selected").val(),
            address:$("#saddressId").val(),
            perFrom:$("#sperFromId").val(),
            perTo:$("#sperToId").val(),
            linage:linage
        }
        if("select" == type){
            sessionStorage.setItem("selectConditions",JSON.stringify(param));
        } else {
            param = JSON.parse(sessionStorage.getItem("selectConditions"));
        }

        var selectPage = $("#currentPage").text();
        var totalPage = $("#totalPage").text();
        switch (type){
            case "select":
                selectPage = "";
                break;
            case "home":
                selectPage = "1";
                break;
            case "last":
                selectPage = totalPage;
                break;
            case "prev":
                if( parseInt(selectPage) > 1){

                    selectPage = parseInt(selectPage) - 1;
                }
                break;
            case "next":
                selectPage = parseInt(selectPage) + 1;
                break;
            case "goto":
                selectPage = $("#gotoInput").val();
                break;

        }
        param.page = selectPage;

        return param;
    }


    // 绘制表格
    function showTable(result,type) {

        if("next" == type && result.data.length <= 0){

            $("#currentPage").text(result.currentPage - 1);
            alert("已经是最后一页!");
            return;
        }

        if("select" == type){

            $("#totalPage").text(result.totalPage);
        }

        $("#btnDel").hide();
        $("#pagediv").hide();


        if(result.data.length > 0){

            // 设置当前页数
            $("#currentPage").text(result.currentPage);
            $("#btnDel").show();

        } else {
            $("#btnDel").hide();
        }


        $("#tbdiv").empty();
        $("#tbdiv").append("<table></table>");

        $("table").append("<tr></tr>").addClass("table table-striped table-bordered");
        $("tr").append("<th><input type='checkbox'></th>" +
            "<th>商家ID</th>" +
            "<th>商家名</th>" +
            "<th>电话</th>" +
            "<th>分类</th>" +
            "<th>地区</th>" +
            "<th>详细地址</th>" +
            "<th>人均(元/人)</th>" +
            "<th></th>");

        $.each(result.data,function (index, seller) {
            $("table").append("<tr></tr>");
            $("tr").eq(index+1).append(
                "<td><input type='checkbox' name='scb'>" +
                "</td>").append("<td>" +seller.id+
                "</td>").append("<td>" +seller.name+
                "</td>").append("<td>" +seller.tel+
                "</td>").append("<td>" +seller.type+
                "</td>").append("<td>" +seller.district+
                "</td>").append("<td>" +seller.address+
                "</td>").append("<td>" +seller.per+
                "</td>").append("<td>" +"<button name='btnEdit'>修改</button>"+
                "</td>");
        });



        $("#pagediv").show();




        // 修改按钮
        $("button[name='btnEdit']").click(function () {
           // alert($(this).parent().parent().find("td").eq(1).text());另一种取id的方法
            var id = $(this).parent().siblings().eq(1).text();
            window.location.href = "/sysmgr/html/sellerEdit.html?id="+id;
        });



        // 全选/全不选
        $("input[type=checkbox]:first").change(function () {

            $("input[type=checkbox]").prop("checked",$(this).prop("checked"));
        });



        // 删除按钮
        $("#btnDel").click(function () {
            if(confirm("确定删除所选商家?")){
                var idArr = [];
                var cbxArr = [];
                //点击确定后操作
                $("input[name='scb']:checked").each(function(i){

                    var $this = $(this);

                    var id = $this.parent().next().text();
                    idArr[i] = id;
                    cbxArr[i] = $this;
                });
                $.post(
                    "/sysmgr/sellerDelete",{
                        id:idArr
                    },function (data,textstatus) {
                        if("success" == data.status){
                            $("input[name='scb']:checked").each(function(i){
                                $(this).parent().parent().remove();
                            });

                        } else {
                            alert(data.msg);
                        }
                    },"json"
                );
            }


        });
    };

    $("#sperFromId").focus(function () {
        $("#sperSpan").html("");
    });

    $("#sperToId").focus(function () {
        $("#sperSpan").html("");
    });

    // 表单验证
    function check() {
        var patt = new RegExp("^\\d+$","");
        if($("#sperFromId").val().length > 0){
            if(!patt.test($("#sperFromId").val()) || $("#sperFromId").val()*1 <= 0){
                $("#sperSpan").html("请输入大于0的数字");
                return false;
            }
        }

        if($("#sperToId").val().length > 0) {
            if (!patt.test($("#sperToId").val()) || $("#sperToId").val() * 1 <= 0) {
                $("#sperSpan").html("请输入大于0的数字");
                return false;
            }
        }
        if($("#sperFromId").val()*1 > $("#sperToId").val()*1){
            $("#sperSpan").html("请输入正确的区间");
            return false;
        }
        return true;
    }



});