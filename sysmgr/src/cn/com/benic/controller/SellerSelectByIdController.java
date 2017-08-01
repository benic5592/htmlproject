package cn.com.benic.controller;

import cn.com.benic.service.SellerService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/21.
 */
public class SellerSelectByIdController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("text/html;charset=utf-8");
        SellerService ss = new SellerService();

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(ss.getSellerSelectById(request.getParameter("id"))));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
