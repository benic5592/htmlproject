package cn.com.benic.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/7.
 */
public class Filter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {


        req.setCharacterEncoding("UTF-8");
        chain.doFilter(req,resp);

        resp.setCharacterEncoding("utf-8");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
