package cn.com.benic.controller;

import cn.com.benic.model.ResultModel;
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
public class SellerDeleteController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("text/html;charset=utf-8");
        SellerService ss = new SellerService();

        boolean result = ss.deleteSeller(request.getParameter("id"));

        Gson gson = new Gson();
        ResultModel resultModel = new ResultModel();

        if(result){
            resultModel.setStatus("success");
        } else {
            resultModel.setStatus("error");
            resultModel.setMsg("删除失败");

        }
        response.getWriter().write(gson.toJson(resultModel));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
