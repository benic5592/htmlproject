package cn.com.benic.controller;

import cn.com.benic.model.ResultModel;
import cn.com.benic.model.SellerModel;
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
public class SellerEditController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("text/html;charset=utf-8");
        SellerService ss = new SellerService();
        SellerModel sellerModel = new SellerModel();
        sellerModel.setId(request.getParameter("id"));
        sellerModel.setName(request.getParameter("name"));
        sellerModel.setTel(request.getParameter("tel"));
        sellerModel.setType(request.getParameter("type"));
        sellerModel.setDistrict(request.getParameter("district"));
        sellerModel.setAddress(request.getParameter("address"));
        sellerModel.setPer(request.getParameter("per"));
        boolean result = ss.editSeller(sellerModel);

        Gson gson = new Gson();
        ResultModel resultModel = new ResultModel();

        if(result){
            resultModel.setStatus("success");
            resultModel.setMsg("/sysmgr/html/sellerList.html");
        } else {
            resultModel.setStatus("error");
            resultModel.setMsg("保存失败");

        }
        response.getWriter().write(gson.toJson(resultModel));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
