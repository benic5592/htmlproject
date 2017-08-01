package cn.com.benic.model;

/**
 * Created by Administrator on 2017/6/28.
 */
public class PageModel extends ResultModel {

    private String currentPage;
    private String totalPage;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }
}
