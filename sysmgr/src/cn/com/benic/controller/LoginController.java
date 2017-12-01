package cn.com.benic.controller;

import cn.com.benic.model.ResultModel;
import cn.com.benic.model.UserModel;
import cn.com.benic.service.UserService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/21.
 */
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("text/html;charset=utf-8");
        UserService us = new UserService();
        UserModel userModel = new UserModel();
        userModel.setName(request.getParameter("name"));
        userModel.setPwd(request.getParameter("pwd"));
        boolean result = us.checkUserByNamePwd(userModel);

        Gson gson = new Gson();
        ResultModel resultModel = new ResultModel();

        if(result){
            resultModel.setStatus("success");
            resultModel.setMsg("/sysmgr/html/sellerList.html");
        } else {
            resultModel.setStatus("error");
            resultModel.setMsg("用户名/密码错误");

        }
        response.getWriter().write(gson.toJson(resultModel));

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
