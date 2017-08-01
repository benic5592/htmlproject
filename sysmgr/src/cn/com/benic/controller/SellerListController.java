package cn.com.benic.controller;

import cn.com.benic.model.PageModel;
import cn.com.benic.model.SellerModel;
import cn.com.benic.service.SellerService;
import cn.com.benic.util.CommonUtil;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/21.
 */
public class SellerListController extends HttpServlet {
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
        sellerModel.setPerFrom(request.getParameter("perFrom"));
        sellerModel.setPerTo(request.getParameter("perTo"));

        String page = request.getParameter("page");
        String linage = request.getParameter("linage");

        String totalPage = "";
        if(CommonUtil.strIsEmpty(page)){

            page = "1";
            totalPage = ss.getSellerTotalCount(sellerModel,linage)+"";
        }

        String currentPage = page;

        Gson gson = new Gson();
        PageModel resultModel = new PageModel();
        resultModel.setStatus("success");
        resultModel.setData(ss.getSellerList(sellerModel,page,linage));

        resultModel.setCurrentPage(currentPage);
        resultModel.setTotalPage(totalPage);


        response.getWriter().write(gson.toJson(resultModel));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
